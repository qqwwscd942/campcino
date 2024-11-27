// src/main/java/com/ssafy/campcino/service/impl/ReviewServiceImpl.java
package com.ssafy.campcino.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.campcino.dto.requestDto.CreateReviewRequestDto;
import com.ssafy.campcino.dto.responseDto.ReviewDto;
import com.ssafy.campcino.mapper.CampMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private CampMapper campMapper;

    @Override
    public List<ReviewDto> getReviewsByCampId(int campId) {
        return campMapper.getReviewsByCampId(campId);
    }
    
    @Override
    public void createReview(CreateReviewRequestDto createReviewRequestDto) throws Exception {
        if (createReviewRequestDto.getCampRate() < 1 || createReviewRequestDto.getCampRate() > 5) {
            throw new Exception("별점은 1에서 5 사이여야 합니다.");
        }
        campMapper.insertReview(createReviewRequestDto);
    }
}
