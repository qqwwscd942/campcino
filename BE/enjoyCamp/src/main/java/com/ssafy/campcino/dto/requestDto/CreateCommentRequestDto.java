// src/main/java/com/ssafy/campcino/dto/requestDto/CreateCommentRequestDto.java
package com.ssafy.campcino.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentRequestDto {
    private Long boardId;
    private String commentContent;
    // userId는 인증된 사용자에서 설정 예정
	private String userId;
}
