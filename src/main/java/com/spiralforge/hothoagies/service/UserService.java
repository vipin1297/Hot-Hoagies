package com.spiralforge.hothoagies.service;

import java.util.Optional;

import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;

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
	OrderDetail placeOrder(Long userId, OrderRequestDto orderRequestDto);

	/**
	 * This method is used to get user by Id. 
	 * 
	 * @param userId
	 * @return
	 */
	Optional<User> getUserByUserId(Long userId);
	
}
