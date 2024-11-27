// src/main/java/com/ssafy/campcino/service/impl/CommentServiceImpl.java
package com.ssafy.campcino.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.campcino.dto.requestDto.CreateCommentRequestDto;
import com.ssafy.campcino.dto.requestDto.UpdateCommentRequestDto;
import com.ssafy.campcino.dto.responseDto.CommentDto;
import com.ssafy.campcino.dto.responseDto.PaginatedResponse;
import com.ssafy.campcino.mapper.CommentMapper;
import com.ssafy.campcino.model.CommentEntity;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentDto createComment(CreateCommentRequestDto createCommentRequestDto) {
        CommentEntity comment = new CommentEntity();
        comment.setBoardId(createCommentRequestDto.getBoardId());
        comment.setUserId(createCommentRequestDto.getUserId()); // 실제 인증된 사용자 ID로 설정
        comment.setCommentContent(createCommentRequestDto.getCommentContent());
        comment.setCommentCreatedAt(LocalDateTime.now());

        commentMapper.insertComment(comment);

        return convertToDto(comment);
    }

    @Override
    public PaginatedResponse<CommentDto> getCommentsByBoardId(Long boardId, int page, int size) {
        int offset = (page - 1) * size;
        List<CommentEntity> comments = commentMapper.getCommentsByBoardId(boardId, offset, size);
//        System.out.println(comments.get(0));
        int totalComments = commentMapper.countCommentsByBoardId(boardId);
        int totalPages = (int) Math.ceil((double) totalComments / size);

        List<CommentDto> commentDtos = comments.stream().map(this::convertToDto).collect(Collectors.toList());
//        System.out.println(commentDtos.get(0).toString());
        PaginatedResponse<CommentDto> response = new PaginatedResponse<>();
        response.setItems(commentDtos);
        response.setCurrentPage(page);
        response.setTotalPages(totalPages);
        response.setTotalItems(totalComments);

        return response;
    }

    @Override
    public boolean updateComment(UpdateCommentRequestDto updateCommentRequestDto) {
        CommentEntity comment = new CommentEntity();
        comment.setCommentId(updateCommentRequestDto.getCommentId());
        comment.setCommentContent(updateCommentRequestDto.getCommentContent());
        comment.setUserId(updateCommentRequestDto.getUserId());

        int affectedRows = commentMapper.updateComment(comment);
        return affectedRows > 0;
    }

    @Override
    public boolean deleteComment(Long commentId, Long boardId) {
//    	System.out.println(commentId+" "+boardId) ;
        int affectedRows = commentMapper.deleteComment(commentId, boardId);
//        System.out.println(affectedRows);
        return affectedRows > 0;
    }

    private CommentDto convertToDto(CommentEntity comment) {
        if (comment == null) {
            return null;
        }
        CommentDto dto = new CommentDto();
        dto.setCommentId(comment.getCommentId());
        dto.setBoardId(comment.getBoardId());
        dto.setUserId(comment.getUserId());
        dto.setCommentContent(comment.getCommentContent());
        dto.setCommentCreatedAt(comment.getCommentCreatedAt());
        return dto;
    }
}
