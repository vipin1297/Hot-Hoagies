package com.spiralforge.hothoagies.dto;

import java.io.Serializable;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderResponseDto implements Serializable{
	private Long orderId;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime eta;
	
	private Integer statusCode;
	private String message;
}
