package com.spiralforge.hothoagies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.hothoagies.constants.ApplicationConstants;
import com.spiralforge.hothoagies.dto.OrderDetailResponseDto;
import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.repository.OrderDetailRepository;
import com.spiralforge.hothoagies.repository.UserRepository;

/**
 * @author Sri Keerthna.
 * @since 2020-02-07.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public List<OrderDetailResponseDto> getOrderHistory(Long userId) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException(ApplicationConstants.USER_NOTFOUND_MESSAGE);
		}
		List<OrderDetailResponseDto> responseDto = new ArrayList<>();
		List<OrderDetail> orderDetail = orderDetailRepository.findOrderDetailByUser(user.get());
		orderDetail.forEach(list -> {
			OrderDetailResponseDto orderDetailResponseDto = new OrderDetailResponseDto();
			BeanUtils.copyProperties(orderDetail, orderDetailResponseDto);
			responseDto.add(orderDetailResponseDto);
		});
		return responseDto;
	}
	
	
	// @Autowired
	// private PaymentFactory paymentFactory;

	/**
	 * @author Sujal
	 *
	 *         This method is used to place the order to the vendor. After the
	 *         payment done ,save all the items for the order and calculate the
	 *         total price.
	 *
	 * @param userId          is used to fetch the user
	 * @param orderRequestDto is the item details
	 * @return OrderResponseDto is the detail of the orders.
	 * @throws InvalidOrderException
	 * @throws InvalidUpiIdException
	 */
	@Override
	public OrderDetail placeOrder(Long userId, OrderRequestDto orderRequestDto) {
		OrderDetail orderDetail = null;
		Optional<User> user = getUserByUserId(userId);
//		if (user.isPresent()) {
//		} else {
//			logger.error("inside user not found");
//		}
		return orderDetail;
	}

	/**
	 * @author Sujal
	 *
	 *         Method is used to fetch the orders for the user.
	 *
	 * @param userId is used to fetch the user
	 * @return is the user.
	 */
	@Override
	public Optional<User> getUserByUserId(Long userId) {
		return userRepository.findById(userId);
	}

}
