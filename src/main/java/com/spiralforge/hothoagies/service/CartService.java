package com.spiralforge.hothoagies.service;

import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;

/**
 * Cart Service to handle crud operation of order details. 
 */
public interface CartService {
	
	/**
	 * This method used to save order detail information.
	 * 
	 * @param user
	 * @param orderRequestDto
	 * @return
	 */
	OrderDetail saveOrderDetail(User user, OrderRequestDto orderRequestDto);

}
