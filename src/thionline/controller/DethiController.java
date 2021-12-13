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
import thionline.entities.dethi.Cauhoi;
import thionline.entities.dethi.CauhoiPackage;
import thionline.entities.phongthi.PhongthiData;
import thionline.entities.phongthi.PhongthiPackage;

public class DethiController {
	List<PhongthiData> listP;
	PhongthiData pData;
	PhongthiPackage pPkg;
	Permission auth;
	Gson gson = new Gson();
	
	CauhoiPackage dethi;
	List<Cauhoi> listCauhoi = new ArrayList<Cauhoi>();
	
	public DethiController() {
		
	}
	
	public DethiController(String jsonString) {
		this.pPkg = gson.fromJson(jsonString, PhongthiPackage.class) ;
		this.listP = pPkg.getListPhongthi();
		this.pData = listP.get(0);
		this.auth = pPkg.getPermission();
	}
	
	public String getListCauhoi() throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		
		SinhvienController svCtrl = new SinhvienController();
		String tested = svCtrl.checkThiSinhvien(auth.getMasv());
		
		if (new String("dathi").equals(tested)) {
			return "null";
		}
		
		PhongthiData phongThi = new PhongthiController().getPhongthi(con, pData.getMaphong());
		
		try {
			if (phongThi != null && new String("mo").equals(phongThi.getTrangthai().toLowerCase())) {
				PreparedStatement ps = 
						con.prepareStatement("select * from BODE where CAUHOI in (select top (?) CAUHOI from BODE order by newid())");
				ps.setInt(1, phongThi.getSocauhoi());
				ResultSet rs = ps.executeQuery();
				System.out.println(phongThi.getSocauhoi());
				while(rs.next()) {
					Cauhoi cauhoi = new Cauhoi(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
					listCauhoi.add(cauhoi);
				}
				
				dethi = new CauhoiPackage(auth, listCauhoi, phongThi.getThoigian());
				return gson.toJson(dethi);
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
	
	
	
	
}
