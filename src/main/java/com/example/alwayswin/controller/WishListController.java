package com.example.alwayswin.controller;

import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @RequestMapping(value = "/uid")
    public List<WishList> getByUid(Integer uid){
        return wishListService.queryWishList(uid);
    }

    @RequestMapping(value ="/wid")
    public WishList getByWid(Integer wid){
        return wishListService.queryWishListByWid(wid);
    }
}
