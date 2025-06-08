package QuanLyLopHoc;

public interface SinhVien_Interface {
	void save();

	void export();

	boolean validateInput();

	void updateCourseInfo();

	boolean checkScheduleConflict();

	boolean isAnySubjectSelected();

	void createTables();

	void saveToDatabase();

	void clearForm();
}
