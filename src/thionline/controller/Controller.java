package thionline.controller;

import java.util.List;

import com.google.gson.Gson;

public class Controller {
	private String jsonString;
	Gson gson = new Gson();
	
	public Controller(String jsonString) {
		this.jsonString = jsonString;
		
	}
	
	public String routing() throws Exception {
		String doSomething = jsonString.split("_")[0];
		switch (doSomething) {
		case "Login":
			LoginController lgCtl = new LoginController(jsonString.split("_")[1]);
			return lgCtl.checkCredentials();
		case "TaoPhongThi":
			PhongthiController pCtl = new PhongthiController(jsonString.split("_")[1]);
			return pCtl.taoPhongthi();
		
		case "XoaPhongThi":
			PhongthiController xoaPt = new PhongthiController(jsonString.split("_")[1]);
			return xoaPt.xoaPhongthi();
			
		case "KhoaPhongThi":
			PhongthiController khoaPt = new PhongthiController(jsonString.split("_")[1]);
			return khoaPt.khoaPhongthi();
			
		case "MoPhongThi":
			PhongthiController moPt = new PhongthiController(jsonString.split("_")[1]);
			return moPt.moPhongthi();
			
		case "LayDeThi":
			DethiController dethi = new DethiController(jsonString.split("_")[1]);
			return dethi.getListCauhoi();
			
		case "LayTatCaDeThi":
			BodeController bode = new BodeController(jsonString.split("_")[1]);
			return bode.getBode();
		
		case "SuaCauhoi":
			BodeController suaCauhoi = new BodeController(jsonString.split("_")[1]);
			return suaCauhoi.suaCauhoi();
			
		case "XoaCauhoi":
			BodeController xoaCauhoi = new BodeController(jsonString.split("_")[1]);
			return xoaCauhoi.xoaCauhoi();
			
		case "LuuBode":
			BodeController luubode = new BodeController(jsonString.split("_")[1]);
			return luubode.luuBode();
			
		case "LuuDiemThi":
			DiemthiController diemThi = new DiemthiController(jsonString.split("_")[1]);
			return diemThi.luuDiemthi();
			
		case "LaySinhVien":
			SinhvienController sv = new SinhvienController(jsonString.split("_")[1]);
			return sv.getListSinhvien();
			
		case "LuuSinhVien":
			SinhvienController luusv = new SinhvienController(jsonString.split("_")[1]);
			return luusv.luuSinhvien();
			
		case "XoaSinhvien":
			SinhvienController xoaSv = new SinhvienController(jsonString.split("_")[1]);
			return xoaSv.XoaSinhvien();

		case "SuaSinhvien":
			SinhvienController suaSv = new SinhvienController(jsonString.split("_")[1]);
			return suaSv.SuaSinhvien();
			
		case "LayDiemThi": 
			DiemthiController layDiemThi = new DiemthiController(jsonString.split("_")[1]);
			return layDiemThi.getListBangdiem();
			
		case "SearchByMasv": 
			DiemthiController searchByMasv = new DiemthiController();
			return searchByMasv.getListBangdiemByMasv(jsonString.split("_")[1]);
		default:
			break;
		}
		return null;
	}
	
	
	
}
