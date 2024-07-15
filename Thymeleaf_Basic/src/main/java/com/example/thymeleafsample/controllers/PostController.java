package com.example.thymeleafsample.controllers;

import com.example.thymeleafsample.entity.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final List<Post> posts = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", posts);
        return "post/list";
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "post/form";
    }

    @PostMapping
    public String savePost(@ModelAttribute Post post) {
        post.setId(nextId++);
        post.setCreatedAt(LocalDateTime.now());
        posts.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = posts.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다"));
        model.addAttribute("post", post);
        return "post/detail";
    }
}
