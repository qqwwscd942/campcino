// com.ssafy.campcino.controller.StoreController.java
package com.ssafy.campcino.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ssafy.campcino.dto.requestDto.ShopReviewRequest;
import com.ssafy.campcino.dto.responseDto.ShopReviewResponse;
import com.ssafy.campcino.dto.responseDto.StoreDto;
import com.ssafy.campcino.service.StoreService;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    // Store 목록 조회
    @GetMapping
    public List<StoreDto> getAllStores() {
//        System.out.println("조회 시작");
        List<StoreDto> list = storeService.getAllStores();
//        for (StoreDto storeDto : list) {
//            System.out.println("storeDto = " + storeDto);
//        }
        return list;
    }

    // 특정 Store 상세 정보 조회
    @GetMapping("/{id}")
    public StoreDto getStoreById(@PathVariable int id) {
        return storeService.getStoreById(id);
    }

    // 상점 리뷰 제출
    @PostMapping("/{id}/reviews")
    public void addStoreReview(@PathVariable int id, @RequestBody ShopReviewRequest reviewRequest) {
        storeService.addStoreReview(id, reviewRequest);
    }

    // 상점 리뷰 목록 조회
    @GetMapping("/{id}/reviews")
    public List<ShopReviewResponse> getStoreReviews(@PathVariable int id) {
//        System.out.println("id = " + id);
        List<ShopReviewResponse> list = storeService.getStoreReviews(id);
//        for (ShopReviewResponse shopReviewResponse : list) {
//            System.out.println("shopReviewResponse = " + shopReviewResponse);
//        }
        return storeService.getStoreReviews(id);
    }

    // 특정 리뷰 수정
    @PutMapping("/{storeId}/reviews/{reviewId}")
    public void updateStoreReview(@PathVariable int storeId, @PathVariable int reviewId, @RequestBody ShopReviewRequest reviewRequest) {
        storeService.updateStoreReview(storeId, reviewId, reviewRequest);
    }

    // 특정 리뷰 삭제
    @DeleteMapping("/{storeId}/reviews/{reviewId}")
    public void deleteStoreReview(@PathVariable int storeId, @PathVariable int reviewId, @RequestParam String userId) {
        storeService.deleteStoreReview(storeId, reviewId, userId);
    }
}
