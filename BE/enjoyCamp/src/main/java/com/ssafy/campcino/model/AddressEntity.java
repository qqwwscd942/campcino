
package com.ssafy.campcino.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressEntity {
    private int campId;
    private String campRoadAddress;
    private String campNumberAddress;
    private double longitude;
    private double latitude;

}
