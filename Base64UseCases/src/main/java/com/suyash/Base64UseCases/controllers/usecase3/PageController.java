package com.suyash.Base64UseCases.controllers.usecase3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //following springmvc
public class PageController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
