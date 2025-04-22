package QuanLyLopHoc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QuanLyLopHoc extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField HienThi;
	private JButton lastClickedButton = null;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	// Hằng số cho cột bảng
	private static final String[] CLASS_COLUMNS = { "Mã SV", "Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Môn học",
			"Email" };
	private static final String[] SCORE_COLUMNS = { "Mã SV", "Tên", "Môn học", "Điểm CC(10%)", "Điểm BT(20%)",
			"Điểm giữa kỳ(20%)", "Điểm cuối kỳ(50%)", "Điểm hệ 10" };

	public QuanLyLopHoc() {
		this("", false);
	}

	public QuanLyLopHoc(String monHoc, boolean showAttendance) {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);

		// Tiêu đề
		JLabel lblNewLabel = new JLabel("QUẢN LÝ LỚP HỌC");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(334, 10, 253, 40);
		add(lblNewLabel);

		// JPopupMenu cho Danh Sách Lớp Học
		JPopupMenu manageLopMenu = new JPopupMenu();
		manageLopMenu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		manageLopMenu.setBackground(new Color(255, 204, 0));
		manageLopMenu.setForeground(Color.BLACK);
		JMenuItem lop1 = new JMenuItem("D21CQVTHI01-N");
		JMenuItem lop2 = new JMenuItem("D21CQVTVT01-N");
		JMenuItem lop3 = new JMenuItem("D21CQVTDM01-N");
		JMenuItem lop4 = new JMenuItem("D22CQVT01-N");
		JMenuItem lop5 = new JMenuItem("D23CQVT01-N");
		JMenuItem allLops = new JMenuItem("Tất cả lớp");
		manageLopMenu.add(lop1);
		manageLopMenu.add(lop2);
		manageLopMenu.add(lop3);
		manageLopMenu.add(lop4);
		manageLopMenu.add(lop5);
		manageLopMenu.add(allLops);

		// Nút Danh Sách Lớp Học
		JButton Lop_bnt = new JButton("Danh Sách Lớp Học");
		Lop_bnt.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Lop_bnt.setBackground(new Color(255, 215, 0));
		Lop_bnt.setForeground(Color.BLACK);
		Lop_bnt.setBounds(51, 61, 187, 40);
		add(Lop_bnt);

		// JPopupMenu cho Danh Sách Môn Học
		JPopupMenu subjectsMenu = new JPopupMenu();
		subjectsMenu.setFont(new Font("Times New Roman", Font.BOLD, 15));
		subjectsMenu.setBackground(new Color(255, 204, 0));
		subjectsMenu.setForeground(Color.BLACK);
		JMenuItem mon1 = new JMenuItem("Lập Trình Hướng Đối Tượng");
		JMenuItem mon2 = new JMenuItem("Hệ Thống nhúng IOT");
		JMenuItem mon3 = new JMenuItem("Hệ Thống Cảm Biến");
		JMenuItem mon4 = new JMenuItem("Điện Toán Đám Mây");
		JMenuItem mon5 = new JMenuItem("Phát Triển ứng Dụng");
		JMenuItem mon6 = new JMenuItem("Tiếng Anh");
		subjectsMenu.add(mon1);
		subjectsMenu.add(mon2);
		subjectsMenu.add(mon3);
		subjectsMenu.add(mon4);
		subjectsMenu.add(mon5);
		subjectsMenu.add(mon6);

		// Nút Danh Sách Môn Học
		JButton Mon_bnt = new JButton("Danh Sách Điểm Môn Học");
		Mon_bnt.setFont(new Font("Times New Roman", Font.BOLD, 14));
		Mon_bnt.setBackground(new Color(255, 215, 0));
		Mon_bnt.setForeground(Color.BLACK);
		Mon_bnt.setBounds(334, 61, 198, 40);
		add(Mon_bnt);

		// JPopupMenu cho Danh Sách Điểm Danh
		JPopupMenu subjectsMenu1 = new JPopupMenu();
		subjectsMenu1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		subjectsMenu1.setBackground(new Color(255, 204, 0));
		subjectsMenu1.setForeground(Color.BLACK);
		JMenuItem DiemDanh1 = new JMenuItem("Lập Trình Hướng Đối Tượng");
		JMenuItem DiemDanh2 = new JMenuItem("Hệ Thống nhúng IOT");
		JMenuItem DiemDanh3 = new JMenuItem("Hệ Thống Cảm Biến");
		JMenuItem DiemDanh4 = new JMenuItem("Điện Toán Đám Mây");
		JMenuItem DiemDanh5 = new JMenuItem("Phát Triển ứng Dụng");
		JMenuItem DiemDanh6 = new JMenuItem("Tiếng Anh");
		subjectsMenu1.add(DiemDanh1);
		subjectsMenu1.add(DiemDanh2);
		subjectsMenu1.add(DiemDanh3);
		subjectsMenu1.add(DiemDanh4);
		subjectsMenu1.add(DiemDanh5);
		subjectsMenu1.add(DiemDanh6);

		// Nút Danh Sách Điểm Danh
		JButton diemDanh_btn = new JButton("Danh Sách Điểm Danh");
		diemDanh_btn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		diemDanh_btn.setBackground(new Color(255, 215, 0));
		diemDanh_btn.setForeground(Color.BLACK);
		diemDanh_btn.setBounds(613, 61, 187, 40);
		add(diemDanh_btn);

		// Nút Xuất Excel
		JButton btnExportExcel = new JButton("Xuất Excel");
		btnExportExcel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnExportExcel.setBackground(new Color(0, 204, 0));
		btnExportExcel.setForeground(Color.WHITE);
		btnExportExcel.setBounds(714, 125, 150, 31);
		add(btnExportExcel);

		// Khởi tạo JTable
		tableModel = new DefaultTableModel(CLASS_COLUMNS, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Chỉ cho phép chỉnh sửa các cột điểm (từ cột 3 đến 6) khi hiển thị bảng điểm
				return tableModel.getColumnName(2).equals("Môn học") && column >= 3 && column <= 6;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (tableModel.getColumnCount() > 0) {
					String columnName = tableModel.getColumnName(columnIndex);
					if (columnName.matches("\\d{2}/\\d{2}/\\d{4}")) {
						return Boolean.class;
					}
					return String.class;
				}
				return String.class;
			}
		};
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(22, 179, 849, 444);
		add(scrollPane);

		HienThi = new JTextField();
		HienThi.setFont(new Font("Times New Roman", Font.BOLD, 15));
		HienThi.setBounds(72, 125, 371, 31);
		HienThi.setColumns(10);
		add(HienThi);

		// Xử lý màu nút
		ActionListener buttonColorHandler = e -> {
			JButton clickedButton = (JButton) e.getSource();
			resetButtonColors(clickedButton);
			updateButtonColor(clickedButton);
			showPopupMenu(clickedButton, Lop_bnt, manageLopMenu, Mon_bnt, subjectsMenu, diemDanh_btn, subjectsMenu1);
		};

		// Gán sự kiện cho các nút
		Lop_bnt.addActionListener(buttonColorHandler);
		Mon_bnt.addActionListener(buttonColorHandler);
		diemDanh_btn.addActionListener(buttonColorHandler);

		// Sự kiện Xuất Excel
		btnExportExcel.addActionListener(e -> {
			exportToExcel();
			resetButtonColors(btnExportExcel);
			btnExportExcel.setBackground(new Color(0, 153, 0));
			lastClickedButton = btnExportExcel;
		});

		// Sự kiện chọn lớp
		lop1.addActionListener(e -> loadClassData("D21CQVTHI01-N"));
		lop2.addActionListener(e -> loadClassData("D21CQVTVT01-N"));
		lop3.addActionListener(e -> loadClassData("D21CQVTDM01-N"));
		lop4.addActionListener(e -> loadClassData("D22CQVT01-N"));
		lop5.addActionListener(e -> loadClassData("D23CQVT01-N"));
		allLops.addActionListener(
				e -> loadClassData("D21CQVTHI01-N", "D21CQVTVT01-N", "D21CQVTDM01-N", "D22CQVT01-N", "D23CQVT01-N"));

		// Sự kiện chọn môn
		mon1.addActionListener(e -> loadScoreData("Lập Trình Hướng Đối Tượng"));
		mon2.addActionListener(e -> loadScoreData("Hệ Thống nhúng IOT"));
		mon3.addActionListener(e -> loadScoreData("Hệ Thống Cảm Biến"));
		mon4.addActionListener(e -> loadScoreData("Điện Toán Đám Mây"));
		mon5.addActionListener(e -> loadScoreData("Phát Triển ứng Dụng"));
		mon6.addActionListener(e -> loadScoreData("Tiếng Anh"));

		// Sự kiện điểm danh
		DiemDanh1.addActionListener(e -> loadAttendanceData("Lập Trình Hướng Đối Tượng"));
		DiemDanh2.addActionListener(e -> loadAttendanceData("Hệ Thống nhúng IOT"));
		DiemDanh3.addActionListener(e -> loadAttendanceData("Hệ Thống Cảm Biến"));
		DiemDanh4.addActionListener(e -> loadAttendanceData("Điện Toán Đám Mây"));
		DiemDanh5.addActionListener(e -> loadAttendanceData("Phát Triển ứng Dụng"));
		DiemDanh6.addActionListener(e -> loadAttendanceData("Tiếng Anh"));
		// Tự động hiển thị danh sách điểm danh nếu được yêu cầu
		if (showAttendance && !monHoc.isEmpty()) {
			loadAttendanceData(monHoc);
			updateButtonColor(diemDanh_btn); // Đánh dấu nút điểm danh được chọn
		}
	}

	private void loadClassData(String... lopList) {
		tableModel.setColumnIdentifiers(CLASS_COLUMNS);
		tableModel.setRowCount(0);

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String placeholders = String.join(",", java.util.Collections.nCopies(lopList.length, "?"));
			String sql = """
					SELECT s.mssv, s.hoten, s.ngaysinh, s.gioitinh, s.lop, s.email
					FROM students s
					WHERE s.lop IN (%s)
					GROUP BY s.mssv, s.hoten, s.ngaysinh, s.gioitinh, s.lop, s.email
					""".formatted(placeholders);

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				for (int i = 0; i < lopList.length; i++) {
					pstmt.setString(i + 1, lopList[i]);
				}
				try (ResultSet rs = pstmt.executeQuery()) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					boolean hasData = false;
					while (rs.next()) {
						tableModel.addRow(new Object[] { rs.getString("mssv"), rs.getString("hoten"),
								rs.getDate("ngaysinh") != null ? sdf.format(rs.getDate("ngaysinh")) : "",
								rs.getString("gioitinh"), rs.getString("lop"), "", rs.getString("email") });
						hasData = true;
					}
					HienThi.setText("Danh Sách Lớp: " + String.join(", ", lopList));
					if (!hasData) {
						JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên.", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		table.setEnabled(false);
		table.repaint();
	}

	private void loadScoreData(String monHoc) {
		HienThi.setText("Danh Sách Lớp Môn: " + monHoc);
		tableModel.setColumnIdentifiers(SCORE_COLUMNS);
		tableModel.setRowCount(0);

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = """
					SELECT s.mssv, s.hoten, c.monhoc, cd.diem_chuyencan, cd.diem_15, cd.diem_giuaky, cd.diem_cuoiky
					FROM students s
					JOIN courses c ON s.mssv = c.mssv
					LEFT JOIN Chamdiem cd ON s.mssv = cd.mssv AND c.monhoc = cd.monhoc
					WHERE c.monhoc = ?
					ORDER BY s.mssv
					""";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, monHoc);
				try (ResultSet rs = pstmt.executeQuery()) {
					boolean hasData = false;
					while (rs.next()) {
						String mssv = rs.getString("mssv");
						String hoten = rs.getString("hoten");
						String monhoc = rs.getString("monhoc");
						Float diemChuyenCan = rs.getObject("diem_chuyencan") != null ? rs.getFloat("diem_chuyencan")
								: null;
						Float diem15 = rs.getObject("diem_15") != null ? rs.getFloat("diem_15") : null;
						Float diemGiuaKy = rs.getObject("diem_giuaky") != null ? rs.getFloat("diem_giuaky") : null;
						Float diemCuoiKy = rs.getObject("diem_cuoiky") != null ? rs.getFloat("diem_cuoiky") : null;

						// Tính điểm hệ 10
						String diemHe10Str = "";
						if (diemChuyenCan != null && diem15 != null && diemGiuaKy != null && diemCuoiKy != null) {
							float diemHe10 = diemChuyenCan * 0.1f + diem15 * 0.2f + diemGiuaKy * 0.2f
									+ diemCuoiKy * 0.5f;
							diemHe10Str = String.format("%.2f", diemHe10);
						}

						tableModel
								.addRow(new Object[] { mssv, hoten, monhoc, diemChuyenCan != null ? diemChuyenCan : "",
										diem15 != null ? diem15 : "", diemGiuaKy != null ? diemGiuaKy : "",
										diemCuoiKy != null ? diemCuoiKy : "", diemHe10Str });
						hasData = true;
					}
					if (!hasData) {
						JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu điểm cho môn " + monHoc,
								"Thông báo", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu điểm: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		table.setEnabled(true);
		table.repaint();
	}

	private void loadAttendanceData(String monHoc) {
		HienThi.setText("Danh Sách Điểm Danh Môn: " + monHoc);
		tableModel.setRowCount(0);

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String dateSql = """
					SELECT DISTINCT ngay
					FROM diemdanh d
					JOIN courses c ON d.mamon = c.mamon
					WHERE c.monhoc = ?
					ORDER BY ngay
					""";
			List<String> dates = new ArrayList<>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try (PreparedStatement pstmt = conn.prepareStatement(dateSql)) {
				pstmt.setString(1, monHoc);
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs.next()) {
						dates.add(sdf.format(rs.getDate("ngay")));
					}
				}
			}

			List<String> columns = new ArrayList<>();
			columns.add("Mã SV");
			columns.add("Tên");
			columns.add("Lớp");
			columns.add("Môn học");
			columns.addAll(dates);
			tableModel.setColumnIdentifiers(columns.toArray());

			String sql = """
					SELECT s.mssv, s.hoten, s.lop
					FROM students s
					JOIN courses c ON s.mssv = c.mssv
					WHERE c.monhoc = ?
					ORDER BY s.mssv
					""";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, monHoc);
				try (ResultSet rs = pstmt.executeQuery()) {
					boolean hasData = false;
					while (rs.next()) {
						String mssv = rs.getString("mssv");
						String hoten = rs.getString("hoten");
						String lop = rs.getString("lop");

						Object[] row = new Object[columns.size()];
						row[0] = mssv;
						row[1] = hoten;
						row[2] = lop;
						row[3] = monHoc;

						String attendanceSql = """
								SELECT ngay, trangthai
								FROM diemdanh d
								JOIN courses c ON d.mamon = c.mamon
								WHERE c.monhoc = ? AND d.mssv = ?
								""";
						try (PreparedStatement attPstmt = conn.prepareStatement(attendanceSql)) {
							attPstmt.setString(1, monHoc);
							attPstmt.setString(2, mssv);
							try (ResultSet attRs = attPstmt.executeQuery()) {
								java.util.Map<String, Boolean> attendanceMap = new java.util.HashMap<>();
								while (attRs.next()) {
									String ngay = sdf.format(attRs.getDate("ngay"));
									boolean trangthai = attRs.getBoolean("trangthai");
									attendanceMap.put(ngay, trangthai);
								}

								for (int i = 4; i < columns.size(); i++) {
									row[i] = attendanceMap.getOrDefault(columns.get(i), false);
								}
							}
						}

						tableModel.addRow(row);
						hasData = true;
					}
					if (!hasData) {
						JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu điểm danh cho môn " + monHoc,
								"Thông báo", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu điểm danh: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		table.setEnabled(false);
		table.repaint();
	}

	private void saveScoresToDatabase() {
		if (!tableModel.getColumnName(2).equals("Môn học")) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một môn học để lưu điểm!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String monHoc = HienThi.getText().replace("Danh Sách Lớp Môn: ", "");
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			conn.setAutoCommit(false);
			String sql = """
					INSERT INTO Chamdiem (mssv, hoten, monhoc, diem_chuyencan, diem_15, diem_giuaky, diem_cuoiky, lop, thoigian_nop, ten_tep)
					VALUES (?, ?, ?, ?, ?, ?, ?, NULL, NULL, NULL)
					ON CONFLICT (mssv, monhoc)
					DO UPDATE SET
					    hoten = EXCLUDED.hoten,
					    diem_chuyencan = EXCLUDED.diem_chuyencan,
					    diem_15 = EXCLUDED.diem_15,
					    diem_giuaky = EXCLUDED.diem_giuaky,
					    diem_cuoiky = EXCLUDED.diem_cuoiky
					""";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					String mssv = (String) tableModel.getValueAt(i, 0);
					String hoten = (String) tableModel.getValueAt(i, 1);
					Object diemChuyenCanObj = tableModel.getValueAt(i, 3);
					Object diem15PhutObj = tableModel.getValueAt(i, 4);
					Object diemGiuaKyObj = tableModel.getValueAt(i, 5);
					Object diemCuoiKyObj = tableModel.getValueAt(i, 6);

					String diemChuyenCan = diemChuyenCanObj != null ? diemChuyenCanObj.toString() : "";
					String diem15Phut = diem15PhutObj != null ? diem15PhutObj.toString() : "";
					String diemGiuaKy = diemGiuaKyObj != null ? diemGiuaKyObj.toString() : "";
					String diemCuoiKy = diemCuoiKyObj != null ? diemCuoiKyObj.toString() : "";

					pstmt.setString(1, mssv);
					pstmt.setString(2, hoten);
					pstmt.setString(3, monHoc);
					pstmt.setObject(4, validateScore(diemChuyenCan));
					pstmt.setObject(5, validateScore(diem15Phut));
					pstmt.setObject(6, validateScore(diemGiuaKy));
					pstmt.setObject(7, validateScore(diemCuoiKy));
					pstmt.addBatch();
				}
				pstmt.executeBatch();
				conn.commit();
				JOptionPane.showMessageDialog(this, "Lưu điểm thành công!", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
				// Tải lại dữ liệu để cập nhật cột Điểm hệ 10
				loadScoreData(monHoc);
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi lưu điểm: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	private Float validateScore(String score) {
		if (score == null || score.trim().isEmpty()) {
			return null;
		}
		try {
			float value = Float.parseFloat(score);
			if (value < 0 || value > 10) {
				throw new IllegalArgumentException("Điểm phải từ 0 đến 10!");
			}
			return value;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Vui lòng nhập điểm ở định dạng số hợp lệ!");
		}
	}

	// Xuất dữ liệu từ bảng giao diện ra file Excel
	private void exportToExcel() {
	    // Kiểm tra xem bảng có dữ liệu để xuất hay không
	    if (tableModel == null || tableModel.getRowCount() == 0 || tableModel.getColumnCount() == 0) {
	        JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    Workbook workbook = null;
	    try {
	        // Tạo workbook và sheet
	        workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("DanhSach");

	        // Tạo hàng tiêu đề
	        Row headerRow = sheet.createRow(0);
	        for (int i = 0; i < tableModel.getColumnCount(); i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(tableModel.getColumnName(i));
	            CellStyle headerStyle = workbook.createCellStyle();
	            org.apache.poi.ss.usermodel.Font font = workbook.createFont();
	            font.setBold(true);
	            headerStyle.setFont(font);
	            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	            cell.setCellStyle(headerStyle);
	        }

	        // Thêm dữ liệu từ tableModel
	        for (int i = 0; i < tableModel.getRowCount(); i++) {
	            Row row = sheet.createRow(i + 1);
	            for (int j = 0; j < tableModel.getColumnCount(); j++) {
	                Cell cell = row.createCell(j);
	                Object value = tableModel.getValueAt(i, j);
	                if (value != null) {
	                    if (value instanceof Boolean) {
	                        cell.setCellValue((Boolean) value ? "Có" : "Vắng");
	                    } else {
	                        cell.setCellValue(value.toString());
	                    }
	                } else {
	                    cell.setCellValue(""); // Xử lý giá trị null
	                }
	            }
	        }

	        // Tự động điều chỉnh kích thước cột
	        for (int i = 0; i < tableModel.getColumnCount(); i++) {
	            sheet.autoSizeColumn(i);
	        }

	        // Tạo tên file mặc định từ HienThi, loại bỏ ký tự không hợp lệ
	        String fileName = HienThi.getText().isEmpty() ? "DanhSach" : HienThi.getText().replaceAll("[^\\p{L}\\p{N}\\s]", "");
	        fileName = fileName.trim().replaceAll("\\s+", "_") + ".xlsx";

	        // Mở dialog chọn nơi lưu file
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
	        fileChooser.setSelectedFile(new java.io.File(fileName));
	        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
	            return; // Người dùng hủy chọn file
	        }

	        // Ghi file Excel
	        try (FileOutputStream fileOut = new FileOutputStream(fileChooser.getSelectedFile())) {
	            workbook.write(fileOut);
	            JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!", "Thông báo",
	                    JOptionPane.INFORMATION_MESSAGE);
	        }
	    } catch (IOException ex) {
	        JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + ex.getMessage() + 
	                ". Vui lòng kiểm tra quyền ghi file hoặc đóng file nếu đang mở.", "Lỗi",
	                JOptionPane.ERROR_MESSAGE);
	        ex.printStackTrace();
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, "Lỗi không xác định khi xuất file: " + ex.getMessage(), "Lỗi",
	                JOptionPane.ERROR_MESSAGE);
	        ex.printStackTrace();
	    } finally {
	        // Đóng workbook để giải phóng tài nguyên
	        if (workbook != null) {
	            try {
	                workbook.close();
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	}

	private void resetButtonColors(JButton clickedButton) {
		if (lastClickedButton != null && lastClickedButton != clickedButton) {
			if (lastClickedButton.getText().equals("Xuất Excel")) {
				lastClickedButton.setBackground(new Color(0, 204, 0));
			} else if (lastClickedButton.getText().equals("Lưu Điểm")) {
				lastClickedButton.setBackground(new Color(0, 204, 204));
			} else {
				lastClickedButton.setBackground(new Color(255, 215, 0));
			}
		}
	}

	private void updateButtonColor(JButton clickedButton) {
		if (clickedButton.getText().equals("Xuất Excel")) {
			clickedButton.setBackground(new Color(0, 153, 0));
		} else if (clickedButton.getText().equals("Lưu Điểm")) {
			clickedButton.setBackground(new Color(0, 153, 153));
		} else {
			clickedButton.setBackground(new Color(255, 153, 0));
		}
		lastClickedButton = clickedButton;
	}

	private void showPopupMenu(JButton clickedButton, JButton Lop_bnt, JPopupMenu manageLopMenu, JButton Mon_bnt,
			JPopupMenu subjectsMenu, JButton diemDanh_btn, JPopupMenu subjectsMenu1) {
		if (clickedButton == Lop_bnt) {
			manageLopMenu.show(Lop_bnt, 0, Lop_bnt.getHeight());
		} else if (clickedButton == Mon_bnt) {
			subjectsMenu.show(Mon_bnt, 0, Mon_bnt.getHeight());
		} else if (clickedButton == diemDanh_btn) {
			subjectsMenu1.show(diemDanh_btn, 0, diemDanh_btn.getHeight());
		}
	}
}