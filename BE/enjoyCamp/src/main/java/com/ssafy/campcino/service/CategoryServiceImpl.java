
package com.ssafy.campcino.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.campcino.mapper.CategoryMapper;
import com.ssafy.campcino.model.CategoryEntity;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryMapper.findAll();
    }
}
