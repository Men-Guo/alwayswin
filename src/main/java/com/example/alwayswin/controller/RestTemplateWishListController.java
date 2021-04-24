package com.example.alwayswin.controller;
import com.example.alwayswin.common.api.CommonResult;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
        System.out.println(responseEntity);
        return responseEntity.getBody();
    }




/*    @RequestMapping(value = "/postwishlist", method = RequestMethod.POST)
    public Object postForWishListEntity(@RequestParam String name){
        String url = "/wishListAttribute/create";
        //HttpHeaders 是一个Map类, MultiValueMap<String,String>
        //MultivalueMap<String,String>是一个Map 延伸于 Map<k,List<V>>

        HttpHeaders headers = new HttpHeaders();
        //用来调整MediaType 接收前端传递过来的类型, 这里接收的是APPLICATION_FORM_URLENCODEDa
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //一个Map<k,List<v>> 用MultiValueMap可以一个key多个值
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("name",name);
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(params,headers);
        *//*ResponseEntity<CommonResult> responseEntity = *//*
    }*/
}
