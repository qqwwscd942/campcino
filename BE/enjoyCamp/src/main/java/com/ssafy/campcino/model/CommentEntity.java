// src/main/java/com/ssafy/campcino/model/CommentEntity.java
package com.ssafy.campcino.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentEntity {
    private Long commentId;
    private Long boardId;
    private String userId;
    private String commentContent;
    private LocalDateTime commentCreatedAt;
}
