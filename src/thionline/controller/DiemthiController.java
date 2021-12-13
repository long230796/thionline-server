package thionline.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import thionline.database.Database;
import thionline.entities.Permission;
import thionline.entities.diemthi.Diemthi;
import thionline.entities.diemthi.DiemthiPackage;
import thionline.entities.ketquathi.Ketquathi;
import thionline.entities.phongthi.PhongthiData;
import thionline.entities.phongthi.PhongthiPackage;
import thionline.entities.sinhvien.Sinhvien;
import thionline.entities.sinhvien.SinhvienPackage;

public class DiemthiController {
	
	Ketquathi ketquathi;
	Permission auth;
	Gson gson = new Gson();
	float diemThi;
	
	public DiemthiController() {
		
	}
	
	public DiemthiController(String jsonString) {
		this.ketquathi = gson.fromJson(jsonString, Ketquathi.class);
		this.auth = ketquathi.getPermission();
		this.diemThi = ketquathi.getDiemtong();
	}
	
	public String luuDiemthi() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		String col = countColumnByMasv(auth.getMasv());
		if (!new String("null").equals(col)) {
			try {
				PreparedStatement ps = 
						con.prepareStatement("INSERT INTO BANGDIEM (MASV, DIEM, LAN) VALUES (?, ?, ?)");
				ps.setString(1, auth.getMasv());
				ps.setFloat(2, diemThi);
				ps.setInt(3, Integer.parseInt(col) + 1);
				
				int row = ps.executeUpdate();
				if (row != 0) {
					SinhvienController svCtrl = new SinhvienController();
					String tested = svCtrl.SuaThiSinhvien(auth.getMasv());
					if (!new String("null").equals(tested)) {
						return "Thêm điểm thành công!";					
					}
				}
				return "null";
			} 
			catch (Exception e) {
				e.printStackTrace();
				return "null";
			}
			finally {
				con.close();
			}
			
		} else {
			return "Không thể đếm số cột!";
		}
		
	}
	
	public String getListBangdiem() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		List<Diemthi> listDiem = new ArrayList<Diemthi>();
		
		try {
			if (new String("ADMIN").equals(auth.getPermisstion())) {
				PreparedStatement ps = 
						con.prepareStatement("select * from BANGDIEM");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					Diemthi diemthi = new Diemthi(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
					listDiem.add(diemthi);
					
				}
				
				DiemthiPackage diemthiPkg = new DiemthiPackage(auth, listDiem);
				String resultString = gson.toJson(diemthiPkg);
				
				return resultString;
			}
		} catch (Exception e) {
			System.out.println("Không thể lấy danh sách điểm thi");
			System.out.println(e);
		}
		
		return "null";
	}
	
	
	public String getListBangdiemByMasv(String masv) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		List<Diemthi> listDiem = new ArrayList<Diemthi>();
		
		try {
			PreparedStatement ps = 
					con.prepareStatement("select * from BANGDIEM where MASV like ?");
			ps.setString(1, "%" + masv + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Diemthi diemthi = new Diemthi(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				listDiem.add(diemthi);
				
			}
			
			DiemthiPackage diemthiPkg = new DiemthiPackage(auth, listDiem);
			String resultString = gson.toJson(diemthiPkg);
			
			return resultString;
			
			
		} catch (Exception e) {
			System.out.println("Không thể lấy danh sách điểm thi by masv");
			System.out.println(e);
		}
		
		return "null";
	}
	
	static String countColumnByMasv(String masv) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		try {
			PreparedStatement ps = 
					con.prepareStatement("select count(*) from BANGDIEM where MASV = ?");
			ps.setString(1, masv);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				return rs.getString(1);
			}	
			
		} catch (Exception e) {
			System.out.println("Không thể đếm số cột");
			System.out.println(e);
		}
		
		return "null";
	}
}
