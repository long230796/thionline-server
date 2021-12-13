package thionline.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

import thionline.database.Database;
import thionline.entities.Permission;
import thionline.entities.phongthi.PhongthiData;
import thionline.entities.phongthi.PhongthiPackage;
import thionline.entities.sinhvien.Sinhvien;
import thionline.entities.sinhvien.SinhvienPackage;

public class SinhvienController {
	List<Sinhvien> listSv;
	SinhvienPackage svPkg;
	Permission auth;
	Gson gson = new Gson();
	
	public SinhvienController() {};
	
	public SinhvienController(String jsonString) {
		this.svPkg = gson.fromJson(jsonString, SinhvienPackage.class);
		this.listSv = svPkg.getListSinhvien();
		this.auth = svPkg.getPermission();
	}
	
	public String getListSinhvien() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		List<Sinhvien> listSv = new ArrayList<Sinhvien>();
		
		try {
			if (new String("ADMIN").equals(auth.getPermisstion())) {
				PreparedStatement ps = 
						con.prepareStatement("select * from SINHVIEN");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					Sinhvien sv = new Sinhvien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
					listSv.add(sv);
					
				}
				
				SinhvienPackage svPkg = new SinhvienPackage(auth, listSv);
				String resultString = gson.toJson(svPkg);
				
				return resultString;
			}
		} catch (Exception e) {
			System.out.println("Không thể lấy danh sách sinh viên");
			System.out.println(e);
		}
		
		return "null";
	}
	
	public String luuSinhvien() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		try {
			for (int i = 0; i < listSv.size(); i ++) {
				Sinhvien sv = listSv.get(i);
				PreparedStatement ps = 
						con.prepareStatement("INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, UserName, PassWord, MAROLE, MATRANGTHAI, THI) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, sv.getMssv());
				ps.setString(2, sv.getHo());
				ps.setString(3, sv.getTen());
				ps.setString(4, sv.getNgaysinh());
				ps.setString(5, sv.getDiachi());
				ps.setString(6, sv.getUsername());
				ps.setString(7, sv.getPassword());
				ps.setInt(8, sv.getMavaitro());
				ps.setInt(9, sv.getMatrangthai());
				ps.setString(10, sv.getThi());
				
				int row = ps.executeUpdate();
				if (row == 0) {
					return "null";
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "null";
		}
		finally {
			con.close();
		}
		
		String listSinhvien = getListSinhvien();
		return listSinhvien;
	}
	
	public String XoaSinhvien() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		try {
			for (int i = 0; i < listSv.size(); i ++) {
				Sinhvien sv = listSv.get(i);
				PreparedStatement ps = 
						con.prepareStatement("DELETE FROM SINHVIEN WHERE MASV=?");
				ps.setString(1, sv.getMssv());
				
				int row = ps.executeUpdate();
				if (row == 0) {
					return "null";
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "null";
		}
		finally {
			con.close();
		}
		
		String listSinhvien = getListSinhvien();
		return listSinhvien;
	}
	
	
	public String SuaSinhvien() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		try {
			for (int i = 0; i < listSv.size(); i ++) {
				Sinhvien sv = listSv.get(i);
				PreparedStatement ps = 
						con.prepareStatement("UPDATE SINHVIEN SET HO = ?, TEN = ?, NGAYSINH = ?, DIACHI = ?, UserName = ?, PassWord = ?, MAROLE = ?, MATRANGTHAI = ?, THI = ? WHERE MASV = ?");
				ps.setString(10, sv.getMssv());
				ps.setString(1, sv.getHo());
				ps.setString(2, sv.getTen());
				ps.setString(3, sv.getNgaysinh());
				ps.setString(4, sv.getDiachi());
				ps.setString(5, sv.getUsername());
				ps.setString(6, sv.getPassword());
				ps.setInt(7, sv.getMavaitro());
				ps.setInt(8, sv.getMatrangthai());
				ps.setString(9, sv.getThi());
				
				int row = ps.executeUpdate();
				if (row == 0) {
					return "null";
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "null";
		}
		finally {
			con.close();
		}
		
		String listSinhvien = getListSinhvien();
		return listSinhvien;
	}
	
	public String SuaThiSinhvien(String masv) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		try {
			PreparedStatement ps = 
					con.prepareStatement("UPDATE SINHVIEN SET THI = ? WHERE MASV = ?");
			
			ps.setString(1, "dathi");
			ps.setString(2, masv);
			
			
			int row = ps.executeUpdate();
			if (row == 0) {
				return "null";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "null";
		}
		finally {
			con.close();
		}
		
		
		return "ok";
	}
	
	public String checkThiSinhvien(String masv) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		try {
			PreparedStatement ps = 
					con.prepareStatement("select THI from SINHVIEN where MASV = ?");
			ps.setString(1, masv);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				return rs.getString(1);
			}	
			
		} catch (Exception e) {
			System.out.println("Không thể kiểm tra sinh viên");
			System.out.println(e);
		}
		
		return "null";
	}
}
