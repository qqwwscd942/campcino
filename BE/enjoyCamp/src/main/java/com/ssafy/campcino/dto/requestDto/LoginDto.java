package com.ssafy.campcino.dto.requestDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String userId;
    private String password;
}
