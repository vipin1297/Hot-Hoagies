package com.spiralforge.hothoagies.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spiralforge.hothoagies.entity.User;

@Component("PhonePe")
public class PhonePe implements Payment {

	Logger logger = LoggerFactory.getLogger(PhonePe.class);

	@Override
	public Boolean pay(String upiId, User user) {
		logger.info("Payment done through PhonePe");
		return user.getUpiId().equalsIgnoreCase(upiId);
	}
}
