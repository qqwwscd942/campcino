package com.ssafy.campcino.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperatingHoursDto {
	private boolean o_weekday;
    private boolean o_weekend;
    private boolean o_spring;
    private boolean o_summer;
    private boolean o_fall;
    private boolean o_winter;
}
