
package QuanLyLopHoc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class GiangVien extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField HoTen_text;
	private JTextField MaGV_text;
	private JTextField Email_text;
	private JTextField txtSoDienThoai;
	private JTextField txtMaMon;
	private JComboBox<String> comboBoxMonHoc;

	// Biến tạm để lưu thông tin
	private String tempHoTen = "";
	private String tempMaGV = "";
	private String tempEmail = "";
	private String tempSoDienThoai = "";
	private String tempMonGiangDay = "";
	private String tempMaMon = "";

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	// Regex cho email (chữ/số + @ptithcm.edu.vn)
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9]+@ptithcm\\.edu\\.vn$");
	// Regex cho số điện thoại (10 số, bắt đầu bằng 0)
	private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");
	// Regex cho mã giảng viên (GV + 3 số)
	private static final Pattern MAGV_PATTERN = Pattern.compile("^GV\\d{3}$");

	public GiangVien() {
		// Tạo bảng giang_vien khi khởi tạo
		createTableIfNotExists();

		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("GIẢNG VIÊN");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(10, 39, 219, 43);
		add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(12, 85, 873, 458);
		add(panel);
		panel.setLayout(null);

		JLabel lblThongTinGiangVien = new JLabel("NHẬP THÔNG TIN GIẢNG VIÊN");
		lblThongTinGiangVien.setForeground(new Color(0, 0, 121));
		lblThongTinGiangVien.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblThongTinGiangVien.setBounds(321, 0, 312, 69);
		panel.add(lblThongTinGiangVien);

		JLabel lblHoTen = new JLabel("HỌ TÊN:");
		lblHoTen.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblHoTen.setBounds(65, 81, 81, 29);
		panel.add(lblHoTen);

		JLabel lblMaGV = new JLabel("MÃ GV:");
		lblMaGV.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMaGV.setBounds(65, 139, 81, 29);
		panel.add(lblMaGV);

		JLabel lblEmail = new JLabel("EMAIL:");
		lblEmail.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEmail.setBounds(65, 196, 81, 29);
		panel.add(lblEmail);

		JLabel lblSoDienThoai = new JLabel("SỐ ĐIỆN THOẠI:");
		lblSoDienThoai.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSoDienThoai.setBounds(65, 255, 131, 29);
		panel.add(lblSoDienThoai);

		HoTen_text = new JTextField();
		HoTen_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		HoTen_text.setBounds(212, 80, 586, 30);
		panel.add(HoTen_text);
		HoTen_text.setColumns(10);

		MaGV_text = new JTextField();
		MaGV_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		MaGV_text.setColumns(10);
		MaGV_text.setBounds(212, 138, 586, 30);
		panel.add(MaGV_text);

		Email_text = new JTextField();
		Email_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Email_text.setColumns(10);
		Email_text.setBounds(212, 195, 586, 30);
		panel.add(Email_text);

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSoDienThoai.setBounds(212, 254, 586, 30);
		panel.add(txtSoDienThoai);

		JLabel lblMonGiangDay = new JLabel("MÔN GIẢNG DẠY:");
		lblMonGiangDay.setBounds(65, 317, 151, 29);
		panel.add(lblMonGiangDay);
		lblMonGiangDay.setFont(new Font("Times New Roman", Font.BOLD, 15));

		comboBoxMonHoc = new JComboBox<>();
		comboBoxMonHoc.setBounds(212, 317, 586, 29);
		panel.add(comboBoxMonHoc);
		comboBoxMonHoc.setModel(new DefaultComboBoxModel<>(new String[] { "", "Lập Trình Hướng Đối Tượng", "Hệ Thống nhúng IOT", "Hệ Thống Cảm Biến","Điện Toán Đám Mây","Phát Triển ứng Dụng","Tiếng Anh" }));
		comboBoxMonHoc.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		JLabel lblMaMon = new JLabel("MÃ MÔN:");
		lblMaMon.setBounds(65, 377, 81, 29);
		panel.add(lblMaMon);
		lblMaMon.setFont(new Font("Times New Roman", Font.BOLD, 15));

		txtMaMon = new JTextField();
		txtMaMon.setBounds(212, 376, 586, 30);
		panel.add(txtMaMon);
		txtMaMon.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMaMon.setColumns(10);

		comboBoxMonHoc.addItemListener(e -> updateMaMon());

		// Nút LƯU
		JButton btnLuu = new JButton("LƯU");
		btnLuu.setBackground(new Color(255, 140, 0));
		btnLuu.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnLuu.setBounds(227, 569, 120, 44);
		add(btnLuu);

		// Gắn sự kiện cho nút LƯU
	    btnLuu.addActionListener(e -> saveGiangVien());

		// Nút XUẤT
		JButton btnXuat = new JButton("XUẤT");
		btnXuat.setBackground(new Color(255, 215, 0));
		btnXuat.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnXuat.setBounds(561, 569, 120, 44);
		add(btnXuat);

		// Gắn sự kiện cho nút XUẤT
	    btnXuat.addActionListener(e -> Xuat());
	}
	
	// Cập nhật mã môn dựa trên môn học được chọn
	private void updateMaMon() {
	    String selectedSubject = (String) comboBoxMonHoc.getSelectedItem();
	    switch (selectedSubject) {
	        case "Lập Trình Hướng Đối Tượng":
	            txtMaMon.setText("TEL1448");
	            break;
	        case "Hệ Thống nhúng IOT":
	            txtMaMon.setText("TEL1457");
	            break;
	        case "Hệ Thống Cảm Biến":
	            txtMaMon.setText("TEL1467");
	            break;
	        case "Điện Toán Đám Mây":
	            txtMaMon.setText("TEL1447");
	            break;
	        case "Phát Triển ứng Dụng":
	            txtMaMon.setText("TEL1461");
	            break;
	        case "Tiếng Anh":
	            txtMaMon.setText("BAS1160");
	            break;
	        default:
	            txtMaMon.setText("");
	            break;
	    }
	}

	// Hàm xử lý sự kiện lưu thông tin giảng viên
    private void saveGiangVien() {
        // 1. Lấy dữ liệu từ các trường nhập liệu
        String hoTen = HoTen_text.getText().trim();
        String maGV = MaGV_text.getText().trim();
        String email = Email_text.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String monGiangDay = (String) comboBoxMonHoc.getSelectedItem();
        String maMon = txtMaMon.getText().trim();

        // 2. Kiểm tra các trường không được để trống
        if (hoTen.isEmpty() || maGV.isEmpty() || email.isEmpty() || soDienThoai.isEmpty()
                || monGiangDay == null || monGiangDay.isEmpty() || maMon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ tất cả các thông tin!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Kiểm tra định dạng mã giảng viên
        if (!MAGV_PATTERN.matcher(maGV).matches()) {
            JOptionPane.showMessageDialog(this, "Mã giảng viên phải có định dạng GV + 3 số (VD: GV123)!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. Kiểm tra định dạng email
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(this, "Email phải có định dạng hợp lệ (VD: ten@ptithcm.edu.vn)!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 5. Kiểm tra định dạng số điện thoại
        if (!PHONE_PATTERN.matcher(soDienThoai).matches()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10 số và bắt đầu bằng 0!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 6. Lưu vào database
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Kiểm tra mã GV đã tồn tại
            String checkMaGVSql = "SELECT ma_gv FROM giang_vien WHERE ma_gv = ?";
            pstmt = conn.prepareStatement(checkMaGVSql);
            pstmt.setString(1, maGV);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Mã giảng viên '" + maGV + "' đã tồn tại trong database!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            rs.close();
            pstmt.close();

            // Kiểm tra xem mã môn đã được gán cho giảng viên khác chưa
            String checkMonSql = "SELECT ma_gv FROM giang_vien WHERE ma_mon = ?";
            pstmt = conn.prepareStatement(checkMonSql);
            pstmt.setString(1, maMon);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Môn học '" + monGiangDay + "' đã được gán cho giảng viên khác!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            rs.close();
            pstmt.close();

            // Câu lệnh SQL để insert
            String insertSql = "INSERT INTO giang_vien (ho_ten, ma_gv, email, so_dien_thoai, mon_giang_day, ma_mon) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, hoTen);
            pstmt.setString(2, maGV);
            pstmt.setString(3, email);
            pstmt.setString(4, soDienThoai);
            pstmt.setString(5, monGiangDay);
            pstmt.setString(6, maMon);

            // Thực thi insert
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Lưu vào biến tạm
                tempHoTen = hoTen;
                tempMaGV = maGV;
                tempEmail = email;
                tempSoDienThoai = soDienThoai;
                tempMonGiangDay = monGiangDay;
                tempMaMon = maMon;

                JOptionPane.showMessageDialog(this, "Lưu thông tin thành công!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

                // Xóa dữ liệu trong các trường sau khi lưu
                HoTen_text.setText("");
                MaGV_text.setText("");
                Email_text.setText("");
                txtSoDienThoai.setText("");
                comboBoxMonHoc.setSelectedIndex(0);
                txtMaMon.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu vào database: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            // Đóng các resource
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

	// Xử lý sự kiện khi nhấn nút XUẤT
	private void Xuat() {
	    ThongTinGiangVien thongTin = new ThongTinGiangVien(tempHoTen, tempMaGV, tempEmail, tempSoDienThoai,
	            tempMonGiangDay, tempMaMon);
	    thongTin.setVisible(true);
	}

	// Tạo bảng giang_vien trong cơ sở dữ liệu nếu chưa tồn tại
	private void createTableIfNotExists() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Kết nối database
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			// Tạo câu lệnh SQL để tạo bảng
			String sql = "CREATE TABLE IF NOT EXISTS giang_vien (" + "ho_ten VARCHAR(100), "
					+ "ma_gv VARCHAR(50) PRIMARY KEY, " + "email VARCHAR(100), " + "so_dien_thoai VARCHAR(20), "
					+ "mon_giang_day VARCHAR(100), " + "ma_mon VARCHAR(50)" + ")";

			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tạo bảng giang_vien: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} finally {
			// Đóng các resource
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
}