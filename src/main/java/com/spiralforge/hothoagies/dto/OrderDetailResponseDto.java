package com.spiralforge.hothoagies.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailResponseDto {

	private Long orderDetailId;
	private LocalDate orderDate;
	private LocalTime orderTime;
	private String orderStatus;
	private String paymentMode;
	private List<CartItemDetailsDto> cartItems;
}
