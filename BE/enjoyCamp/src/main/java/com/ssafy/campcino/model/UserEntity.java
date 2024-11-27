package com.ssafy.campcino.model;
import lombok.Data;

import java.sql.*;
import java.time.LocalDateTime;

@Data
public class UserEntity {
    private String userId;              // 사용자 ID
    private String userPassword;        // 암호화된 비밀번호
    private String userPhone;           // 사용자 전화번호
    private Integer userAge;            // 사용자 나이
    private String userEmail;           // 사용자 이메일
    private String userGender;          // 사용자 성별
    private LocalDateTime userSignup;   // 가입 일자
    private LocalDateTime userDelete;   // 탈퇴 일자
    private Long userMileage;           // 회원 마일리지
    private String userUrl;             // 사용자 프로필 URL
    private String userTempAuth;        // 임시 비밀번호 또는 Refresh Token
    private String userRefreshToken;    // Refresh Token
    private String userRes;             // 기타 정보
    private String userGrade;
}
