
package com.ssafy.campcino.mapper;

import com.ssafy.campcino.model.SidoEntity;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface SidoMapper {
    List<SidoEntity> findAll();
}
