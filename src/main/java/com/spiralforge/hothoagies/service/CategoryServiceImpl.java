package com.spiralforge.hothoagies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.hothoagies.constants.ApplicationConstants;
import com.spiralforge.hothoagies.dto.CategoryResponseDto;
import com.spiralforge.hothoagies.dto.FoodItemList;
import com.spiralforge.hothoagies.entity.Category;
import com.spiralforge.hothoagies.entity.FoodItem;
import com.spiralforge.hothoagies.exception.CategoriesNotFoundException;
import com.spiralforge.hothoagies.exception.FoodItemListEmptyException;
import com.spiralforge.hothoagies.repository.CategoryRepository;
import com.spiralforge.hothoagies.repository.FoodItemRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sri Keerthna.
 * @since 2020-02-07.
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	FoodItemRepository foodItemRepository;

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-05. In this method list of categories are fetched from
	 *        database.
	 * @return List of categories.
	 * @throws CategoriesNotFoundException if there is no category then it will
	 *                                     throw an error.
	 */
	@Override
	public List<CategoryResponseDto> getCategoryList() throws CategoriesNotFoundException {
		List<Category> categoryList = categoryRepository.findAll();
		if (categoryList.isEmpty()) {
			logger.error("Categories not found");
			throw new CategoriesNotFoundException(ApplicationConstants.CATEGORY_NOT_FOUND_EXCEPTION);
		}
		List<CategoryResponseDto> categoryResponseDto = new ArrayList<>();
		categoryList.forEach(list -> {
			CategoryResponseDto responseDto = new CategoryResponseDto();
			BeanUtils.copyProperties(list, responseDto);
			categoryResponseDto.add(responseDto);
		});
		logger.info("Got the list of categories");
		return categoryResponseDto;
	}

	/**
	 * @author Muthu
	 * 
	 *         Method is used to get the list of all the food items available on a
	 *         particular category
	 * 
	 * @param categoryId fetch the details of a category by a unique id
	 * @return
	 * @throws CategoriesNotFoundException which is thrown when no category is found
	 * @throws FoodItemListEmptyException  which is thrown when the food list on a
	 *                                     category is found empty
	 */
	@Override
	public List<FoodItemList> getFoodItemList(long categoryId)
			throws CategoriesNotFoundException, FoodItemListEmptyException {
		Optional<Category> category = categoryRepository.findById(categoryId);
		if (!(category.isPresent())) {
			log.error(ApplicationConstants.CATEGORY_NOT_FOUND_EXCEPTION);
			throw new CategoriesNotFoundException(ApplicationConstants.CATEGORY_NOT_FOUND_EXCEPTION);
		}
		List<FoodItem> foodItemDet = foodItemRepository.findByCategory(category.get());
		if (foodItemDet.isEmpty()) {
			log.error(ApplicationConstants.FOOD_ITEMLIST_EMPTY_MESSAGE);
			throw new FoodItemListEmptyException(ApplicationConstants.FOOD_ITEMLIST_EMPTY_MESSAGE);
		}
		List<FoodItemList> foodItemList = new ArrayList<>();
		foodItemDet.forEach(foodItem -> {
			FoodItemList foodItemDetails = new FoodItemList();
			foodItemDetails.setFoodItemId(foodItem.getFoodItemId());
			foodItemDetails.setFoodItemName(foodItem.getFoodItemName());
			foodItemDetails.setPrice(foodItem.getPrice());
			foodItemList.add(foodItemDetails);
		});
		return foodItemList;
	}

}
