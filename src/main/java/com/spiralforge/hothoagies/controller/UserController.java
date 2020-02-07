package com.spiralforge.hothoagies.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.hothoagies.dto.OrderDetailResponseDto;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.service.UserService;

/**
 * This controller is having Order history functionality.
 * 
 * @author Sri Keerthna.
 * @since 2020-02-07.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class UserController {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<OrderDetailResponseDto>> getOrderHistory(@PathVariable Long userId)
			throws UserNotFoundException {
		logger.info("Entered into categoryList method in controller");
		List<OrderDetailResponseDto> orderDetailList = userService.getOrderHistory(userId);
		return new ResponseEntity<>(orderDetailList, HttpStatus.OK);

	}
}
