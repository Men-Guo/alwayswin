package com.example.alwayswin.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserController
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
@RestController
public class UserController {

    /*
     * @Description: 密码规则。长度大于等于6位且数字字母混合, 字符滚粗
     * @Param: [password]
     * @Return: boolean
     * @Author: SQ
     * @Date: 2021-4-20
     **/
    private boolean isValidPassword(String password) {
        if (password.length() < 6)
            return false;
        else {
            int digitCnt = 0, letterCnt = 0, otherCnt = 0;
            for (char c : password.toCharArray()) {
                if (Character.isDigit(c))
                    digitCnt++;
                else if (Character.isLetter(c))
                    letterCnt++;
                else {
                    otherCnt++;
                    break;
                }
            }
            return digitCnt > 0 && letterCnt > 0 && otherCnt == 0;
        }
    }
}
