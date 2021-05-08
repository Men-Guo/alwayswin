package com.example.alwayswin.service.impl;

import com.example.alwayswin.entity.WishList;
import com.example.alwayswin.mapper.WishListMapper;
import com.example.alwayswin.service.WishListService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * WishList实现类
 */
@Service
public class WishListServiceImpl implements WishListService {

    //引用DAO
    @Autowired
    private WishListMapper wishListMapper;


    public void setWishListMapper(WishListMapper wishListMapper) {
        this.wishListMapper = wishListMapper;
    }

    @Override
    public Integer addWishList(WishList wishList) {
        if (checkDuplicate(wishList.getPid(),wishList.getUid()) == 1)
            return -1;
        wishList.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return wishListMapper.insertWishList(wishList);
    }

    @Override
    public List<WishList> queryWishList(Integer uid) {
        return wishListMapper.getByUid(uid);
    }

    @Override
    public List<WishList> queryWishListPage(Integer uid, int pageNum, int pageSize) {
        // 在你需要进行分页的 MyBatis 查询方法前调用 PageHelper.startPage 静态方法即可
        // 紧跟在这个方法后的第一个MyBatis 查询方法会被进行分页
        PageHelper.startPage(pageNum, pageSize);
        List<WishList> wishLists = wishListMapper.getByUid(uid);
        return wishLists;
    }

    @Override
    public WishList queryWishListByWid(Integer wid) {
        return wishListMapper.selectWid(wid);
    }

    @Override
    public int deleteWishList(Integer uid, Integer pid) {
        return wishListMapper.deleteWishList(uid,pid);
    }

    @Override
    public int deleteWishList(Integer wid) {
        return wishListMapper.deleteWishListByWid(wid);
    }

    @Override
    public int checkDuplicate(Integer uid, Integer pid) {
        return wishListMapper.checkDuplicate(uid, pid);
    }


}
