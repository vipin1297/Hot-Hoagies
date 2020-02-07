package com.spiralforge.hothoagies.payment;

public interface PaymentFactory {
	
	Payment getPaymentMethod(String paymentType);

}
