package com.spiralforge.hothoagies.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.spiralforge.hothoagies.dto.CategoryResponseDto;
import com.spiralforge.hothoagies.dto.FoodItemList;
import com.spiralforge.hothoagies.entity.Category;
import com.spiralforge.hothoagies.entity.FoodItem;
import com.spiralforge.hothoagies.exception.CategoriesNotFoundException;
import com.spiralforge.hothoagies.exception.FoodItemListEmptyException;
import com.spiralforge.hothoagies.repository.CategoryRepository;
import com.spiralforge.hothoagies.repository.FoodItemRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CategoryServiceTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceTest.class);

	@InjectMocks
	CategoryServiceImpl categoryService;

	@Mock
	CategoryRepository categoryRepository;

	@Mock
	FoodItemRepository foodItemRepository;

	Category category = new Category();
	List<Category> categoryList = new ArrayList<>();
	CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
	List<CategoryResponseDto> responseList = new ArrayList<>();

	List<FoodItem> foodItemList = new ArrayList<>();
	List<FoodItem> foodItemList1 = new ArrayList<>();
	FoodItem foodItem = new FoodItem();;

	@Before
	public void setUp() {
		category.setCategoryId(1L);
		category.setCategoryName("veg pizzas");
		categoryList.add(category);
		BeanUtils.copyProperties(categoryList, categoryResponseDto);
		responseList.add(categoryResponseDto);

		foodItem.setFoodItemId(1L);
		foodItem.setFoodItemName("Pizza");
		foodItem.setPrice(233.99);
		foodItem.setCategory(category);
		foodItemList.add(foodItem);
	}

	@Test
	public void testCategoryListPositive() throws CategoriesNotFoundException {
		Mockito.when(categoryRepository.findAll()).thenReturn(categoryList);
		logger.info("Got the list of categories");
		List<CategoryResponseDto> response = categoryService.getCategoryList();
		assertEquals(1, response.size());

	}

	@Test(expected = CategoriesNotFoundException.class)
	public void testCategoryListNegative() throws CategoriesNotFoundException {
		List<Category> categoriesList = new ArrayList<>();
		Mockito.when(categoryRepository.findAll()).thenReturn(categoriesList);
		logger.error("Categories not found");
		categoryService.getCategoryList();
	}

	@Test(expected = CategoriesNotFoundException.class)
	public void testGetFoodItemCategoryException() throws CategoriesNotFoundException, FoodItemListEmptyException {
		Long categoryId = 1L;
		Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
		categoryService.getFoodItemList(2L);
	}

	@Test(expected = FoodItemListEmptyException.class)
	public void testGetFoodItemListEmptyException() throws CategoriesNotFoundException, FoodItemListEmptyException {
		Long categoryId = 1L;
		Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
		Mockito.when(foodItemRepository.findByCategory(category)).thenReturn(foodItemList1);
		categoryService.getFoodItemList(1L);
	}

	@Test
	public void testGetFoodItemListPositive() throws CategoriesNotFoundException, FoodItemListEmptyException {
		Long categoryId = 1L;
		Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
		Mockito.when(foodItemRepository.findByCategory(category)).thenReturn(foodItemList);
		categoryService.getFoodItemList(1L);
	}
}