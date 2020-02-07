package com.spiralforge.hothoagies.service;

import java.util.List;

import com.spiralforge.hothoagies.entity.CartItem;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;

/**
 * Cart Service to handle crud operation of order details. 
 */
public interface CartItemService {
	
	/**
	 * This method used to save order detail information.
	 * 
	 * @param user
	 * @param orderRequestDto
	 * @return
	 */
	void saveCartItem(User user, OrderDetail orderDetail, List<CartItem> cartItemList);

	List<CartItem> getCartItemByUser(User user);

}
