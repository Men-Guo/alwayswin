package com.example.alwayswin;

import com.example.alwayswin.common.api.CommonResult;
import com.example.alwayswin.mapper.WishListMapper;
import com.example.alwayswin.service.WishListService;
import com.example.alwayswin.service.impl.WishListServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class WishListServiceTest {

    private RestTemplate restTemplate;

    private WishListServiceImpl wishListService;

    @Mock
    private WishListMapper wishListMapper;

    @BeforeEach
    public void init() {
        wishListMapper = mock(WishListMapper.class);
        wishListService.setWishListMapper(wishListMapper);
    }

/*    @Test
    public void happy_path_with_wish_list(){
        String url = "http://127.0.0.1:8080/wishlist/wid/{wid}";
        ResponseEntity<CommonResult> responseEntity = restTemplate.getForEntity(url,CommonResult.class,2);
        System.out.println(responseEntity);
    }*/
}
