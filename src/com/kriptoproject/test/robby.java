package com.kriptoproject.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class robby {


    @RequestMapping("/profileobi")
    String profileControllerS() {
        return "obiin!";
    }
    
    @RequestMapping("/testobi")
    String profileControllerXS() {
        return "dscsc!";
    }

}