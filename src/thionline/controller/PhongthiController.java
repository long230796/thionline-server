package thionline.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import thionline.database.Database;
import thionline.entities.Permission;
import thionline.entities.phongthi.PhongthiData;
import thionline.entities.phongthi.PhongthiPackage;

public class PhongthiController {
	List<PhongthiData> listP;
	PhongthiData pData;
	PhongthiPackage pPkg;
	Permission auth;
	Gson gson = new Gson();
	
	public PhongthiController() {
		
	}
	
	public PhongthiController(String jsonString) {
		this.pPkg = gson.fromJson(jsonString, PhongthiPackage.class) ;
		this.listP = pPkg.getListPhongthi();
		this.pData = listP.get(0);
		this.auth = pPkg.getPermission();
	}
	
	public String taoPhongthi() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		String map = pData.getMaphong();
		String tenp = pData.getTenphong();
		int socauhoi = pData.getSocauhoi();
		int time = pData.getThoigian();
		String tt = pData.getTrangthai();
		
		
		try {
			PreparedStatement ps = 
					con.prepareStatement("INSERT INTO PHONGTHI (MAPHONG, TENPHONG, SOCAUHOI, THOIGIANTHI, TRANGTHAI) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, map);
			ps.setString(2, tenp);
			ps.setInt(3, socauhoi);
			ps.setInt(4, time);
			ps.setString(5, tt);
			
			int row = ps.executeUpdate();
			if (row != 0) {
				// get danh sach phong thi
				List<PhongthiData> listPt = getListPhongthi(con, auth.getPermisstion());
				PhongthiPackage pPkg = new PhongthiPackage(auth, listPt);
				
				String response = gson.toJson(pPkg);
				return response;
			}
		} finally {
			con.close();
		}
		return "null";
		
	}
	
	public String xoaPhongthi () throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		String map = pData.getMaphong();
		
		try {
			PreparedStatement ps = 
					con.prepareStatement("DELETE FROM PHONGTHI WHERE MAPHONG = ?");
			ps.setString(1, map);
			
			int row = ps.executeUpdate();
			if (row != 0) {
				// get danh sach phong thi
				List<PhongthiData> listPt = getListPhongthi(con, auth.getPermisstion());
				PhongthiPackage pPkg = new PhongthiPackage(auth, listPt);
				
				String response = gson.toJson(pPkg);
				return response;
			}
		} finally {
			con.close();
		}
		
		return null;
	}
	
	public String khoaPhongthi () throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		String map = pData.getMaphong();
		
		try {
			PreparedStatement ps = 
					con.prepareStatement("UPDATE PHONGTHI SET TRANGTHAI = ? WHERE MAPHONG = ?");
			ps.setString(1, "KHOA");
			ps.setString(2, map);
			
			int row = ps.executeUpdate();
			if (row != 0) {
				// get danh sach phong thi
				List<PhongthiData> listPt = getListPhongthi(con, auth.getPermisstion());
				PhongthiPackage pPkg = new PhongthiPackage(auth, listPt);
				
				String response = gson.toJson(pPkg);
				return response;
			}
		} finally {
			con.close();
		}
		
		return null;
	}
	
	public String moPhongthi () throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		String map = pData.getMaphong();
		
		try {
			PreparedStatement ps = 
					con.prepareStatement("UPDATE PHONGTHI SET TRANGTHAI = ? WHERE MAPHONG = ?");
			ps.setString(1, "MO");
			ps.setString(2, map);
			
			int row = ps.executeUpdate();
			if (row != 0) {
				// get danh sach phong thi
				List<PhongthiData> listPt = getListPhongthi(con, auth.getPermisstion());
				PhongthiPackage pPkg = new PhongthiPackage(auth, listPt);
				String response = gson.toJson(pPkg);
				return response;
			}
		} finally {
			con.close();
		}
		
		return null;
	}
	
	public PhongthiData getPhongthi(Connection con, String maPhong) {
		PhongthiData p;
		try {
			PreparedStatement ps = 
					con.prepareStatement("select * from PHONGTHI WHERE MAPHONG = ?");
			ps.setString(1, maPhong);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				p = new PhongthiData(rs.getString(1), rs.getString(2), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(3), rs.getInt(4));
				return p;
			}
		} catch (Exception e) {
			System.out.println("Không thể lấy phòng thi bằng mã phòng");
			System.out.println(e);
		}
		
		return null;
	}
	
	
	public List<PhongthiData> getListPhongthi(Connection con, String auth) {
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
