package com.ssafy.campcino.mapper;

import com.ssafy.campcino.dto.requestDto.JoinDto;
import com.ssafy.campcino.dto.requestDto.UpdateUserDto;
import com.ssafy.campcino.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void insertUser(JoinDto joinDto);
    UserEntity findByUserId(String userId);
    void updateRefreshToken(@Param("userId") String userId, @Param("refreshToken") String refreshToken);
    String findRefreshTokenByUserId(String userId); // 리프레시 토큰 조회
    void updateUserInfo(@Param("userId") String userId, @Param("updateUserDto") UpdateUserDto updateUserDto);
}
