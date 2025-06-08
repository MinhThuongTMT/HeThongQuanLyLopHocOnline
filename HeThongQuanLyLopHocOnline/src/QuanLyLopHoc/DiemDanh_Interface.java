package QuanLyLopHoc;

public interface DiemDanh_Interface {
	boolean createRequiredTables();

	void loadStudentData(String monGiangDay);

	void saveAttendance(String monGiangDay);

	void exportToExcel();
}
