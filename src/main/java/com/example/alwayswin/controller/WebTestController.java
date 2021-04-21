package com.example.alwayswin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebTestController {

    @RequestMapping(value = "/say")
    public @ResponseBody String say(){
        return "hello";
    }

}
