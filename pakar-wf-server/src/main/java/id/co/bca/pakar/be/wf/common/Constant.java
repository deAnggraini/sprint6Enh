package id.co.bca.pakar.be.wf.common;

public class Constant {
	public static final String OK_ACK = "00";

	public static enum ApiResponseCode {
		// This will call enum constructor with one
		// String argument
		LOGIN_SUCCEED(new String[] { "00", "LOGIN SUCCESS" }),
		INCORRECT_USERNAME_PASSWORD(new String[] { "01", "Incorrect User ID or password" }),
		SESSION_HAS_EXPIRED(new String[] { "02", "Session has expired" }),
		LOGOUT_SUCCEED(new String[] { "00", "User Has Logout" }),
		LOGOUT_FAILED(new String[] { "01", "User Fail to Logout" }),
		REFRESH_TOKEN_SUCCEED(new String[] { "00", "New Access Token Has Generated" }),
		REFRESH_TOKEN_FAILED(new String[] { "01", "Fail to Generate new access token" }),
		EXIST_USER_PROFILE(new String[] { "00", "User Profile Exist" }),
		USER_PROFILE_NOT_FOUND(new String[] { "01", "User Profile Not Found" }),
		MENU_PROFILE_SUCCESS(new String[] { "00", "Menu Success Load" }),
		OK(new String[] { "00", "Success" }),
		GENERAL_ERROR(new String[] { "01", "Undefined Error" }),
		REQUEST_PARAM_INVALID(new String[] { "01", "Request Param Invalid" }),
		MAX_UPLOAD_EXCEEDED(new String[] { "01", "Exceeded file size" }),
		DATA_NOT_FOUND(new String[] { "01", "Not found data" }),
		INVALID_STRUCTURE_LEVEL(new String[] { "01", "Level Structure Invalid" }),
		INVALID_SORT_STRUCTURE(new String[] { "01", "Sort value exist in same parent id" }),
		ACCESS_TOKEN_EXPIRED(new String[] {"100", "Access Token Expired"}),
		INVALID_TOKEN(new String[] {"101", "Invalid Token"}),
		INVALID_ACCESS_TOKEN(new String[] {"102", "Invalid Access Token"}),
		INVALID_REFRESH_TOKEN(new String[] {"103", "Invalid Refresh Token"}),
		INVALID_GRANT(new String[] {"104", "Invalid Grant"}),
		REFRESH_TOKEN_EXPIRED(new String[] {"105", "Invalid Refresh Token (expired)"}),
		INVALID_CLIENT_ID(new String[] {"106", "Client not valid"}),
		TOKEN_HAS_EXPIRED(new String[] {"107", "Token Has Expired"}),
		ARTICLE_EXIST_IN_DATABASE(new String[] {"01", "Judul Article Sudah Ada"});;

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

	public static class Headers {
		public static final String AUTHORIZATION = "Authorization";
		public static final String X_USERNAME = "X-USERNAME";
		public static final String BEARER = "Bearer ";
	}

	public static final class Roles {
		public static final String ROLE_ADMIN = "SUPERADMIN";
		public static final String ROLE_READER = "READER";
		public static final String ROLE_EDITOR = "EDITOR";
		public static final String ROLE_PUBLISHER = "PUBLISHER";
	}

	public static final class ArticleWfState {
		public static final String NEW = "NEW";
		public static final String DRAFT = "DRAFT";
		public static final String PENDING = "PENDING";
		public static final String PUBLISHED = "PUBLISHED";
		public static final String DRAFT_DELETED = "DRAFTDELETED";
		public static final String PENDING_DELETED = "PENDINGDELETED";
		public static final String DELETED = "DELETED";
		public static final String DENIED_DELETED = "DENIEDDELETED";

	}

	public static final class Workflow {
		public static final String PROCESS_KEY = "PROCESS_KEY";
		public static final String ARTICLE_ID = "ARTICLE_ID";
		public static final String WORKFLOW_REQ_ID_PARAM = "WORKFLOW_REQ_ID_PARAM";
		public static final String RECEIVER_GROUP_PARAM = "RECEIVER_GROUP_PARAM";
		public static final String ARTICLE_ID_PARAM = "ARTICLE_ID_PARAM";
		public static final String TASK_TYPE_PARAM = "TASK_TYPE_PARAM";
		public static final String SEND_TO_PARAM = "SEND_TO_PARAM";
		public static final String USERNAME_PARAM = "USERNAME_PARAM";
		public static final String SEND_NOTE_PARAM = "SEND_NOTE_PARAM";
		public static final String GROUP_PARAM = "GROUP_PARAM";
		public static final String TITLE_PARAM = "TITLE_PARAM";
		public static final String RECEIVER_PARAM = "RECEIVER_PARAM";
		public static final String SENDER_PARAM = "SENDER_PARAM";
		public static final String ARTICLE_DELETE_WF = "ARTICLE_DELETE";

	}
}
