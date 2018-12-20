package components.beans.loginbeans;

import components.Response;

public class SimpleLogin implements Response{

	String id;
	int loginStatus; // 0 is success
	String responseBody;
	
	public SimpleLogin(String id, int loginStatus, String responseBody) {
		this.id = id;
		this.loginStatus = loginStatus;
		this.responseBody = responseBody;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
}
