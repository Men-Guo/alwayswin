package com.example.alwayswin.service;

import com.example.alwayswin.entity.WishList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishListService{
    Integer addWishList(WishList wishList);
    List<WishList> queryWishList(Integer uid);
    WishList queryWishListByWid(Integer wid);
    int deleteWishList(Integer uid, Integer pid);
    List<WishList> queryWishListPage(Integer uid, int page, int pageSize);
}
