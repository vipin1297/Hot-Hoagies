package com.spiralforge.hothoagies.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.spiralforge.hothoagies.dto.LoginRequestDto;
import com.spiralforge.hothoagies.dto.LoginResponseDto;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.repository.UserRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginServiceTest {
	LoginRequestDto loginRequestDto = null;
	LoginRequestDto loginRequestDto1 = null;
	User user = null;

	@InjectMocks
	LoginServiceImpl loginServiceImpl;

	@Mock
	UserRepository userRepository;

	@Before
	public void before() {
		loginRequestDto = new LoginRequestDto();
		loginRequestDto.setMobileNumber(9876543210L);

		loginRequestDto1 = new LoginRequestDto();
		loginRequestDto1.setMobileNumber(9876543211L);

		user = new User();
		user.setMobileNumber(9876543210L);
		user.setFullName("Muthu");
		user.setEmailId("muthu@gmail.com");
	}

	@Test
	public void testCheckLoginPositive() throws UserNotFoundException {
		Mockito.when(userRepository.findBymobileNumber(loginRequestDto.getMobileNumber())).thenReturn(user);
		LoginResponseDto response = loginServiceImpl.checkLogin(loginRequestDto);
		assertEquals(user.getFullName(), response.getFullName());
	}

	@Test(expected = UserNotFoundException.class)
	public void testCheckLoginException() throws UserNotFoundException {
		Mockito.when(userRepository.findBymobileNumber(loginRequestDto.getMobileNumber())).thenReturn(user);
		loginServiceImpl.checkLogin(loginRequestDto1);
	}
}
