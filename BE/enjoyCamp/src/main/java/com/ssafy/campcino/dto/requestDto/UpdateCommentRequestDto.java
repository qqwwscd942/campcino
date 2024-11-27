// src/main/java/com/ssafy/campcino/dto/requestDto/UpdateCommentRequestDto.java
package com.ssafy.campcino.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentRequestDto {
    private Long commentId;
    private String commentContent;
    private String userId;
}
