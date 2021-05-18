package com.example.alwayswin.controller;

import com.example.alwayswin.entity.Address;
import com.example.alwayswin.security.JwtUtils;
import com.example.alwayswin.service.AddressService;
import com.example.alwayswin.utils.commonAPI.CommonResult;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.Claims;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AddressController
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-22
 */

@RestController
public class AddressController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @ResponseBody
    @GetMapping("/user/address/{aid}")
    public CommonResult<Address> getByAid(@PathVariable int aid){
        Address address = addressService.getAddressByAid(aid);
        if (address == null) {
            return CommonResult.validateFailure();
        }
        return CommonResult.success(address);
    }

    @ResponseBody
    @GetMapping("/user/address")
    public CommonResult<List<Address>> getByUid(@RequestHeader("Authorization") String authHeader,
                                                @RequestParam(value = "page",required = false, defaultValue = "1") Integer page,
                                                @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize){

        Claims claims = JwtUtils.getClaimFromToken(JwtUtils.getTokenFromHeader(authHeader));
        if (claims == null)
            return CommonResult.unauthorized();
        else {
            int uid = Integer.parseInt(claims.getAudience());
            PageHelper.startPage(page,pageSize);
            List<Address> addressList = addressService.getAllAddresses(uid);
            if (addressList == null) {
                return CommonResult.failure();
            }
            else {
                return CommonResult.success(addressList);
            }
        }
    }

    @ResponseBody
    @PostMapping("/user/address/create")
    public CommonResult<Object> addAddress(@RequestBody Map param) {
        int res = addressService.addAddress(param);
        if (res == 1) {
            logger.info("Add address successfully");
            return CommonResult.success(res);
        } else {
            logger.debug("Add address failed");
            return CommonResult.failure();
        }
    }

    @ResponseBody
    @PutMapping("/user/address/update/{aid}")
    public CommonResult<Object> updateAddress(@RequestBody Map param, @PathVariable int aid){
        int res = addressService.updateAddress(aid, param);
        if (res == 1) {
            logger.info("Edit address successfully");
            return CommonResult.success(res);
        }
        else {
            logger.debug("Edit address failed");
            return CommonResult.failure();
        }
    }

    @ResponseBody
    @DeleteMapping("/user/address/delete/{aid}")
    public CommonResult<Object> deleteAddress(@PathVariable int aid){
        int res = addressService.deleteAddress(aid);
        if (res == 1) {
            logger.info("Delete address successfully");
            return CommonResult.success(res);
        }
        else {
            logger.debug("Delete address failed");
            return CommonResult.failure();
        }
    }
}
