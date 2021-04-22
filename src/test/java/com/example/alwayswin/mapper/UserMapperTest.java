package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.User;
import com.example.alwayswin.entity.UserInfo;
import com.example.alwayswin.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    ////////////////////        getByUid     ///////////////////////////////////
    @Test
    public void happyPathWithGetByUid() {
        assertEquals(userMapper.getByUid(1).getUsername(), "Arthur");
    }

    @Test
    public void noSuchUserGetByUid() {
        assertNull(userMapper.getByUid(0));
    }

    ////////////////////        getByUsername     ///////////////////////////////////

    @Test
    public void happyPathWithGetByUsername() {
        assertEquals(userMapper.getByUsername("Arthur").getUid(), 1);
    }

    @Test
    public void noSuchUserGetByUsername() {
        assertNull(userMapper.getByUsername("xxx"));
    }

    ////////////////////        add    ///////////////////////////////////
    @Test
    public void happyPathWithAddUser() {
        User user = new User();
        user.setUsername("Jason");
        user.setPassword("Bourne2");
        assertEquals(userMapper.add(user), 1);

        // verify added
        user = userMapper.getByUsername("Jason");
        assertNotNull(user);

        assertEquals(userMapper.deleteUser(user.getUid()), 1);
    }


    @Test
    public void exceptionWhenAddUser() {
        User user = new User();
        user.setUsername("Arthur");
        // duplicate username
        assertThrows(Exception.class, () -> userMapper.add(user));

    }


    ////////////////////        update    ///////////////////////////////////
    @Test
    public void happyPathWithUpdateLoginAndLogout() {
        User user = userMapper.getByUid(1);
        assertNotNull(user);


        // login
        try {
            Timestamp timestamp = DateUtil.String2Timestamp("2021-04-21 16:00:00", "yyyy-MM-dd HH:mm:ss");
            assertEquals(userMapper.updateLoginStatus(user.getUid(), true, timestamp), 1);
        }catch (Exception e) {
            e.printStackTrace();
        }

        user = userMapper.getByUid(1);
        assertTrue(user.isStatus());

        // logout
        assertEquals(userMapper.updateLogoutStatus(user.getUid(), false), 1);
        user = userMapper.getByUid(1);
        assertFalse(user.isStatus());
    }

    @Test
    public void happyPathWithUpdatePassword() {
        User user = userMapper.getByUsername("Arthur");
        assertNotNull(user);

        // change psw
        String oldPassword = user.getPassword();
        String newPassword = user.getPassword() + "new";
        assertEquals(userMapper.updatePassword(user.getUid(), newPassword), 1);

        // verify psw changed
        user = userMapper.getByUsername("Arthur");
        assertNotEquals(user.getPassword(), oldPassword);
        assertEquals(user.getPassword(), newPassword);

        //change back to old psw
        assertEquals(userMapper.updatePassword(user.getUid(), oldPassword), 1);
    }

    ////////////////////////////////////////////////////
    /////////          UserInfo          //////////////
    ////////////////////////////////////////////////////


    /////////          getUserInfo          //////////////
    @Test
    public void happyPathWithGetUserInfo() {
        UserInfo userInfo = userMapper.getUserInfoByUid(1);
        assertNotNull(userInfo);
    }

    public void ExceptionWhenGetUserInfo() {
        UserInfo userInfo = userMapper.getUserInfoByUid(0);
        assertNull(userInfo);
    }

    /////////          addUserInfo          //////////////
    @Test
    public void happyPathWithAddUserInfo() {
        // add new user
        User user = new User();
        user.setUsername("Jason");
        user.setPassword("Bourne2");
        assertEquals(userMapper.add(user), 1);

        // verify new user added
        user = userMapper.getByUsername("Jason");
        assertNotNull(user);

        // add new userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(user.getUid());
        userInfo.setBalance(8000);
        assertEquals(userMapper.addUserInfo(userInfo), 1);

        // verify userInfo added
        userInfo = userMapper.getUserInfoByUid(user.getUid());
        assertNotNull(userInfo);
        assertEquals(userInfo.getBalance(), 8000);

        // delete user and userInfo
        assertEquals(1, userMapper.deleteUser(user.getUid()));
        // uid是外键，userinfo 会被同时删除
        assertEquals(0, userMapper.deleteUserInfo(user.getUid()));
    }

    @Test
    public void happyPathWithUpdateUserInfo() {
        UserInfo userInfo = userMapper.getUserInfoByUid(1);
        assertNotNull(userInfo);

        double originalBalance = userInfo.getBalance();

        // update
        userInfo.setBalance(1000);
        assertEquals(userMapper.updateUserInfo(userInfo), 1);
        // verify change
        userInfo = userMapper.getUserInfoByUid(1);
        assertEquals(userInfo.getBalance(), 1000);

        // rollback
        userInfo.setBalance(originalBalance);
        assertEquals(userMapper.updateUserInfo(userInfo), 1);
    }
}