package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.mapper.WishListMapper;
import com.example.alwayswin.service.impl.WishListServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

//to do
/*
* 1.uid重复的pid
* 2.create time error
* 3.插入时 uid 不存在
* 4.插入时 pid 不存在
* */
@SpringBootTest
public class WishListServiceTest {

    @Autowired
    private WishListServiceImpl wishListService = new WishListServiceImpl();
    @Autowired
    private WishListMapper wishListMapper;

    @Test
    public void happyPathAddingWishList(){
        WishList wishList = new WishList();
        wishList.setUid(1);
        wishList.setPid(8);
        assertEquals(1,wishListService.addWishList(wishList));
    }

    @Test
    public void happyPathQueryWishListWithUid(){
        assertEquals(4,wishListService.queryWishList(1).size());
    }

    @Test
    public void printOutHappyPathQueryWishListWithUid(){
        System.out.println(wishListService.queryWishList(1));
    }

    @Test
    public void NoSuchUid(){
        assertEquals(0,wishListService.queryWishList(-1).size());
    }



    @Test
    public void addDuplicatedWishList(){
        WishList wishList = new WishList();
        wishList.setUid(1);
        wishList.setPid(3);
        assertEquals(0,wishListService.addWishList(wishList));
    }

    @Test
    public void addNotExistUidToWishList(){
        WishList wishList = new WishList();
        wishList.setUid(-1);
        wishList.setPid(3);
        assertEquals(0,wishListService.addWishList(wishList));
    }

    @Test
    public void addNotExistPidToWishList(){
        WishList wishList = new WishList();
        wishList.setUid(1);
        wishList.setPid(-1);
        assertEquals(0,wishListService.addWishList(wishList));
    }

    @Test
    public void addNotExistBothUidAndPidToWishList(){
        WishList wishList = new WishList();
        wishList.setUid(-1);
        wishList.setPid(-1);
        assertEquals(0,wishListService.addWishList(wishList));
    }

    @Test
    public void happyPathDeleteWishList(){
        assertEquals(1,wishListService.deleteWishList(1,3));
    }

    @Test
    public void SoSuchUidAndPidDeleteWishList(){
        assertEquals(0,wishListService.deleteWishList(55,24));
        assertEquals(0,wishListService.deleteWishList(-1,3));
        assertEquals(0,wishListService.deleteWishList(1,-3));
        assertEquals(0,wishListService.deleteWishList(-1,-3));
    }

    @Test
    public void happyPathDeleteWishListWithWid(){
        assertEquals(1,wishListService.deleteWishList(31));
    }

    @Test
    public void SoSuchWidDeleteWishList(){
        assertEquals(0,wishListService.deleteWishList(-1));
        assertEquals(0,wishListService.deleteWishList(22));
    }

}
