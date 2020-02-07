package com.spiralforge.hothoagies.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.hothoagies.entity.CartItem;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

	Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);

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
	public void saveCartItem(User user, OrderDetail orderDetail) {
		List<CartItem> cartItemList = getCartItemByUser(user);
		if (Objects.isNull(cartItemList) && cartItemList.isEmpty()) {
			cartItemList.stream().map(cartItem -> {
				cartItem.setOrderDetail(orderDetail);
				return cartItem;
			}).collect(Collectors.toList());
			
			cartItemRepository.saveAll(cartItemList);
		}
	}

	@Override
	public List<CartItem> getCartItemByUser(User user) {
		return cartItemRepository.getCartItemByUser(user);
	}

}
