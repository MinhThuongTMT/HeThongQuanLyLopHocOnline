package QuanLyLopHoc;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

public class ChamDiem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField Mon_text;

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true&prepareThreshold=0";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public ChamDiem() {
		this("");
	}

	public ChamDiem(String monGiangDay) {
		setTitle("Chấm Điểm - " + monGiangDay);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 911, 690);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 121));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		createTableIfNotExists();

		JLabel lblTitle = new JLabel("CHẤM ĐIỂM");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(350, 10, 200, 40);
		contentPane.add(lblTitle);

		JLabel lblMonHoc = new JLabel("MÔN HỌC:");
		lblMonHoc.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMonHoc.setForeground(new Color(255, 255, 255));
		lblMonHoc.setBounds(50, 70, 100, 30);
		contentPane.add(lblMonHoc);

		JButton btnTimKiem = new JButton("TÌM KIẾM");
		btnTimKiem.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnTimKiem.setBackground(new Color(255, 204, 0));
		btnTimKiem.setForeground(Color.BLACK);
		btnTimKiem.setBounds(734, 70, 111, 30);
		contentPane.add(btnTimKiem);

		String[] columnNames = { "MSSV", "Họ Tên", "Lớp", "Thời gian nộp", "Tên Tệp", "Điểm chuyên cần", "Điểm 15",
				"Điểm giữa kỳ", "Điểm cuối kỳ" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column >= 5; // Cho phép chỉnh sửa các cột điểm
			}
		};
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(21, 120, 852, 447);
		contentPane.add(scrollPane);

		JButton btnLuu = new JButton("LƯU");
		btnLuu.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnLuu.setBackground(new Color(255, 140, 0));
		btnLuu.setForeground(Color.BLACK);
		btnLuu.setBounds(227, 585, 120, 44);
		contentPane.add(btnLuu);

		JButton btnXuat = new JButton("XUẤT EXCEL");
		btnXuat.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnXuat.setBackground(new Color(0, 204, 0));
		btnXuat.setForeground(new Color(0, 0, 0));
		btnXuat.setBounds(539, 585, 139, 44);
		contentPane.add(btnXuat);

		Mon_text = new JTextField(monGiangDay);
		Mon_text.setForeground(Color.BLACK);
		Mon_text.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Mon_text.setEditable(false);
		Mon_text.setBounds(147, 70, 269, 31);
		contentPane.add(Mon_text);

		JButton btnDiemDanh = new JButton("ĐIỂM DANH");
		btnDiemDanh.setForeground(Color.BLACK);
		btnDiemDanh.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnDiemDanh.setBackground(new Color(255, 204, 0));
		btnDiemDanh.setBounds(562, 70, 133, 30);
		contentPane.add(btnDiemDanh);

		btnTimKiem.addActionListener(e -> loadStudentData());
		btnLuu.addActionListener(e -> saveScores());
		btnXuat.addActionListener(e -> exportToExcel());
	}

	private void createTableIfNotExists() {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				Statement stmt = conn.createStatement()) {
			String sqlStudents = "CREATE TABLE IF NOT EXISTS students (" + "mssv VARCHAR(50) PRIMARY KEY,"
					+ "hoten VARCHAR(100)," + "ngaysinh DATE," + "gioitinh VARCHAR(10)," + "lop VARCHAR(50),"
					+ "email VARCHAR(100)" + ")";
			stmt.executeUpdate(sqlStudents);

			String sqlBaitap = "CREATE TABLE IF NOT EXISTS Baitap (" + "id SERIAL PRIMARY KEY,"
					+ "mssv VARCHAR(50) REFERENCES students(mssv)," + "monhoc VARCHAR(100)," + "tieude VARCHAR(100),"
					+ "trangthai VARCHAR(20)," + "thoigian_nop TIMESTAMP," + "ten_tep VARCHAR(255)" + ")";
			stmt.executeUpdate(sqlBaitap);

			String sqlChamdiem = "CREATE TABLE IF NOT EXISTS Chamdiem (" + "mssv VARCHAR(50)," + "hoten VARCHAR(100),"
					+ "lop VARCHAR(50)," + "thoigian_nop TIMESTAMP," + "ten_tep VARCHAR(255)," + "diem_chuyencan FLOAT,"
					+ "diem_15 FLOAT," + "diem_giuaky FLOAT," + "diem_cuoiky FLOAT," + "monhoc VARCHAR(100),"
					+ "PRIMARY KEY (mssv, monhoc)," + "FOREIGN KEY (mssv) REFERENCES students(mssv)" + ")";
			stmt.executeUpdate(sqlChamdiem);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tạo bảng: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void loadStudentData() {
		tableModel.setRowCount(0);
		String monHoc = Mon_text.getText();

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT s.mssv, s.hoten, s.lop, b.thoigian_nop, b.ten_tep, c.diem_chuyencan, c.diem_15, c.diem_giuaky, c.diem_cuoiky "
								+ "FROM students s " + "LEFT JOIN Baitap b ON s.mssv = b.mssv AND b.monhoc = ? "
								+ "LEFT JOIN Chamdiem c ON s.mssv = c.mssv AND c.monhoc = ?")) {
			pstmt.setString(1, monHoc);
			pstmt.setString(2, monHoc);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String mssv = rs.getString("mssv");
				String hoten = rs.getString("hoten");
				String lop = rs.getString("lop");
				Timestamp thoigianNop = rs.getTimestamp("thoigian_nop");
				String tenTep = rs.getString("ten_tep");
				Float diemChuyenCan = rs.getFloat("diem_chuyencan");
				Float diem15 = rs.getFloat("diem_15");
				Float diemGiuaKy = rs.getFloat("diem_giuaky");
				Float diemCuoiKy = rs.getFloat("diem_cuoiky");

				String thoigianNopStr = thoigianNop != null ? thoigianNop.toString() : "";
				String tenTepStr = tenTep != null ? tenTep : "";
				String diemChuyenCanStr = rs.wasNull() ? "" : String.valueOf(diemChuyenCan);
				String diem15Str = rs.wasNull() ? "" : String.valueOf(diem15);
				String diemGiuaKyStr = rs.wasNull() ? "" : String.valueOf(diemGiuaKy);
				String diemCuoiKyStr = rs.wasNull() ? "" : String.valueOf(diemCuoiKy);

				tableModel.addRow(new Object[] { mssv, hoten, lop, thoigianNopStr, tenTepStr, diemChuyenCanStr,
						diem15Str, diemGiuaKyStr, diemCuoiKyStr });
			}

			if (tableModel.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu phù hợp!", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void saveScores() {
		String monHoc = Mon_text.getText();
		if (monHoc.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập môn học!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO Chamdiem (mssv, hoten, lop, thoigian_nop, ten_tep, diem_chuyencan, diem_15, diem_giuaky, diem_cuoiky, monhoc) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " + "ON CONFLICT (mssv, monhoc) "
					+ "DO UPDATE SET hoten = EXCLUDED.hoten, " + "lop = EXCLUDED.lop, "
					+ "thoigian_nop = EXCLUDED.thoigian_nop, " + "ten_tep = EXCLUDED.ten_tep, "
					+ "diem_chuyencan = EXCLUDED.diem_chuyencan, " + "diem_15 = EXCLUDED.diem_15, "
					+ "diem_giuaky = EXCLUDED.diem_giuaky, " + "diem_cuoiky = EXCLUDED.diem_cuoiky";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				int validRows = 0;
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					String mssv = (String) tableModel.getValueAt(i, 0);
					String hoten = (String) tableModel.getValueAt(i, 1);
					String lop = (String) tableModel.getValueAt(i, 2);
					String thoigianNopStr = (String) tableModel.getValueAt(i, 3);
					String tenTep = (String) tableModel.getValueAt(i, 4);
					String diemChuyenCanStr = (String) tableModel.getValueAt(i, 5);
					String diem15Str = (String) tableModel.getValueAt(i, 6);
					String diemGiuaKyStr = (String) tableModel.getValueAt(i, 7);
					String diemCuoiKyStr = (String) tableModel.getValueAt(i, 8);

					Timestamp thoigianNop = thoigianNopStr != null && !thoigianNopStr.isEmpty()
							? Timestamp.valueOf(thoigianNopStr)
							: null;
					Float diemChuyenCan = null;
					Float diem15 = null;
					Float diemGiuaKy = null;
					Float diemCuoiKy = null;

					try {
						if (diemChuyenCanStr != null && !diemChuyenCanStr.trim().isEmpty()) {
							diemChuyenCan = Float.parseFloat(diemChuyenCanStr.trim());
							if (diemChuyenCan < 0 || diemChuyenCan > 10) {
								JOptionPane.showMessageDialog(this,
										"Điểm chuyên cần của sinh viên " + mssv + " phải từ 0 đến 10!", "Lỗi",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						if (diem15Str != null && !diem15Str.trim().isEmpty()) {
							diem15 = Float.parseFloat(diem15Str.trim());
							if (diem15 < 0 || diem15 > 10) {
								JOptionPane.showMessageDialog(this,
										"Điểm 15 của sinh viên " + mssv + " phải từ 0 đến 10!", "Lỗi",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						if (diemGiuaKyStr != null && !diemGiuaKyStr.trim().isEmpty()) {
							diemGiuaKy = Float.parseFloat(diemGiuaKyStr.trim());
							if (diemGiuaKy < 0 || diemGiuaKy > 10) {
								JOptionPane.showMessageDialog(this,
										"Điểm giữa kỳ của sinh viên " + mssv + " phải từ 0 đến 10!", "Lỗi",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						if (diemCuoiKyStr != null && !diemCuoiKyStr.trim().isEmpty()) {
							diemCuoiKy = Float.parseFloat(diemCuoiKyStr.trim());
							if (diemCuoiKy < 0 || diemCuoiKy > 10) {
								JOptionPane.showMessageDialog(this,
										"Điểm cuối kỳ của sinh viên " + mssv + " phải từ 0 đến 10!", "Lỗi",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}

						pstmt.setString(1, mssv);
						pstmt.setString(2, hoten);
						pstmt.setString(3, lop);
						if (thoigianNop != null)
							pstmt.setTimestamp(4, thoigianNop);
						else
							pstmt.setNull(4, java.sql.Types.TIMESTAMP);
						pstmt.setString(5, tenTep);
						if (diemChuyenCan != null)
							pstmt.setFloat(6, diemChuyenCan);
						else
							pstmt.setNull(6, java.sql.Types.FLOAT);
						if (diem15 != null)
							pstmt.setFloat(7, diem15);
						else
							pstmt.setNull(7, java.sql.Types.FLOAT);
						if (diemGiuaKy != null)
							pstmt.setFloat(8, diemGiuaKy);
						else
							pstmt.setNull(8, java.sql.Types.FLOAT);
						if (diemCuoiKy != null)
							pstmt.setFloat(9, diemCuoiKy);
						else
							pstmt.setNull(9, java.sql.Types.FLOAT);
						pstmt.setString(10, monHoc);
						pstmt.addBatch();
						validRows++;
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(this, "Điểm của sinh viên " + mssv + " không hợp lệ!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				if (validRows == 0) {
					JOptionPane.showMessageDialog(this, "Không có dữ liệu để lưu!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				pstmt.executeBatch();
				conn.commit();
				JOptionPane.showMessageDialog(this, "Lưu điểm thành công!", "Thành công",
						JOptionPane.INFORMATION_MESSAGE);
				loadStudentData();
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi lưu điểm: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
				conn.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
		}
	}

	private void exportToExcel() {
		if (tableModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DanhSachNopBai");

		Row headerRow = sheet.createRow(0);
		String[] columns = { "MSSV", "Họ Tên", "Lớp", "Thời gian nộp", "Tên Tệp", "Điểm chuyên cần", "Điểm 15",
				"Điểm giữa kỳ", "Điểm cuối kỳ" };
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
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

		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
		fileChooser.setSelectedFile(new java.io.File("DanhSachNopBai.xlsx"));
		int userSelection = fileChooser.showSaveDialog(this);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			try (FileOutputStream fileOut = new FileOutputStream(fileChooser.getSelectedFile())) {
				workbook.write(fileOut);
				JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!", "Thành công",
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

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ChamDiem frame = new ChamDiem();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
