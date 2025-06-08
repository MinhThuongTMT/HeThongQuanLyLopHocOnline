package QuanLyLopHoc;

public interface ChamDiem_Interface {
	void createTableIfNotExists();

	void loadStudentData();

	void saveScores();

	void exportToExcel();

	void QuanLyBaiTap();

	void DiemDanh();
}
