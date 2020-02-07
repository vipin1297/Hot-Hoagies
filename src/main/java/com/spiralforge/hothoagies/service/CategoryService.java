package com.spiralforge.hothoagies.service;

import java.util.List;

import com.spiralforge.hothoagies.dto.CategoryResponseDto;
import com.spiralforge.hothoagies.dto.FoodItemList;
import com.spiralforge.hothoagies.exception.CategoriesNotFoundException;
import com.spiralforge.hothoagies.exception.FoodItemListEmptyException;

/**
 * @author Sri Keerthna.
 * @since 2020-02-07.
 */
public interface CategoryService {

	public List<CategoryResponseDto> getCategoryList() throws CategoriesNotFoundException;

	public List<FoodItemList> getFoodItemList(long categoryId)
			throws CategoriesNotFoundException, FoodItemListEmptyException;
}
