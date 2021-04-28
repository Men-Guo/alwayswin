package com.example.alwayswin.controller;
import com.example.alwayswin.common.api.CommonResult;
import com.example.alwayswin.entity.WishList;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestTemplateWishListController {

    @Value("http://localhost:8080")
    String HOST_ADMIN;

    @RequestMapping(value = "/wishList/getByUid/{uid}", method = RequestMethod.GET)
    @ResponseBody
    public Object getForEntity(@PathVariable("uid") Integer uid){
        String url = HOST_ADMIN + "/wishList/listAll/{uid}";
        ResponseEntity<CommonResult> responseEntity = new RestTemplate().getForEntity(url,CommonResult.class,uid);
        return responseEntity.getBody();
    }

    @RequestMapping(value = "/wishList/getByWid/{wid}", method = RequestMethod.GET)
    @ResponseBody
    public Object getForEntity2(@PathVariable("wid") Integer wid){
        String url = HOST_ADMIN + "/wishList/wid/{wid}";
        ResponseEntity<CommonResult> responseEntity = new RestTemplate().getForEntity(url,CommonResult.class,wid);
        return responseEntity.getBody();
    }

    //只用一个delete于WishListController
/*    @RequestMapping(value = "/wishList/delete/{uid}&{pid}",method = RequestMethod.GET)
    @ResponseBody
    public Object deleteWishList(@PathVariable("uid") Integer uid, @PathVariable("pid") Integer pid){
        String url = HOST_ADMIN + "/wishList/delete/uid/{uid}/pid/{pid}";
        Map<String,Object> params = new HashMap<>();
        params.put("uid",uid);
        params.put("pid",pid);
        ResponseEntity<CommonResult> responseEntity = new RestTemplate().getForEntity(url,CommonResult.class,params);
        return responseEntity.getBody();
    }*/



    @RequestMapping(value = "/wishList/post", method = RequestMethod.POST)
    public Object postForWishListEntity(Map<String,String> params) throws ParseException {
        String url = HOST_ADMIN + "/wishList/create";
        WishList wishlist = new WishList();
        wishlist.setWid(Integer.parseInt(params.get("wid")));
        wishlist.setPid(Integer.parseInt(params.get("pid")));
        wishlist.setUid(Integer.parseInt(params.get("uid")));
        wishlist.setCreateTime(Timestamp.valueOf(params.get("createTime")));
        ResponseEntity<CommonResult> responseEntity = new RestTemplate().getForEntity(url, CommonResult.class, wishlist);
        return responseEntity.getBody();
    }
}
