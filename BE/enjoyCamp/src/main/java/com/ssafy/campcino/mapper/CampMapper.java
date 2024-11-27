
package com.ssafy.campcino.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.campcino.dto.requestDto.CreateReviewRequestDto;
import com.ssafy.campcino.dto.responseDto.CampDto;
import com.ssafy.campcino.dto.responseDto.CampReviewSummaryDto;
import com.ssafy.campcino.dto.responseDto.ReviewDto;
@Mapper
public interface CampMapper {
    List<CampDto> findAll();
    List<CampDto> getSelect(int region, List<Integer> category, String text);
    
    CampDto getCampById(@Param("campId") int campId);
    List<ReviewDto> getReviewsByCampId(@Param("campId") int campId);
    List<CampReviewSummaryDto> getCampReviewSummaries(Map<String, Object> params);
    long getCampReviewSummariesCount(Map<String, Object> params);
    // 새로 추가된 메서드
    List<ReviewDto> getReviewsByCampIdPaginated(Map<String, Object> params);
    int getReviewCountByCampId(@Param("campId") int campId);
 // 새로 추가된 리뷰 삽입 메서드
    void insertReview(CreateReviewRequestDto createReviewRequestDto);
}
