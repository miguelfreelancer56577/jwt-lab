package com.github.mangelt.jwt.lab.util;

public class ApiConstants {
	private ApiConstants() {}
	public static final String API_VERSION = "/v1";
	public static final String MAPPING_AUTHENTICATION = "/authentication";
	public static final String MAPPING_SIGNIN = "/signin";
	public static final String MAPPING_SIGNUP = "/signup";
	public static final String MAPPING_REFRESHTOKEN = "/refreshtoken";
	public static final String MAPPING_ACTUATOR = "/actuator";
	
//	message for validation
	public static final String USER_SERVICE_USERID_MANDATORY = "User Id is mandatory.";
	public static final String USER_SERVICE_PASSWORD_MANDATORY = "Password Name is mandatory.";
	public static final String USER_SERVICE_SIGNIN_OK = "Token generated properly.";
	
//	signs and cords used along with the application 
	public static final String SIGN_COMMA = ",";
	public static final String SIGN_COLON = ":";
	public static final String SIGN_DOT = ".";
	public static final String SIGN_SACAPED_DOT = "\\.";
	public static final String DEFAULT_ROLE = "ROLE_USER";
	public static final String CARD_AUTHORITIES = "authorities";
	public static final String CARD_AUTHORIZATION = "Authorization";
	public static final String CARD_BEARER = "Bearer";
	public static final String CARD_BLANK = " ";
	public static final String CARD_EMPTY = "";
	public static final String CARD_MATCH_ALL_FRAGMENTS = "/**";
	public static final int DEFAULTS_SECURITY_STRENGTH = 11;
	
//	exception messages 
	public static final String EXP_ERROR_FIND_USER = "There was an error to find the user.";
	public static final String EXP_ERROR_USER_ALREADY_REGISTERED = "The user is already registered.";
	public static final String EXP_ERROR_USER_NOT_REGISTERED = "User is not registered to be updated.";
	public static final String EXP_ERROR_DELETE_USER = "There was an error to delete the user.";
	public static final String EXP_ERROR_NOT_FOUND_USER = "cannot find username: ";
	public static final String EXP_ERROR_INVALID_CREDENCIALS = "Invalid credencials.";
	public static final String EXP_ERROR_EXPIRED_TOKEN = "Your token has expired.";
	public static final String EXP_ERROR_INVALID_TOKEN = "Invalid Token.";
	public static final String EXP_ERROR_INVALID_USER = "Invalid user.";
	public static final String EXP_ERROR_DISABLED_USER = "User disabled.";
	public static final String EXP_ERROR_INVALID_ROLES = "You do not have roles assigned.";
	public static final String EXP_ERROR_REQUEST_WITHOUT_TOKEN = "This request doesn't have a valid token.";
	
//	spring profiles
	public static final String PROFILE_AWS = "aws";
	public static final String PROFILE_AZURE = "azure";
	
}
