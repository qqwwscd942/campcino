package com.ssafy.campcino.service;

import java.util.List;

import com.ssafy.campcino.dto.requestDto.CreateReviewRequestDto;
import com.ssafy.campcino.dto.responseDto.ReviewDto;

public interface ReviewService {
	List<ReviewDto> getReviewsByCampId(int campId);

	void createReview(CreateReviewRequestDto createReviewRequestDto) throws Exception;
}
