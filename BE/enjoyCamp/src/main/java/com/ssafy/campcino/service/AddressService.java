
package com.ssafy.campcino.service;

import com.ssafy.campcino.model.AddressEntity;

public interface AddressService {
    AddressEntity getAddressByCampId(int campId);
}
