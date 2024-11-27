// src/main/java/com/ssafy/campcino/dto/responseDto/CampDto.java
package com.ssafy.campcino.dto.responseDto;

import com.ssafy.campcino.model.AmenitiesEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CampDto {
    private int campId;             // 캠핑장 ID
    private int regionId;           // 지역 ID
    private int categoryId;         // 카테고리 ID
    private String campName;        // 캠핑장 이름
    private String campHomepage;    // 캠핑장 홈페이지
    private String campExplanation; // 캠핑장 설명
    private String campPhone;       // 캠핑장 전화번호
    private String campCharacter;   // 캠핑장 특징
    private double longitude;       // 경도
    private double latitude;        // 위도
    private String regionName;      // 지역 이름
    private String categoryName;    // 카테고리 이름
    private String roadAddress;     // 도로명 주소
    private String numberAddress;   // 지번 주소
    
    private double rating; // 캠핑장 평점
    private AmenitiesEntity amenities; // tbl_facilities에서 가져온 편의 시설
    private NearAmenitiesDto nearAmenities; // tbl_near_facilities에서 가져온 근처 시설
    private OperatingHoursDto operatingHours; // tbl_op에서 가져온 운영 시간
    private GlampingInfoDto glampingInfo; // tbl_glamping에서 가져온 글램핑 정보 (글램핑인 경우)
    
    private List<ReviewDto> reviews; // 캠핑장 리뷰 리스트
}
