package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class SinhVien extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField HoTen_text;
	private JTextField Mssv_text;
	private JTextField Email_text;
	private JDateChooser NgaySinh;
	private JTextField ThoiGian_text;
	private JTextField MaMon_text;
	private JTextField SoTin_text;
	private JComboBox<String> Lop_ComboBox;
	private JComboBox<String> GioiTinh_ComboBox;
	private JComboBox<String> MonHoc_ComboBox;
	private ThongTinSinhVien tempStudentInfo; // Biến tạm để lưu thông tin

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public SinhVien() {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("SINH VIÊN");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(10, 39, 132, 43);
		add(lblNewLabel);

		// Panel nhập thông tin sinh viên
		JPanel panel = new JPanel();
		panel.setBounds(12, 85, 432, 448);
		add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_2_1 = new JLabel("NHẬP THÔNG TIN SINH VIÊN");
		lblNewLabel_2_1.setForeground(new Color(0, 0, 121));
		lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_2_1.setBounds(51, 0, 299, 69);
		panel.add(lblNewLabel_2_1);

		JLabel HoTen_Label = new JLabel("HỌ TÊN :");
		HoTen_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		HoTen_Label.setBounds(18, 74, 81, 29);
		panel.add(HoTen_Label);

		JLabel MSSV_Label = new JLabel("MSSV  :");
		MSSV_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		MSSV_Label.setBounds(19, 127, 81, 29);
		panel.add(MSSV_Label);

		JLabel Lop_Label = new JLabel("LỚP :");
		Lop_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Lop_Label.setBounds(18, 187, 81, 29);
		panel.add(Lop_Label);

		JLabel NgaySinh_label = new JLabel("NGÀY SINH:");
		NgaySinh_label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		NgaySinh_label.setBounds(18, 250, 103, 29);
		panel.add(NgaySinh_label);

		JLabel GiowiTinh_Label = new JLabel("GIỚI TÍNH:");
		GiowiTinh_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		GiowiTinh_Label.setBounds(256, 250, 94, 29);
		panel.add(GiowiTinh_Label);

		JLabel Email_Label = new JLabel("EMAIL :");
		Email_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Email_Label.setBounds(18, 314, 81, 29);
		panel.add(Email_Label);

		HoTen_text = new JTextField();
		HoTen_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		HoTen_text.setBounds(98, 72, 310, 30);
		panel.add(HoTen_text);
		HoTen_text.setColumns(10);

		Mssv_text = new JTextField();
		Mssv_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Mssv_text.setColumns(10);
		Mssv_text.setBounds(98, 127, 310, 30);
		panel.add(Mssv_text);

		Email_text = new JTextField();
		Email_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Email_text.setColumns(10);
		Email_text.setBounds(98, 314, 310, 30);
		panel.add(Email_text);

		NgaySinh = new JDateChooser();
		NgaySinh.setBounds(112, 249, 130, 30);
		NgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		NgaySinh.setDateFormatString("dd/MM/yyyy");
		panel.add(NgaySinh);

		String[] items = { "", "Nam", "Nữ" };
		GioiTinh_ComboBox = new JComboBox<>(items);
		GioiTinh_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		GioiTinh_ComboBox.setBounds(343, 250, 65, 29);
		panel.add(GioiTinh_ComboBox);

		String[] items2 = { "", "D21CQVTHI-01", "D21CQVTVT-01", "D21CQVT-01N" };
		Lop_ComboBox = new JComboBox<>(items2);
		Lop_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Lop_ComboBox.setBounds(98, 187, 310, 29);
		panel.add(Lop_ComboBox);

		// Panel nhập thông tin khóa học
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(454, 85, 432, 448);
		add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2_1_1 = new JLabel("NHẬP THÔNG TIN KHÓA HỌC");
		lblNewLabel_2_1_1.setForeground(new Color(0, 0, 121));
		lblNewLabel_2_1_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_2_1_1.setBounds(66, 0, 299, 69);
		panel_1.add(lblNewLabel_2_1_1);

		JLabel HoTen_Label_1 = new JLabel("MÔN HỌC :");
		HoTen_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		HoTen_Label_1.setBounds(24, 71, 103, 29);
		panel_1.add(HoTen_Label_1);

		ThoiGian_text = new JTextField();
		ThoiGian_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThoiGian_text.setColumns(10);
		ThoiGian_text.setBounds(114, 276, 296, 30);
		panel_1.add(ThoiGian_text);

		MaMon_text = new JTextField();
		MaMon_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		MaMon_text.setColumns(10);
		MaMon_text.setBounds(114, 138, 296, 30);
		panel_1.add(MaMon_text);

		JLabel Lop_Label_1 = new JLabel("MÃ MÔN :");
		Lop_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Lop_Label_1.setBounds(24, 139, 81, 29);
		panel_1.add(Lop_Label_1);

		SoTin_text = new JTextField();
		SoTin_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		SoTin_text.setColumns(10);
		SoTin_text.setBounds(113, 205, 296, 30);
		panel_1.add(SoTin_text);

		JLabel Sdt_Label_1 = new JLabel("THỜI GIAN:");
		Sdt_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Sdt_Label_1.setBounds(24, 277, 93, 29);
		panel_1.add(Sdt_Label_1);

		MonHoc_ComboBox = new JComboBox<>();
		MonHoc_ComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "", "Cảm Biến", "Java", "Android" }));
		MonHoc_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		MonHoc_ComboBox.setBounds(114, 71, 296, 29);
		panel_1.add(MonHoc_ComboBox);

		JLabel SoTin_Label_1_1 = new JLabel("SỐ TÍN :");
		SoTin_Label_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		SoTin_Label_1_1.setBounds(24, 205, 103, 29);
		panel_1.add(SoTin_Label_1_1);

		MonHoc_ComboBox.addItemListener(e -> {
			String selectedSubject = (String) MonHoc_ComboBox.getSelectedItem();
			if ("Cảm Biến".equals(selectedSubject)) {
				MaMon_text.setText("iot");
				SoTin_text.setText("3");
				ThoiGian_text.setText("4/5-6-8");
			} else if ("Java".equals(selectedSubject)) {
				MaMon_text.setText("jv");
				SoTin_text.setText("2");
				ThoiGian_text.setText("6/7-7/8");
			} else if ("Android".equals(selectedSubject)) {
				MaMon_text.setText("ad");
				SoTin_text.setText("3");
				ThoiGian_text.setText("7/7-7/9");
			} else {
				MaMon_text.setText("");
				SoTin_text.setText("");
				ThoiGian_text.setText("");
			}
		});

		// Tạo bảng khi khởi tạo
		createTables();

		// Nút LƯU
		JButton Luu_button = new JButton("LƯU");
		Luu_button.setBackground(new Color(255, 140, 0));
		Luu_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Luu_button.setBounds(226, 563, 120, 44);
		add(Luu_button);

		Luu_button.addActionListener(e -> {
			if (HoTen_text.getText().trim().isEmpty() || Mssv_text.getText().trim().isEmpty()
					|| Lop_ComboBox.getSelectedIndex() == 0 || NgaySinh.getDate() == null
					|| GioiTinh_ComboBox.getSelectedIndex() == 0 || Email_text.getText().trim().isEmpty()
					|| MonHoc_ComboBox.getSelectedIndex() == 0 || MaMon_text.getText().trim().isEmpty()
					|| SoTin_text.getText().trim().isEmpty() || ThoiGian_text.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ tất cả các thông tin!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			} else {
				saveToDatabase();
			}
		});

		// Nút XUẤT
		JButton xuat_button = new JButton("XUẤT");
		xuat_button.setBackground(new Color(255, 215, 0));
		xuat_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
		xuat_button.setBounds(572, 563, 120, 44);
		xuat_button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		add(xuat_button);

		xuat_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tempStudentInfo == null) {
					JOptionPane.showMessageDialog(SinhVien.this,
							"Chưa có dữ liệu nào được lưu. Vui lòng nhấn LƯU trước!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				} else {
					EventQueue.invokeLater(() -> {
						ThongTinSinhVien frame = new ThongTinSinhVien(tempStudentInfo.getHoTen(),
								tempStudentInfo.getMssv(), tempStudentInfo.getLop(), tempStudentInfo.getNgaySinh(),
								tempStudentInfo.getGioiTinh(), tempStudentInfo.getEmail(), tempStudentInfo.getMonHoc(),
								tempStudentInfo.getMaMon(), tempStudentInfo.getSoTin(), tempStudentInfo.getThoiGian());
						frame.setVisible(true);
					});
				}
			}
		});
	}

	private void createTables() {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			stmt = conn.createStatement();

			String sqlStudents = "CREATE TABLE IF NOT EXISTS students (" + "mssv VARCHAR(50) PRIMARY KEY,"
					+ "hoten VARCHAR(100)," + "ngaysinh DATE," + "gioitinh VARCHAR(10)," + "lop VARCHAR(50),"
					+ "email VARCHAR(100)" + ")";
			stmt.executeUpdate(sqlStudents);

			String sqlCourses = "CREATE TABLE IF NOT EXISTS courses (" + "id SERIAL PRIMARY KEY,"
					+ "mssv VARCHAR(50) REFERENCES students(mssv)," + "monhoc VARCHAR(100)," + "mamon VARCHAR(50),"
					+ "sotin INTEGER," + "thoigian VARCHAR(50)" + ")";
			stmt.executeUpdate(sqlCourses);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tạo bảng: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void saveToDatabase() {
		Connection conn = null;
		PreparedStatement pstmtStudent = null;
		PreparedStatement pstmtCourse = null;
		PreparedStatement pstmtCheck = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			// Kiểm tra định dạng MSSV: N + 2 số + DCVT + 3 số
			String mssv = Mssv_text.getText();
			if (!mssv.matches("^N\\d{2}DCVT\\d{3}$")) {
				JOptionPane.showMessageDialog(this, "MSSV phải có định dạng N + 2 số + DCVT + 3 số (VD: N21DCVT101)",
						"Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Kiểm tra định dạng Email
			String email = Email_text.getText();
			if (!email.matches("^[a-zA-Z0-9]+@student\\.ptithcm\\.edu\\.vn$")) {
				JOptionPane.showMessageDialog(this, "Email phải có định dạng [số/chữ]@student.ptithcm.edu.vn", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Kiểm tra xem MSSV đã tồn tại chưa
			String checkSql = "SELECT mssv FROM students WHERE mssv = ?";
			pstmtCheck = conn.prepareStatement(checkSql);
			pstmtCheck.setString(1, mssv);
			rs = pstmtCheck.executeQuery();

			if (rs.next()) {
				JOptionPane.showMessageDialog(this, "MSSV đã tồn tại trong cơ sở dữ liệu!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Lưu thông tin sinh viên
			String sqlStudent = "INSERT INTO students (mssv, hoten, ngaysinh, gioitinh, lop, email) VALUES (?, ?, ?, ?, ?, ?)";
			pstmtStudent = conn.prepareStatement(sqlStudent);
			pstmtStudent.setString(1, mssv);
			pstmtStudent.setString(2, HoTen_text.getText());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = NgaySinh.getDate();
			pstmtStudent.setDate(3, date != null ? new java.sql.Date(date.getTime()) : null);
			pstmtStudent.setString(4, GioiTinh_ComboBox.getSelectedItem().toString());
			pstmtStudent.setString(5, Lop_ComboBox.getSelectedItem().toString());
			pstmtStudent.setString(6, email);
			pstmtStudent.executeUpdate();

			// Lưu thông tin khóa học
			String sqlCourse = "INSERT INTO courses (mssv, monhoc, mamon, sotin, thoigian) VALUES (?, ?, ?, ?, ?)";
			pstmtCourse = conn.prepareStatement(sqlCourse);
			pstmtCourse.setString(1, mssv);
			pstmtCourse.setString(2, MonHoc_ComboBox.getSelectedItem().toString());
			pstmtCourse.setString(3, MaMon_text.getText());
			pstmtCourse.setInt(4, Integer.parseInt(SoTin_text.getText()));
			pstmtCourse.setString(5, ThoiGian_text.getText());
			pstmtCourse.executeUpdate();

			// Lưu dữ liệu vào biến tạm
			SimpleDateFormat sdfDisplay = new SimpleDateFormat("dd/MM/yyyy");
			String ngaySinh = date != null ? sdfDisplay.format(date) : "";
			tempStudentInfo = new ThongTinSinhVien(HoTen_text.getText(), mssv,
					Lop_ComboBox.getSelectedItem().toString(), ngaySinh, GioiTinh_ComboBox.getSelectedItem().toString(),
					email, MonHoc_ComboBox.getSelectedItem().toString(), MaMon_text.getText(), SoTin_text.getText(),
					ThoiGian_text.getText());

			JOptionPane.showMessageDialog(this, "Lưu thông tin thành công!", "Thành công",
					JOptionPane.INFORMATION_MESSAGE);

			clearForm();

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi lưu vào database: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Số tín phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmtCheck != null)
					pstmtCheck.close();
				if (pstmtStudent != null)
					pstmtStudent.close();
				if (pstmtCourse != null)
					pstmtCourse.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void clearForm() {
		HoTen_text.setText("");
		Mssv_text.setText("");
		Email_text.setText("");
		NgaySinh.setDate(null);
		ThoiGian_text.setText("");
		MaMon_text.setText("");
		SoTin_text.setText("");
		Lop_ComboBox.setSelectedIndex(0);
		GioiTinh_ComboBox.setSelectedIndex(0);
		MonHoc_ComboBox.setSelectedIndex(0);
	}
}
