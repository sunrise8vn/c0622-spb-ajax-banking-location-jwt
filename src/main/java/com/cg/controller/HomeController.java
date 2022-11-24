package com.cg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/shop")
    public String showShoppingPage() {
        return "shop";
    }

    @GetMapping("/temp")
    public String showTempPage() {
        return "temp";
    }

    @GetMapping("/temp2")
    public String showTemp2Page() {
        return "temp2";
    }

}
