// com.ssafy.campcino.dto.responseDto.StoreDto.java
package com.ssafy.campcino.dto.responseDto;

import lombok.Data;

@Data
public class StoreDto {
    private int shopId;
    private String shopTitle;
    private long shopPrice;
    private String shopComment;
    private int productId;
    private double rating; // 평점
    private String image;   // 이미지 URL
}
