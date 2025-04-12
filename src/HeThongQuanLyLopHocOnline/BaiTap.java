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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private JComboBox<String> Mssv_comboBox;

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public BaiTap() {
		// Khởi tạo bảng Baitap
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

		// Mã sinh viên
		JLabel lblMssv = new JLabel("MSSV:");
		lblMssv.setForeground(Color.WHITE);
		lblMssv.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMssv.setBounds(23, 79, 114, 30);
		contentPane.add(lblMssv);

		Mssv_comboBox = new JComboBox<>();
		Mssv_comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Mssv_comboBox.setBounds(113, 79, 300, 30);
		loadMssvList(); // Tải danh sách MSSV từ database
		contentPane.add(Mssv_comboBox);

		// Thông tin giảng viên và môn học
		JLabel lblGiaoVien = new JLabel("GIẢNG VIÊN:");
		lblGiaoVien.setForeground(Color.WHITE);
		lblGiaoVien.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblGiaoVien.setBounds(442, 79, 114, 31);
		contentPane.add(lblGiaoVien);

		GiangVien = new JTextField();
		GiangVien.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		GiangVien.setEditable(false);
		GiangVien.setColumns(10);
		GiangVien.setBounds(551, 79, 306, 31);
		contentPane.add(GiangVien);

		JLabel lblMonHoc = new JLabel("TÊN MÔN:");
		lblMonHoc.setForeground(Color.WHITE);
		lblMonHoc.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMonHoc.setBounds(23, 120, 114, 30);
		contentPane.add(lblMonHoc);

		Mon_comboBox = new JComboBox<>(new String[] { "", "Cảm Biến", "Java", "Android" });
		Mon_comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Mon_comboBox.setBounds(113, 120, 300, 30);
		contentPane.add(Mon_comboBox);

		JLabel lblTieuDe = new JLabel("TIÊU ĐỀ:");
		lblTieuDe.setForeground(Color.WHITE);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblTieuDe.setBounds(23, 161, 138, 30);
		contentPane.add(lblTieuDe);

		TieuDe_text = new JTextField();
		TieuDe_text.setFont(new Font("Times New Roman", Font.BOLD, 15));
		TieuDe_text.setColumns(10);
		TieuDe_text.setBounds(113, 162, 744, 31);
		contentPane.add(TieuDe_text);

		// Nội dung bài tập (TextArea)
		JTextArea noiDungBaiTap = new JTextArea();
		noiDungBaiTap.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		noiDungBaiTap.setBounds(23, 256, 834, 248);
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
					// Nếu không tìm thấy bài tập, xóa các trường
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
		btnChonTep.setBounds(23, 520, 124, 31);
		contentPane.add(btnChonTep);

		LinkNop = new JLabel("Chưa chọn tệp nào");
		LinkNop.setBackground(new Color(255, 255, 255));
		LinkNop.setFont(new Font("Times New Roman", Font.BOLD, 15));
		LinkNop.setForeground(new Color(0, 0, 0));
		LinkNop.setBounds(161, 520, 260, 31);
		LinkNop.setOpaque(true);
		LinkNop.setBorder(new LineBorder(Color.WHITE, 2));
		contentPane.add(LinkNop);

		btnChonTep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Java files", "java"));
				int result = fileChooser.showOpenDialog(BaiTap.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					LinkNop.setText(selectedFile.getName());
					LinkNop.setToolTipText(selectedFile.getAbsolutePath());
				}
			}
		});

		JLabel lblNewLabel = new JLabel("HẠN NỘP:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(526, 515, 89, 40);
		contentPane.add(lblNewLabel);

		TimePickerSettings timeSettings = new TimePickerSettings();
		timeSettings.setDisplaySpinnerButtons(true);
		timeSettings.setColor(TimePickerSettings.TimeArea.TimePickerTextDisabled, new Color(0, 51, 102));
		timeSettings.setFormatForDisplayTime("hh:mm a");
		timePicker = new TimePicker(timeSettings);
		timePicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		timePicker.setBounds(611, 522, 89, 30);
		contentPane.add(timePicker);

		NgayNop = new JDateChooser();
		NgayNop.setBounds(710, 521, 147, 30);
		NgayNop.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		NgayNop.setDateFormatString("dd/MM/yyyy");
		contentPane.add(NgayNop);

		// Nút Nộp bài
		JButton NopBaiButton = new JButton("NỘP BÀI");
		NopBaiButton.setBounds(187, 562, 162, 42);
		NopBaiButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
		NopBaiButton.setBackground(new Color(0, 221, 55));
		NopBaiButton.setForeground(Color.BLACK);
		NopBaiButton.setBorder(new LineBorder(Color.WHITE, 1));
		contentPane.add(NopBaiButton);

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
		lblNoiDung.setBounds(23, 204, 180, 41);
		contentPane.add(lblNoiDung);

		// Xử lý sự kiện nút Nộp bài
		NopBaiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Kiểm tra dữ liệu đầu vào
				if (Mssv_comboBox.getSelectedItem() == null || Mssv_comboBox.getSelectedItem().toString().isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Vui lòng chọn mã sinh viên!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (LinkNop.getText().equals("Chưa chọn tệp nào")) {
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

				String mssv = (String) Mssv_comboBox.getSelectedItem();
				String tenTep = LinkNop.getText();
				Date thoiGianNop = new Date(); // Thời gian nộp hiện tại

				// Xác định trạng thái dựa trên hạn nộp
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
					trangThai = "CHƯA NỘP"; // Quá hạn
				}

				// Lưu vào database
				try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
					String sql = "INSERT INTO Baitap (mssv, monhoc, tieude, trangthai, thoigian_nop, ten_tep) VALUES (?, ?, ?, ?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, mssv);
					pstmt.setString(2, monHoc);
					pstmt.setString(3, tieuDe);
					pstmt.setString(4, trangThai);
					pstmt.setTimestamp(5, new Timestamp(thoiGianNop.getTime()));
					pstmt.setString(6, tenTep);
					pstmt.executeUpdate();

					JOptionPane.showMessageDialog(contentPane,
							"Nộp bài thành công!\nTệp: " + tenTep + "\nTrạng thái: " + trangThai, "Thành công",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, "Lỗi khi lưu bài nộp: " + ex.getMessage(), "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Xử lý sự kiện nút Hủy
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
	}

	// Tải danh sách MSSV từ bảng students
	private void loadMssvList() {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT mssv FROM students ORDER BY mssv";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			List<String> mssvList = new ArrayList<>();
			mssvList.add(""); // Thêm tùy chọn rỗng
			while (rs.next()) {
				mssvList.add(rs.getString("mssv"));
			}
			Mssv_comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(mssvList.toArray(new String[0])));
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(contentPane, "Lỗi khi tải danh sách MSSV: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Khởi tạo bảng Baitap trong database
	private void initializeDatabase() {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				Statement stmt = conn.createStatement()) {

			// Tạo bảng Baitap
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

	public static void main(String[] args) {
		// Thêm dữ liệu giả lập cho bài tập
		GiaoBaiTap.danhSachBaiTap.add(new BaiTapInfo("Bài tập Java", "Viết chương trình quản lý sinh viên",
				new Date(System.currentTimeMillis() + 86400000), "Nguyen Van A", "Java"));
		new BaiTap().setVisible(true);
	}
}