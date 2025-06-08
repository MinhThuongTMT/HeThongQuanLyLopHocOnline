package QuanLyLopHoc;

public interface ThongTinSinhVien_Interface {
	void edit();

	void save();

	void delete();

	void deleteCourse();

	void submitAssignment();

	boolean validateInput();

	void loadDataFromDatabase();

	void saveToDatabase();

	void updateAvatar(String gender);

	String getHoTen();

	String getMssv();

	String getLop();

	String getNgaySinh();

	String getGioiTinh();

	String getEmail();

	String getMonHoc();

	String getMaMon();

	String getSoTin();

	String getThoiGian();
}
