package com.example.alwayswin.controller;

import ch.qos.logback.classic.Logger;
import com.example.alwayswin.common.api.CommonResult;
import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
public class WishListController {

    @Autowired
    private WishListService wishListService;


    @RequestMapping(value = "/wishList/listAll/{uid}", method = RequestMethod.GET)
    public CommonResult<List<WishList>> getWishListsByUid(@PathVariable("uid") Integer uid){
        return CommonResult.success(wishListService.queryWishList(uid));
    }

    @RequestMapping(value ="/wishList/wid/{wid}", method = RequestMethod.GET)
    public CommonResult<WishList> getByWid(@PathVariable("wid") Integer wid){
        return CommonResult.success(wishListService.queryWishListByWid(wid));
    }

    @RequestMapping(value ="/wishlist/delete/{uid}&{pid}", method = RequestMethod.GET)
    public int deleteWishList(@PathVariable("uid") Integer uid,@PathVariable("pid") Integer pid){
        int res = wishListService.deleteWishList(uid,pid);
        if (res == 1){
            System.out.println("操作成功");
        }else{
            System.out.println("操作失败");
        }
        return res;
    }

    @RequestMapping(value = "/wishlist/create", method = RequestMethod.POST)
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
