package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.WishList;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class WishListMapperTest {

    @Autowired
    private WishListMapper wishListMapper;

    // 失败
    @Test
    public void happyPathWithGetByUidPaged1() {
        PageInfo pageInfo = PageHelper.startPage(1,2)
                .doSelectPageInfo(()->wishListMapper.getByUid(2));
        assertEquals(2, pageInfo.getPageSize());
//        actual: 0
        assertEquals(4, pageInfo.getTotal());
    }

    // 失败
    @Test
    public void happyPathWithGetByUidPaged2() {
        PageHelper.startPage(1, 2);
        List<WishList> wishLists = wishListMapper.getByUid(2);
        PageInfo pageInfo = PageInfo.of(wishLists);

        // actual： 4
        assertEquals(2, pageInfo.getPageSize());
        assertEquals(4, pageInfo.getTotal());
    }


    @Test
    public void happypathwithgetWishList() {
        assertEquals(4, wishListMapper.getByUid(2).size());
    }

}