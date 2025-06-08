package QuanLyLopHoc;

public interface QuanLyBaiTap_Interface {
	void Xoa();

	void timKiem();

	void loadBaiTapDaGiao(String monHocFilter);

	void loadDataFromDatabase(String monHocFilter);

	void loadStudentSubmissionsForAssignment(String monHoc, String tieuDe);

	void exportToExcel();
}
