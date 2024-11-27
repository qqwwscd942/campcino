// src/main/java/com/ssafy/campcino/dto/responseDto/CommentDto.java
package com.ssafy.campcino.dto.responseDto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private Long boardId;
    private String userId;
    private String commentContent;
    private LocalDateTime commentCreatedAt;
}
