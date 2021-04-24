package com.example.alwayswin;

import com.example.alwayswin.entity.User;
import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Test
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-24
 */


public class Test {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("username", "xiaoming");
        map.put("password", "gebilaowang");
        User user = new User();

        try {
            BeanUtils.populate(user, map);
        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(user.toString());


        org.springframework.beans.BeanUtils.copyProperties(map, user);
        System.out.println(user.toString());
    }


}
