package com.example.alwayswin.service;

import com.example.alwayswin.entity.WishList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishListService{
    int addWishList(WishList wishList);
    List<WishList> queryWishList(Integer uid);
    WishList queryWishListByWid(Integer wid);
}