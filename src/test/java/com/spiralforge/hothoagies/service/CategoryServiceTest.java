package com.spiralforge.foodplex.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.spiralforge.foodplex.controller.CategoryController;
import com.spiralforge.foodplex.controller.CategoryControllerTest;
import com.spiralforge.foodplex.dto.CategoryResponseDto;
import com.spiralforge.foodplex.dto.ItemCategoryDto;
import com.spiralforge.foodplex.dto.ItemDto;
import com.spiralforge.foodplex.dto.ItemPriceDto;
import com.spiralforge.foodplex.entity.Category;
import com.spiralforge.foodplex.entity.Item;
import com.spiralforge.foodplex.entity.User;
import com.spiralforge.foodplex.entity.VendorItem;
import com.spiralforge.foodplex.repository.CategoryRepository;
import com.spiralforge.foodplex.repository.ItemRepository;
import com.spiralforge.foodplex.repository.UserRepository;
import com.spiralforge.foodplex.repository.VendorItemRepository;

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
	VendorItemRepository vendorItemRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	ItemRepository itemRepository;

	User user = new User();
	Category category = new Category();
	List<Category> categoryList = new ArrayList<>();
	CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
	ItemCategoryDto itemCategoryDto= new ItemCategoryDto();
	List<ItemCategoryDto> itemCategoryDtoList = new ArrayList<>();
	List<ItemDto> itemList = new ArrayList<>();
	ItemDto ItemDto=new ItemDto();
	List<VendorItem> vendorItemList = new ArrayList<>();
	VendorItem vendorItem = new VendorItem();
	Item item = new Item();
	List<Item> itemLists= new ArrayList<>();
	List<ItemPriceDto> itemPriceDtoList = new ArrayList<>();
	ItemPriceDto itemPriceDto = new ItemPriceDto();
	@Before
	public void setUp() {
		category.setCategoryId(1);
		category.setCategoryName("Beverage");
		categoryList.add(category);
		
		categoryResponseDto.setStatusCode(HttpStatus.OK.value());
		
		ItemDto.setItemId(1);
		ItemDto.setItemName("tea");
		itemList.add(ItemDto);
		
		itemCategoryDto.setItemList(itemList);
		itemCategoryDto.setCategoryName("Salads");
		itemCategoryDtoList.add(itemCategoryDto);
		
		user.setUserId(1);
		item.setItemId(1);
		item.setItemName("tea");
		item.setCategory(category);
		vendorItem.setItem(item);
		vendorItem.setPrice(200.00);
		vendorItemList.add(vendorItem);
		itemLists.add(item);
		
		itemPriceDto.setCategoryId(1);
		itemPriceDto.setItemName("tea");
		itemPriceDto.setItemId(1);
		itemPriceDto.setItemPrice(25D);
		itemPriceDtoList.add(itemPriceDto);
	}
	
	@Test
	public void testCategoryListPositive() {
		Mockito.when(userRepository.findByUserId(user.getUserId())).thenReturn(user);
		Mockito.when(vendorItemRepository.findVendorItemByUser(user)).thenReturn(vendorItemList);
		Mockito.when(itemRepository.findAll()).thenReturn(itemLists);
		List<ItemCategoryDto> response=categoryService.getItemCategoryListByVendorId(1);
		assertEquals(1, response.size());
		
	}
}
