package com.spiralforge.hothoagies.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderResponseDto implements Serializable{
	private Integer orderId;
	private Integer statusCode;
	private String message;
}
