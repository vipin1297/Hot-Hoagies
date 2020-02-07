package com.spiralforge.hothoagies.service;

import javax.validation.Valid;

import com.spiralforge.hothoagies.dto.LoginRequestDto;
import com.spiralforge.hothoagies.dto.LoginResponseDto;
import com.spiralforge.hothoagies.exception.UserNotFoundException;

public interface LoginService {

	LoginResponseDto checkLogin(@Valid LoginRequestDto loginRequestDto) throws UserNotFoundException;

}
