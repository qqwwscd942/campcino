// com.ssafy.campcino.dto.responseDto.ShopReviewResponse.java
package com.ssafy.campcino.dto.responseDto;

import lombok.Data;

@Data
public class ShopReviewResponse {
    private int reviewId;
    private int shopId;
    private String userId;
    private int shopRate;
    private String comment;
    private String createdAt;
    private String updatedAt;
}
