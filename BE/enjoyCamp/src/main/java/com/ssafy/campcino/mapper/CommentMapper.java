// src/main/java/com/ssafy/campcino/mapper/CommentMapper.java
package com.ssafy.campcino.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.campcino.model.CommentEntity;

@Mapper
public interface CommentMapper {
    // 특정 게시글의 댓글 목록 조회 (페이징 적용)
    List<CommentEntity> getCommentsByBoardId(
        @Param("boardId") Long boardId,
        @Param("offset") int offset,
        @Param("limit") int limit
    );
    
    // 특정 게시글의 댓글 총 개수 조회
    int countCommentsByBoardId(@Param("boardId") Long boardId);
    
    // 댓글 작성
    void insertComment(CommentEntity comment);

    // 댓글 수정
    int updateComment(CommentEntity comment);

    // 댓글 삭제
    int deleteComment(@Param("commentId") Long commentId, @Param("boardId") Long boardId);
}
