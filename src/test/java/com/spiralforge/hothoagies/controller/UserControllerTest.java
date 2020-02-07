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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spiralforge.hothoagies.dto.PreferenceList;
import com.spiralforge.hothoagies.dto.PreferenceResponseDto;
import com.spiralforge.hothoagies.exception.PreferenceListEmptyException;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {
	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	List<PreferenceList> preferenceList = null;
	List<PreferenceList> preferenceList1 = null;
	PreferenceList preference = null;

	@Before
	public void setUp() {
		preferenceList = new ArrayList<>();
		preference = new PreferenceList();
		preference.setCategoryId(1L);
		preference.setCategoryName("Pizza");
		preference.setFoodItemId(1L);
		preference.setFoodItemName("Veg-Pizza");
		preferenceList.add(preference);

		preferenceList1 = new ArrayList<>();
	}

	@Test
	public void testGetPreferenceDetailsPositive() throws UserNotFoundException, PreferenceListEmptyException {
		Long userId = 1L;
		Mockito.when(userService.getPreferenceDetails(userId)).thenReturn(preferenceList);
		ResponseEntity<PreferenceResponseDto> response = userController.getPreferenceDetails(userId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testGetPreferenceDetailsNegative() throws UserNotFoundException, PreferenceListEmptyException {
		Long userId = 1L;
		Mockito.when(userService.getPreferenceDetails(userId)).thenReturn(preferenceList1);
		ResponseEntity<PreferenceResponseDto> response = userController.getPreferenceDetails(userId);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
