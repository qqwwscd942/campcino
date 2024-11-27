
package com.ssafy.campcino.mapper;

import com.ssafy.campcino.model.AddressEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface AddressMapper {
    AddressEntity findByCampId(@Param("campId") int campId);
}
