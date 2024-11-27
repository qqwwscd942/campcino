// src/main/java/com/ssafy/campcino/dto/responseDto/BoardDto.java
package com.ssafy.campcino.dto.responseDto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private Integer boardId; // 변경: Long -> Integer
    private String boardTitle;
    private String boardContent;
    private String category;
    private String imgUrl;
    private String userId;
    private LocalDateTime boardCreatedAt;
    private int boardView;
    private List<CommentDto> comments; // 댓글 목록 (옵션)
}
