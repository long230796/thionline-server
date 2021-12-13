package thionline.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import thionline.database.Database;
import thionline.entities.Permission;
import thionline.entities.dethi.Cauhoi;
import thionline.entities.dethi.CauhoiPackage;
import thionline.entities.phongthi.PhongthiPackage;

public class BodeController {
	List<Cauhoi> listCauhoi;
	Cauhoi cauhoi;
	CauhoiPackage questPkg;
	Permission auth;
	Gson gson = new Gson();
	
	int thoigian;
	
	public BodeController() {
		
	}
	
	public BodeController(String jsonString) {
		this.questPkg = gson.fromJson(jsonString, CauhoiPackage.class) ;
		this.listCauhoi = questPkg.getListCauhoi();
		this.auth = questPkg.getPermission();
		this.thoigian = questPkg.getThoigianthi();
	}
	
	
	public String getBode() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		List<Cauhoi> listCauhoi = new ArrayList<Cauhoi>();
		
		
		try {
			if (new String("ADMIN").equals(auth.getPermisstion())) {
				PreparedStatement ps = 
						con.prepareStatement("select * from BODE");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					Cauhoi cauhoi = new Cauhoi(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
					listCauhoi.add(cauhoi);
				}
				
				CauhoiPackage cauhoiPkg = new CauhoiPackage(auth, listCauhoi, 0);
				return gson.toJson(cauhoiPkg);
			}
			
			return "null";
		} catch (Exception e) {
			System.out.println("Không thể lấy danh sách phòng thi");
			e.printStackTrace();
			return "null";
		} finally {
			con.close();
		}
		
	}
	
	public String xoaCauhoi() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		try {
			for (int i = 0; i < listCauhoi.size(); i ++) {
				Cauhoi cauhoi = listCauhoi.get(i);
				PreparedStatement ps = 
						con.prepareStatement("DELETE FROM BODE WHERE CAUHOI=?");
				ps.setString(1, cauhoi.getMaCau());
				
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
		
		String listBode = getBode();
		return listBode;
	}
	
	public String suaCauhoi() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		try {
			for (int i = 0; i < listCauhoi.size(); i ++) {
				Cauhoi cauhoi = listCauhoi.get(i);
				PreparedStatement ps = 
						con.prepareStatement("UPDATE BODE SET TRINHDO = ?, NOIDUNG = ?, A = ?, B = ?, C = ?, D = ?, DAP_AN = ? WHERE CAUHOI = ?");
				ps.setString(1, cauhoi.getTrinhDo());
				ps.setString(2, cauhoi.getNoiDung());
				ps.setString(3, cauhoi.getCauA());
				ps.setString(4, cauhoi.getCauB());
				ps.setString(5, cauhoi.getCauC());
				ps.setString(6, cauhoi.getCauD());
				ps.setString(7, cauhoi.getDapAn());
				ps.setString(8, cauhoi.getMaCau());
				
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
		
		String listBode = getBode();
		return listBode;
	}
	
	public String luuBode() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		try {
			for (int i = 0; i < listCauhoi.size(); i ++) {
				Cauhoi cauhoi = listCauhoi.get(i);
				PreparedStatement ps = 
						con.prepareStatement("INSERT INTO BODE (TRINHDO, NOIDUNG, A, B, C, D, DAP_AN) VALUES (?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, cauhoi.getTrinhDo());
				ps.setString(2, cauhoi.getNoiDung());
				ps.setString(3, cauhoi.getCauA());
				ps.setString(4, cauhoi.getCauB());
				ps.setString(5, cauhoi.getCauC());
				ps.setString(6, cauhoi.getCauD());
				ps.setString(7, cauhoi.getDapAn());
				
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
		
		String listBode = getBode();
		return listBode;
	}
}
