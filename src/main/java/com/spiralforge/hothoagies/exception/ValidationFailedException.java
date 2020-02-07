package com.spiralforge.hothoagies.exception;

public class ValidationFailedException  extends Exception{
	
	private static final long serialVersionUID = 1L;

	 public ValidationFailedException(String exception) {
		
		super(exception);
	}

}
