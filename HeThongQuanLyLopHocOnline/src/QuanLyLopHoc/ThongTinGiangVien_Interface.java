package QuanLyLopHoc;

public interface ThongTinGiangVien_Interface {
	void editOrSave();

	void markAttendance();

	void grade();

	void assignTask();

	void updateMaMon();

	boolean checkFieldsFilled();

	void setFieldsEditable(boolean editable);
}
