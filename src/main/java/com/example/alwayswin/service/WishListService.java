package com.example.alwayswin.service;

import com.example.alwayswin.entity.WishList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishListService{
    Integer addWishList(WishList wishList);
    WishList queryWishListByWid(Integer wid);
    int deleteWishList(Integer uid, Integer pid);
    int deleteWishList(Integer wid);
    List<WishList> queryWishList(Integer uid);
    List<WishList> queryWishListPage(Integer uid, int pageNum, int pageSize);
    int checkDuplicate(Integer uid, Integer pid);
}
