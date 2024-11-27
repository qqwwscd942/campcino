package com.ssafy.campcino.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampReviewSummaryDto {
	private Long campId;
    private String campName;
    private Double avgRating;
    private Integer reviewCount;

    // Constructors
    public CampReviewSummaryDto() {}

    public CampReviewSummaryDto(Long campId, String campName, Double avgRating, Integer reviewCount) {
        this.campId = campId;
        this.campName = campName;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
    }
}
