package com.spiralforge.hothoagies.payment;

import com.spiralforge.hothoagies.entity.User;

public interface Payment {
	
    Boolean pay(String upiId, User user);
}
