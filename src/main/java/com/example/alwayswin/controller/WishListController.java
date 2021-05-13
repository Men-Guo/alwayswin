package com.example.alwayswin.controller;

import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.WishListService;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class WishListController {

    @Resource
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    /**
     * 【分页】查询某个uid的list

    @ResponseBody
    @GetMapping(value = "/my-wishlist")
    public CommonResult<PageInfo> getWishListsByUid(@RequestHeader("Authorization") String authHeader,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();

        int uid = Integer.valueOf(claims.getAudience());
        PageInfo pageInfo= PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> wishListService.queryWishList(uid));
        return CommonResult.success(pageInfo);
    }
    */

    
    /**
     * 查询某个uid的list
     */

    @GetMapping(value = "/my-wishlist")
    public CommonResult<List<WishList>> getWishListsByUid(@RequestHeader("Authorization") String authHeader) {
        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();

        int uid = Integer.parseInt(claims.getAudience());
        List<WishList> wishLists = wishListService.queryWishList(uid);
        if (wishLists == null)
            return CommonResult.failure();
        return CommonResult.success(wishLists);
    }

    /**
     * 根据wid查询wishlist
     */
    @ResponseBody
    @GetMapping(value ="/wishlist/wid/{wid}")
    public CommonResult<WishList> getByWid(@PathVariable("wid") Integer wid){
        WishList wishList = wishListService.queryWishListByWid(wid);
        if (wishList == null) return CommonResult.failure();
        return CommonResult.success(wishList);
    }

    /**
     * 根据UID和PID查询该商品是否已经被收藏
     */
    @ResponseBody
    @PostMapping(value ="/wishlist/{uid}/{pid}")
    public CommonResult getWishListByUidPid(@PathVariable("uid") Integer uid, @PathVariable("pid") Integer pid){
        int num = wishListService.checkDuplicate(uid, pid);
        if (num ==0) return CommonResult.success(null);
        return CommonResult.failure("The item already in the wishList");
    }

    /**
     * 根据UID和PID删除数据
     */
    @Deprecated
    @DeleteMapping(value ="/wishlist/{uid}/{pid}")
    public CommonResult deleteWishListByUidPid(@PathVariable("uid") Integer uid, @PathVariable("pid") Integer pid){
        int res = wishListService.deleteWishList(uid,pid);
        if (res == 1){
            return CommonResult.success(null);
        }else {
            return CommonResult.failure();
        }
    }

    @ResponseBody
    @DeleteMapping(value ="/wishlist/delete/{wid}")
    public CommonResult deleteWishListByWid(@PathVariable("wid") Integer wid) {
        int res = wishListService.deleteWishList(wid);
        if (res == 1){
            return CommonResult.success(null);
        }else {
            return CommonResult.failure();
        }
    }

    /**
     * 创建wishList
     */
    @ResponseBody
    @PostMapping(value = "/wishlist/create")
    public CommonResult insertWishList(@RequestBody WishList wishList){
        int res = wishListService.addWishList(wishList);
        if (res == 1){
            return CommonResult.success(null);
        }
        else if (res == 0){
            return CommonResult.validateFailure("This product is already in wishList");
        }else {
            return CommonResult.failure();
        }
    }
}
