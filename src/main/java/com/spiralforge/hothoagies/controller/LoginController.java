package com.spiralforge.hothoagies.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.hothoagies.constants.ApplicationConstants;
import com.spiralforge.hothoagies.dto.LoginRequestDto;
import com.spiralforge.hothoagies.dto.LoginResponseDto;
import com.spiralforge.hothoagies.exception.UserNotFoundException;
import com.spiralforge.hothoagies.service.LoginService;

import lombok.extern.slf4j.Slf4j;

/*
 *  Method is used for user login for the users
 */
@RequestMapping("/login")
@RestController
@Slf4j
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class LoginController {
	@Autowired
	LoginService loginService;

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
	@PostMapping
	public ResponseEntity<LoginResponseDto> checkLogin(@Valid @RequestBody LoginRequestDto loginRequestDto)
			throws UserNotFoundException {
		log.info("For checking whether the person is staff or a customer");
		LoginResponseDto loginResponse = loginService.checkLogin(loginRequestDto);
		log.info(ApplicationConstants.LOGIN_SUCCESSMESSAGE);
		loginResponse.setStatusCode(ApplicationConstants.SUCCESS_CODE);
		loginResponse.setMessage(ApplicationConstants.LOGIN_SUCCESSMESSAGE);
		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}
}
