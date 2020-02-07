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
import com.spiralforge.hothoagies.dto.ItemResponseDto;
import com.spiralforge.hothoagies.dto.OrderDetailResponseDto;
import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.dto.PreferenceList;
import com.spiralforge.hothoagies.entity.CartItem;
import com.spiralforge.hothoagies.entity.Category;
import com.spiralforge.hothoagies.entity.FoodItem;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.OrderNotFoundException;
import com.spiralforge.hothoagies.exception.PreferenceListEmptyException;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.exception.ValidationFailedException;
import com.spiralforge.hothoagies.payment.Payment;
import com.spiralforge.hothoagies.payment.PaymentFactory;
import com.spiralforge.hothoagies.repository.CartItemRepository;
import com.spiralforge.hothoagies.repository.CategoryRepository;
import com.spiralforge.hothoagies.repository.FoodItemRepository;
import com.spiralforge.hothoagies.repository.OrderDetailRepository;
import com.spiralforge.hothoagies.repository.UserRepository;
import com.spiralforge.hothoagies.util.ApiConstant;
import com.spiralforge.hothoagies.util.Utility;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sri Keerthna.
 * @author Muthu
 * @author Sujal
 * @since 2020-02-07.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	FoodItemRepository foodItemRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	UserRepository userRepository;

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

	/**
	 * @author Muthu
	 * 
	 *         Method is used to fetch the preferences of a particular customer
	 *         based on his previous cart
	 * 
	 * @param userId which is used to get user details on cart
	 * @return PreferenceResponseDto which contains list of preferences with status
	 *         code and message
	 * @throws UserNotFoundException        thrown when user is not found
	 * @throws PreferenceListEmptyException thrown when the user cart is empty
	 */
	@Override
	public List<PreferenceList> getPreferenceDetails(Long userId)
			throws UserNotFoundException, PreferenceListEmptyException {
		List<PreferenceList> preferenceList = new ArrayList<>();
		Optional<User> user = userRepository.findById(userId);
		if (!(user.isPresent())) {
			log.error(ApplicationConstants.USER_NOTFOUND_MESSAGE);
			throw new UserNotFoundException(ApplicationConstants.USER_NOTFOUND_MESSAGE);
		}
		List<CartItem> userCartItem = cartItemRepository.findAllByUser(user.get().getUserId());
		if (userCartItem.isEmpty()) {
			log.error(ApplicationConstants.PREFERENCE_LIST_EMPTY_MESSAGE);
			throw new PreferenceListEmptyException(ApplicationConstants.PREFERENCE_LIST_EMPTY_MESSAGE);
		}
		userCartItem.forEach(userCartDetails -> {
			PreferenceList preference = new PreferenceList();
			FoodItem foodItem = foodItemRepository.findByFoodItemId(userCartDetails.getItem().getFoodItemId());
			preference.setFoodItemId(foodItem.getFoodItemId());
			preference.setFoodItemName(foodItem.getFoodItemName());
			Category category = categoryRepository.findByCategoryId(foodItem.getCategory().getCategoryId());
			preference.setCategoryId(category.getCategoryId());
			preference.setCategoryName(category.getCategoryName());
			preferenceList.add(preference);
		});
		return preferenceList;
	}

	@Override
	public LocalTime getEta(OrderDetail orderDetail) {
		return orderDetail.getOrderTime().plusHours(1);

	}

	@Override
	public OrderDetail getOrder(Long orderId) {

		Optional<OrderDetail> orderDetail = orderDetailService.getOrder(orderId);
		if (orderDetail.isPresent())
			return orderDetail.get();
		return null;
	}

	@Override
	public CartResponseDto addToCart(Long userId, CartRequestDto cartRequestDto) {
		CartResponseDto cartResponseDto = new CartResponseDto();
		List<CartItem> cartItemList = new ArrayList<CartItem>();
		List<CartItem> cartItemList1 = null;
		Optional<User> user = getUserByUserId(userId);
		Double totalPrice = 0D;
		if (user.isPresent()) {
			totalPrice = cartRequestDto.getItems().stream().mapToDouble(item -> {
				CartItem cartItem = new CartItem();
				Long itemId = item.getItemId();
				Optional<FoodItem> optionalItem = foodItemRepository.findById(itemId);
				cartItem.setItem(optionalItem.get());
				cartItem.setQuantity(item.getQuantity());
				cartItem.setUser(user.get());
				cartItemList.add(cartItem);
				return Utility.getTotalPrice(item.getQuantity(), optionalItem.get().getPrice());
			}).sum();
			cartResponseDto.setTotalPrice(totalPrice);

			List<ItemResponseDto> itemList = new ArrayList<ItemResponseDto>();
			cartItemList1 = cartItemService.saveCartItems(cartItemList);
			cartItemList1.stream().forEach(item -> {
				ItemResponseDto itemRequestDto = new ItemResponseDto();
				BeanUtils.copyProperties(item.getItem(), itemRequestDto);
				itemRequestDto.setQuantity(item.getQuantity());
				itemList.add(itemRequestDto);
			});
			cartResponseDto.setItems(itemList);

		}
		return cartResponseDto;
	}

}
