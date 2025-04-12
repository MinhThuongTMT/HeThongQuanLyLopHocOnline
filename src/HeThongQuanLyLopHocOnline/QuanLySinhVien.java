package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.Font;
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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QuanLySinhVien extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField MaSV_text;
	private JTable table;
	private DefaultTableModel tableModel;
	private JPanel currentPanel; // Biến để theo dõi panel hiện tại

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public QuanLySinhVien() {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);

		currentPanel = this; // Panel hiện tại là chính nó

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
		btnAdd.setBounds(612, 167, 100, 30);
		add(btnAdd);

		JButton btnDelete = new JButton("XÓA");
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDelete.setBackground(new Color(255, 204, 0));
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBounds(762, 167, 100, 30);
		add(btnDelete);

		JButton btnExport = new JButton("XUẤT EXCEL");
		btnExport.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnExport.setBackground(new Color(0, 255, 0));
		btnExport.setForeground(new Color(0, 0, 0));
		btnExport.setBounds(704, 596, 158, 30);
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

		JButton btnTimKiem = new JButton("Tìm Kiếm");
		btnTimKiem.setVerticalTextPosition(SwingConstants.CENTER);
		btnTimKiem.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnTimKiem.setForeground(Color.BLACK);
		btnTimKiem.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTimKiem.setBounds(612, 74, 117, 40);
		add(btnTimKiem);

		JButton btn_TTSV = new JButton("THÔNG TIN CHI TIẾT");
		btn_TTSV.setForeground(Color.WHITE);
		btn_TTSV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btn_TTSV.setBorder(new LineBorder(Color.WHITE, 1));
		btn_TTSV.setBackground(new Color(50, 150, 255));
		btn_TTSV.setBounds(36, 167, 210, 30);
		add(btn_TTSV);

		// Bảng danh sách sinh viên
		String[] columnNames = { "Mã SV", "Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Chuyên Ngành", "Email" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);

		// Đặt bảng trong JScrollPane để có thanh cuộn
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 219, 848, 366);
		add(scrollPane);

		// Sự kiện cho nút Xuất Excel
		btnExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportToExcel();
			}
		});

		// Sự kiện cho nút Tìm Kiếm
		btnTimKiem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchStudentByMSSV();
			}
		});

		// Sự kiện cho nút Xóa
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteStudent();
			}
		});

		// Sự kiện cho nút Thêm
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddStudentPanel();
			}
		});

		// Sự kiện cho nút Thông Tin Chi Tiết
		btn_TTSV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showStudentDetails();
			}
		});

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

		// Thêm nút Quay lại để trở về giao diện QuanLySinhVien
		JButton btnBack = new JButton("QUAY LẠI");
		btnBack.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnBack.setBackground(new Color(255, 204, 0));
		btnBack.setForeground(Color.BLACK);
		btnBack.setBounds(10, 10, 100, 30);
		addStudentPanel.add(btnBack);

		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Trở lại giao diện QuanLySinhVien
				currentPanel.removeAll();
				currentPanel.add(MaSV_text);
				currentPanel.add(table.getTableHeader());
				currentPanel.add(table);
				currentPanel.add(new JScrollPane(table));
				currentPanel.revalidate();
				currentPanel.repaint();
				loadStudentData(); // Tải lại dữ liệu
			}
		});
	}

	// Phương thức tải dữ liệu sinh viên từ database
	private void loadStudentData() {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			Statement stmt = conn.createStatement();
			String sql = "SELECT mssv, hoten, ngaysinh, gioitinh, lop, email FROM students";
			ResultSet rs = stmt.executeQuery(sql);

			tableModel.setRowCount(0);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String mssv = rs.getString("mssv");
				String hoten = rs.getString("hoten");
				String ngaysinh = rs.getDate("ngaysinh") != null ? sdf.format(rs.getDate("ngaysinh")) : "";
				String gioitinh = rs.getString("gioitinh");
				String lop = rs.getString("lop");
				String email = rs.getString("email");
				String chuyennganh = ""; // Chuyên ngành không có trong bảng students

				tableModel.addRow(new Object[] { mssv, hoten, ngaysinh, gioitinh, lop, chuyennganh, email });
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	// Phương thức tìm kiếm sinh viên theo MSSV
	private void searchStudentByMSSV() {
		String mssv = MaSV_text.getText().trim();
		if (mssv.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập MSSV để tìm kiếm!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			loadStudentData();
			return;
		}

		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sql = "SELECT mssv, hoten, ngaysinh, gioitinh, lop, email FROM students WHERE mssv = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mssv);
			ResultSet rs = pstmt.executeQuery();

			tableModel.setRowCount(0);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if (rs.next()) {
				String hoten = rs.getString("hoten");
				String ngaysinh = rs.getDate("ngaysinh") != null ? sdf.format(rs.getDate("ngaysinh")) : "";
				String gioitinh = rs.getString("gioitinh");
				String lop = rs.getString("lop");
				String email = rs.getString("email");
				String chuyennganh = "";

				tableModel.addRow(new Object[] { mssv, hoten, ngaysinh, gioitinh, lop, chuyennganh, email });
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên với MSSV: " + mssv, "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	// Phương thức xóa sinh viên
	private void deleteStudent() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một sinh viên để xóa!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String mssv = tableModel.getValueAt(selectedRow, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sinh viên với MSSV: " + mssv + "?",
				"Xác nhận xóa", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

				String sqlCourses = "DELETE FROM courses WHERE mssv = ?";
				PreparedStatement pstmtCourses = conn.prepareStatement(sqlCourses);
				pstmtCourses.setString(1, mssv);
				pstmtCourses.executeUpdate();

				String sqlStudents = "DELETE FROM students WHERE mssv = ?";
				PreparedStatement pstmtStudents = conn.prepareStatement(sqlStudents);
				pstmtStudents.setString(1, mssv);
				int rowsAffected = pstmtStudents.executeUpdate();

				if (rowsAffected > 0) {
					tableModel.removeRow(selectedRow);
					JOptionPane.showMessageDialog(this, "Xóa sinh viên thành công!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên để xóa!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}

				pstmtCourses.close();
				pstmtStudents.close();
				conn.close();

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Lỗi khi xóa sinh viên: " + e.getMessage(), "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
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

		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sql = "SELECT monhoc, mamon, sotin, thoigian FROM courses WHERE mssv = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mssv);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				monHoc = rs.getString("monhoc") != null ? rs.getString("monhoc") : "";
				maMon = rs.getString("mamon") != null ? rs.getString("mamon") : "";
				soTin = String.valueOf(rs.getInt("sotin"));
				thoiGian = rs.getString("thoigian") != null ? rs.getString("thoigian") : "";
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin khóa học: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		// Gọi ThongTinSinhVien với đầy đủ thông tin
		ThongTinSinhVien detailFrame = new ThongTinSinhVien(hoten, mssv, lop, ngaysinh, gioitinh, email, monHoc, maMon,
				soTin, thoiGian);
		detailFrame.setVisible(true);
	}

	private void exportToExcel() {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DanhSachSinhVien");

		Row headerRow = sheet.createRow(0);
		String[] columns = { "Mã SV", "Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Chuyên Ngành", "Email" };
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
			}
		}

		try {
			workbook.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}