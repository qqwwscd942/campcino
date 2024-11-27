
package com.ssafy.campcino.service;

import java.util.List;

import com.ssafy.campcino.dto.requestDto.CreateReviewRequestDto;
import com.ssafy.campcino.dto.responseDto.CampDto;
import com.ssafy.campcino.dto.responseDto.CampReviewSummaryDto;
import com.ssafy.campcino.dto.responseDto.PaginatedResponse;
import com.ssafy.campcino.dto.responseDto.ReviewDto;

public interface CampService {
    List<CampDto> getAllCamps();
    List<CampDto> getSelectCamps(int region, List<Integer> category, String text);
    CampDto getCampById(int campId);
    PaginatedResponse<CampReviewSummaryDto> getCampReviewSummaries(String sortBy, String sortOrder, int page, int size);
 // 새로 추가된 메서드
    PaginatedResponse<ReviewDto> getPaginatedReviewsByCampId(int campId, int page, int size);
    // 리뷰 작성 메서드 추가
    void createReview(CreateReviewRequestDto createReviewRequestDto);
}
