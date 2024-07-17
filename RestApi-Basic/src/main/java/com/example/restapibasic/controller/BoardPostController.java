package com.example.restapibasic.controller;

import com.example.restapibasic.domain.BoardPostDto;
import com.example.restapibasic.service.BoardPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board-post")
public class BoardPostController {
    private final BoardPostService boardPostService;

    @Autowired
    public BoardPostController(BoardPostService boardPostService) {
        this.boardPostService = boardPostService;
    }

    @PostMapping
    public ResponseEntity<BoardPostDto> createBoardPost(@RequestBody BoardPostDto boardPostDto) {
        BoardPostDto createdBoardPost = boardPostService.createBoardPost(boardPostDto);
        return ResponseEntity.ok(createdBoardPost);
    }
}
