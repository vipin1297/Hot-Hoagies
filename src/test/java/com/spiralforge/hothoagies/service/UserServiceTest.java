package com.spiralforge.hothoagies.service;

import static org.junit.Assert.assertEquals;

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

import com.spiralforge.hothoagies.dto.PreferenceList;
import com.spiralforge.hothoagies.entity.CartItem;
import com.spiralforge.hothoagies.entity.Category;
import com.spiralforge.hothoagies.entity.FoodItem;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.PreferenceListEmptyException;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.repository.CartItemRepository;
import com.spiralforge.hothoagies.repository.CategoryRepository;
import com.spiralforge.hothoagies.repository.FoodItemRepository;
import com.spiralforge.hothoagies.repository.UserRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {
	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	UserRepository userRepository;

	@Mock
	CartItemRepository cartItemRepository;

	@Mock
	FoodItemRepository foodItemRepository;

	@Mock
	CategoryRepository categoryRepository;

	User user = null;
	List<CartItem> userCartItem = null;
	List<CartItem> userCartItem1 = null;
	CartItem cartItem = null;
	FoodItem item = null;
	Category category = null;
	List<PreferenceList> preferenceList = null;
	PreferenceList preference = null;

	@Before
	public void setUp() {
		user = new User();
		user.setUserId(1L);

		userCartItem = new ArrayList<>();

		userCartItem1 = new ArrayList<>();
		cartItem = new CartItem();
		item = new FoodItem();
		category = new Category();
		category.setCategoryId(1L);
		item.setFoodItemId(1L);
		item.setCategory(category);
		cartItem.setCartItemId(1L);
		cartItem.setItem(item);
		userCartItem1.add(cartItem);
		preferenceList = new ArrayList<>();
		preference = new PreferenceList();
		preference.setCategoryId(1L);
		preference.setCategoryName("Pizza");
		preference.setFoodItemId(1L);
		preference.setFoodItemName("Veg-Pizza");
		preferenceList.add(preference);
	}

	@Test(expected = UserNotFoundException.class)
	public void testGetPreferenceUserException() throws UserNotFoundException, PreferenceListEmptyException {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		userServiceImpl.getPreferenceDetails(2L);
	}

	@Test(expected = PreferenceListEmptyException.class)
	public void testGetPreferenceEmptyException() throws UserNotFoundException, PreferenceListEmptyException {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(cartItemRepository.findAllByUser(1L)).thenReturn(userCartItem);
		userServiceImpl.getPreferenceDetails(1L);
	}

	@Test
	public void testGetPreferencePositive() throws UserNotFoundException, PreferenceListEmptyException {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(cartItemRepository.findAllByUser(1L)).thenReturn(userCartItem1);
		Mockito.when(foodItemRepository.findByFoodItemId(1L)).thenReturn(item);
		Mockito.when(categoryRepository.findByCategoryId(1L)).thenReturn(category);
		List<PreferenceList> response = userServiceImpl.getPreferenceDetails(1L);
		assertEquals(preferenceList.size(), response.size());
	}

}
