package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.mapper.ProductMapper;
import com.example.alwayswin.mapper.UserMapper;
import com.example.alwayswin.mapper.WishListMapper;
import com.example.alwayswin.service.WishListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * WishList实现类
 */
@Service
public class WishListServiceImpl implements WishListService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    //引用DAO
    @Autowired
    private WishListMapper wishListMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer addWishList(WishList wishList){
        if (checkDuplicate(wishList.getUid(),wishList.getPid()) != 0)
        {
            logger.debug("Duplicated pid and uid");
            return 0;
        }
        if (null == productMapper.getByPid(wishList.getPid())){
            logger.debug("No Such Pid");
            return 0;
        }
        if (null == userMapper.getByUid(wishList.getUid())){
            logger.debug("No such Uid");
            return 0;
        }
        wishList.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return wishListMapper.insertWishList(wishList);
    }

    @Override
    public List<WishList> queryWishList(Integer uid) {
        List<WishList> wishLists = new ArrayList<>();
        if (null==userMapper.getByUid(uid)){
            logger.debug("User is not exist");
            return wishLists;
        }
        wishLists = wishListMapper.getByUid(uid);
        for (int i=0; i<wishLists.size();i++){
            WishList wishList= wishLists.get(i);
            wishList.setProductPreview(productMapper.getProductPreviewByPid(wishList.getPid()));
            wishLists.set(i,wishList);
        }
        return wishLists;
    }

    @Override
    public WishList queryWishListByWid(Integer wid) {
        WishList wishList = wishListMapper.selectWid(wid);
        if (null == wishList) return null;
        wishList.setProductPreview(productMapper.getProductPreviewByPid(wishList.getPid()));
        return wishList;
    }

    @Override
    public int deleteWishList(Integer uid, Integer pid) {
        int res =  wishListMapper.deleteWishList(uid,pid);
        if (res == 0){
            logger.debug("Delete failure.");
        }
        return res;
    }

    @Override
    public int deleteWishList(Integer wid) {
        int res = wishListMapper.deleteWishListByWid(wid);
        if (res ==0) logger.debug("Delete failure.");
        return res;
    }

    @Override
    public int checkDuplicate(Integer uid, Integer pid) {
        return wishListMapper.checkDuplicate(pid, uid);
    }
}
