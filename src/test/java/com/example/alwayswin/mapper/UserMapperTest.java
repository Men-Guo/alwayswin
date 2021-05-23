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
        assertNull(userMapper.getByUsername("arthur"));
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

        int uid = user.getUid();

        //verify userinfo is also added
        assertNotNull(userMapper.getUserInfoByUid(uid));

        // delete user
        assertEquals(userMapper.deleteUser(uid), 1);
        assertNull(userMapper.getByUid(uid));
        assertNull(userMapper.getUserInfoByUid(uid));
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
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            assertEquals(1, userMapper.updateLoginStatus(user.getUid(), true, timestamp));
        }catch (Exception e) {
            e.printStackTrace();
        }

        user = userMapper.getByUid(1);
        assertTrue(user.isStatus());

        // logout
        assertEquals(1, userMapper.updateLogoutStatus(user.getUid(), false));
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

    @Test
    public void happyPathWithUpdateUserInfo() {
        UserInfo userInfo = userMapper.getUserInfoByUid(1);
        assertNotNull(userInfo);

        String originalGender = userInfo.getGender();

        // update
        userInfo.setGender("unknown");
        assertEquals(userMapper.updateUserInfo(userInfo), 1);
        // verify change
        userInfo = userMapper.getUserInfoByUid(1);
        assertEquals("unknown", userInfo.getGender());

        // rollback
        userInfo.setGender(originalGender);
        assertEquals(userMapper.updateUserInfo(userInfo), 1);
    }

    @Test
    public void happyPathWithUpdateBalance() {
        UserInfo userInfo = userMapper.getUserInfoByUid(1);
        assertNotNull(userInfo);

        double originalBalance = userInfo.getBalance();

        // update
        userInfo.setBalance(5.5);
        assertEquals(userMapper.updateUserBalance(userInfo), 1);
        // verify change
        userInfo = userMapper.getUserInfoByUid(1);
        assertEquals(userInfo.getBalance(), 5.5);

        // rollback
        userInfo.setBalance(originalBalance);
        assertEquals(userMapper.updateUserBalance(userInfo), 1);
    }

    @Test
    public void testpreview(){
        System.out.println(userMapper.getPreviewByUid(1).toString());
    }
}