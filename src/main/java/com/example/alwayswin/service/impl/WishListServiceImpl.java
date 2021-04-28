package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.mapper.WishListMapper;
import com.example.alwayswin.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * WishList实现类
 */
@Service
public class WishListServiceImpl implements WishListService {

    //引用DAO
    @Autowired
    private WishListMapper wishListMapper;


    public void setWishListMapper(WishListMapper wishListMapper) {
        this.wishListMapper = wishListMapper;
    }

    @Override
    public Integer addWishList(WishList wishList) {
        if (wishListMapper.checkDuplicate(wishList.getPid(),wishList.getUid()) > 0)
            return 0;
        return wishListMapper.insertWishList(wishList);
    }

    @Override
    public List<WishList> queryWishList(Integer uid) {
        List<WishList> wishLists = wishListMapper.getByUid(uid);
        return wishLists;
    }

    @Override
    public WishList queryWishListByWid(Integer wid) {
        WishList wishlist = wishListMapper.selectWid(wid);
        return wishlist;
    }

    @Override
    public int deleteWishList(Integer uid, Integer pid) {
        if (wishListMapper.checkDuplicate(pid,uid)==0) {
            System.out.println("商品不存在");
            return 0;
        }
        return wishListMapper.deleteWishList(uid,pid);
    }
}
