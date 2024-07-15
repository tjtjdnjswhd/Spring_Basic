package com.example.thymeleafsample.controllers;

import com.example.thymeleafsample.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ThymeleafController {

    @GetMapping("/index")
    public String index(Model model)
    {
        User user = new User("John Doe", "user@user.com", false);
        int[] datas = new int[] {1, 2, 3, 4, 5};

        model.addAttribute("datas", datas);
        model.addAttribute("user", user);
        return "index";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute User user, Model model)
    {
        model.addAttribute("user", user);
        return "index";
    }
}
