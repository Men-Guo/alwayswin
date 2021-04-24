package com.example.alwayswin.controller;

import com.example.alwayswin.common.api.CommonResult;
import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "/wishList/listAll/{uid}", method = RequestMethod.GET)
    public CommonResult<List<WishList>> getWishListsByUid(@PathVariable("uid") Integer uid){
        List<WishList> wishLists = wishListService.queryWishList(uid);
        if (wishLists.size()==0) return CommonResult.unauthorized();
        return CommonResult.success(wishLists);
    }

    /**
     * 根据wid查询wishlist
     * @param wid
     * @return
     */
    @RequestMapping(value ="/wishList/wid/{wid}", method = RequestMethod.GET)
    public CommonResult<WishList> getByWid(@PathVariable("wid") Integer wid){
        WishList wishList = wishListService.queryWishListByWid(wid);
        if (wishList == null) return CommonResult.unauthorized();
        return CommonResult.success(wishList);
    }

    /**
     * 根据UID和PID删除数据
     * @param uid
     * @param pid
     * @return
     */
    @RequestMapping(value ="/wishList/delete/uid/{uid}/pid/{pid}", method = RequestMethod.GET)
    public CommonResult<Object> deleteWishList(@PathVariable("uid") Integer uid, @PathVariable("pid") Integer pid){
        int res = wishListService.deleteWishList(uid,pid);
        if (res == 1){
            return CommonResult.success(null);
        }else {
            return CommonResult.failure();
        }
    }

    @RequestMapping(value = "/wishlist/create", method = RequestMethod.POST)
    public CommonResult insertWishList(WishList wishList){
        int res = wishListService.addWishList(wishList);
        if (res == 1){
            return CommonResult.success(null);
        }else {
            return CommonResult.failure();
        }
    }
}
