package com.spiralforge.hothoagies.util;

public class Utility {
	private Utility() {
	}
	

	public static Double getTotalPrice(Integer quantity, Double price) {
		return price * quantity;
	}

	public static Double getTotalPriceInCart(Integer quantity, Double price) {
		Double totalPrice = 0.00;
		Double priceCalculation = price * quantity;
		totalPrice = totalPrice + priceCalculation;
		return totalPrice;
	}
}
