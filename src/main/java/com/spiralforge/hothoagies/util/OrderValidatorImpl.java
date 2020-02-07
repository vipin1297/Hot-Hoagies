package com.spiralforge.hothoagies.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.service.UserService;

/**
 * 
 * @author Sujal The booking validator is used to validate the booking
 *         information
 *
 */
@Component("orderValidator")
public class OrderValidatorImpl implements OrderValidator<Integer, OrderRequestDto> {

	Logger logger = LoggerFactory.getLogger(OrderValidatorImpl.class);

	@Autowired
	private UserService userService;

	/**
	 * 
	 */
	@Override
	public Boolean validate(Integer userId, OrderRequestDto orderRequestDto) {

		return true;
	}
}
