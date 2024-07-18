package com.example.restapibasic.controller;

import com.example.restapibasic.domain.BoardPostDto;
import com.example.restapibasic.domain.CommentDto;
import com.example.restapibasic.service.BoardPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<BoardPostDto>> getAllBoardPosts() {
        List<BoardPostDto> boardPostDtos = boardPostService.getAllBoardPosts();
        return ResponseEntity.ok(boardPostDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardPostDto> getBoardPostById(@PathVariable(name = "id") Long id) {
        BoardPostDto foundBoardPost = boardPostService.getBoardPostById(id);
        return ResponseEntity.ok(foundBoardPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardPost(@PathVariable(name = "id") Long id) {
        boardPostService.deleteBoardPost(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardPostDto> updateBoardPost(@PathVariable("id") Long id, @RequestBody
    BoardPostDto updateBoardPostDto) {
        BoardPostDto updatedBoardPostDto = boardPostService.updateBoardPost(id, updateBoardPostDto);

        return ResponseEntity.ok(updatedBoardPostDto);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId, @RequestBody CommentDto createCommentDto) {
        CommentDto createdCommentDto = boardPostService.createComment(postId, createCommentDto);
        return ResponseEntity.ok(createCommentDto);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        boardPostService.deleteComment(postId, commentId);
        return ResponseEntity.noContent().build();
    }
}
