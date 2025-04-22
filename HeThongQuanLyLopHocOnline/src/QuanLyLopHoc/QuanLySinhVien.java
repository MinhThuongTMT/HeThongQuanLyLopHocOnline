package QuanLyLopHoc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
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

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QuanLySinhVien extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField MaSV_text;
	private JTable table;
	private DefaultTableModel tableModel;
	private JPanel currentPanel;

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true&prepareThreshold=0";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public QuanLySinhVien() {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);

		currentPanel = this;

		// Tiêu đề
		JLabel lblNewLabel = new JLabel("QUẢN LÝ SINH VIÊN");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(300, 10, 300, 40);
		add(lblNewLabel);

		// Khu vực nút chức năng
		JButton btnAdd = new JButton("THÊM");
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAdd.setBackground(new Color(255, 204, 0));
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setBounds(593, 144, 100, 30);
		add(btnAdd);

		JButton btnExport = new JButton("XUẤT EXCEL");
		btnExport.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnExport.setBackground(new Color(0, 255, 0));
		btnExport.setForeground(new Color(0, 0, 0));
		btnExport.setBounds(713, 144, 158, 30);
		add(btnExport);

		JLabel lblNewLabel_1 = new JLabel("Mã Sinh Viên:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_1.setBounds(142, 74, 117, 40);
		add(lblNewLabel_1);

		MaSV_text = new JTextField();
		MaSV_text.setColumns(10);
		MaSV_text.setBounds(271, 74, 321, 40);
		add(MaSV_text);
		
		ImageIcon icon = null;
		icon = new ImageIcon(getClass().getResource("/Icon/find.png"));
		Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledImage);

		JButton btnTimKiem = new JButton("Tìm Kiếm", icon);
		btnTimKiem.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTimKiem.setForeground(Color.BLACK);
		btnTimKiem.setBounds(612, 74, 117, 40);
		btnTimKiem.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnTimKiem.setVerticalTextPosition(SwingConstants.CENTER);
		add(btnTimKiem);

		JButton btn_TTSV = new JButton("THÔNG TIN CHI TIẾT");
		btn_TTSV.setForeground(Color.WHITE);
		btn_TTSV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btn_TTSV.setBorder(new LineBorder(Color.WHITE, 1));
		btn_TTSV.setBackground(new Color(50, 150, 255));
		btn_TTSV.setBounds(23, 144, 210, 30);
		add(btn_TTSV);

		// Bảng danh sách sinh viên
		String[] columnNames = { "Mã SV", "Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Môn học", "Email" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);

		// Đặt bảng trong JScrollPane để có thanh cuộn
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 196, 848, 433);
		add(scrollPane);

		btnAdd.addActionListener(e -> showAddStudentPanel());
        btnExport.addActionListener(e -> exportToExcel());
        btnTimKiem.addActionListener(e -> searchStudentByMSSV());
        btn_TTSV.addActionListener(e -> showStudentDetails());

		// Tải dữ liệu từ database khi khởi tạo
		loadStudentData();
	}

	// Phương thức hiển thị panel thêm sinh viên
	private void showAddStudentPanel() {
		// Tạo panel SinhVien
		SinhVien addStudentPanel = new SinhVien();
		addStudentPanel.setBounds(0, 0, 895, 652);

		// Xóa nội dung hiện tại và thêm panel mới
		currentPanel.removeAll();
		currentPanel.add(addStudentPanel);
		currentPanel.revalidate();
		currentPanel.repaint();
	}

	// Phương thức tải dữ liệu sinh viên từ database
	private void loadStudentData() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			stmt = conn.createStatement();
			String sql = "SELECT s.mssv, s.hoten, s.ngaysinh, s.gioitinh, s.lop, s.email, c.monhoc "
					+ "FROM students s " + "LEFT JOIN courses c ON s.mssv = c.mssv";
			rs = stmt.executeQuery(sql);

			tableModel.setRowCount(0);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String mssv = rs.getString("mssv");
				String hoten = rs.getString("hoten");
				String ngaysinh = rs.getDate("ngaysinh") != null ? sdf.format(rs.getDate("ngaysinh")) : "";
				String gioitinh = rs.getString("gioitinh");
				String lop = rs.getString("lop");
				String email = rs.getString("email");
				String monhoc = rs.getString("monhoc") != null ? rs.getString("monhoc") : "";

				tableModel.addRow(new Object[] { mssv, hoten, ngaysinh, gioitinh, lop, monhoc, email });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException closeEx) {
				closeEx.printStackTrace();
			}
		}
	}

	// tim kiem sinh viên bằng MSSV
	private void searchStudentByMSSV() {
		String mssv = MaSV_text.getText().trim();
		if (mssv.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập MSSV để tìm kiếm!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			loadStudentData();
			return;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sql = "SELECT s.mssv, s.hoten, s.ngaysinh, s.gioitinh, s.lop, s.email, c.monhoc "
					+ "FROM students s LEFT JOIN courses c ON s.mssv = c.mssv WHERE s.mssv = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mssv);
			rs = pstmt.executeQuery();

			tableModel.setRowCount(0);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			boolean found = false;

			while (rs.next()) {
				found = true;
				String hoten = rs.getString("hoten");
				String ngaysinh = rs.getDate("ngaysinh") != null ? sdf.format(rs.getDate("ngaysinh")) : "";
				String gioitinh = rs.getString("gioitinh");
				String lop = rs.getString("lop");
				String email = rs.getString("email");
				String monhoc = rs.getString("monhoc") != null ? rs.getString("monhoc") : "";

				tableModel.addRow(new Object[] { mssv, hoten, ngaysinh, gioitinh, lop, monhoc, email });
			}

			if (!found) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên với MSSV: " + mssv, "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi",
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

	// Phương thức hiển thị thông tin chi tiết sinh viên
	private void showStudentDetails() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một sinh viên để xem chi tiết!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String mssv = tableModel.getValueAt(selectedRow, 0).toString();
		String hoten = tableModel.getValueAt(selectedRow, 1).toString();
		String ngaysinh = tableModel.getValueAt(selectedRow, 2).toString();
		String gioitinh = tableModel.getValueAt(selectedRow, 3).toString();
		String lop = tableModel.getValueAt(selectedRow, 4).toString();
		String email = tableModel.getValueAt(selectedRow, 6).toString();

		// Lấy thông tin từ bảng courses
		String monHoc = "";
		String maMon = "";
		String soTin = "";
		String thoiGian = "";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sql = "SELECT monhoc, mamon, sotin, thoigian FROM courses WHERE mssv = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mssv);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				monHoc = rs.getString("monhoc") != null ? rs.getString("monhoc") : "";
				maMon = rs.getString("mamon") != null ? rs.getString("mamon") : "";
				soTin = String.valueOf(rs.getInt("sotin"));
				thoiGian = rs.getString("thoigian") != null ? rs.getString("thoigian") : "";
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin khóa học: " + e.getMessage(), "Lỗi",
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

		// Gọi ThongTinSinhVien với đầy đủ thông tin
		ThongTinSinhVien detailFrame = new ThongTinSinhVien(hoten, mssv, lop, ngaysinh, gioitinh, email, monHoc, maMon,
				soTin, thoiGian);
		detailFrame.setVisible(true);
	}

	//xuất excel
	private void exportToExcel() {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DanhSachSinhVien");

		Row headerRow = sheet.createRow(0);
		String[] columns = { "Mã SV", "Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Môn học", "Email" };
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
		fileChooser.setSelectedFile(new java.io.File("DanhSachSinhVien.xlsx"));
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

	//đếm số lượng sinh viên 
	public int getStudentCount() {
		int count = 0;
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sql = "SELECT COUNT(*) FROM students"; //từu bảng Student ở SQL để đếm dòng
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi đếm sinh viên: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return count;
	}
}