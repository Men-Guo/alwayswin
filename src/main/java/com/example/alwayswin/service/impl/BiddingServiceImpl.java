package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.Bidding;
import com.example.alwayswin.mapper.BiddingMapper;
import com.example.alwayswin.service.BiddingService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class BiddingServiceImpl implements BiddingService {

    private static Logger logger = LoggerFactory.getLogger(BiddingServiceImpl.class);

    @Resource
    private BiddingMapper biddingMapper;

    public BiddingServiceImpl(BiddingMapper biddingMapper) {
        this.biddingMapper = biddingMapper;
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

    // todo: there are so many things........
    @Override
    public int addBid(Map param) {
        Bidding bidding = new Bidding();
        try {
            BeanUtils.populate(bidding, param);
            bidding.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        return biddingMapper.add(bidding);
    }
}
