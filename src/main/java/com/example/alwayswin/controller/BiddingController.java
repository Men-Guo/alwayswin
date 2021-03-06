package com.example.alwayswin.controller;

import com.example.alwayswin.entity.Bidding;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.BiddingService;
import com.example.alwayswin.service.OrderService;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BiddingController
 * @Description:
 * @Author: SQ
 * @Date: 2021-5-7
 */

@RestController
public class BiddingController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private BiddingService biddingService;
    @Resource
    private OrderService orderService;

    public BiddingController(BiddingService biddingService) {
        this.biddingService = biddingService;
    }


    @ResponseBody
    @GetMapping("/product/bids/{bid}")
    public CommonResult<Bidding> getByBid(@PathVariable int bid){
        Bidding bidding = biddingService.getByBid(bid);
        if (bidding == null) {
            return CommonResult.validateFailure();
        }
        return CommonResult.success(bidding);
    }

    @ResponseBody
    @GetMapping("/user/bids")
    public CommonResult<PageInfo<Bidding>> getByUid(@RequestHeader("Authorization") String authHeader,
                                                    @RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                                    @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize){

        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.parseInt(claims.getAudience());
            PageHelper.startPage(page,pageSize);
            List<Bidding> biddingList = biddingService.getBidsByUid(uid);
            if (biddingList == null) {
                return CommonResult.failure();
            }
            else {
                PageInfo<Bidding> pageInfo = new PageInfo<>(biddingList);
                return CommonResult.success(pageInfo);
            }
        }
    }

    @ResponseBody
    @GetMapping("/product/{pid}/bids")
    public CommonResult<List<Bidding>> getByPid(@PathVariable int pid,
                                                @RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                                @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Bidding> biddingList = biddingService.getBidsByPid(pid);
        if (biddingList == null) {
            return CommonResult.failure();
        } else {
            return CommonResult.success(biddingList);
        }
    }

    @ResponseBody
    @PostMapping("/product/bids/create")
    public CommonResult<Object> addBidding(@RequestHeader("Authorization") String authHeader,
                                   @RequestBody Map param) {
        int res = biddingService.addBid(param);
        if (res < 0) {
            if (res == -1)
                return CommonResult.validateFailure("This product is not in auction!");
            else if (res == -2)
                return CommonResult.validateFailure("This auction has ended!");
            else if (res == -3)
                return CommonResult.validateFailure("You can't bid for your own product!");
            else // res == -4
                return CommonResult.failure("You need to offer a higher bid");
        }
        else if (res > 0){
            logger.info("Add bid successfully");
            if (res == 2) {  // ???????????????????????????
                res = orderService.addOrder(param);  // param ????????????uid, pid, offer
                logger.info("Create order successfully");
            }
            return CommonResult.success(res);
        }
        else {
            logger.debug("Add bid failed");
            return CommonResult.failure();
        }
    }
}
