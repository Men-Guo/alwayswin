package com.example.alwayswin.controller;

import ch.qos.logback.classic.Logger;
import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @RequestMapping(value = "/uid", method = RequestMethod.GET)
    public List<WishList> getByUid(Integer uid){
        List<WishList> wishLists = wishListService.queryWishList(uid);
        return wishLists;
    }

    @RequestMapping(value ="/wid", method = RequestMethod.GET)
    public WishList getByWid(Integer wid){
        return wishListService.queryWishListByWid(wid);
    }

    @RequestMapping(value ="/delete", method = RequestMethod.GET)
    public int deleteWishList(Integer uid,Integer pid){
        int res = wishListService.deleteWishList(uid,pid);
        if (res == 1){
            System.out.println("操作成功");
        }else{
            System.out.println("操作失败");
        }
        return res;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int insertWishList(WishList wishList){
        int res = wishListService.addWishList(wishList);
        if (res == 1){
            System.out.println("操作成功");
        }else{
            System.out.println("操作失败");
        }
        return res;
    }

}
