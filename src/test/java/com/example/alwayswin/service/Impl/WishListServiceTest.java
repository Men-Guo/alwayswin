package com.example.alwayswin.service.Impl;

import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.service.WishListService;
import com.example.alwayswin.service.impl.WishListServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class WishListServiceTest {

    @Autowired
    private final WishListService wishListService = new WishListServiceImpl();

    @Test
    public void happyPathAddingWishList(){
        WishList wishList = new WishList();
        wishList.setUid(3);
        wishList.setPid(7);
        assertEquals(1,wishListService.addWishList(wishList));
        wishList.setUid(5);
        wishList.setPid(7);
        assertEquals(1, wishListService.addWishList(wishList));
    }

    @Test
    public void happyPathQueryWid(){
        assertEquals(3, wishListService.queryWishListByWid(3).getWid());
    }

    @Test
    public void NoSuchWid(){
        assertNull(wishListService.queryWishListByWid(-3));
    }

    @Test
    public void happyPathQueryWishListWithUid(){
        assertEquals(3,wishListService.queryWishList(1).size());
    }

    @Test
    public void NoSuchUid(){
        assertEquals(0,wishListService.queryWishList(-1).size());
    }

    @Test
    public void addWishListNoSuchUid(){
        WishList wishList = new WishList();
        wishList.setUid(-1);
        wishList.setPid(3);
        assertEquals(0,wishListService.addWishList(wishList));
    }

    @Test
    public void addWishListNoSuchPid(){
        WishList wishList = new WishList();
        wishList.setUid(1);
        wishList.setPid(-3);
        assertEquals(0,wishListService.addWishList(wishList));
    }

    @Test
    public void addDuplicatedWishList(){
        WishList wishList = new WishList();
        wishList.setUid(2);
        wishList.setPid(2);
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
        assertEquals(1,wishListService.deleteWishList(3,8));
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
        assertEquals(1,wishListService.deleteWishList(66));
    }

    @Test
    public void SoSuchWidDeleteWishList(){
        assertEquals(0,wishListService.deleteWishList(-1));
        assertEquals(0,wishListService.deleteWishList(22));
    }

    @Test
    public void TestDuplicate(){
        assertEquals(1, wishListService.checkDuplicate(1,6));
        assertEquals(0,wishListService.checkDuplicate(0,-1));
    }
}
