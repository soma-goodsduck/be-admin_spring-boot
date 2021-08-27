package com.ducks.goodsduck.admin.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    @GetMapping("/test")
    public String test(){
        String testStr = "Hi~~";
        System.out.println(testStr);
        return testStr;
    }
}
