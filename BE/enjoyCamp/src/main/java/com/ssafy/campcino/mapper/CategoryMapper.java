
package com.ssafy.campcino.mapper;

import com.ssafy.campcino.model.CategoryEntity;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CategoryMapper {
    List<CategoryEntity> findAll();
}
