package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.WishList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class WishListMapperTest {

    @Autowired
    WishListMapper wishListMapper;


    @Test
    public void happyPathWithGetByUid(){
        List<WishList> wishLists = wishListMapper.getByUid(1);
        assertEquals(3, wishLists.size());
    }

    @Test
    public void noSuchUidInWishList(){
        List<WishList> wishLists = wishListMapper.getByUid(-1);
        assertEquals(0,wishLists.size());
    }

    @Test
    public void happyPathWithGetByWid(){
        WishList wishList = wishListMapper.selectWid(3);
        assertEquals(3, wishList.getWid());
    }

    @Test
    public void noSuchWidInWishList(){
        WishList wishList = wishListMapper.selectWid(-1);
        assertNull(wishList);
    }

    @Test
    public void insertWishList(){
        WishList wishList=new WishList();
        wishList.setPid(4);
        wishList.setUid(58);
        Date date = new Date();
        wishList.setCreateTime(new Timestamp(date.getTime()));
        Integer res = wishListMapper.insertWishList(wishList);
        assertEquals(1,res);
    }

    @Test
    public void deleteWishListHappyPath(){
        assertEquals(1, wishListMapper.deleteWishList(58,4));
    }

    @Test
    public void deleteWishListNoSuchPidAndUid(){
        assertEquals(0, wishListMapper.deleteWishList(58,-1));
        assertEquals(0, wishListMapper.deleteWishList(-1,4));
        assertEquals(0, wishListMapper.deleteWishList(-1,-1));
    }

    @Test
    public void DuplicatedUidAndPid(){
        assertEquals(1,wishListMapper.checkDuplicate(6,1));
        assertEquals(0,wishListMapper.checkDuplicate(-1,1));
    }

}
