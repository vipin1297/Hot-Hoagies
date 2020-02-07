package com.spiralforge.hothoagies.service;

import java.time.LocalTime;
import java.util.Optional;

import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.ValidationFailedException;

/**
 * @author Sujal.
 * @since 2020-02-07.
 */
public interface UserService {
	

	/**
	 * This method is used to place order. 
	 * 
	 * @param userId
	 * @param orderRequestDto
	 * @return
	 * @throws InvalidUpiIdException
	 */
	OrderDetail placeOrder(Long userId, OrderRequestDto orderRequestDto) throws ValidationFailedException;

	/**
	 * This method is used to get user by Id. 
	 * 
	 * @param userId
	 * @return
	 */
	Optional<User> getUserByUserId(Long userId);

	LocalTime getEta(OrderDetail orderDetail);

	OrderDetail getOrder(Long orderId);
	
}
