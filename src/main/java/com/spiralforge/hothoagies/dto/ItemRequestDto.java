package com.spiralforge.hothoagies.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemRequestDto implements Serializable{
	
	private Long itemId;
	private Integer quantity;
}
