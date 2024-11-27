
package com.ssafy.campcino.dto.requestDto;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String email;
    private String phone;
    private String gender;
    // 필요에 따라 추가 필드
}