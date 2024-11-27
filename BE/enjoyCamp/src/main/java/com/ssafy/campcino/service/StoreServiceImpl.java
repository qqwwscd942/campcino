// com.ssafy.campcino.service.StoreServiceImpl.java
package com.ssafy.campcino.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.campcino.dto.requestDto.ShopReviewRequest;
import com.ssafy.campcino.dto.responseDto.ShopReviewResponse;
import com.ssafy.campcino.dto.responseDto.StoreDto;
import com.ssafy.campcino.mapper.StoreMapper;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public List<StoreDto> getAllStores() {
        List<StoreDto> stores = storeMapper.findAllStores();
        for (StoreDto store : stores) {
            store.setRating(calculateAverageRating(store.getShopId()));
//             store.setImage(store.getShopImg()); // 불필요한 코드 제거
            
        }
        return stores;
    }

    @Override
    public StoreDto getStoreById(int id) {
        StoreDto store = storeMapper.findStoreById(id);
        if (store != null) {
            store.setRating(calculateAverageRating(id));
            // store.setImage(store.getShopImg()); // 불필요한 코드 제거
        }
        return store;
    }

    @Override
    public void addStoreReview(int shopId, ShopReviewRequest reviewRequest) {
        storeMapper.insertStoreReview(shopId, reviewRequest);
    }

    @Override
    public List<ShopReviewResponse> getStoreReviews(int shopId) {
        return storeMapper.findStoreReviewsByShopId(shopId);
    }
    @Override
    public void updateStoreReview(int storeId, int reviewId, ShopReviewRequest reviewRequest) {
        // 리뷰 작성자 확인
        String currentUserId = reviewRequest.getUserId();
        String reviewAuthorId = storeMapper.findReviewAuthorByReviewId(reviewId);
        if (reviewAuthorId == null) {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }
        if (!currentUserId.equals(reviewAuthorId)) {
            throw new SecurityException("리뷰 수정 권한이 없습니다.");
        }
        storeMapper.updateStoreReview(reviewId, reviewRequest.getShopRate(), reviewRequest.getComment());
    }

    @Override
    public void deleteStoreReview(int storeId, int reviewId, String userId) {
        String reviewAuthorId = storeMapper.findReviewAuthorByReviewId(reviewId);
        if (reviewAuthorId == null) {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }
        if (!userId.equals(reviewAuthorId)) {
            throw new SecurityException("리뷰 삭제 권한이 없습니다.");
        }
        storeMapper.deleteStoreReview(reviewId);
    }

    // 상점의 평균 평점 계산
    private double calculateAverageRating(int shopId) {
        List<Integer> ratings = storeMapper.findRatingsByShopId(shopId);
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (int rate : ratings) {
            sum += rate;
        }
        return sum / ratings.size();
    }
}
