package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
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

public class ThongTinGiangVien extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel ThongTinGiangVienPanel;
	private JTextField HoTen_text1;
	private JTextField MaGV_text1;
	private JTextField Email_text1;
	private JTextField SoDienThoai_text1;
	private JTextField MaMon_text1;
	private JLabel avata;
	private boolean isEditing = false;
	private JButton btnSua;
	private JComboBox<String> Mon_ComboBox;
	private String originalMaGV; // Lưu mã GV ban đầu để xóa bản ghi cũ

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	// Regex cho email và số điện thoại
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9]+@ptithcm\\.edu\\.vn$");
	private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");

	public ThongTinGiangVien() {
		initialize();
	}

	public ThongTinGiangVien(String hoTen, String maGV, String email, String soDienThoai, String monGiangDay,
			String maMon) {
		initialize();
		HoTen_text1.setText(hoTen);
		MaGV_text1.setText(maGV);
		Email_text1.setText(email);
		SoDienThoai_text1.setText(soDienThoai);
		Mon_ComboBox.setSelectedItem(monGiangDay != null ? monGiangDay : "");
		MaMon_text1.setText(maMon);
		originalMaGV = maGV; // Lưu mã GV ban đầu
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 895, 652);
		setLocationRelativeTo(null);

		ThongTinGiangVienPanel = new JPanel();
		ThongTinGiangVienPanel.setBackground(new Color(0, 0, 121));
		ThongTinGiangVienPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		ThongTinGiangVienPanel.setLayout(null);
		setContentPane(ThongTinGiangVienPanel);

		// Khởi tạo các trường nhập liệu
		HoTen_text1 = new JTextField();
		HoTen_text1.setBounds(539, 95, 310, 30);
		HoTen_text1.setEditable(false);
		ThongTinGiangVienPanel.add(HoTen_text1);
		HoTen_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		MaGV_text1 = new JTextField();
		MaGV_text1.setBounds(539, 157, 309, 30);
		MaGV_text1.setEditable(false);
		ThongTinGiangVienPanel.add(MaGV_text1);
		MaGV_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		Email_text1 = new JTextField();
		Email_text1.setBounds(540, 226, 309, 30);
		Email_text1.setEditable(false);
		ThongTinGiangVienPanel.add(Email_text1);
		Email_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		SoDienThoai_text1 = new JTextField();
		SoDienThoai_text1.setBounds(539, 296, 309, 30);
		SoDienThoai_text1.setEditable(false);
		ThongTinGiangVienPanel.add(SoDienThoai_text1);
		SoDienThoai_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		MaMon_text1 = new JTextField();
		MaMon_text1.setBounds(539, 436, 309, 30);
		MaMon_text1.setEditable(false);
		ThongTinGiangVienPanel.add(MaMon_text1);
		MaMon_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		// Các nhãn
		JLabel HoTen_Label = new JLabel("HỌ TÊN:");
		HoTen_Label.setForeground(new Color(255, 255, 255));
		HoTen_Label.setBounds(374, 96, 81, 29);
		ThongTinGiangVienPanel.add(HoTen_Label);
		HoTen_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel MaGV_Label = new JLabel("MÃ GV:");
		MaGV_Label.setForeground(new Color(255, 255, 255));
		MaGV_Label.setBounds(374, 158, 81, 29);
		ThongTinGiangVienPanel.add(MaGV_Label);
		MaGV_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel Email_Label = new JLabel("EMAIL:");
		Email_Label.setForeground(new Color(255, 255, 255));
		Email_Label.setBounds(374, 227, 81, 29);
		ThongTinGiangVienPanel.add(Email_Label);
		Email_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel SoDienThoai_Label = new JLabel("SỐ ĐIỆN THOẠI:");
		SoDienThoai_Label.setForeground(new Color(255, 255, 255));
		SoDienThoai_Label.setBounds(374, 297, 137, 29);
		ThongTinGiangVienPanel.add(SoDienThoai_Label);
		SoDienThoai_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel MonGiangDay_Label = new JLabel("MÔN GIẢNG DẠY:");
		MonGiangDay_Label.setForeground(new Color(255, 255, 255));
		MonGiangDay_Label.setBounds(363, 364, 137, 29);
		ThongTinGiangVienPanel.add(MonGiangDay_Label);
		MonGiangDay_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel MaMon_Label = new JLabel("MÃ MÔN:");
		MaMon_Label.setForeground(new Color(255, 255, 255));
		MaMon_Label.setBounds(374, 437, 81, 29);
		ThongTinGiangVienPanel.add(MaMon_Label);
		MaMon_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));

		avata = new JLabel();
		avata.setBounds(77, 121, 224, 227);
		avata.setBorder(new LineBorder(Color.WHITE, 3));
		avata.setBackground(Color.WHITE);
		avata.setOpaque(true);
		ThongTinGiangVienPanel.add(avata);

		ImageIcon avatarIcon = new ImageIcon(getClass().getResource("/Icon/GV.png"));
		Image scaledImage = avatarIcon.getImage().getScaledInstance(224, 227, Image.SCALE_SMOOTH);
		avata.setIcon(new ImageIcon(scaledImage));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 0));
		panel.setBounds(0, 0, 881, 57);
		panel.setLayout(null);
		ThongTinGiangVienPanel.add(panel);

		JLabel lblTitle = new JLabel("THÔNG TIN GIẢNG VIÊN");
		lblTitle.setBounds(284, 11, 339, 37);
		panel.add(lblTitle);
		lblTitle.setForeground(new Color(0, 0, 0));
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));

		// Khởi tạo JComboBox cho Môn giảng dạy
		Mon_ComboBox = new JComboBox<>();
		Mon_ComboBox.setBounds(539, 363, 310, 30);
		Mon_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Mon_ComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "", "Cảm Biến", "Java", "Android" }));
		Mon_ComboBox.setEnabled(false);
		ThongTinGiangVienPanel.add(Mon_ComboBox);

		// Xử lý khi chọn môn học để cập nhật mã môn
		Mon_ComboBox.addItemListener(e -> {
			String selectedSubject = (String) Mon_ComboBox.getSelectedItem();
			if ("Cảm Biến".equals(selectedSubject)) {
				MaMon_text1.setText("iot");
			} else if ("Java".equals(selectedSubject)) {
				MaMon_text1.setText("jv");
			} else if ("Android".equals(selectedSubject)) {
				MaMon_text1.setText("ad");
			} else {
				MaMon_text1.setText("");
			}
		});

		// Nút SỬA
		btnSua = new JButton("SỬA");
		btnSua.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnSua.setBounds(97, 514, 129, 44);
		btnSua.setBackground(new Color(50, 150, 255));
		btnSua.setForeground(new Color(0, 0, 0));
		btnSua.setBorder(new LineBorder(Color.WHITE, 1));
		ThongTinGiangVienPanel.add(btnSua);

		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isEditing) {
					// Chuyển sang chế độ chỉnh sửa
					btnSua.setText("LƯU");
					btnSua.setBackground(new Color(255, 53, 53));
					btnSua.setForeground(new Color(0, 0, 0));
					btnSua.setBorder(new LineBorder(Color.WHITE, 1));
					setFieldsEditable(true);
					isEditing = true;
				} else {
					// Lưu thông tin vào database
					if (!checkFieldsFilled()) {
						JOptionPane.showMessageDialog(ThongTinGiangVien.this, "Vui lòng điền đầy đủ thông tin!",
								"Cảnh báo", JOptionPane.WARNING_MESSAGE);
						return;
					}

					String hoTen = HoTen_text1.getText().trim();
					String maGV = MaGV_text1.getText().trim();
					String email = Email_text1.getText().trim();
					String soDienThoai = SoDienThoai_text1.getText().trim();
					String monGiangDay = (String) Mon_ComboBox.getSelectedItem();
					String maMon = MaMon_text1.getText().trim();

					// Kiểm tra định dạng email
					if (!EMAIL_PATTERN.matcher(email).matches()) {
						JOptionPane.showMessageDialog(ThongTinGiangVien.this,
								"Email phải có định dạng: [chữ/số]@ptithcm.edu.vn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Kiểm tra định dạng số điện thoại
					if (!PHONE_PATTERN.matcher(soDienThoai).matches()) {
						JOptionPane.showMessageDialog(ThongTinGiangVien.this,
								"Số điện thoại phải có 10 chữ số và bắt đầu bằng 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Lưu vào database
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					try {
						conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

						// Kiểm tra mã GV mới nếu khác mã GV cũ
						if (!maGV.equals(originalMaGV)) {
							String checkSql = "SELECT ma_gv FROM giang_vien WHERE ma_gv = ?";
							pstmt = conn.prepareStatement(checkSql);
							pstmt.setString(1, maGV);
							rs = pstmt.executeQuery();
							if (rs.next()) {
								JOptionPane.showMessageDialog(ThongTinGiangVien.this,
										"Mã giảng viên '" + maGV + "' đã tồn tại trong database!", "Lỗi",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
							rs.close();
							pstmt.close();
						}

						// Xóa bản ghi cũ
						String deleteSql = "DELETE FROM giang_vien WHERE ma_gv = ?";
						pstmt = conn.prepareStatement(deleteSql);
						pstmt.setString(1, originalMaGV);
						pstmt.executeUpdate();
						pstmt.close();

						// Chèn bản ghi mới
						String insertSql = "INSERT INTO giang_vien (ho_ten, ma_gv, email, so_dien_thoai, mon_giang_day, ma_mon) "
								+ "VALUES (?, ?, ?, ?, ?, ?)";
						pstmt = conn.prepareStatement(insertSql);
						pstmt.setString(1, hoTen);
						pstmt.setString(2, maGV);
						pstmt.setString(3, email);
						pstmt.setString(4, soDienThoai);
						pstmt.setString(5, monGiangDay);
						pstmt.setString(6, maMon);

						int rowsAffected = pstmt.executeUpdate();
						if (rowsAffected > 0) {
							JOptionPane.showMessageDialog(ThongTinGiangVien.this, "Cập nhật thông tin thành công!",
									"Thành công", JOptionPane.INFORMATION_MESSAGE);
							btnSua.setText("SỬA");
							btnSua.setBackground(new Color(50, 150, 255));
							setFieldsEditable(false);
							isEditing = false;
							originalMaGV = maGV; // Cập nhật mã GV ban đầu
						}
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(ThongTinGiangVien.this,
								"Lỗi khi cập nhật database: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					} finally {
						try {
							if (rs != null)
								rs.close();
							if (pstmt != null)
								pstmt.close();
							if (conn != null)
								conn.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});

		JButton btnChamDiem = new JButton("CHẤM ĐIỂM");
		btnChamDiem.setBackground(new Color(255, 140, 0));
		btnChamDiem.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnChamDiem.setBounds(286, 514, 137, 44);
		btnChamDiem.setBorder(new LineBorder(Color.WHITE, 1));
		ThongTinGiangVienPanel.add(btnChamDiem);

		JButton btnGiaoBai = new JButton("GIAO BÀI");
		btnGiaoBai.setBackground(new Color(255, 215, 0));
		btnGiaoBai.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnGiaoBai.setBounds(469, 514, 129, 44);
		btnGiaoBai.setBorder(new LineBorder(Color.WHITE, 1));
		ThongTinGiangVienPanel.add(btnGiaoBai);
		btnGiaoBai.addActionListener(e -> {
			String hoTen = HoTen_text1.getText();
			String monGiangDay = (String) Mon_ComboBox.getSelectedItem();
			GiaoBaiTap thongTin = new GiaoBaiTap(hoTen, monGiangDay);
			thongTin.setVisible(true);
		});

		JButton btnDiemDanh = new JButton("ĐIỂM DANH");
		btnDiemDanh.setBackground(new Color(255, 165, 80));
		btnDiemDanh.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnDiemDanh.setBounds(652, 514, 129, 44);
		btnDiemDanh.setBorder(new LineBorder(Color.WHITE, 1));
		ThongTinGiangVienPanel.add(btnDiemDanh);
	}

	// Bật/tắt chế độ chỉnh sửa cho các trường
	private void setFieldsEditable(boolean editable) {
		HoTen_text1.setEditable(editable);
		MaGV_text1.setEditable(editable);
		Email_text1.setEditable(editable);
		SoDienThoai_text1.setEditable(editable);
		MaMon_text1.setEditable(editable);
		Mon_ComboBox.setEnabled(editable);
	}

	// Kiểm tra xem tất cả các trường đã được điền chưa
	private boolean checkFieldsFilled() {
		return !HoTen_text1.getText().trim().isEmpty() && !MaGV_text1.getText().trim().isEmpty()
				&& !Email_text1.getText().trim().isEmpty() && !SoDienThoai_text1.getText().trim().isEmpty()
				&& !MaMon_text1.getText().trim().isEmpty() && Mon_ComboBox.getSelectedIndex() != 0;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ThongTinGiangVien frame = new ThongTinGiangVien();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}