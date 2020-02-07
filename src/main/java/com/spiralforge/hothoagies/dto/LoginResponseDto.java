package com.spiralforge.hothoagies.dto;

import com.spiralforge.hothoagies.util.HotHoagiesEnum.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
	private String message;
	private Integer statusCode;
	private Long userId;
	private String fullName;
	private Role role;
}
