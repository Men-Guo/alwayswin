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
    public int addWishList(WishList wishList) {
        int nums = wishListMapper.insertWishList(wishList);
        return nums;
    }

    @Override
    public List<WishList> queryWishList(Integer uid) {
        return wishListMapper.getByUid(uid);
    }

    @Override
    public WishList queryWishListByWid(Integer wid) {
        WishList wishlist = wishListMapper.selectWid(wid);
        return wishlist;
    }
}
