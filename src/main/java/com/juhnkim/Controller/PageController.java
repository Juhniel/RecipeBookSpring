package com.juhnkim.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String index() {
        return "../templates/index";
    }

    @GetMapping("/articles")
    public String articles() {
        return "../templates/html/articles";
    }

    @GetMapping("/about")
    public String aboutUs() {
        return "../templates/html/about";
    }

    @GetMapping("/contact")
    public String contactUs() {
        return "../templates/html/contact";
    }
}