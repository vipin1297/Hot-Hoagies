package com.spiralforge.hothoagies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.hothoagies.constants.ApplicationConstants;
import com.spiralforge.hothoagies.dto.OrderRequestDto;
import com.spiralforge.hothoagies.dto.PreferenceList;
import com.spiralforge.hothoagies.entity.CartItem;
import com.spiralforge.hothoagies.entity.Category;
import com.spiralforge.hothoagies.entity.FoodItem;
import com.spiralforge.hothoagies.entity.OrderDetail;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.PreferenceListEmptyException;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.repository.CartItemRepository;
import com.spiralforge.hothoagies.repository.CategoryRepository;
import com.spiralforge.hothoagies.repository.FoodItemRepository;
import com.spiralforge.hothoagies.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sri Keerthna.
 * @author Muthu
 * @author Sujal
 * 
 * @since 2020-02-05.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	FoodItemRepository foodItemRepository;

	@Autowired
	CategoryRepository categoryRepository;

//	@Autowired
//	private PaymentFactory paymentFactory;

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

}
