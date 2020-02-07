package com.spiralforge.hothoagies.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.dto.OrderResponseDto;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.exception.ValidationFailedException;
import com.spiralforge.hothoagies.service.UserService;
import com.spiralforge.hothoagies.util.ApiConstant;
import com.spiralforge.hothoagies.util.OrderValidator;

import lombok.extern.slf4j.Slf4j;

/*
 *  Method is used for user login for the users
 */
@RequestMapping("/")
@RestController
@Slf4j
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private OrderValidator<Long, OrderRequestDto> orderValidator;

	@Autowired
	private UserService userService;

	/**
	 * @author Sujal
	 * @since 2020-02-07
	 * 
	 *        Method is used to classify a user as a staff or customer
	 * 
	 * @param loginRequestDto which takes input as a mobile number
	 * @return LoginResponseDto includes all particulars of the user
	 * @throws BeansException
	 * @throws UserNotFoundException expose a message when user is not found
	 */
	@PostMapping("users/{userId}/order")
	public ResponseEntity<OrderResponseDto> placeOrder(@PathVariable("userId") Long userId,
			@RequestBody OrderRequestDto orderRequestDto) throws ValidationFailedException {

		if (orderValidator.validate(userId, orderRequestDto)) {
			OrderResponseDto orderResponseDto = new OrderResponseDto();
			OrderDetail orderDetail = userService.placeOrder(userId, orderRequestDto);
			logger.info("place order started");
			if (Objects.isNull(orderDetail)) {
				orderResponseDto.setStatusCode(ApiConstant.NO_CONTENT_CODE);
				orderResponseDto.setMessage(ApiConstant.NO_ELEMENT_FOUND);
				return new ResponseEntity<>(orderResponseDto, HttpStatus.NO_CONTENT);
			} else {
				orderResponseDto.setOrderId(orderDetail.getOrderDetailId());
				orderResponseDto.setEta(userService.getEta(orderDetail));
				orderResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
				orderResponseDto.setMessage(ApiConstant.SUCCESS);
				return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);

			}
		} else {
			logger.error("invalid order data");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @author Sujal
	 * @since 2020-02-07
	 * 
	 *        Method is used to classify a user as a staff or customer
	 * 
	 * @param loginRequestDto which takes input as a mobile number
	 * @return LoginResponseDto includes all particulars of the user
	 * @throws BeansException
	 * @throws UserNotFoundException expose a message when user is not found
	 */
	@GetMapping("orders/{orderId}")
	public ResponseEntity<OrderResponseDto> getOrder(@PathVariable("orderId") Long orderId) {

		OrderResponseDto orderResponseDto = new OrderResponseDto();
		OrderDetail orderDetail = userService.getOrder(orderId);
		logger.info("place order started");
		if (Objects.isNull(orderDetail)) {
			orderResponseDto.setStatusCode(ApiConstant.NO_CONTENT_CODE);
			orderResponseDto.setMessage(ApiConstant.NO_ELEMENT_FOUND);
			return new ResponseEntity<>(orderResponseDto, HttpStatus.NO_CONTENT);
		} else {
			orderResponseDto.setOrderId(orderDetail.getOrderDetailId());
			orderResponseDto.setEta(userService.getEta(orderDetail));
			orderResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			orderResponseDto.setMessage(ApiConstant.SUCCESS);
			return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);

		}
	}
}
