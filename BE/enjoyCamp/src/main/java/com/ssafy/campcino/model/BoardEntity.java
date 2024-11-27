// src/main/java/com/ssafy/campcino/model/BoardEntity.java
package com.ssafy.campcino.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardEntity {
    
    private Integer boardId; // 변경: Long -> Integer

    private String boardTitle;

    private String boardContent;

    private String category;

    private String imgUrl;

    private String userId;

    private LocalDateTime boardCreatedAt;

    private int boardView;

    private List<ReviewEntity> reviews;

}
