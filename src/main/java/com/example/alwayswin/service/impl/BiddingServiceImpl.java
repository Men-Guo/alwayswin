package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Bidding;
import com.example.alwayswin.entity.Product;
import com.example.alwayswin.entity.ProductStatus;
import com.example.alwayswin.mapper.BiddingMapper;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.service.BiddingService;
import com.example.alwayswin.utils.enums.ProductStatusCode;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BiddingServiceImpl
 * @Description:
 * @Author: SQ
 * @Date: 2021-5-7
 */

// todo: 随着时间推移，product的状态也会发生改变
//  mq还没登场……
@Service
public class BiddingServiceImpl implements BiddingService {

    private static Logger logger = LoggerFactory.getLogger(BiddingServiceImpl.class);

    @Resource
    private BiddingMapper biddingMapper;

    @Resource
    private ProductMapper productMapper;

    public BiddingServiceImpl(BiddingMapper biddingMapper, ProductMapper productMapper) {
        this.biddingMapper = biddingMapper;
        this.productMapper = productMapper;
    }

    @Override
    public Bidding getByBid(int bid) {
        return  biddingMapper.getByBid(bid);
    }

    @Override
    public List<Bidding> getBidsByUid(int uid) {
        return biddingMapper.getByUid(uid);
    }

    @Override
    public List<Bidding> getBidsByPid(int pid) {
        return biddingMapper.getByPid(pid);
    }

    @Override
    public int addBid(Map param) {
        int res = 0;
        Bidding bidding = new Bidding();
        try {
            // create new bid
            BeanUtils.populate(bidding, param);

            // 获取product status
            int pid = bidding.getPid();
            ProductStatus productStatus = productMapper.getProductStatusByPid(pid);

            // 检查product status是否在拍卖状态
            if (!ProductStatusCode.isBidding(productStatus.getStatus()))
                return -1;

            // 检查ddl
            Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
            if (productStatus.getEndTime().before(currentTimeStamp))
                return -2;

            // 获取product info
            Product product = productMapper.getByPid(pid);

            // 你不能竞标自己的拍品
            if (bidding.getUid().equals(product.getUid()))
                return -3;

            // 检查出价和current price + 最小加价
            if(bidding.getOffer() < productStatus.getPrice() + product.getMinIncrement())
                return -4;

            // 到这一步时，已经保证该商品处于拍卖状态，当前时间没超过ddl，出价有效
            // 新建bid 成功，写入db
            bidding.setCreateTime(currentTimeStamp);
            res = biddingMapper.add(bidding);

            // 修改product status 的price
            productStatus.setPrice(bidding.getOffer());

            // 出价高于autowin，竞拍成功
            if (bidding.getOffer() >= product.getAutoWinPrice()) {
                productStatus.setStatus(ProductStatusCode.SUCCESS.getStatus());
            }
            // 出价 >= current price + min_increment && 出价 < autowin
            // status == bidding
            else if (productStatus.getStatus().equals(ProductStatusCode.BIDDING.getStatus())) {
                // 不是last 1 min，不用更新状态
                // 是last 1 min
                if (productStatus.getEndTime().getTime() <= currentTimeStamp.getTime() + 60 * 1000)
                {
                    productStatus.setStatus(ProductStatusCode.EXTENDED_1.getStatus());  // bidding -> extended1
                    productStatus.setEndTime(new Timestamp(
                            productStatus.getEndTime().getTime() + 10 * 60 * 1000));  // extend ddl to 10 min later
                }
            }
            // status == extended1
            else if (productStatus.getStatus().equals(ProductStatusCode.EXTENDED_1.getStatus())) {
                if (productStatus.getEndTime().getTime() <= currentTimeStamp.getTime() + 60 * 1000) {
                    productStatus.setStatus(ProductStatusCode.EXTENDED_2.getStatus());  // extended1 -> extended2
                    productStatus.setEndTime(new Timestamp(
                            productStatus.getEndTime().getTime() + 5 * 60 * 1000));  // extend ddl to 5 min later
                }
            }
            // status == extended2
            else if (productStatus.getStatus().equals(ProductStatusCode.EXTENDED_2.getStatus())) {
                if (productStatus.getEndTime().getTime() <= currentTimeStamp.getTime() + 60 * 1000) {
                    productStatus.setStatus(ProductStatusCode.EXTENDED_3.getStatus());  // extended2 -> extended3
                    productStatus.setEndTime(new Timestamp(
                            productStatus.getEndTime().getTime() + 1 * 60 * 1000));  // extend ddl to 1 min later
                }
            }
            else {  // status == extended3
                if(bidding.getOffer() >= product.getReservedPrice())  // 大于保留价
                    productStatus.setStatus(ProductStatusCode.SUCCESS.getStatus());  // 即拍即中
            }

            // 将product status的更新写入db
            res = productMapper.updateProductStatus(productStatus);

            // 拍到了就新建一份order
            if (productStatus.getStatus().equals(ProductStatusCode.SUCCESS.getStatus())) {
                return 2;
            }

        }catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        return res;
    }
}
