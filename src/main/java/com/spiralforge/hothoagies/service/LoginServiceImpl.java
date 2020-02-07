package com.spiralforge.hothoagies.service;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.hothoagies.constants.ApplicationConstants;
import com.spiralforge.hothoagies.dto.LoginRequestDto;
import com.spiralforge.hothoagies.dto.LoginResponseDto;
import com.spiralforge.hothoagies.entity.User;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/*
 *  Method is used for user login for the users
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
	@Autowired
	UserRepository userRepository;

	/**
	 * @author Muthu
	 * @since 2020-02-07
	 * 
	 *        Method is used to classify a user as a staff or customer
	 * 
	 * @param loginRequestDto which takes input as a mobile number
	 * @return LoginResponseDto includes all particulars of the user
	 * @throws UserNotFoundException expose a message when user is not found
	 */
	@Override
	public LoginResponseDto checkLogin(@Valid LoginRequestDto loginRequestDto) throws UserNotFoundException {
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		User user = userRepository.findBymobileNumber(loginRequestDto.getMobileNumber());
		if (Objects.isNull(user)) {
			log.error(ApplicationConstants.USER_NOTFOUND_MESSAGE);
			throw new UserNotFoundException(ApplicationConstants.USER_NOTFOUND_MESSAGE);
		}
		BeanUtils.copyProperties(user, loginResponseDto);
		return loginResponseDto;
	}

}
