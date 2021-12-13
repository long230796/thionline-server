package thionline.entities.login;

import java.util.List;

import thionline.entities.Permission;


public class LoginPackage {
	Permission permission;
	LoginData loginData;
	public LoginPackage(Permission permission, LoginData loginData) {
		super();
		this.permission = permission;
		this.loginData = loginData;
	}
	public Permission getpermission() {
		return permission;
	}
	public void setpermission(Permission permission) {
		this.permission = permission;
	}
	public LoginData getLoginData() {
		return loginData;
	}
	public void setLoginData(LoginData loginData) {
		this.loginData = loginData;
	}
	
	
	
	
}
