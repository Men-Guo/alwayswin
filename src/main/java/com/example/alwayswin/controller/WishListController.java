package com.example.alwayswin.controller;

import com.example.alwayswin.utils.commonAPI.CommonResult;
import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.service.WishListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    /**
     * 查询某个uid的全部wishlist
     */
    @RequestMapping(value = "/wishList/{uid}", method = RequestMethod.GET)
    public CommonResult<List<WishList>> getWishListsByUid(@PathVariable("uid") Integer uid){
        List<WishList> wishLists = wishListService.queryWishList(uid);
        if (wishLists.size()==0) return CommonResult.unauthorized();
        return CommonResult.success(wishLists);
    }

    /**
     * 根据wid查询wishlist
     */
    @RequestMapping(value ="/wishList/wid/{wid}", method = RequestMethod.GET)
    public CommonResult<WishList> getByWid(@PathVariable("wid") Integer wid){
        WishList wishList = wishListService.queryWishListByWid(wid);
        if (wishList == null) return CommonResult.unauthorized();
        return CommonResult.success(wishList);
    }

    /**
     * 根据UID和PID删除数据
     */
    @DeleteMapping(value ="/wishList/{uid}/{pid}")
    public CommonResult<Object> deleteWishListByUidPid(@PathVariable("uid") Integer uid, @PathVariable("pid") Integer pid){
        int res = wishListService.deleteWishList(uid,pid);
        if (res == 1){
            return CommonResult.success(null);
        }else {
            return CommonResult.failure();
        }
    }

    /**
     * 创建wishList
     */
    @PostMapping(value = "/wishList/create")
    public CommonResult<Object> insertWishList(@RequestBody WishList wishList){
        int res = wishListService.addWishList(wishList);
        if (res == 1){
            return CommonResult.success(null);
        }else {
            return CommonResult.failure();
        }
    }
}
