package com.example.alwayswin;

import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.mapper.WishListMapper;
import com.example.alwayswin.service.impl.WishListServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WishListServiceTest {

    private RestTemplate restTemplate;

    @Autowired
    private WishListServiceImpl wishListService = new WishListServiceImpl();

    @Autowired
    private WishListMapper wishListMapper;

    @Test
    public void happy_path_with_wish_list(){
        WishList wishList = new WishList();
        wishList.setUid(1);
        wishList.setPid(3);
        wishList.setCreateTime(new Timestamp(System.currentTimeMillis()));

        //查找成功
        assertEquals(1,wishListMapper.checkDuplicate(wishList.getPid(),wishList.getUid()));

        //查找失败
        wishList.setPid(2);
        assertEquals(0,wishListMapper.checkDuplicate(wishList.getPid(),wishList.getUid()));

        //插入成功
        assertEquals(1,wishListService.addWishList(wishList));
        //插入失败
        assertEquals(null,wishListService.addWishList(wishList));

        //删除成功
        assertEquals(1,wishListService.deleteWishList(1,2));
        //删除失败
        assertEquals(0,wishListService.deleteWishList(1,2));
        //assertEquals(1,wishListService.addWishList(wishlist));
    }
}
