package com.spiralforge.hothoagies.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.hothoagies.constants.ApplicationConstants;
import com.spiralforge.hothoagies.dto.CartItemDetailsDto;
import com.spiralforge.hothoagies.dto.CartRequestDto;
import com.spiralforge.hothoagies.dto.CartResponseDto;
import com.spiralforge.hothoagies.dto.OrderDetailResponseDto;
import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.entity.Item;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.OrderNotFoundException;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.exception.ValidationFailedException;
import com.spiralforge.hothoagies.payment.Payment;
import com.spiralforge.hothoagies.payment.PaymentFactory;
import com.spiralforge.hothoagies.repository.ItemRepository;
import com.spiralforge.hothoagies.repository.OrderDetailRepository;
import com.spiralforge.hothoagies.repository.UserRepository;
import com.spiralforge.hothoagies.util.ApiConstant;
import com.spiralforge.hothoagies.util.Utility;

/**
 * @author Sujal.
 * @since 2020-02-07.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	OrderDetailRepository orderDetailRepository;

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	private PaymentFactory paymentFactory;

	/**
	 * @author Sri Keerthna
	 * @since 2020-02-07. In this method using userId order history will be shown.
	 * @param userId userId of an user.
	 * @return List of oredrs placed by user.
	 * @throws UserNotFoundException  if user is not there it will thrown an error.
	 * @throws OrderNotFoundException if orders not found for that particular user
	 *                                it will throw this error.
	 */
	@Override
	public List<OrderDetailResponseDto> getOrderHistory(Long userId)
			throws UserNotFoundException, OrderNotFoundException {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			logger.error("user not found");
			throw new UserNotFoundException(ApplicationConstants.USER_NOTFOUND_MESSAGE);
		}
		List<OrderDetailResponseDto> responseDto = new ArrayList<>();
		List<CartItemDetailsDto> cartItemDetailsDtoList = new ArrayList<>();
		List<OrderDetail> orderDetail = orderDetailRepository.findOrderDetailByUser(user.get());
		if (orderDetail.isEmpty()) {
			logger.error("order not found for that user");
			throw new OrderNotFoundException(ApplicationConstants.ORDER_NOTFOUND_MESSAGE);
		}
		orderDetail.forEach(list -> {
			OrderDetailResponseDto orderDetailResponseDto = new OrderDetailResponseDto();
			CartItemDetailsDto cartItemDetailsDto = new CartItemDetailsDto();
			BeanUtils.copyProperties(list, cartItemDetailsDto);
			cartItemDetailsDtoList.add(cartItemDetailsDto);
			BeanUtils.copyProperties(list, orderDetailResponseDto);
			responseDto.add(orderDetailResponseDto);
		});
		return responseDto;
	}

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
	public List<CartResponseDto> addToCart(CartRequestDto cartRequestDto) {
		cartRequestDto.getItems().forEach(item -> {
			Long itemId = item.getItemId();
			Optional<Item> optionalItem = itemRepository.findById(itemId);
			Double itemPrice = optionalItem.get().getPrice();
			Double priceCalculation = Utility.getTotalPriceInCart(cartRequestDto.getQuantity(), itemPrice);
		});
		return null;
	}

	

}
