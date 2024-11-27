// src/main/java/com/ssafy/campcino/dto/responseDto/ReviewDto.java
package com.ssafy.campcino.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {
    private int reviewId;        // 리뷰 ID
    private double campRate;        // 캠핑장 평점
    private String userId;       // 사용자 ID
    private String comment;      // 리뷰 내용
    private LocalDateTime createdAt; // 리뷰 작성일
}
