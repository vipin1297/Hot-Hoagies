package com.spiralforge.hothoagies.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

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
import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.dto.OrderResponseDto;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.exception.ValidationFailedException;
import com.spiralforge.hothoagies.service.LoginService;
import com.spiralforge.hothoagies.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {
	
	OrderRequestDto orderRequestDto = null;
	User user = null;
	OrderDetail orderDetail = null;

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Before
	public void before() {
		orderRequestDto = new OrderRequestDto();
		orderRequestDto.setPaymentMode("PayTM");;
		orderRequestDto.setUpiId("sujal@upi");
		
		user = new User();
		user.setUserId(1L);
		user.setUpiId("sujal@upi");
		user.setEmailId("sujal@gmail.com");
		
		orderDetail=new OrderDetail();
		orderDetail.setOrderDetailId(1L);
		orderDetail.setOrderTime(LocalTime.now());
	}

	@Test
	public void testPlaceOrderPositive() throws ValidationFailedException {
		Long userId=1L;
		Mockito.when(userService.placeOrder(userId, orderRequestDto)).thenReturn(orderDetail);
		Mockito.when(userService.getEta(orderDetail)).thenReturn(LocalTime.now());
		OrderResponseDto response = userController.placeOrder(userId, orderRequestDto).getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
