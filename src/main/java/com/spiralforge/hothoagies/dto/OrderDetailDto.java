package com.spiralforge.hothoagies.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.spiralforge.hothoagies.entity.CartItem;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailDto implements Serializable{
	
	private Long orderDetailId;
	private LocalTime orderTime;
	private String orderStatus;
	private String paymentMode;
	private Double totalPrice;
	private List<CartItem> cartItems;
	
	private Integer statusCode;
	private String message;
}
