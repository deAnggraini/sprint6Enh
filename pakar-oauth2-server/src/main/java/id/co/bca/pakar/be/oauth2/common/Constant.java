package id.co.bca.pakar.be.oauth2.common;

public class Constant {
	public static final class ApiEndpoint {
		public static final String LOGIN_URL = "/api/auth/login";
		public static final String LOGOUT_URL = "/api/auth/logout";
		public static final String REFRESH_TOKEN_URL = "/api/auth/refreshToken";
	}
	
	public static final class LoginStatus {
		public static final String INCORRECT_USER_PASSWORD_CODE = "01"; 
		public static final String INCORRECT_USER_PASSWORD_MESSAGE = "Incorrect User ID or password"; 
	}
}
