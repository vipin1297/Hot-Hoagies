package com.spiralforge.hothoagies.util;

public class Utility {
	private Utility() {
	}
	

	public static Double getTotalPrice(Integer quantity, Double price) {
		return price * quantity;
	}
}
