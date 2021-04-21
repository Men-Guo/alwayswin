package com.alwayswin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorld {

    @RequestMapping(value = "/spring/helloworld")
    public @ResponseBody String helloworld(){
        return "Hello,World!";
    }

}
