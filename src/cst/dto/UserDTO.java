package cst.dto;

public class UserDTO {
	int userLevel = 0;
	String userID = null;
	String userPassword = null;
	String userNick = null;
	String userEmail = null;
	String userProfile = null;
	String userBorn = null;
	String userGender = null;
	int isCertificated = 0;
	
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	public String getUserBorn() {
		return userBorn;
	}
	public void setUserBorn(String userBorn) {
		this.userBorn = userBorn;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public int getIsCertificated() {
		return isCertificated;
	}
	public void setIsCertificated(int isCertificated) {
		this.isCertificated = isCertificated;
	}
}
