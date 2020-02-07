package com.spiralforge.hothoagies.service;

import java.util.Optional;

import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;

/**
 * OrderDetailService to handle crud operation of order details. 
 */
public interface OrderDetailService {
	
	/**
	 * This method used to save order detail information.
	 * 
	 * @param user
	 * @param orderRequestDto
	 * @return
	 */
	OrderDetail saveOrderDetail(User user, OrderRequestDto orderRequestDto);

	Optional<OrderDetail> getOrder(Long orderId);

}
