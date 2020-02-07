package com.spiralforge.hothoagies.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.hothoagies.constants.ApplicationConstants;
import com.spiralforge.hothoagies.dto.CategoryResponseDto;
import com.spiralforge.hothoagies.entity.Category;
import com.spiralforge.hothoagies.exception.CategoriesNotFoundException;
import com.spiralforge.hothoagies.repository.CategoryRepository;

/**
 * @author Sri Keerthna.
 * @since 2020-02-07.
 */
@Service
public class CategoryServiceImpl implements CategoryService{

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	CategoryRepository categoryRepository;
	
	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-05. In this method list of categories are fetched from
	 *        database.
	 * @return List of categories.
	 * @throws CategoriesNotFoundException if there is no category then it will throw an error.
	 */
	@Override
	public List<CategoryResponseDto> getCategoryList() throws CategoriesNotFoundException {
		List<Category> categoryList=categoryRepository.findAll();
		if(categoryList.isEmpty()) {
			logger.error("Categories not found");
			throw new CategoriesNotFoundException(ApplicationConstants.CATEGORY_NOT_FOUND_EXCEPTION);
		}
		else {
		List<CategoryResponseDto> categoryResponseDto=new ArrayList<>();
		categoryList.forEach(list->{
			CategoryResponseDto responseDto = new CategoryResponseDto();
			BeanUtils.copyProperties(list, responseDto);
			categoryResponseDto.add(responseDto);
		});
		logger.info("Got the list of categories");
		return categoryResponseDto;
	}
	}

}
