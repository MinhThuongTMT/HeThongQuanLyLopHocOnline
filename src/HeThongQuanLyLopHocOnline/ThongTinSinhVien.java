package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

public class ThongTinSinhVien extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel ThongTinSinhVien;
	private JTextField MSSV_text1;
	private JTextField HoTen_text1;
	private JTextField MaMon_text1;
	private JTextField SoTin_text1;
	private JDateChooser NgaySinh_text;
	private JComboBox<String> GioiTinh_ComboBox;
	private JTextField Email_text1;
	private JTextField ThoiGian_text1;
	private JComboBox<String> MonHoc_comboBox1;
	private JComboBox<String> Lop_comboBox_1;
	private JLabel avata;
	// Các thuộc tính để lưu dữ liệu
	private String hoTen, mssv, lop, ngaySinh, gioiTinh, email, monHoc, maMon, soTin, thoiGian;
	// Lưu MSSV ban đầu để xóa thông tin cũ
	private final String originalMssv;
	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";
	// Regex để kiểm tra email
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

	public ThongTinSinhVien() {
		this.originalMssv = "";
		initialize();
	}

	public ThongTinSinhVien(String hoTen, String mssv, String lop, String ngaySinh, String gioiTinh, String email,
			String monHoc, String maMon, String soTin, String thoiGian) {
		this.hoTen = hoTen;
		this.mssv = mssv;
		this.originalMssv = mssv; // Lưu MSSV ban đầu
		this.lop = lop;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.email = email;
		this.monHoc = monHoc;
		this.maMon = maMon;
		this.soTin = soTin;
		this.thoiGian = thoiGian;
		initialize();
		loadDataToFields();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 895, 652);
		setLocationRelativeTo(null);

		ThongTinSinhVien = new JPanel();
		ThongTinSinhVien.setBackground(new Color(0, 0, 121));
		ThongTinSinhVien.setBorder(new EmptyBorder(5, 5, 5, 5));
		ThongTinSinhVien.setLayout(null);
		setContentPane(ThongTinSinhVien);

		// Panel tiêu đề
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 0));
		panel.setBounds(0, 0, 881, 57);
		panel.setLayout(null);
		ThongTinSinhVien.add(panel);

		JLabel lblTitle = new JLabel("THÔNG TIN SINH VIÊN");
		lblTitle.setBounds(284, 11, 302, 37);
		lblTitle.setForeground(new Color(0, 0, 0));
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panel.add(lblTitle);

		// Avatar
		avata = new JLabel();
		avata.setBounds(99, 95, 224, 227);
		avata.setBorder(new LineBorder(Color.WHITE, 3));
		avata.setBackground(Color.WHITE);
		avata.setOpaque(true);
		ThongTinSinhVien.add(avata);

		// Các trường thông tin
		HoTen_text1 = new JTextField();
		HoTen_text1.setBounds(539, 95, 310, 30);
		HoTen_text1.setEditable(false);
		HoTen_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThongTinSinhVien.add(HoTen_text1);

		MSSV_text1 = new JTextField();
		MSSV_text1.setBounds(539, 146, 310, 30);
		MSSV_text1.setEnabled(false);
		MSSV_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThongTinSinhVien.add(MSSV_text1);

		Lop_comboBox_1 = new JComboBox<>(new String[] { "", "D21CQVTHI-01", "D21CQVTVT-01", "D21CQVT-01N" });
		Lop_comboBox_1.setBounds(539, 197, 310, 30);
		Lop_comboBox_1.setEnabled(false);
		Lop_comboBox_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThongTinSinhVien.add(Lop_comboBox_1);

		NgaySinh_text = new JDateChooser();
		NgaySinh_text.setBounds(539, 248, 150, 30);
		NgaySinh_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		NgaySinh_text.setDateFormatString("dd/MM/yyyy");
		NgaySinh_text.setEnabled(false);
		ThongTinSinhVien.add(NgaySinh_text);

		GioiTinh_ComboBox = new JComboBox<>(new String[] { "", "Nam", "Nữ" });
		GioiTinh_ComboBox.setBounds(793, 248, 56, 30);
		GioiTinh_ComboBox.setEnabled(false);
		GioiTinh_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThongTinSinhVien.add(GioiTinh_ComboBox);

		Email_text1 = new JTextField();
		Email_text1.setBounds(539, 299, 310, 30);
		Email_text1.setEnabled(false);
		Email_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThongTinSinhVien.add(Email_text1);

		MonHoc_comboBox1 = new JComboBox<>(new String[] { "", "Cảm Biến", "Java", "Android" });
		MonHoc_comboBox1.setBounds(539, 350, 310, 30);
		MonHoc_comboBox1.setEnabled(false);
		MonHoc_comboBox1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThongTinSinhVien.add(MonHoc_comboBox1);

		SoTin_text1 = new JTextField();
		SoTin_text1.setBounds(539, 401, 310, 30);
		SoTin_text1.setEditable(false);
		SoTin_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThongTinSinhVien.add(SoTin_text1);

		MaMon_text1 = new JTextField();
		MaMon_text1.setBounds(539, 452, 310, 30);
		MaMon_text1.setEditable(false);
		MaMon_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThongTinSinhVien.add(MaMon_text1);

		ThoiGian_text1 = new JTextField();
		ThoiGian_text1.setBounds(539, 503, 310, 30);
		ThoiGian_text1.setEditable(false);
		ThoiGian_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThongTinSinhVien.add(ThoiGian_text1);

		// Các nhãn
		JLabel HoTen_Label = new JLabel("HỌ TÊN :");
		HoTen_Label.setForeground(Color.WHITE);
		HoTen_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		HoTen_Label.setBounds(448, 95, 81, 29);
		ThongTinSinhVien.add(HoTen_Label);

		JLabel MSSV_Label = new JLabel("MSSV :");
		MSSV_Label.setForeground(Color.WHITE);
		MSSV_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		MSSV_Label.setBounds(448, 146, 81, 29);
		ThongTinSinhVien.add(MSSV_Label);

		JLabel Lop_Label = new JLabel("LỚP :");
		Lop_Label.setForeground(Color.WHITE);
		Lop_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Lop_Label.setBounds(448, 197, 81, 29);
		ThongTinSinhVien.add(Lop_Label);

		JLabel NgaySinh_label = new JLabel("NGÀY SINH:");
		NgaySinh_label.setForeground(Color.WHITE);
		NgaySinh_label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		NgaySinh_label.setBounds(448, 248, 103, 29);
		ThongTinSinhVien.add(NgaySinh_label);

		JLabel GioiTinh_label = new JLabel("GIỚI TÍNH:");
		GioiTinh_label.setForeground(Color.WHITE);
		GioiTinh_label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		GioiTinh_label.setBounds(700, 248, 93, 29);
		ThongTinSinhVien.add(GioiTinh_label);

		JLabel Email_Label = new JLabel("EMAIL :");
		Email_Label.setForeground(Color.WHITE);
		Email_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Email_Label.setBounds(448, 299, 81, 29);
		ThongTinSinhVien.add(Email_Label);

		JLabel MonHoc_Label = new JLabel("MÔN HỌC :");
		MonHoc_Label.setForeground(Color.WHITE);
		MonHoc_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		MonHoc_Label.setBounds(448, 350, 93, 29);
		ThongTinSinhVien.add(MonHoc_Label);

		JLabel SoTin_Label = new JLabel("SỐ TÍN:");
		SoTin_Label.setForeground(Color.WHITE);
		SoTin_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		SoTin_Label.setBounds(448, 401, 81, 29);
		ThongTinSinhVien.add(SoTin_Label);

		JLabel MaMon_Label = new JLabel("MÃ MÔN :");
		MaMon_Label.setForeground(Color.WHITE);
		MaMon_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		MaMon_Label.setBounds(448, 452, 81, 29);
		ThongTinSinhVien.add(MaMon_Label);

		JLabel ThoiGian_Label = new JLabel("THỜI GIAN:");
		ThoiGian_Label.setForeground(Color.WHITE);
		ThoiGian_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		ThoiGian_Label.setBounds(448, 503, 93, 29);
		ThongTinSinhVien.add(ThoiGian_Label);

		// Các nút
		JButton Sua_button = new JButton("SỬA");
		Sua_button.setBounds(61, 360, 120, 44);
		Sua_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Sua_button.setBackground(new Color(50, 150, 255));
		Sua_button.setForeground(Color.BLACK);
		Sua_button.setBorder(new LineBorder(Color.WHITE, 1));
		ThongTinSinhVien.add(Sua_button);

		Sua_button.addActionListener(e -> {
			// Lấy dữ liệu từ database
			loadDataFromDatabase();
			// Cho phép chỉnh sửa các trường
			HoTen_text1.setEditable(true);
			MSSV_text1.setEnabled(true);
			NgaySinh_text.setEnabled(true);
			GioiTinh_ComboBox.setEnabled(true);
			Email_text1.setEnabled(true);
			SoTin_text1.setEditable(true);
			MaMon_text1.setEditable(true);
			ThoiGian_text1.setEditable(true);
			Lop_comboBox_1.setEnabled(true);
			MonHoc_comboBox1.setEnabled(true);
		});

		JButton Luu_button = new JButton("LƯU");
		Luu_button.setBounds(252, 360, 120, 44);
		Luu_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Luu_button.setBackground(new Color(50, 150, 255));
		Luu_button.setForeground(Color.BLACK);
		Luu_button.setBorder(new LineBorder(Color.WHITE, 1));
		ThongTinSinhVien.add(Luu_button);

		Luu_button.addActionListener(e -> {
			// Kiểm tra dữ liệu đầu vào
			if (HoTen_text1.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Họ tên không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (MSSV_text1.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "MSSV không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (Lop_comboBox_1.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (NgaySinh_text.getDate() == null) {
				JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (GioiTinh_ComboBox.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (Email_text1.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Email không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!EMAIL_PATTERN.matcher(Email_text1.getText().trim()).matches()) {
				JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (MonHoc_comboBox1.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (MaMon_text1.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Mã môn không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (SoTin_text1.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Số tín không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				Integer.parseInt(SoTin_text1.getText().trim());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Số tín phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (ThoiGian_text1.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Thời gian không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Cập nhật lại các thuộc tính

			hoTen = HoTen_text1.getText();
			mssv = MSSV_text1.getText();
			lop = (String) Lop_comboBox_1.getSelectedItem();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			ngaySinh = sdf.format(NgaySinh_text.getDate());
			gioiTinh = (String) GioiTinh_ComboBox.getSelectedItem();
			email = Email_text1.getText();
			monHoc = (String) MonHoc_comboBox1.getSelectedItem();
			maMon = MaMon_text1.getText();
			soTin = SoTin_text1.getText();
			thoiGian = ThoiGian_text1.getText();

			// Lưu vào database
			saveToDatabase();

			// Vô hiệu hóa các trường sau khi lưu
			HoTen_text1.setEditable(false);
			MSSV_text1.setEnabled(false);
			NgaySinh_text.setEnabled(false);
			GioiTinh_ComboBox.setEnabled(false);
			Email_text1.setEnabled(false);
			SoTin_text1.setEditable(false);
			MaMon_text1.setEditable(false);
			ThoiGian_text1.setEditable(false);
			Lop_comboBox_1.setEnabled(false);
			MonHoc_comboBox1.setEnabled(false);

			updateAvatar((String) GioiTinh_ComboBox.getSelectedItem());
		});

		JButton NopBaiTap_button = new JButton("NỘP BÀI TẬP");
		NopBaiTap_button.setBounds(139, 471, 162, 44);
		NopBaiTap_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
		NopBaiTap_button.setBackground(new Color(255, 165, 80));
		NopBaiTap_button.setForeground(Color.BLACK);
		NopBaiTap_button.setBorder(new LineBorder(Color.WHITE, 1));
		ThongTinSinhVien.add(NopBaiTap_button);

		NopBaiTap_button.addActionListener(e -> {
			BaiTap baiTapFrame = new BaiTap();
			baiTapFrame.setVisible(true);
		});

		// Xử lý khi chọn môn học
		MonHoc_comboBox1.addItemListener(e -> {
			String selectedSubject = (String) MonHoc_comboBox1.getSelectedItem();
			if ("Cảm Biến".equals(selectedSubject)) {
				MaMon_text1.setText("iot");
				SoTin_text1.setText("3");
				ThoiGian_text1.setText("4/5-6-8");
			} else if ("Java".equals(selectedSubject)) {
				MaMon_text1.setText("jv");
				SoTin_text1.setText("2");
				ThoiGian_text1.setText("6/7-7/8");
			} else if ("Android".equals(selectedSubject)) {
				MaMon_text1.setText("ad");
				SoTin_text1.setText("3");
				ThoiGian_text1.setText("7/7-7/9");
			} else {
				MaMon_text1.setText("");
				SoTin_text1.setText("");
				ThoiGian_text1.setText("");
			}
		});
	}

	private void loadDataToFields() {
		HoTen_text1.setText(hoTen);
		MSSV_text1.setText(mssv);
		Lop_comboBox_1.setSelectedItem(lop);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (ngaySinh != null && !ngaySinh.isEmpty()) {
				NgaySinh_text.setDate(sdf.parse(ngaySinh));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		GioiTinh_ComboBox.setSelectedItem(gioiTinh);
		Email_text1.setText(email);
		MonHoc_comboBox1.setSelectedItem(monHoc);
		MaMon_text1.setText(maMon);
		SoTin_text1.setText(soTin);
		ThoiGian_text1.setText(thoiGian);
		updateAvatar(gioiTinh);
	}

	private void loadDataFromDatabase() {
		Connection conn = null;
		PreparedStatement pstmtStudent = null;
		PreparedStatement pstmtCourse = null;
		ResultSet rsStudent = null;
		ResultSet rsCourse = null;

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			// Lấy thông tin sinh viên
			String sqlStudent = "SELECT * FROM students WHERE mssv = ?";
			pstmtStudent = conn.prepareStatement(sqlStudent);
			pstmtStudent.setString(1, originalMssv);
			rsStudent = pstmtStudent.executeQuery();

			// Lấy thông tin khóa học
			String sqlCourse = "SELECT * FROM courses WHERE mssv = ?";
			pstmtCourse = conn.prepareStatement(sqlCourse);
			pstmtCourse.setString(1, originalMssv);
			rsCourse = pstmtCourse.executeQuery();

			if (rsStudent.next()) {
				hoTen = rsStudent.getString("hoten");
				lop = rsStudent.getString("lop");
				gioiTinh = rsStudent.getString("gioitinh");
				email = rsStudent.getString("email");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				ngaySinh = rsStudent.getDate("ngaysinh") != null ? sdf.format(rsStudent.getDate("ngaysinh")) : "";

				// Cập nhật các trường
				HoTen_text1.setText(hoTen);
				MSSV_text1.setText(originalMssv);
				Lop_comboBox_1.setSelectedItem(lop);
				GioiTinh_ComboBox.setSelectedItem(gioiTinh);
				Email_text1.setText(email);
				if (!ngaySinh.isEmpty()) {
					NgaySinh_text.setDate(sdf.parse(ngaySinh));
				}
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin sinh viên với MSSV: " + originalMssv,
						"Lỗi", JOptionPane.ERROR_MESSAGE);
			}

			if (rsCourse.next()) {
				monHoc = rsCourse.getString("monhoc");
				maMon = rsCourse.getString("mamon");
				soTin = String.valueOf(rsCourse.getInt("sotin"));
				thoiGian = rsCourse.getString("thoigian");

				// Cập nhật các trường
				MonHoc_comboBox1.setSelectedItem(monHoc);
				MaMon_text1.setText(maMon);
				SoTin_text1.setText(soTin);
				ThoiGian_text1.setText(thoiGian);
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khóa học với MSSV: " + originalMssv,
						"Lỗi", JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày sinh: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} finally {
			closeResources(rsStudent, rsCourse, pstmtStudent, pstmtCourse, conn);
		}
	}

	private void saveToDatabase() {
		Connection conn = null;
		PreparedStatement pstmtDeleteCourse = null;
		PreparedStatement pstmtDeleteStudent = null;
		PreparedStatement pstmtInsertStudent = null;
		PreparedStatement pstmtInsertCourse = null;

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			conn.setAutoCommit(false); // Bắt đầu transaction

			// Xóa thông tin cũ của khóa học trước (do ràng buộc khóa ngoại)
			String sqlDeleteCourse = "DELETE FROM courses WHERE mssv = ?";
			pstmtDeleteCourse = conn.prepareStatement(sqlDeleteCourse);
			pstmtDeleteCourse.setString(1, originalMssv);
			pstmtDeleteCourse.executeUpdate();

			// Xóa thông tin cũ của sinh viên
			String sqlDeleteStudent = "DELETE FROM students WHERE mssv = ?";
			pstmtDeleteStudent = conn.prepareStatement(sqlDeleteStudent);
			pstmtDeleteStudent.setString(1, originalMssv);
			pstmtDeleteStudent.executeUpdate();

			// Thêm thông tin sinh viên mới
			String sqlInsertStudent = "INSERT INTO students (mssv, hoten, ngaysinh, gioitinh, lop, email) VALUES (?, ?, ?, ?, ?, ?)";
			pstmtInsertStudent = conn.prepareStatement(sqlInsertStudent);
			pstmtInsertStudent.setString(1, MSSV_text1.getText());
			pstmtInsertStudent.setString(2, HoTen_text1.getText());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = NgaySinh_text.getDate();
			pstmtInsertStudent.setDate(3, date != null ? new java.sql.Date(date.getTime()) : null);
			pstmtInsertStudent.setString(4, GioiTinh_ComboBox.getSelectedItem().toString());
			pstmtInsertStudent.setString(5, Lop_comboBox_1.getSelectedItem().toString());
			pstmtInsertStudent.setString(6, Email_text1.getText());
			pstmtInsertStudent.executeUpdate();

			// Thêm thông tin khóa học mới
			String sqlInsertCourse = "INSERT INTO courses (mssv, monhoc, mamon, sotin, thoigian) VALUES (?, ?, ?, ?, ?)";
			pstmtInsertCourse = conn.prepareStatement(sqlInsertCourse);
			pstmtInsertCourse.setString(1, MSSV_text1.getText());
			pstmtInsertCourse.setString(2, MonHoc_comboBox1.getSelectedItem().toString());
			pstmtInsertCourse.setString(3, MaMon_text1.getText());
			pstmtInsertCourse.setInt(4, Integer.parseInt(SoTin_text1.getText()));
			pstmtInsertCourse.setString(5, ThoiGian_text1.getText());
			pstmtInsertCourse.executeUpdate();

			conn.commit(); // Commit transaction
			JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!", "Thành công",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException ex) {
			try {
				if (conn != null) {
					conn.rollback(); // Rollback nếu có lỗi
				}
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "Lỗi khi lưu vào cơ sở dữ liệu: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Số tín phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		} finally {
			closeResources(null, null, pstmtDeleteCourse, pstmtDeleteStudent, conn);
			closeResources(null, null, pstmtInsertStudent, pstmtInsertCourse, null);
		}
	}

	private void closeResources(ResultSet rsStudent, ResultSet rsCourse, PreparedStatement pstmt1,
			PreparedStatement pstmt2, Connection conn) {
		try {
			if (rsStudent != null)
				rsStudent.close();
			if (rsCourse != null)
				rsCourse.close();
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();
			if (conn != null)
				conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void updateAvatar(String gioiTinh) {
		String avatarPath = "/Icon/avata_nam.png"; // Default avatar
		if ("Nữ".equalsIgnoreCase(gioiTinh)) {
			avatarPath = "/Icon/avata_nu.png";
		} else if ("Nam".equalsIgnoreCase(gioiTinh)) {
			avatarPath = "/Icon/avata_nam.png";
		}

		try {
			ImageIcon avatarIcon = new ImageIcon(getClass().getResource(avatarPath));
			Image scaledImage = avatarIcon.getImage().getScaledInstance(224, 227, Image.SCALE_SMOOTH);
			avata.setIcon(new ImageIcon(scaledImage));
		} catch (Exception e) {
			avata.setText("Avatar not found");
			avata.setHorizontalAlignment(JLabel.CENTER);
		}
	}

	// Getter methods
	public String getHoTen() {
		return hoTen;
	}

	public String getMssv() {
		return mssv;
	}

	public String getLop() {
		return lop;
	}

	public String getNgaySinh() {
		return ngaySinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public String getEmail() {
		return email;
	}

	public String getMonHoc() {
		return monHoc;
	}

	public String getMaMon() {
		return maMon;
	}

	public String getSoTin() {
		return soTin;
	}

	public String getThoiGian() {
		return thoiGian;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ThongTinSinhVien frame = new ThongTinSinhVien();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}