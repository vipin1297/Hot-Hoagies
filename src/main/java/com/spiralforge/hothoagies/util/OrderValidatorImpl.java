package com.spiralforge.hothoagies.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.entity.CartItem;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.exception.ValidationFailedException;
import com.spiralforge.hothoagies.service.CartItemService;
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
	private CartItemService cartItemService;

	/**
	 * @throws UserNotFoundException
	 * @throws InvalidPaymentException
	 * 
	 */
	@Override
	public Boolean validate(Long userId, OrderRequestDto orderRequestDto) throws ValidationFailedException {

		Optional<User> user = userService.getUserByUserId(userId);
		if (!user.isPresent())
			throw new ValidationFailedException(ApiConstant.INVALID_USER);
		else if (Objects.isNull(orderRequestDto.getPaymentMode()))
			throw new ValidationFailedException(ApiConstant.INVALID_PAYMENT);
		else if (Objects.isNull(orderRequestDto.getUpiId()))
			throw new ValidationFailedException(ApiConstant.INVALID_UPI);
		else if (getCartItemByUser(user.get()).isEmpty())
			throw new ValidationFailedException(ApiConstant.ITEM_NOT_FOUND);
		return true;
	}

	private List<CartItem> getCartItemByUser(User user) {
		List<CartItem> list = cartItemService.getCartItemByUser(user);
		if(Objects.isNull(list))
			return Collections.emptyList();
		return list;
	}
}
