package com.ssafy.campcino.controller;

import com.ssafy.campcino.config.JwtTokenProvider;
import com.ssafy.campcino.dto.requestDto.JoinDto;
import com.ssafy.campcino.dto.requestDto.LoginDto;
import com.ssafy.campcino.dto.requestDto.UpdateUserDto;
import com.ssafy.campcino.model.UserEntity;
import com.ssafy.campcino.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/join")
    public ResponseEntity<String> registerUser(@RequestBody JoinDto joinDto) {
        joinDto.setUserPassword(passwordEncoder.encode(joinDto.getUserPassword()));
        userService.registerUser(joinDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        try {
            String userId = userService.authenticateUser(loginDto);
            if (userId == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body("아이디 또는 비밀번호가 잘못되었습니다.");
            }

            String accessToken = jwtTokenProvider.generateAccessToken(userId);
            String refreshToken = jwtTokenProvider.generateRefreshToken(userId);

            userService.updateRefreshToken(userId, refreshToken);

            ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(false) // 개발 환경에서는 false, 배포 시 true
                    .path("/")
                    .sameSite("Strict")
                    .maxAge(7 * 24 * 60 * 60) // 7일
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

            return ResponseEntity.ok(Map.of("accessToken", accessToken));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .body("로그인 처리 중 오류가 발생했습니다.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserInfo(@RequestHeader("Authorization") String token,
                                            @RequestBody UpdateUserDto updateUserDto) {
        try {
            // Authorization 헤더에서 "Bearer " 제거
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization 헤더가 유효하지 않습니다.");
            }

            // 액세스 토큰 유효성 검증
            if (!jwtTokenProvider.validateToken(token, true)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 액세스 토큰입니다.");
            }

            // 토큰에서 사용자 ID 추출
            String userId = jwtTokenProvider.getUserIdFromToken(token, true);

            // 사용자 정보 업데이트
            userService.updateUserInfo(userId, updateUserDto);

            return ResponseEntity.ok("회원정보가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원정보 업데이트 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;

        for (Cookie cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken, false)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body("리프레시 토큰이 유효하지 않습니다.");
        }

        String userId = jwtTokenProvider.getUserIdFromToken(refreshToken, false);
        String newAccessToken = jwtTokenProvider.generateAccessToken(userId);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = null;

            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }

            if (refreshToken != null) {
                String userId = jwtTokenProvider.getUserIdFromToken(refreshToken, false);
                userService.deleteRefreshToken(userId);
            }

            Cookie accessCookie = new Cookie("accessToken", null);
            accessCookie.setHttpOnly(true);
            accessCookie.setSecure(false);
            accessCookie.setPath("/");
            accessCookie.setMaxAge(0);

            Cookie refreshCookie = new Cookie("refreshToken", null);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setSecure(false);
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge(0);

            response.addCookie(accessCookie);
            response.addCookie(refreshCookie);

            return ResponseEntity.ok("로그아웃이 완료되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .body("로그아웃 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * 사용자 정보 조회
     */
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            // Authorization 헤더에서 "Bearer " 제거
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization 헤더가 유효하지 않습니다.");
            }

            // 액세스 토큰 유효성 검증
            if (!jwtTokenProvider.validateToken(token, true)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 액세스 토큰입니다.");
            }

            // 토큰에서 사용자 ID 추출
            String userId = jwtTokenProvider.getUserIdFromToken(token, true);

            // 사용자 정보 조회
            UserEntity user = userService.findByUserId(userId);
            if (user == null) {
                return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
            }

            // 사용자 정보 반환
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", user.getUserId());
            userInfo.put("email", user.getUserEmail());
            userInfo.put("phone", user.getUserPhone());
            userInfo.put("gender", user.getUserGender());
            // 필요한 다른 필드도 추가 가능

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다.");
        }
    }

}

