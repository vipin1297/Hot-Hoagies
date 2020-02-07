package com.spiralforge.hothoagies.util;

public interface OrderValidator<E, T> {
	
	Boolean validate(E e, T t);
}
