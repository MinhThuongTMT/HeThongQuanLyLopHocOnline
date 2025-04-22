package QuanLyLopHoc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.toedter.calendar.JDateChooser;

public class DiemDanh extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField monTextField;
	private JTable table;
	private DefaultTableModel tableModel;
	private JDateChooser dateChooser;

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true&prepareThreshold=0";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public DiemDanh(String monGiangDay) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 895, 652);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 121));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 0));
		panel.setBounds(0, 0, 881, 57);
		panel.setLayout(null);
		contentPane.add(panel);

		JLabel lblTitle = new JLabel("ĐIỂM DANH");
		lblTitle.setBounds(342, 11, 198, 37);
		panel.add(lblTitle);
		lblTitle.setForeground(new Color(0, 0, 0));
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));

		JLabel lblMon = new JLabel("MÔN GIẢNG DẠY:");
		lblMon.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMon.setForeground(new Color(255, 255, 255));
		lblMon.setBounds(35, 82, 149, 31);
		contentPane.add(lblMon);

		monTextField = new JTextField(monGiangDay);
		monTextField.setFont(new Font("Times New Roman", Font.BOLD, 15));
		monTextField.setForeground(new Color(0, 0, 0));
		monTextField.setBounds(206, 82, 302, 31);
		monTextField.setEditable(false);
		contentPane.add(monTextField);

		JLabel lblNgay = new JLabel("NGÀY ĐIỂM DANH:");
		lblNgay.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNgay.setForeground(new Color(255, 255, 255));
		lblNgay.setBounds(565, 82, 149, 31);
		contentPane.add(lblNgay);

		dateChooser = new JDateChooser();
		dateChooser.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		dateChooser.setBounds(724, 82, 130, 31);
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new java.util.Date()); // Mặc định là ngày hiện tại
		contentPane.add(dateChooser);

		// Thêm sự kiện nhấn Enter cho JDateChooser
		dateChooser.getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (dateChooser.getDate() != null) {
						loadStudentData(monGiangDay);
					} else {
						JOptionPane.showMessageDialog(DiemDanh.this, "Vui lòng chọn ngày hợp lệ!", "Cảnh báo",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		JButton btnXuat = new JButton("Xuất Excel");
		btnXuat.setForeground(new Color(0, 0, 0));
		btnXuat.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnXuat.setBackground(new Color(0, 204, 0));
		btnXuat.setBounds(704, 552, 150, 40);
		contentPane.add(btnXuat);

		JButton btnLuu = new JButton("LƯU");
		btnLuu.setForeground(Color.BLACK);
		btnLuu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnLuu.setBackground(new Color(0, 204, 0));
		btnLuu.setBounds(593, 552, 101, 40);
		contentPane.add(btnLuu);

		JButton btnSua = new JButton("SỬA");
		btnSua.setForeground(Color.BLACK);
		btnSua.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSua.setBackground(new Color(0, 204, 0));
		btnSua.setBounds(482, 552, 101, 40);
		contentPane.add(btnSua);

		// Khởi tạo bảng với các cột
		String[] columnNames = { "Mã SV", "Họ Tên", "Lớp", "Ngày", "Trạng Thái" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 4 ? Boolean.class : String.class;
			}
		};
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);
		table.setEnabled(false); // Mặc định không cho chỉnh sửa

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 129, 839, 412);
		contentPane.add(scrollPane);

		// Tạo các bảng cần thiết
		if (!createRequiredTables()) {
			JOptionPane.showMessageDialog(this, "Không thể khởi tạo bảng cần thiết. Vui lòng kiểm tra database.", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Tải dữ liệu sinh viên
		loadStudentData(monGiangDay);

		btnSua.addActionListener(e -> Sua());
        btnLuu.addActionListener(e -> Luu());
		btnXuat.addActionListener(e -> exportToExcel());
	}
	
	// Hàm xử lý sự kiện nút Sửa
    private void Sua() {
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày điểm danh!", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        table.setEnabled(true);
    }

    // Hàm xử lý sự kiện nút Lưu
    private void Luu() {
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày điểm danh!", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        saveAttendance(monTextField.getText());
    }

	// Phương thức tạo các bảng cần thiết (students, courses, diemdanh)
	private boolean createRequiredTables() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			stmt = conn.createStatement();

			// Tạo bảng students nếu chưa tồn tại
			String createStudentsTableSQL = "CREATE TABLE IF NOT EXISTS students (" + "mssv VARCHAR(50) PRIMARY KEY,"
					+ "hoten VARCHAR(100)," + "ngaysinh DATE," + "gioitinh VARCHAR(10)," + "lop VARCHAR(50),"
					+ "email VARCHAR(100)" + ")";
			stmt.executeUpdate(createStudentsTableSQL);

			// Tạo bảng courses nếu chưa tồn tại
			String createCoursesTableSQL = "CREATE TABLE IF NOT EXISTS courses (" + "id SERIAL PRIMARY KEY,"
					+ "mssv VARCHAR(50) REFERENCES students(mssv)," + "monhoc VARCHAR(100)," + "mamon VARCHAR(50),"
					+ "sotin INTEGER," + "thoigian VARCHAR(50)" + ")";
			stmt.executeUpdate(createCoursesTableSQL);

			// Tạo bảng diemdanh nếu chưa tồn tại
			String createDiemDanhTableSQL = "CREATE TABLE IF NOT EXISTS diemdanh (" + "mssv VARCHAR(50) NOT NULL,"
					+ "mamon VARCHAR(50) NOT NULL," + "ngay DATE NOT NULL," + "trangthai BOOLEAN NOT NULL,"
					+ "PRIMARY KEY (mssv, mamon, ngay)," + "FOREIGN KEY (mssv) REFERENCES students(mssv)" + ")";
			stmt.executeUpdate(createDiemDanhTableSQL);

			return true;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tạo bảng: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException closeEx) {
				closeEx.printStackTrace();
			}
		}
	}

	// Tải dữ liệu sinh viên từ database
	private void loadStudentData(String monGiangDay) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sql = "SELECT s.mssv, s.hoten, s.lop, d.trangthai, d.ngay " + "FROM students s "
					+ "JOIN courses c ON s.mssv = c.mssv "
					+ "LEFT JOIN diemdanh d ON s.mssv = d.mssv AND d.mamon = c.mamon AND d.ngay = ? "
					+ "WHERE c.monhoc = ?";
			pstmt = conn.prepareStatement(sql);
			java.util.Date selectedDate = dateChooser.getDate();
			if (selectedDate == null) {
				selectedDate = new java.util.Date(); // Mặc định ngày hiện tại nếu chưa chọn
			}
			pstmt.setDate(1, new Date(selectedDate.getTime()));
			pstmt.setString(2, monGiangDay);
			rs = pstmt.executeQuery();

			tableModel.setRowCount(0);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			while (rs.next()) {
				String mssv = rs.getString("mssv");
				String hoten = rs.getString("hoten");
				String lop = rs.getString("lop");
				Boolean trangthai = rs.getBoolean("trangthai");
				if (rs.wasNull()) {
					trangthai = false; // Mặc định là false nếu chưa điểm danh
				}
				String ngay = sdf.format(selectedDate); // Hiển thị ngày đã chọn
				tableModel.addRow(new Object[] { mssv, hoten, lop, ngay, trangthai });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException closeEx) {
				closeEx.printStackTrace();
			}
		}
	}

	// Lưu điểm danh vào database
	private void saveAttendance(String monGiangDay) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			conn.setAutoCommit(false);

			java.util.Date selectedDate = dateChooser.getDate();
			if (selectedDate == null) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày điểm danh!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Xóa dữ liệu điểm danh cũ của ngày được chọn
			String deleteSql = "DELETE FROM diemdanh WHERE mamon IN (SELECT mamon FROM courses WHERE monhoc = ?) AND ngay = ?";
			pstmt = conn.prepareStatement(deleteSql);
			pstmt.setString(1, monGiangDay);
			pstmt.setDate(2, new Date(selectedDate.getTime()));
			pstmt.executeUpdate();
			pstmt.close();

			// Thêm dữ liệu điểm danh mới
			String insertSql = "INSERT INTO diemdanh (mssv, mamon, ngay, trangthai) "
					+ "SELECT ?, mamon, ?, ? FROM courses WHERE monhoc = ? AND mssv = ?";
			pstmt = conn.prepareStatement(insertSql);
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				String mssv = tableModel.getValueAt(i, 0).toString();
				boolean trangthai = (boolean) tableModel.getValueAt(i, 4);
				pstmt.setString(1, mssv);
				pstmt.setDate(2, new Date(selectedDate.getTime()));
				pstmt.setBoolean(3, trangthai);
				pstmt.setString(4, monGiangDay);
				pstmt.setString(5, mssv);
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			conn.commit();
			JOptionPane.showMessageDialog(this, "Lưu điểm danh thành công!", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
			table.setEnabled(false);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi lưu điểm danh: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException closeEx) {
				closeEx.printStackTrace();
			}
		}
	}

	// Xuất dữ liệu điểm danh ra file Excel
	private void exportToExcel() {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DiemDanh");

		// Tạo hàng tiêu đề
		Row headerRow = sheet.createRow(0);
		String[] columns = { "Mã SV", "Họ Tên", "Lớp", "Ngày", "Trạng Thái" };
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

		// Thêm dữ liệu từ bảng
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			Row row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(tableModel.getValueAt(i, 0).toString());
			row.createCell(1).setCellValue(tableModel.getValueAt(i, 1).toString());
			row.createCell(2).setCellValue(tableModel.getValueAt(i, 2).toString());
			row.createCell(3).setCellValue(tableModel.getValueAt(i, 3).toString());
			row.createCell(4).setCellValue((boolean) tableModel.getValueAt(i, 4) ? "Có mặt" : "Vắng");
		}

		// Tự động điều chỉnh kích thước cột
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Hiển thị hộp thoại lưu file
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
		fileChooser.setSelectedFile(new java.io.File("DiemDanh_" + monTextField.getText() + ".xlsx"));
		int userSelection = fileChooser.showSaveDialog(this);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
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

	public static void main(String[] args) {
		// Cấu hình font chữ mặc định cho JDateChooser
		Properties props = new Properties();
		props.put("text.today", "Hôm nay");
		props.put("text.month", "Tháng");
		props.put("text.year", "Năm");
		com.toedter.calendar.JDateChooser.setDefaultLocale(new java.util.Locale("vi"));
		new DiemDanh("Môn Học Test").setVisible(true);
	}
}