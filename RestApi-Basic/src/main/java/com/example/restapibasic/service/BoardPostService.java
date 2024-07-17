package com.example.restapibasic.service;

import com.example.restapibasic.controller.BoardPostController;
import com.example.restapibasic.domain.BoardPost;
import com.example.restapibasic.domain.BoardPostDto;
import com.example.restapibasic.domain.Comment;
import com.example.restapibasic.domain.CommentDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardPostService {
    private List<BoardPost> boardPosts = new ArrayList<>();
    private Long nextPostId = 1L;
    private Long nextCommentId = 1L;

    public BoardPostDto createBoardPost(BoardPostDto boardPostDto) {
        BoardPost boardPost = new BoardPost();
        boardPost.setId(nextPostId++);
        boardPost.setTitle(boardPostDto.getTitle());
        boardPost.setContent(boardPostDto.getContent());
        boardPost.setAuthor(boardPostDto.getAuthor());

        if (boardPostDto.getComments() != null) {
            boardPostDto.getComments().forEach(commentDto -> {
                Comment comment = convertToComment(commentDto);
                boardPost.addComment(comment);
            });
        }

        return boardPostDto;
    }

    private static BoardPostDto convertToBoardPostDto(BoardPost boardPost) {
        BoardPostDto boardPostDto = new BoardPostDto();
        boardPostDto.setId(boardPost.getId());
        boardPostDto.setTitle(boardPost.getTitle());
        boardPostDto.setContent(boardPost.getContent());
        boardPostDto.setAuthor(boardPost.getAuthor());
        boardPostDto.setCreatedAt(boardPost.getCreatedAt());
        boardPostDto.setUpdatedAt(boardPost.getUpdatedAt());
        if (boardPost.getComments() != null) {
            boardPostDto.setComments(
                    boardPost.getComments().stream().map(this::convertToCommentDto).collect(Collectors.toList()));
        }
        return boardPostDto;
    }
}
