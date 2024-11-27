package com.ssafy.campcino.dto.requestDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
public class JoinDto {
    private String userId;
    private String userPassword;
    private String userPhone;
    private int userAge;
    private String userEmail;
    private String userGender;

}
