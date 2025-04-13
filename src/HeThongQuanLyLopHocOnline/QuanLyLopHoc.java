package HeThongQuanLyLopHocOnline;

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
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
	private static final String[] SCORE_COLUMNS = { "Mã SV", "Tên", "Môn học", "Điểm chuyên cần", "Điểm 15'",
			"Điểm giữa kỳ", "Điểm cuối kỳ" };

	public QuanLyLopHoc() {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);

		// Tạo bảng BangDiem
		createBangDiemTable();

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
		JMenuItem lop1 = new JMenuItem("D21CQVT01-N");
		JMenuItem lop2 = new JMenuItem("D21CQVTHI01-N");
		JMenuItem lop3 = new JMenuItem("D21CQVTVT01-N");
		JMenuItem allLops = new JMenuItem("Tất cả lớp");
		manageLopMenu.add(lop1);
		manageLopMenu.add(lop2);
		manageLopMenu.add(lop3);
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
		JMenuItem mon1 = new JMenuItem("Cảm Biến");
		JMenuItem mon2 = new JMenuItem("Java");
		JMenuItem mon3 = new JMenuItem("Android");
		subjectsMenu.add(mon1);
		subjectsMenu.add(mon2);
		subjectsMenu.add(mon3);

		// Nút Danh Sách Môn Học
		JButton Mon_bnt = new JButton("Danh Sách Môn Học");
		Mon_bnt.setFont(new Font("Times New Roman", Font.BOLD, 14));
		Mon_bnt.setBackground(new Color(255, 215, 0));
		Mon_bnt.setForeground(Color.BLACK);
		Mon_bnt.setBounds(334, 61, 187, 40);
		add(Mon_bnt);

		// JPopupMenu cho Danh Sách Điểm Danh
		JPopupMenu subjectsMenu1 = new JPopupMenu();
		subjectsMenu1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		subjectsMenu1.setBackground(new Color(255, 204, 0));
		subjectsMenu1.setForeground(Color.BLACK);
		JMenuItem DiemDanh1 = new JMenuItem("Cảm Biến");
		JMenuItem DiemDanh2 = new JMenuItem("Java");
		JMenuItem DiemDanh3 = new JMenuItem("Android");
		subjectsMenu1.add(DiemDanh1);
		subjectsMenu1.add(DiemDanh2);
		subjectsMenu1.add(DiemDanh3);

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

		// Nút Lưu Điểm
		JButton btnSaveScores = new JButton("Lưu Điểm");
		btnSaveScores.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSaveScores.setBackground(new Color(0, 204, 204));
		btnSaveScores.setForeground(Color.WHITE);
		btnSaveScores.setBounds(514, 125, 150, 31);
		add(btnSaveScores);

		// Khởi tạo JTable
		tableModel = new DefaultTableModel(CLASS_COLUMNS, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Chỉ cho phép chỉnh sửa các cột điểm (từ cột 3 trở đi) khi bảng điểm được hiển
				// thị
				return tableModel.getColumnName(2).equals("Môn học") && column >= 3;
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

		// Sự kiện Lưu Điểm
		btnSaveScores.addActionListener(e -> {
			saveScoresToDatabase();
			resetButtonColors(btnSaveScores);
			btnSaveScores.setBackground(new Color(0, 153, 153));
			lastClickedButton = btnSaveScores;
		});

		// Sự kiện chọn lớp
		String[] allLopsArray = { "D21CQVTVT-01", "D21CQVTHI-01", "D21CQVT-01N" };
		lop1.addActionListener(e -> loadClassData("D21CQVT-01N"));
		lop2.addActionListener(e -> loadClassData("D21CQVTHI-01"));
		lop3.addActionListener(e -> loadClassData("D21CQVTVT-01"));
		allLops.addActionListener(e -> loadClassData(allLopsArray)); // Sửa lỗi ở đây

		// Sự kiện chọn môn
		mon1.addActionListener(e -> loadScoreData("Cảm Biến"));
		mon2.addActionListener(e -> loadScoreData("Java"));
		mon3.addActionListener(e -> loadScoreData("Android"));

		// Sự kiện điểm danh
		ActionListener diemDanhListener = e -> {
			String mon = ((JMenuItem) e.getSource()).getText();
			HienThi.setText("Danh Sách Điểm Danh Môn: " + mon);
			JOptionPane.showMessageDialog(this, "Chức năng này chưa được triển khai.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		};
		DiemDanh1.addActionListener(diemDanhListener);
		DiemDanh2.addActionListener(diemDanhListener);
		DiemDanh3.addActionListener(diemDanhListener);
	}

	// Tạo bảng BangDiem
	private void createBangDiemTable() {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = """
					CREATE TABLE IF NOT EXISTS BangDiem (
					    mssv TEXT,
					    hoten TEXT,
					    monhoc TEXT,
					    diemchuyencan FLOAT,
					    diem15phut FLOAT,
					    diemgiuaky FLOAT,
					    diemcuoiky FLOAT,
					    PRIMARY KEY (mssv, monhoc)
					)
					""";
			try (Statement stmt = conn.createStatement()) {
				stmt.execute(sql);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tạo bảng BangDiem: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	// Tải dữ liệu lớp
	private void loadClassData(String... lopList) {
		HienThi.setText("Danh Sách Lớp: " + (lopList.length == 1 ? lopList[0] : "Tất cả lớp"));
		tableModel.setColumnIdentifiers(CLASS_COLUMNS);
		tableModel.setRowCount(0);

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String placeholders = String.join(",", java.util.Collections.nCopies(lopList.length, "?"));
			String sql = """
					SELECT s.mssv, s.hoten, s.ngaysinh, s.gioitinh, s.lop,
					       STRING_AGG(COALESCE(c.monhoc, ''), ', ') AS monhoc, s.email
					FROM students s
					LEFT JOIN courses c ON s.mssv = c.mssv
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
								rs.getString("gioitinh"), rs.getString("lop"), rs.getString("monhoc"),
								rs.getString("email") });
						hasData = true;
					}
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
		table.repaint();
	}

	// Tải dữ liệu điểm
	private void loadScoreData(String monHoc) {
		HienThi.setText("Danh Sách Lớp Môn: " + monHoc);
		tableModel.setColumnIdentifiers(SCORE_COLUMNS);
		tableModel.setRowCount(0);

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = """
					SELECT s.mssv, s.hoten, c.monhoc,
					       b.diemchuyencan, b.diem15phut, b.diemgiuaky, b.diemcuoiky
					FROM students s
					JOIN courses c ON s.mssv = c.mssv
					LEFT JOIN BangDiem b ON s.mssv = b.mssv AND b.monhoc = c.monhoc
					WHERE c.monhoc = ?
					""";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, monHoc);
				try (ResultSet rs = pstmt.executeQuery()) {
					boolean hasData = false;
					while (rs.next()) {
						tableModel.addRow(
								new Object[] { rs.getString("mssv"), rs.getString("hoten"), rs.getString("monhoc"),
										rs.getObject("diemchuyencan") != null ? rs.getFloat("diemchuyencan") : "",
										rs.getObject("diem15phut") != null ? rs.getFloat("diem15phut") : "",
										rs.getObject("diemgiuaky") != null ? rs.getFloat("diemgiuaky") : "",
										rs.getObject("diemcuoiky") != null ? rs.getFloat("diemcuoiky") : "" });
						hasData = true;
					}
					if (!hasData) {
						JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên cho môn " + monHoc, "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu điểm: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		table.repaint();
	}

	// Lưu điểm vào database
	private void saveScoresToDatabase() {
		if (!tableModel.getColumnName(2).equals("Môn học")) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một môn học để lưu điểm!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String monHoc = HienThi.getText().replace("Danh Sách Lớp Môn: ", "");
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = """
					INSERT INTO BangDiem (mssv, hoten, monhoc, diemchuyencan, diem15phut, diemgiuaky, diemcuoiky)
					VALUES (?, ?, ?, ?, ?, ?, ?)
					ON CONFLICT (mssv, monhoc)
					DO UPDATE SET
					    hoten = EXCLUDED.hoten,
					    diemchuyencan = EXCLUDED.diemchuyencan,
					    diem15phut = EXCLUDED.diem15phut,
					    diemgiuaky = EXCLUDED.diemgiuaky,
					    diemcuoiky = EXCLUDED.diemcuoiky
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
				JOptionPane.showMessageDialog(this, "Lưu điểm thành công!", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi lưu điểm: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Kiểm tra điểm hợp lệ
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

	// Xuất Excel
	private void exportToExcel() {
		if (tableModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DanhSach");
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

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			Row row = sheet.createRow(i + 1);
			for (int j = 0; j < tableModel.getColumnCount(); j++) {
				Cell cell = row.createCell(j);
				Object value = tableModel.getValueAt(i, j);
				if (value != null) {
					cell.setCellValue(value.toString());
				}
			}
		}

		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			sheet.autoSizeColumn(i);
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
		String fileName = HienThi.getText().isEmpty() ? "DanhSach" : HienThi.getText().replace(" ", "_");
		fileChooser.setSelectedFile(new java.io.File(fileName + ".xlsx"));
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			try (FileOutputStream fileOut = new FileOutputStream(fileChooser.getSelectedFile())) {
				workbook.write(fileOut);
				JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + ex.getMessage(), "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}

		try {
			workbook.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// Reset màu nút
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

	// Cập nhật màu nút
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

	// Hiển thị popup menu
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
