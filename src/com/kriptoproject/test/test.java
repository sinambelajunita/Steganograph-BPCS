package com.kriptoproject.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {


    @RequestMapping("/profile")
    String profileController() {
        return "glmglfbm!";
    }
    
    @RequestMapping("/test")
    String profileControllerX() {
        return "test!";
    }

}