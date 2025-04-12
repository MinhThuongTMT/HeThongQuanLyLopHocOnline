package HeThongQuanLyLopHocOnline;

import java.util.Date;

public class BaiTapInfo {
	private String tieuDe;
	private String noiDung;
	private Date hanNop;
	private String tenGV;
	private String monHoc;

	public BaiTapInfo(String tieuDe, String noiDung, Date hanNop, String tenGV, String monHoc) {
		this.tieuDe = tieuDe;
		this.noiDung = noiDung;
		this.hanNop = hanNop;
		this.tenGV = tenGV;
		this.monHoc = monHoc;
	}

	// Getter methods
	public String getTieuDe() {
		return tieuDe;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public Date getHanNop() {
		return hanNop;
	}

	public String getTenGV() {
		return tenGV;
	}

	public String getMonHoc() {
		return monHoc;
	}
}