package components.beans;

public class UserInfo {

	long id;
	String username;
	String passwd; //MD5
	public UserInfo(long id, String username, String passwd) {
		this.id = id;
		this.username = username;
		this.passwd = passwd;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
