package com.spiralforge.hothoagies.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.http.ResponseEntity;

import com.spiralforge.hothoagies.dto.CategoryResponseDto;
import com.spiralforge.hothoagies.entity.Category;
import com.spiralforge.hothoagies.exception.CategoriesNotFoundException;
import com.spiralforge.hothoagies.service.CategoryService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CategoryControllerTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryControllerTest.class);

	@InjectMocks
	CategoryController categoryController;

	@Mock
	CategoryService categoryService;

	Category category = new Category();
	List<Category> categoryList = new ArrayList<>();
	CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
	List<CategoryResponseDto> responseList = new ArrayList<>();

	@Before
	public void setUp() {
		category.setCategoryId(1L);
		category.setCategoryName("veg pizzas");
		categoryList.add(category);
		BeanUtils.copyProperties(categoryList, categoryResponseDto);
		responseList.add(categoryResponseDto);
	}

	@Test
	public void testCategoryListPositive() throws CategoriesNotFoundException {
		logger.info("Entered into categoryList method in controller");
		Mockito.when(categoryService.getCategoryList()).thenReturn(responseList);
		ResponseEntity<List<CategoryResponseDto>> result = categoryController.getCategoryList();
		assertEquals(200, result.getStatusCodeValue());
	}
}