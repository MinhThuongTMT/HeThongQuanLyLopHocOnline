package HeThongQuanLyLopHocOnline;

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
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ChamDiem extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> monHocComboBox;
	private JComboBox<String> lopComboBox;
	private JTable table;
	private DefaultTableModel tableModel;

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true&prepareThreshold=0";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public ChamDiem() {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);

		// Tạo bảng diem nếu chưa tồn tại
		createTableIfNotExists();

		// Tiêu đề
		JLabel lblTitle = new JLabel("CHẤM ĐIỂM");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(350, 10, 200, 40);
		add(lblTitle);

		// Nhãn và ComboBox Môn học
		JLabel lblMonHoc = new JLabel("MÔN HỌC:");
		lblMonHoc.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMonHoc.setForeground(new Color(255, 255, 255));
		lblMonHoc.setBounds(50, 70, 100, 30);
		add(lblMonHoc);

		monHocComboBox = new JComboBox<>();
		monHocComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "", "Cảm Biến", "Java", "Android" }));
		monHocComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		monHocComboBox.setBounds(150, 70, 200, 30);
		add(monHocComboBox);

		// Nhãn và ComboBox Lớp
		JLabel lblLop = new JLabel("LỚP:");
		lblLop.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLop.setForeground(new Color(255, 255, 255));
		lblLop.setBounds(400, 70, 100, 30);
		add(lblLop);

		lopComboBox = new JComboBox<>();
		lopComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lopComboBox.setBounds(500, 70, 200, 30);
		loadLopComboBox(); // Tải danh sách lớp từ database
		add(lopComboBox);

		// Nút Tìm kiếm
		JButton btnTimKiem = new JButton("TÌM KIẾM");
		btnTimKiem.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTimKiem.setBackground(new Color(255, 204, 0));
		btnTimKiem.setForeground(Color.BLACK);
		btnTimKiem.setBounds(750, 70, 100, 30);
		add(btnTimKiem);

		// Bảng danh sách sinh viên
		String[] columnNames = { "MSSV", "Họ Tên", "Lớp", "Môn Học", "Điểm" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 4; // Chỉ cho phép chỉnh sửa cột Điểm
			}
		};
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 120, 848, 400);
		add(scrollPane);

		// Nút LƯU
		JButton btnLuu = new JButton("LƯU");
		btnLuu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnLuu.setBackground(new Color(255, 140, 0));
		btnLuu.setForeground(Color.BLACK);
		btnLuu.setBounds(227, 569, 120, 44);
		add(btnLuu);

		// Nút XUẤT EXCEL
		JButton btnXuat = new JButton("XUẤT EXCEL");
		btnXuat.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnXuat.setBackground(new Color(0, 204, 0));
		btnXuat.setForeground(Color.WHITE);
		btnXuat.setBounds(561, 569, 120, 44);
		add(btnXuat);

		// Sự kiện cho nút Tìm kiếm
		btnTimKiem.addActionListener(e -> loadStudentData());

		// Sự kiện cho nút LƯU
		btnLuu.addActionListener(e -> saveScores());

		// Sự kiện cho nút XUẤT EXCEL
		btnXuat.addActionListener(e -> exportToExcel());
	}

	// Tạo bảng diem nếu chưa tồn tại
	private void createTableIfNotExists() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS diem (" + "mssv VARCHAR(50) NOT NULL,"
					+ "monhoc VARCHAR(100) NOT NULL," + "lop VARCHAR(50) NOT NULL," + "diem FLOAT,"
					+ "thoigian_chamdiem TIMESTAMP," + "PRIMARY KEY (mssv, monhoc, lop),"
					+ "FOREIGN KEY (mssv) REFERENCES students(mssv)" + ")";
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tạo bảng diem: " + ex.getMessage(), "Lỗi",
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

	// Tải danh sách lớp vào ComboBox
	private void loadLopComboBox() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> lopList = new ArrayList<>();
		lopList.add(""); // Thêm tùy chọn rỗng
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT lop FROM students ORDER BY lop";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				lopList.add(rs.getString("lop"));
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		lopComboBox.setModel(new DefaultComboBoxModel<>(lopList.toArray(new String[0])));
	}

	// Tải danh sách sinh viên theo môn học và lớp
	private void loadStudentData() {
		String monHoc = (String) monHocComboBox.getSelectedItem();
		String lop = (String) lopComboBox.getSelectedItem();
		tableModel.setRowCount(0);

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			StringBuilder sql = new StringBuilder("SELECT s.mssv, s.hoten, s.lop, c.monhoc, d.diem "
					+ "FROM students s " + "LEFT JOIN courses c ON s.mssv = c.mssv "
					+ "LEFT JOIN diem d ON s.mssv = d.mssv AND c.monhoc = d.monhoc AND s.lop = d.lop " + "WHERE 1=1");

			List<String> params = new ArrayList<>();
			if (monHoc != null && !monHoc.isEmpty()) {
				sql.append(" AND c.monhoc = ?");
				params.add(monHoc);
			}
			if (lop != null && !lop.isEmpty()) {
				sql.append(" AND s.lop = ?");
				params.add(lop);
			}

			pstmt = conn.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				pstmt.setString(i + 1, params.get(i));
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				String mssv = rs.getString("mssv");
				String hoten = rs.getString("hoten");
				String lopSv = rs.getString("lop");
				String monHocSv = rs.getString("monhoc");
				Float diem = rs.getFloat("diem");
				String diemStr = rs.wasNull() ? "" : String.valueOf(diem);
				if (monHocSv != null) { // Chỉ thêm nếu sinh viên có đăng ký môn học
					tableModel.addRow(new Object[] { mssv, hoten, lopSv, monHocSv, diemStr });
				}
			}

			if (tableModel.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên nào phù hợp!", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
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

	// Lưu điểm vào database
	private void saveScores() {
		String monHoc = (String) monHocComboBox.getSelectedItem();
		String lop = (String) lopComboBox.getSelectedItem();
		if (monHoc == null || monHoc.isEmpty() || lop == null || lop.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học và lớp trước khi lưu!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Connection conn = null;
		PreparedStatement pstmtUpsert = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			conn.setAutoCommit(false);

			String sqlUpsert = "INSERT INTO diem (mssv, monhoc, lop, diem, thoigian_chamdiem) "
					+ "VALUES (?, ?, ?, ?, ?) " + "ON CONFLICT (mssv, monhoc, lop) "
					+ "DO UPDATE SET diem = EXCLUDED.diem, thoigian_chamdiem = EXCLUDED.thoigian_chamdiem";

			pstmtUpsert = conn.prepareStatement(sqlUpsert);
			int validScores = 0;

			for (int i = 0; i < tableModel.getRowCount(); i++) {
				String mssv = (String) tableModel.getValueAt(i, 0);
				String diemStr = (String) tableModel.getValueAt(i, 4);
				if (diemStr != null && !diemStr.trim().isEmpty()) {
					try {
						float diem = Float.parseFloat(diemStr.trim());
						if (diem < 0 || diem > 10) {
							JOptionPane.showMessageDialog(this, "Điểm của sinh viên " + mssv + " phải từ 0 đến 10!",
									"Lỗi", JOptionPane.ERROR_MESSAGE);
							return;
						}
						pstmtUpsert.setString(1, mssv);
						pstmtUpsert.setString(2, monHoc);
						pstmtUpsert.setString(3, lop);
						pstmtUpsert.setFloat(4, diem);
						pstmtUpsert.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
						pstmtUpsert.addBatch();
						validScores++;
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(this, "Điểm của sinh viên " + mssv + " không hợp lệ!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}

			if (validScores == 0) {
				JOptionPane.showMessageDialog(this, "Không có điểm nào để lưu!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			pstmtUpsert.executeBatch();
			conn.commit();
			JOptionPane.showMessageDialog(this, "Lưu điểm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
			loadStudentData(); // Tải lại dữ liệu để cập nhật
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi lưu điểm: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
		} finally {
			try {
				if (pstmtUpsert != null)
					pstmtUpsert.close();
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	// Xuất dữ liệu ra Excel
	private void exportToExcel() {
		if (tableModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("BangDiem");

		// Tạo tiêu đề
		Row headerRow = sheet.createRow(0);
		String[] columns = { "MSSV", "Họ Tên", "Lớp", "Môn Học", "Điểm" };
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

		// Điền dữ liệu
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

		// Tự động điều chỉnh kích thước cột
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Lưu file
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
		fileChooser.setSelectedFile(new java.io.File("BangDiem.xlsx"));
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
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setBounds(100, 100, 911, 690);
				frame.add(new ChamDiem());
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
