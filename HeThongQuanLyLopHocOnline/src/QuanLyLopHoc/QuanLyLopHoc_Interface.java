package QuanLyLopHoc;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

public interface QuanLyLopHoc_Interface {
	void loadClassData(String... lopList);

	void loadScoreData(String monHoc);

	void loadAttendanceData(String monHoc);

	void saveScoresToDatabase();

	Float validateScore(String score);

	void exportToExcel();

	void resetButtonColors(JButton clickedButton);

	void updateButtonColor(JButton clickedButton);

	void showPopupMenu(JButton clickedButton, JButton Lop_bnt, JPopupMenu manageLopMenu, JButton Mon_bnt,
			JPopupMenu subjectsMenu, JButton diemDanh_btn, JPopupMenu subjectsMenu1);
}
