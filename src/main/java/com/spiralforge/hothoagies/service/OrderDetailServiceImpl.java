package com.spiralforge.hothoagies.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.hothoagies.constants.ApplicationConstants;
import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.repository.OrderDetailRepository;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	Logger logger = LoggerFactory.getLogger(OrderDetailServiceImpl.class);

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private CartItemService cartItemService;

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
		logger.info("inside order place method");
		OrderDetail orderDetail1 = null;
		OrderDetail orderDetail = new OrderDetail();
		BeanUtils.copyProperties(orderRequestDto, orderDetail);
		orderDetail.setOrderStatus(ApplicationConstants.CONFIRMED_STATUS);
		orderDetail.setOrderDate(LocalDate.now());
		orderDetail.setOrderTime(LocalTime.now());
		orderDetail.setUser(user);
		orderDetail1 = orderDetailRepository.save(orderDetail);

		if (!Objects.isNull(orderDetail1))
			cartItemService.saveCartItem(user, orderDetail1);
		return orderDetail;
	}

}
