package com.example.alwayswin.controller;

import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.service.WishListService;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    /**
     * 查询某个uid的list 分页
     */
    @GetMapping(value = "/wishList/{uid}")
    public CommonResult<List<WishList>> getWishListsByUid(@PathVariable("uid") Integer uid,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer pageSize){
        return CommonResult.success(wishListService.queryWishListPage(uid,page,pageSize));
        //List<WishList> wishLists = wishListService.queryWishList(uid);

    }

    /**
     * 根据wid查询wishlist
     */
    @GetMapping(value ="/wishList/wid/{wid}")
    public CommonResult<WishList> getByWid(@PathVariable("wid") Integer wid){
        WishList wishList = wishListService.queryWishListByWid(wid);
        if (wishList == null) return CommonResult.failure();
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
