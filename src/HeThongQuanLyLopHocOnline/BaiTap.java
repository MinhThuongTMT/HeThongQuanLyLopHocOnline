package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.toedter.calendar.JDateChooser;

public class BaiTap extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField GiangVien;
	private JTextField TieuDe_text;
	private JLabel LinkNop;
	private JDateChooser NgayNop;
	private TimePicker timePicker;
	private JComboBox<String> Mon_comboBox;
	private JTextField MSSV_text;
	private JTextField Ten_text;

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public BaiTap(String hoTen, String mssv) {
		initializeDatabase();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 895, 652);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 121));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Panel tiêu đề
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(255, 204, 0));
		panel.setBounds(0, 0, 881, 57);
		contentPane.add(panel);

		JLabel lblBiTp = new JLabel("BÀI TẬP");
		lblBiTp.setForeground(Color.BLACK);
		lblBiTp.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblBiTp.setBounds(393, 11, 133, 37);
		panel.add(lblBiTp);

		// Thông tin giảng viên và môn học
		JLabel lblGiaoVien = new JLabel("GIẢNG VIÊN:");
		lblGiaoVien.setForeground(Color.WHITE);
		lblGiaoVien.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblGiaoVien.setBounds(441, 132, 114, 31);
		contentPane.add(lblGiaoVien);

		GiangVien = new JTextField();
		GiangVien.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		GiangVien.setEditable(false);
		GiangVien.setColumns(10);
		GiangVien.setBounds(550, 132, 306, 31);
		contentPane.add(GiangVien);

		JLabel lblMonHoc = new JLabel("TÊN MÔN:");
		lblMonHoc.setForeground(Color.WHITE);
		lblMonHoc.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMonHoc.setBounds(22, 133, 114, 30);
		contentPane.add(lblMonHoc);

		Mon_comboBox = new JComboBox<>(new String[] { "", "Cảm Biến", "Java", "Android" });
		Mon_comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Mon_comboBox.setBounds(112, 132, 300, 30);
		contentPane.add(Mon_comboBox);

		JLabel lblMssv = new JLabel("MSSV :");
		lblMssv.setForeground(Color.WHITE);
		lblMssv.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMssv.setBounds(22, 82, 114, 30);
		contentPane.add(lblMssv);

		MSSV_text = new JTextField();
		MSSV_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		MSSV_text.setEditable(false);
		MSSV_text.setColumns(10);
		MSSV_text.setBounds(110, 81, 302, 31);
		MSSV_text.setText(mssv);
		contentPane.add(MSSV_text);

		JLabel lblH = new JLabel("HỌ VÀ TÊN:");
		lblH.setForeground(Color.WHITE);
		lblH.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblH.setBounds(441, 82, 114, 30);
		contentPane.add(lblH);

		Ten_text = new JTextField();
		Ten_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Ten_text.setEditable(false);
		Ten_text.setColumns(10);
		Ten_text.setBounds(550, 82, 306, 31);
		Ten_text.setText(hoTen);
		contentPane.add(Ten_text);

		JLabel lblTieuDe = new JLabel("TIÊU ĐỀ:");
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblTieuDe.setBounds(22, 181, 138, 30);
		contentPane.add(lblTieuDe);

		TieuDe_text = new JTextField();
		TieuDe_text.setFont(new Font("Times New Roman", Font.BOLD, 15));
		TieuDe_text.setColumns(10);
		TieuDe_text.setBounds(112, 182, 744, 31);
		contentPane.add(TieuDe_text);

		// Nội dung bài tập
		JTextArea noiDungBaiTap = new JTextArea();
		noiDungBaiTap.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		noiDungBaiTap.setBounds(23, 256, 834, 237);
		noiDungBaiTap.setEditable(false);
		noiDungBaiTap.setBackground(Color.WHITE);
		contentPane.add(noiDungBaiTap);

		Mon_comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedMon = (String) Mon_comboBox.getSelectedItem();
				if (selectedMon != null && !selectedMon.isEmpty()) {
					for (BaiTapInfo baiTap : GiaoBaiTap.danhSachBaiTap) {
						if (baiTap.getMonHoc().equals(selectedMon)) {
							TieuDe_text.setText(baiTap.getTieuDe());
							noiDungBaiTap.setText(baiTap.getNoiDung());
							GiangVien.setText(baiTap.getTenGV());
							NgayNop.setDate(baiTap.getHanNop());
							SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
							timePicker.setText(sdf.format(baiTap.getHanNop()));
							return;
						}
					}
					// Xóa các trường nếu không tìm thấy
					TieuDe_text.setText("");
					noiDungBaiTap.setText("");
					GiangVien.setText("");
					NgayNop.setDate(null);
					timePicker.setText("");
				} else {
					TieuDe_text.setText("");
					noiDungBaiTap.setText("");
					GiangVien.setText("");
					NgayNop.setDate(null);
					timePicker.setText("");
				}
			}
		});

		JButton btnChonTep = new JButton("Chọn tệp");
		btnChonTep.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnChonTep.setBackground(Color.WHITE);
		btnChonTep.setBounds(23, 504, 124, 31);
		contentPane.add(btnChonTep);
		btnChonTep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Java files", "java"));
				int result = fileChooser.showOpenDialog(BaiTap.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					LinkNop.setText(selectedFile.getName());
				}
			}
		});

		LinkNop = new JLabel("");
		LinkNop.setBackground(Color.WHITE);
		LinkNop.setFont(new Font("Times New Roman", Font.BOLD, 15));
		LinkNop.setForeground(Color.BLACK);
		LinkNop.setBounds(153, 504, 260, 31);
		LinkNop.setOpaque(true);
		LinkNop.setBorder(new LineBorder(Color.WHITE, 2));
		contentPane.add(LinkNop);

		JLabel lblNewLabel = new JLabel("HẠN NỘP:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(526, 495, 89, 40);
		contentPane.add(lblNewLabel);

		// Giờ nộp
		TimePickerSettings timeSettings = new TimePickerSettings();
		timeSettings.setDisplaySpinnerButtons(true);
		timeSettings.setColor(TimePickerSettings.TimeArea.TimePickerTextDisabled, new Color(0, 51, 102));
		timeSettings.setFormatForDisplayTime("hh:mm a");
		timePicker = new TimePicker(timeSettings);
		timePicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		timePicker.setBounds(611, 502, 89, 30);
		// timePicker.setLayout(null);
		contentPane.add(timePicker);

		// Ngày nộp
		NgayNop = new JDateChooser();
		NgayNop.setBounds(710, 501, 147, 30);
		NgayNop.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		NgayNop.setDateFormatString("dd/MM/yyyy");
		contentPane.add(NgayNop);

		// Nút Nộp bài
		JButton NopBaiButton = new JButton("NỘP BÀI");
		NopBaiButton.setBounds(200, 562, 162, 42);
		NopBaiButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
		NopBaiButton.setBackground(new Color(0, 221, 55));
		NopBaiButton.setForeground(Color.BLACK);
		NopBaiButton.setBorder(new LineBorder(Color.WHITE, 1));
		contentPane.add(NopBaiButton);

		NopBaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Kiểm tra dữ liệu đầu vào
				if (LinkNop.getText().isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Vui lòng chọn tệp bài làm trước khi nộp!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				String monHoc = (String) Mon_comboBox.getSelectedItem();
				if (monHoc == null || monHoc.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Vui lòng chọn môn học!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				String tieuDe = TieuDe_text.getText().trim();
				if (tieuDe.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Tiêu đề bài tập không được để trống!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String mssv = MSSV_text.getText().trim();
				String hoTen = Ten_text.getText().trim();
				if (mssv.isEmpty() || hoTen.isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Thông tin sinh viên không hợp lệ!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Kiểm tra MSSV tồn tại
				if (!isMssvExists(mssv)) {
					JOptionPane.showMessageDialog(contentPane, "MSSV không tồn tại trong hệ thống!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Kiểm tra nộp bài trùng lặp
				if (isSubmissionExists(mssv, monHoc, tieuDe)) {
					JOptionPane.showMessageDialog(contentPane, "Bạn đã nộp bài cho bài tập này!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String tenTep = LinkNop.getText();
				Date thoiGianNop = new Date();

				// Xác định trạng thái
				String trangThai = "ĐÃ NỘP";
				Date hanNop = null;
				for (BaiTapInfo baiTap : GiaoBaiTap.danhSachBaiTap) {
					if (baiTap.getMonHoc().equals(monHoc) && baiTap.getTieuDe().equals(tieuDe)) {
						hanNop = baiTap.getHanNop();
						break;
					}
				}
				if (hanNop == null) {
					JOptionPane.showMessageDialog(contentPane, "Không tìm thấy thông tin bài tập!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (thoiGianNop.after(hanNop)) {
					trangThai = "NỘP MUỘN";
				}

				// Lưu vào database
				PreparedStatement pstmt = null;
				try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
					String sql = "INSERT INTO Baitap (mssv, monhoc, tieude, trangthai, thoigian_nop, ten_tep) VALUES (?, ?, ?, ?, ?, ?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, mssv);
					pstmt.setString(2, monHoc);
					pstmt.setString(3, tieuDe);
					pstmt.setString(4, trangThai);
					pstmt.setTimestamp(5, new Timestamp(thoiGianNop.getTime()));
					pstmt.setString(6, tenTep);
					pstmt.executeUpdate();

					JOptionPane.showMessageDialog(
							contentPane, "Nộp bài thành công!\nMSSV: " + mssv + "\nHọ tên: " + hoTen + "\nTệp: "
									+ tenTep + "\nTrạng thái: " + trangThai,
							"Thành công", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, "Lỗi khi lưu bài nộp: " + ex.getMessage(), "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					if (pstmt != null) {
						try {
							pstmt.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});

		// Nút Hủy
		JButton HuyBaiButton = new JButton("HỦY NỘP BÀI");
		HuyBaiButton.setBounds(551, 561, 162, 44);
		HuyBaiButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
		HuyBaiButton.setBackground(new Color(255, 53, 53));
		HuyBaiButton.setForeground(Color.BLACK);
		HuyBaiButton.setBorder(new LineBorder(Color.WHITE, 1));
		contentPane.add(HuyBaiButton);

		JLabel lblNoiDung = new JLabel("NỘI DUNG BÀI TẬP");
		lblNoiDung.setForeground(Color.WHITE);
		lblNoiDung.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNoiDung.setBounds(23, 210, 180, 41);
		contentPane.add(lblNoiDung);

		HuyBaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(contentPane, "Bạn có muốn hủy nộp bài không?", "Xác nhận",
						JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(contentPane, "Hủy nộp bài thành công!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
		});

		// Kiểm tra trạng thái nộp bài cũ
		checkPreviousSubmission(mssv);
	}

	private void initializeDatabase() {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				Statement stmt = conn.createStatement()) {
			String sqlBaitap = "CREATE TABLE IF NOT EXISTS Baitap (" + "id SERIAL PRIMARY KEY,"
					+ "mssv VARCHAR(50) REFERENCES students(mssv)," + "monhoc VARCHAR(100)," + "tieude VARCHAR(100),"
					+ "trangthai VARCHAR(20)," + "thoigian_nop TIMESTAMP," + "ten_tep VARCHAR(255))";
			stmt.executeUpdate(sqlBaitap);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi kết nối database: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean isMssvExists(String mssv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT 1 FROM students WHERE mssv = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mssv);
			rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private boolean isSubmissionExists(String mssv, String monHoc, String tieuDe) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT 1 FROM Baitap WHERE mssv = ? AND monhoc = ? AND tieude = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mssv);
			pstmt.setString(2, monHoc);
			pstmt.setString(3, tieuDe);
			rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void checkPreviousSubmission(String mssv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT tieude, monhoc, trangthai, ten_tep FROM Baitap WHERE mssv = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mssv);
			rs = pstmt.executeQuery();
			StringBuilder message = new StringBuilder("Bài tập đã nộp:\n");
			boolean hasSubmission = false;
			while (rs.next()) {
				hasSubmission = true;
				message.append("Tiêu đề: ").append(rs.getString("tieude")).append(", Môn: ")
						.append(rs.getString("monhoc")).append(", Trạng thái: ").append(rs.getString("trangthai"))
						.append(", Tệp: ").append(rs.getString("ten_tep")).append("\n");
			}
			if (hasSubmission) {
				JOptionPane.showMessageDialog(contentPane, message.toString(), "Thông tin nộp bài",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public BaiTap() {
		this("", "");
	}

	public static void main(String[] args) {
		GiaoBaiTap.danhSachBaiTap.add(new BaiTapInfo("Bài tập Java", "Viết chương trình quản lý sinh viên",
				new Date(System.currentTimeMillis() + 86400000), "Nguyen Van A", "Java"));
		new BaiTap().setVisible(true);
	}
}