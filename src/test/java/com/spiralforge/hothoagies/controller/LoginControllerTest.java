package com.spiralforge.hothoagies.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spiralforge.hothoagies.dto.LoginRequestDto;
import com.spiralforge.hothoagies.dto.LoginResponseDto;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.service.LoginService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginControllerTest {
	LoginRequestDto loginRequestDto = null;
	LoginResponseDto loginResponse = null;
	User user = null;

	@InjectMocks
	LoginController loginController;

	@Mock
	LoginService loginService;

	@Before
	public void before() {
		loginRequestDto = new LoginRequestDto();
		loginRequestDto.setMobileNumber(9876543210L);

		loginResponse = new LoginResponseDto();
	}

	@Test
	public void testCheckLoginPositive() throws UserNotFoundException {
		Mockito.when(loginService.checkLogin(loginRequestDto)).thenReturn(loginResponse);
		ResponseEntity<LoginResponseDto> response = loginController.checkLogin(loginRequestDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
