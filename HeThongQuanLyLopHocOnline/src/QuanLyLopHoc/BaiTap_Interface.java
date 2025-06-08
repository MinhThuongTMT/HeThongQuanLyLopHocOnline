package QuanLyLopHoc;

public interface BaiTap_Interface {
	void initializeDatabase();

	void loadMonHocFromDatabase(String mssv);

	void updateTieuDeComboBox();

	void updateAssignmentDetails();

	void chooseFile();

	void submitAssignment();

	void cancelSubmission();

	boolean isMssvExists(String mssv);

	boolean isSubmissionExists(String mssv, String monHoc, String tieuDe);

	void checkPreviousSubmission(String mssv);
}
