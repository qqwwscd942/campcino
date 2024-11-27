// src/main/java/com/ssafy/campcino/service/CommentService.java
package com.ssafy.campcino.service;

import com.ssafy.campcino.dto.requestDto.CreateCommentRequestDto;
import com.ssafy.campcino.dto.requestDto.UpdateCommentRequestDto;
import com.ssafy.campcino.dto.responseDto.CommentDto;
import com.ssafy.campcino.dto.responseDto.PaginatedResponse;

public interface CommentService {
    CommentDto createComment(CreateCommentRequestDto createCommentRequestDto);
    PaginatedResponse<CommentDto> getCommentsByBoardId(Long boardId, int page, int size);
    boolean updateComment(UpdateCommentRequestDto updateCommentRequestDto);
    boolean deleteComment(Long commentId, Long boardId);
}
