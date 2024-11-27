// src/main/java/com/ssafy/campcino/controller/BoardController.java
package com.ssafy.campcino.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.campcino.dto.requestDto.CreateBoardRequestDto;
import com.ssafy.campcino.dto.requestDto.CreateCommentRequestDto;
import com.ssafy.campcino.dto.requestDto.UpdateCommentRequestDto;
import com.ssafy.campcino.dto.responseDto.BoardDto;
import com.ssafy.campcino.dto.responseDto.CommentDto;
import com.ssafy.campcino.dto.responseDto.PaginatedResponse;
import com.ssafy.campcino.service.BoardService;
import com.ssafy.campcino.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    /**
     * 게시글 목록 조회 (페이징, 필터링)
     */
    @GetMapping
    public PaginatedResponse<BoardDto> getBoards(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(defaultValue = "") String text,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "board_created_at") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder) {
        // 기본 카테고리 설정
        if (categories == null || categories.isEmpty()) {
            categories = Arrays.asList("일반", "질문", "공지", "자유", "정보", "기타");
        }
        return boardService.getBoards(categories, text, page, size, sortBy, sortOrder);
    }

    /**
     * 특정 게시글 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable("id") Long boardId) {
        try {
            BoardDto board = boardService.getBoardById(boardId);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 게시글 작성
     */
    @PostMapping()
    public ResponseEntity<BoardDto> createBoard(@RequestBody CreateBoardRequestDto request, Authentication authentication) {
        try {
            // 인증된 사용자 ID 설정
//            String userId = authentication.getName(); // 로그인된 사용자 ID 가져오기
//            request.setUserId(userId); // 사용자 ID 설정
            BoardDto createdBoard = boardService.createBoard(request);
            return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 목록 조회 (페이징 적용)
     */
    @GetMapping("/{id}/comments")
    public PaginatedResponse<CommentDto> getCommentsByBoardId(
            @PathVariable("id") Long boardId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return commentService.getCommentsByBoardId(boardId, page, size);
    }

    /**
     * 댓글 작성
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity<String> createComment(
            @PathVariable("id") Long boardId,
            @Valid @RequestBody CreateCommentRequestDto request,
            Authentication authentication) {
        try {
            // 인증된 사용자 ID 설정
//            String userId = authentication.getName(); // 로그인된 사용자 ID 가져오기
            request.setBoardId(boardId);
//            request.setUserId(userId); // 사용자 ID 설정
            commentService.createComment(request);

            return new ResponseEntity<>("댓글이 성공적으로 작성되었습니다!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("댓글 작성에 실패했습니다: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{id}/comments/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody UpdateCommentRequestDto requestDto,
            Authentication authentication) {
        try {
//            String userId = authentication.getName(); // 로그인된 사용자 ID 가져오기
//            requestDto.setUserId(userId); // 사용자 ID 설정
            boolean isUpdated = commentService.updateComment(requestDto);
            if (isUpdated) {
                return new ResponseEntity<>("댓글이 성공적으로 수정되었습니다!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("댓글 수정에 실패했습니다. 권한이 없거나 댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("댓글 수정에 실패했습니다: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("id") Long boardId,
            Authentication authentication) {
        try {
//            String userId = authentication.getName(); // 로그인된 사용자 ID 가져오기
            boolean isDeleted = commentService.deleteComment(commentId,boardId);
            if (isDeleted) {
                return new ResponseEntity<>("댓글이 성공적으로 삭제되었습니다!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("댓글 삭제에 실패했습니다. 권한이 없거나 댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("댓글 삭제에 실패했습니다: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBoard(
            @PathVariable("id") Long boardId,
            @RequestBody CreateBoardRequestDto requestDto,
            Authentication authentication) {
        try {
            // 인증된 사용자 ID 가져오기
//            String userId = authentication.getName();
            boardService.updateBoard(requestDto, boardId);
            return new ResponseEntity<>("게시글이 성공적으로 수정되었습니다!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 수정에 실패했습니다: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(
            @PathVariable("id") Long boardId,
            Authentication authentication) {
        try {
            // 인증된 사용자 ID 가져오기
//            String userId = authentication.getName();
            boardService.deleteBoard(boardId);
            return new ResponseEntity<>("게시글이 성공적으로 삭제되었습니다!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 삭제에 실패했습니다: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
