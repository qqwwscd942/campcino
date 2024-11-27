package com.ssafy.campcino.service;

import com.ssafy.campcino.dto.requestDto.JoinDto;
import com.ssafy.campcino.dto.requestDto.LoginDto;
import com.ssafy.campcino.mapper.UserMapper;
import com.ssafy.campcino.model.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ssafy.campcino.dto.requestDto.UpdateUserDto;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(JoinDto joinDto) {
        userMapper.insertUser(joinDto);
    }

    @Override
    public UserEntity findByUserId(String userId) {
        return userMapper.findByUserId(userId);
    }

    @Override
    public void updateRefreshToken(String userId, String refreshToken) {
        userMapper.updateRefreshToken(userId, refreshToken);
    }

    @Override
    public boolean validateRefreshToken(String userId, String refreshToken) {
        String storedRefreshToken = userMapper.findRefreshTokenByUserId(userId);
        return refreshToken != null && refreshToken.equals(storedRefreshToken);
    }

    @Override
    public void deleteRefreshToken(String userId) {
        userMapper.updateRefreshToken(userId, null);
    }

    @Override
    public void updateUserInfo(String userId, UpdateUserDto updateUserDto) {
        userMapper.updateUserInfo(userId, updateUserDto);
    }

    @Override
    public String authenticateUser(LoginDto loginDto) {
        UserEntity user = userMapper.findByUserId(loginDto.getUserId());
        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getUserPassword())) {
            return user.getUserId(); // 인증 성공 시 사용자 ID 반환
        } else {
            throw new RuntimeException("Invalid username or password"); // 인증 실패 처리
        }
    }
}
