package com.uautso.sovs.service;

import com.uautso.sovs.dto.CandidateDto;
import com.uautso.sovs.dto.CategoryDto;
import com.uautso.sovs.model.Category;
import com.uautso.sovs.utils.Response;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface CategoryService {

    Page<Category> getAllCategory(Pageable pageable);

    Response<Category> createCategory(CategoryDto categoryDto);

    Response<Category> getCategoryByUuid(String uuid);

    

}
