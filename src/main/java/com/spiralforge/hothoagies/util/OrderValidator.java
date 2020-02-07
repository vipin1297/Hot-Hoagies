package com.spiralforge.hothoagies.util;

import com.spiralforge.hothoagies.exception.ValidationFailedException;

public interface OrderValidator<E, T> {
	
	Boolean validate(E e, T t) throws ValidationFailedException ;
}
