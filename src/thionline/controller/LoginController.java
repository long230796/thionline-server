package thionline.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import thionline.database.Database;
import thionline.entities.Permission;
import thionline.entities.login.LoginPackage;
import thionline.entities.phongthi.PhongthiData;
import thionline.entities.phongthi.PhongthiPackage;
import thionline.entities.login.LoginData;

public class LoginController {
	
	LoginPackage login;
	Permission permission;
	LoginData loginData;
	Gson gson = new Gson();
	
	public LoginController(String jsonString) {
		this.login = gson.fromJson(jsonString, LoginPackage.class);
		this.permission = login.getpermission();
		this.loginData = login.getLoginData();
	}
	
	public String checkCredentials() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		String uname = new String(loginData.getUsername());
		String passwd = new String(loginData.getPassword());
		
		try {
			PreparedStatement ps = 
					con.prepareStatement("select * from SINHVIEN");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (uname.equals(rs.getString(6)) && passwd.equals(rs.getString(7))) {
					String roleName = getPermisstion(con, rs);
					PhongthiController pCtrl = new PhongthiController();
					List<PhongthiData> listPt = pCtrl.getListPhongthi(con, roleName);
					
					permission.setPermisstion(roleName);
					permission.setMasv(rs.getString(1).trim());
					PhongthiPackage pPkg = new PhongthiPackage(permission, listPt);
					
					String response = gson.toJson(pPkg);
					return response;
				}
			} 
		} finally {
			con.close();
		}
		
		return gson.toJson("Tài khoản hoặc mật khẩu không chính xác!");
	}
	
	static String getPermisstion(Connection con, ResultSet rs) {
		try {
			System.out.println(rs.getString(8));
			PreparedStatement ps = 
					con.prepareStatement("select * from ROLE where MAROLE = ? ");
			ps.setString(1, rs.getString(8));
			ResultSet rs2 = ps.executeQuery();
			while(rs2.next()) {
				return rs2.getString(2);
				
			}
		} catch (Exception e) {
			System.out.println("Không thể lấy quyền của user");
			System.out.println(e);
		}
		
		return null;
	}
	
	static List<PhongthiData> getListPhongthi(Connection con, String auth) {
		List<PhongthiData> listPhongthi = new ArrayList<PhongthiData>();
		try {
			if (new String("ADMIN").equals(auth)) {
				PreparedStatement ps = 
						con.prepareStatement("select * from PHONGTHI");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					PhongthiData p = new PhongthiData(rs.getString(1), rs.getString(2), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(3), rs.getInt(4));
					listPhongthi.add(p);
					
				}
				return listPhongthi;
			}
		} catch (Exception e) {
			System.out.println("Không thể lấy danh sách phòng thi");
			System.out.println(e);
		}
		
		return null;
	}
	
}
