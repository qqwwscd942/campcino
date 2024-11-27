// src/main/java/com/ssafy/campcino/dto/requestDto/CreateBoardRequestDto.java
package com.ssafy.campcino.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBoardRequestDto {
    private String boardTitle;
    
    private String boardContent;
    
    private String category;
    
    private String imgUrl;  // 선택 사항
    private String userId;   // 인증된 사용자에서 설정
    
    private Integer boardId; // useGeneratedKeys를 위해 필요
}
