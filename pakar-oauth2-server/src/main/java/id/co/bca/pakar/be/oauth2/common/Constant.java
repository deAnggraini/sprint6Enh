package id.co.bca.pakar.be.oauth2.common;

public class Constant {
	public static final class ApiEndpoint {
		public static final String LOGIN_URL = "/api/auth/login";
		public static final String LOGOUT_URL = "/api/auth/logout";
		public static final String REFRESH_TOKEN_URL = "/api/auth/refreshToken";
	}

	public static enum ApiResponseCode {
		// This will call enum constructor with one
		// String argument
		LOGIN_SUCCEED(new String[] { "00", "LOGIN SUCCESS" }),
		INCORRECT_USERNAME_PASSWORD(new String[] { "01", "Incorrect User ID or password" }),
		LOCKING_USERNAME_PASSWORD(new String[] { "01", " currently locked. Please try again in " }),
		INCORRECT_USERNAME_PASSWORD_UPDATE(new String[] {"01", "User ID atau Password tidak sesuai. Pastikan kamu memasukkan user ID dan Password yang benar agar akun tidak terkunci."}),
		LOGOUT_SUCCEED(new String[] { "00", "User Has Logout" }),
		LOGOUT_FAILED(new String[] { "01", "User Fail to Logout" }),
		REFRESH_TOKEN_SUCCEED(new String[] { "00", "New Access Token Has Generated" }),
		REFRESH_TOKEN_FAILED(new String[] { "01", "Fail to Generate new access token" }),
		EXIST_USER_PROFILE(new String[] { "00", "User Profile Exist" }),
		USER_PROFILE_NOT_FOUND(new String[] { "01", "User Profile Not Found" }),
		OK(new String[] { "00", "Success" }),
		GENERAL_ERROR(new String[] { "01", "Undefined Error" }),
		ACCESS_TOKEN_EXPIRED(new String[] {"100", "Access Token Expired"}),
		INVALID_TOKEN(new String[] {"101", "Invalid Token"}),
		INVALID_ACCESS_TOKEN(new String[] {"102", "Invalid Access Token"}),
		INVALID_REFRESH_TOKEN(new String[] {"103", "Invalid Refresh Token"}),
		INVALID_GRANT(new String[] {"104", "Invalid Grant"}),
		REFRESH_TOKEN_EXPIRED(new String[] {"105", "Invalid Refresh Token (expired)"}),
		INVALID_CLIENT_ID(new String[] {"106", "Client not valid"}),
        TOKEN_HAS_EXPIRED(new String[] {"107", "Token Has Expired"});

		// declaring private variable for getting values
		private String action[];

		// getter method
		public String[] getAction() {
			return this.action;
		}

		// enum constructor - cannot be public or protected
		private ApiResponseCode(String[] action) {
			this.action = action;
		}
	}

	public static final class Roles {
		public static final String ROLE_ADMIN = "SUPERADMIN";
		public static final String ROLE_READER = "READER";
		public static final String ROLE_EDITOR = "EDITOR";
		public static final String ROLE_PUBLISHER = "PUBLISHER";
	}
}
