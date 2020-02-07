package com.spiralforge.hothoagies.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spiralforge.hothoagies.entity.User;

@Component("PayTM")
public class PayTM implements Payment {

	Logger logger = LoggerFactory.getLogger(PayTM.class);

	@Override
	public Boolean pay(String upiId, User user) {
		logger.info("Payment done through PayTM");
		return user.getUpiId().equalsIgnoreCase(upiId);
	}

}
