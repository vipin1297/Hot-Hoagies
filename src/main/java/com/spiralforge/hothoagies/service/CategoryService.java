package com.spiralforge.hothoagies.service;

import java.util.List;

import com.spiralforge.hothoagies.dto.CategoryResponseDto;
import com.spiralforge.hothoagies.exception.CategoriesNotFoundException;

/**
 * @author Sri Keerthna.
 * @since 2020-02-07.
 */
public interface CategoryService {

	public List<CategoryResponseDto> getCategoryList() throws CategoriesNotFoundException;
}
