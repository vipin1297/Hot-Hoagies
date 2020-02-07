package com.spiralforge.hothoagies.util;

/**
 * 
 * @author Sujal
 *
 */
public class ApiConstant {
	private ApiConstant() {
	}

	public static final String LOGIN_ERROR = "please enter valid username and password";
	public static final String LOGIN_SUCCESS = "you are successfully logged in";
	public static final String MOBILE_NUMBER_VALIDATION = "Enter a valid Mobile number";
	
	public static final String VENDOR_NOT_FOUND_EXCEPTION = "Vendor not available";
	
	public static final String SUCCESS = "SUCCESSFUL";
	public static final String FAILED = "FAILED";

	public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
	public static final String VALIDATION_FAILED = "VALIDATION FAILED";
	public static final String NO_ELEMENT_FOUND = "NO ELEMENT FOUND";
	public static final String CUSTOMER_NOT_FOUND = "Not a valid customer";

	public static final Integer SUCCESS_CODE = 200;
	public static final Integer FAILURE_CODE = 404;
	public static final Integer NO_CONTENT_CODE = 204;

	public static final String INVALID_ORDER = "Order data is not valid";
	public static final String INVALID_USER = "User doesn't exist";
	public static final String ITEM_NOT_FOUND = "Item is not selected";
	public static final String INVALID_UPI = "Please enter valid upi id";
	public static final String INVALID_ITEM = "Please order valid item only";

}
