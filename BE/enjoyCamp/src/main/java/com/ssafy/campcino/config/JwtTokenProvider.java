
package com.ssafy.campcino.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private Key accessKey;
    private Key refreshKey;
    private final String accessSecretKey = "AccessKeySecretKeyForSigning123!@#"; // Access Token 서명용 키
    private final String refreshSecretKey = "RefreshKeySecretKeyForSigning456!@#"; // Refresh Token 서명용 키
    private final long accessTokenExpirationTime = 15 * 60 * 1000; // 15분
    private final long refreshTokenExpirationTime = 7 * 24 * 60 * 60 * 1000; // 7일

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService; // 생성자에서 주입
    }

    @PostConstruct
    public void init() {
        this.accessKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes());
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes());
    }

    public Authentication getAuthentication(String token) {
        try {
            // 토큰에서 사용자 이름 추출
            String username = getUserIdFromToken(token, true); // true: Access Token 사용

            // UserDetailsService에서 사용자 정보 로드
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (userDetails == null) {
                throw new IllegalArgumentException("User details not found for token: " + token);
            }

            // 인증 정보 생성 및 반환
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to authenticate token: " + token, e);
        }
    }

    public String generateAccessToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenExpirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenExpirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, boolean isAccessToken) {
        try {
            Key signingKey = isAccessToken ? accessKey : refreshKey;
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserIdFromToken(String token, boolean isAccessToken) {
        Key signingKey = isAccessToken ? accessKey : refreshKey;
        return Jwts.parserBuilder().setSigningKey(signingKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}



/*
Todo


제너레이트 토큰-> 리프레쉬 토큰, 토큰 엑세스 토큰
클레임
 */

//package com.ssafy.campcino.service;
//
//import io.jsonwebtoken.*;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtTokenProvider {
//
//    private String secretKey = "trump"; // 실제로는 안전한 곳에 보관해야 해요.
//    private long validityInMilliseconds = 3600000; // 토큰의 유효기간: 1시간
//
//    // 토큰 생성 메서드
//    public String createToken(String username) {
//        Claims claims = Jwts.claims().setSubject(username); // 토큰에 담을 정보 설정
//
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + validityInMilliseconds); // 토큰 만료 시간
//
//        return Jwts.builder()
//                .setClaims(claims) // 정보 설정
//                .setIssuedAt(now) // 토큰 발행 시간
//                .setExpiration(validity) // 토큰 만료 시간 설정
//                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 시크릿 키 설정
//                .compact();
//    }
//
//    // 토큰에서 유저 정보 추출 메서드
//    public String getUsername(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey) // 시크릿 키 설정
//                .parseClaimsJws(token) // 토큰 파싱
//                .getBody()
//                .getSubject();
//    }
//
//    // 토큰 유효성 검증 메서드
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); // 토큰 파싱 및 검증
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false; // 유효하지 않은 토큰인 경우
//        }
//    }
//
//    // Request의 Header에서 토큰 정보를 가져오는 메서드
//    public String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization"); // 헤더에서 토큰 값 가져오기
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7); // "Bearer " 부분 제거
//        }
//        return null;
//    }
//}