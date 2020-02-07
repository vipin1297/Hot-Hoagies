package com.spiralforge.hothoagies.util;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.exception.ValidationFailedException;
import com.spiralforge.hothoagies.service.CartService;
import com.spiralforge.hothoagies.service.UserService;

/**
 * 
 * @author Sujal The booking validator is used to validate the booking
 *         information
 *
 */
@Component("orderValidator")
public class OrderValidatorImpl implements OrderValidator<Long, OrderRequestDto> {

	Logger logger = LoggerFactory.getLogger(OrderValidatorImpl.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;

	/**
	 * @throws UserNotFoundException 
	 * @throws InvalidPaymentException 
	 * 
	 */
	@Override
	public Boolean validate(Long userId, OrderRequestDto orderRequestDto) throws ValidationFailedException {

		if(!userService.getUserByUserId(userId).isPresent())
			throw new ValidationFailedException(ApiConstant.INVALID_USER);
		else if(Objects.isNull(orderRequestDto.getCartId()))
			throw new ValidationFailedException(ApiConstant.INVALID_ITEM);
		else if(Objects.isNull(orderRequestDto.getPaymentMode()))
			throw new ValidationFailedException(ApiConstant.INVALID_PAYMENT);
		return true;
	}
}
