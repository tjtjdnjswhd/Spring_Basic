package com.example.restapibasic.service;

import com.example.restapibasic.LogExecutionTime;
import com.example.restapibasic.domain.BoardPost;
import com.example.restapibasic.domain.BoardPostDto;
import com.example.restapibasic.domain.Comment;
import com.example.restapibasic.domain.CommentDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardPostService {
    private List<BoardPost> boardPosts = new ArrayList<>();
    private Long nextPostId = 1L;
    private Long nextCommentId = 1L;

    @LogExecutionTime
    public List<BoardPostDto> getAllBoardPosts() {
        return boardPosts.stream()
                .map(BoardPostService::convertToBoardPostDto)
                .collect(Collectors.toList());
    }

    public BoardPostDto getBoardPostById(Long id) {
        return boardPosts.stream()
                .filter(boardPost -> boardPost.getId().equals(id))
                .map(BoardPostService::convertToBoardPostDto)
                .findFirst()
                .orElse(null);
    }

    public BoardPostDto createBoardPost(BoardPostDto boardPostDto) {
        BoardPost boardPost = convertToBoardPostEntity(boardPostDto);
        boardPost.setId(nextPostId++);
        boardPost.setCreatedAt(LocalDateTime.now());
        boardPost.setUpdatedAt(LocalDateTime.now());
        boardPosts.add(boardPost);
        return convertToBoardPostDto(boardPost);
    }

    public void deleteBoardPost(Long id) {
        BoardPost foundBoardPost = findBoardPostById(id);
        boardPosts.remove(foundBoardPost);
    }

    public BoardPostDto updateBoardPost(Long id, BoardPostDto updateBoardPostDto) {
        BoardPost boardPost = findBoardPostById(id);
        boardPost.setTitle(updateBoardPostDto.getTitle());
        boardPost.setContent(updateBoardPostDto.getContent());
        boardPost.setUpdatedAt(LocalDateTime.now());
        return convertToBoardPostDto(boardPost);
    }

    public CommentDto createComment(Long postId, CommentDto createCommentDto) {
        BoardPost boardPost = findBoardPostById(postId);
        Comment comment = convertToCommentEntity(createCommentDto);
        comment.setId(nextCommentId++);
        comment.setCreatedAt(LocalDateTime.now());
        boardPost.addComment(comment);
        return convertToCommentDto(comment);
    }

    public void deleteComment(Long postId, Long commentId) {
        BoardPost boardPost = findBoardPostById(postId);
        Comment comment = findCommentById(commentId, boardPost);
        boardPost.removeComment(comment);
    }

    private static Comment findCommentById(Long commentId, BoardPost boardPost) {
        return boardPost.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("댓글을 찾을 수 없습니다."));
    }

    private BoardPost findBoardPostById(Long id) {
        return boardPosts.stream()
                .filter(boardPost -> boardPost.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static BoardPost convertToBoardPostEntity(BoardPostDto boardPostDto) {
        BoardPost boardPost = new BoardPost();
        boardPost.setTitle(boardPostDto.getTitle());
        boardPost.setContent(boardPostDto.getContent());
        boardPost.setAuthor(boardPostDto.getAuthor());
        if (boardPostDto.getComments() != null) {
            boardPostDto.getComments().forEach(commentDto -> {
                Comment comment = convertToCommentEntity(commentDto);
                comment.setBoardPost(boardPost);
                boardPost.addComment(comment);
            });
        }
        return boardPost;
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
                    boardPost.getComments().stream().map(BoardPostService::convertToCommentDto)
                            .collect(Collectors.toList())
            );
        }
        return boardPostDto;
    }

    private static Comment convertToCommentEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        comment.setAuthor(commentDto.getAuthor());
        return comment;
    }

    private static CommentDto convertToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setAuthor(comment.getAuthor());
        return commentDto;
    }
}
