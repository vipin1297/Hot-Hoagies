package com.spiralforge.hothoagies.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.repository.CartItemRepository;

@Service
public class CartServiceImpl implements CartService {

	Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	private CartItemRepository cartItemRepository;

	/**
	 * @author Sujal
	 *
	 *         Method is used to save the orders for the user.
	 *
	 * @param user            is used to set the user
	 * @param orderRequestDto is the details of orders
	 * @return is the detail of the orders.
	 */
	@Transactional
	@Override
	public OrderDetail saveOrderDetail(User user, OrderRequestDto orderRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
