package com.spiralforge.hothoagies.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.hothoagies.constants.ApplicationConstants;
import com.spiralforge.hothoagies.dto.CategoryResponseDto;
import com.spiralforge.hothoagies.dto.FoodItemList;
import com.spiralforge.hothoagies.dto.FoodItemResponseDto;
import com.spiralforge.hothoagies.exception.CategoriesNotFoundException;
import com.spiralforge.hothoagies.exception.FoodItemListEmptyException;
import com.spiralforge.hothoagies.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

/**
 * This controller is having listing category functionality.
 * 
 * @author Sri Keerthna.
 * @since 2020-02-07.
 */
@RestController
@RequestMapping("/categories")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
public class CategoryController {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-05. In this method list of categories are fetched from
	 *        database.
	 * @return List of categories.
	 * @throws CategoriesNotFoundException if there is no category then it will
	 *                                     throw an error.
	 */
	@GetMapping
	public ResponseEntity<List<CategoryResponseDto>> getCategoryList() throws CategoriesNotFoundException {
		logger.info("Entered into categoryList method in controller");
		List<CategoryResponseDto> categoryList = categoryService.getCategoryList();
		return new ResponseEntity<>(categoryList, HttpStatus.OK);
	}

	@GetMapping("/{categoryId}/fooditems")
	public ResponseEntity<FoodItemResponseDto> getFoodItemList(@PathVariable(name = "categoryId") long categoryId)
			throws CategoriesNotFoundException, FoodItemListEmptyException {
		log.info("For displaying the list of food items on a particular category");
		List<FoodItemList> foodItemList = categoryService.getFoodItemList(categoryId);
		FoodItemResponseDto foodItemResponseDto = new FoodItemResponseDto();
		if (!(foodItemList.isEmpty())) {
			foodItemResponseDto.setFoodItemList(foodItemList);
			foodItemResponseDto.setStatusCode(ApplicationConstants.SUCCESS_CODE);
			foodItemResponseDto.setMessage(ApplicationConstants.FOOD_ITEMLIST_SUCCESS_MESSAGE);
			return new ResponseEntity<>(foodItemResponseDto, HttpStatus.OK);
		}
		foodItemResponseDto.setStatusCode(ApplicationConstants.NOTFOUND_CODE);
		foodItemResponseDto.setMessage(ApplicationConstants.FOOD_ITEMLIST_EMPTY_MESSAGE);
		return new ResponseEntity<>(foodItemResponseDto, HttpStatus.NOT_FOUND);

	}
}
