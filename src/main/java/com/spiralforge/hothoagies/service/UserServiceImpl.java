package com.spiralforge.hothoagies.service;

import java.time.LocalTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.ValidationFailedException;
import com.spiralforge.hothoagies.payment.Payment;
import com.spiralforge.hothoagies.payment.PaymentFactory;
import com.spiralforge.hothoagies.repository.UserRepository;
import com.spiralforge.hothoagies.util.ApiConstant;

/**
 * @author Sujal.
 * @since 2020-02-07.
 */
@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaymentFactory paymentFactory;

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
	public OrderDetail placeOrder(Long userId, OrderRequestDto orderRequestDto) throws ValidationFailedException {
		OrderDetail orderDetail = null;
		Optional<User> user = getUserByUserId(userId);
		if (user.isPresent()) {
			Payment payment = paymentFactory.getPaymentMethod(orderRequestDto.getPaymentMode());
			if (payment.pay(orderRequestDto.getUpiId(), user.get())) {
				logger.info("upi is valid");
				orderDetail = orderDetailService.saveOrderDetail(user.get(), orderRequestDto);
			} else {
				logger.error("upi is not valid");
				throw new ValidationFailedException(ApiConstant.INVALID_UPI);
			}
		} else {
			logger.error("inside user not found");
		}
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
	
	@Override
	public LocalTime getEta(OrderDetail orderDetail) {
		return orderDetail.getOrderTime().plusHours(1);
		
	}

	@Override
	public OrderDetail getOrder(Long orderId) {
		
		Optional<OrderDetail> orderDetail=orderDetailService.getOrder(orderId);
		if(orderDetail.isPresent())
			return orderDetail.get();
		return null;
	}


}
