package com.spiralforge.hothoagies.service;

import java.util.List;

import com.spiralforge.hothoagies.dto.OrderDetailResponseDto;
import com.spiralforge.hothoagies.exception.UserNotFoundException;

/**
 * @author Sri Keerthna.
 * @since 2020-02-07.
 */
public interface UserService {

	public List<OrderDetailResponseDto> getOrderHistory(Long userId) throws UserNotFoundException;
}
