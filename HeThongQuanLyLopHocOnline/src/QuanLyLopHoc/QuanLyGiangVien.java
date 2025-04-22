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

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QuanLyGiangVien extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTable table;
	private DefaultTableModel tableModel;
	private JPanel currentPanel;

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public QuanLyGiangVien() {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);
		currentPanel = this; // Panel hiện tại là chính nó

		// Tiêu đề
		JLabel lblNewLabel = new JLabel("QUẢN LÝ GIẢNG VIÊN");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(300, 10, 300, 40);
		add(lblNewLabel);

		// Khu vực nút chức năng
		JButton btnAdd = new JButton("THÊM");
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAdd.setBackground(new Color(255, 204, 0));
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setBounds(631, 165, 100, 30);
		btnAdd.setBorder(new LineBorder(Color.WHITE, 1));
		add(btnAdd);

		JButton btnDelete = new JButton("XÓA");
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDelete.setBackground(new Color(255, 204, 0));
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBounds(773, 165, 100, 30);
		btnDelete.setBorder(new LineBorder(Color.WHITE, 1));
		add(btnDelete);

		JButton btnExport = new JButton("XUẤT EXCEL");
		btnExport.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnExport.setBackground(new Color(0, 255, 0));
		btnExport.setForeground(new Color(0, 0, 0));
		btnExport.setBounds(715, 594, 158, 30);
		btnExport.addActionListener(e -> exportToExcel());
		add(btnExport);

		JButton btnTTGV = new JButton("THÔNG TIN CHI TIẾT");
		btnTTGV.setForeground(new Color(255, 255, 255));
		btnTTGV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTTGV.setBackground(new Color(50, 150, 255));
		btnTTGV.setBounds(47, 165, 210, 30);
		btnTTGV.setBorder(new LineBorder(Color.WHITE, 1));
		add(btnTTGV);

		// Kiểm tra tải hình ảnh icon
		ImageIcon icon = null;
		icon = new ImageIcon(getClass().getResource("/Icon/find.png"));
		Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledImage);

		JButton btnTimKiem = new JButton("Tìm Kiếm", icon);
		btnTimKiem.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTimKiem.setForeground(Color.BLACK);
		btnTimKiem.setBounds(610, 75, 131, 40);
		btnTimKiem.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnTimKiem.setVerticalTextPosition(SwingConstants.CENTER);
		add(btnTimKiem);

		textField = new JTextField();
		textField.setBounds(269, 75, 321, 40);
		add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Mã Giảng Viên:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(140, 75, 117, 40);
		add(lblNewLabel_1);

		// Bảng danh sách giảng viên
		String[] columnNames = { "Mã GV", "Họ Tên", "Email", "Số Điện Thoại", "Môn Giảng Dạy", "Mã Môn" };
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

		btnTimKiem.addActionListener(e -> timKiemGiangVien());
        btnDelete.addActionListener(e -> xoaGiangVien());
        btnAdd.addActionListener(e -> Add());
        btnTTGV.addActionListener(e -> showLecturerDetails());
        btnExport.addActionListener(e -> handleExportToExcel());

		// Tải dữ liệu từ database
		loadTableData();
	}
	
    // Hàm xử lý sự kiện thêm
    private void Add() {
        GiangVien giangVienPanel = new GiangVien();
        giangVienPanel.setBounds(0, 0, 895, 652);
        currentPanel.removeAll();
        currentPanel.add(giangVienPanel);
        currentPanel.revalidate();
        currentPanel.repaint();
    }

    // Hàm xử lý sự kiện xuất Excel
    private void handleExportToExcel() {
        exportToExcel();
    }

	private void loadTableData() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			stmt = conn.createStatement();
			String sql = "SELECT ma_gv, ho_ten, email, so_dien_thoai, mon_giang_day, ma_mon FROM giang_vien";
			rs = stmt.executeQuery(sql);

			// Xóa dữ liệu cũ trong bảng
			tableModel.setRowCount(0);

			// Thêm dữ liệu từ ResultSet vào bảng
			while (rs.next()) {
				Object[] row = { rs.getString("ma_gv"), rs.getString("ho_ten"), rs.getString("email"),
						rs.getString("so_dien_thoai"), rs.getString("mon_giang_day"), rs.getString("ma_mon") };
				tableModel.addRow(row);
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//tim kiem giang vien theo mã GV
	private void timKiemGiangVien() {
		String maGV = textField.getText().trim();
		if (maGV.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mã giảng viên!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			loadTableData(); // Tải lại toàn bộ dữ liệu nếu không nhập mã
			return;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sql = "SELECT ma_gv, ho_ten, email, so_dien_thoai, mon_giang_day, ma_mon FROM giang_vien WHERE ma_gv = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maGV);
			rs = pstmt.executeQuery();

			// Xóa dữ liệu cũ trong bảng
			tableModel.setRowCount(0);

			// Thêm dữ liệu tìm kiếm vào bảng
			if (rs.next()) {
				Object[] row = { rs.getString("ma_gv"), rs.getString("ho_ten"), rs.getString("email"),
						rs.getString("so_dien_thoai"), rs.getString("mon_giang_day"), rs.getString("ma_mon") };
				tableModel.addRow(row);
			} else {
				JOptionPane.showMessageDialog(this, "Không tìm thấy giảng viên với mã: " + maGV, "Thông báo",
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void xoaGiangVien() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một giảng viên để xóa!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String maGV = tableModel.getValueAt(selectedRow, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa giảng viên với mã: " + maGV + "?",
				"Xác nhận xóa", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				String sql = "DELETE FROM giang_vien WHERE ma_gv = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, maGV);
				int rowsAffected = pstmt.executeUpdate();

				if (rowsAffected > 0) {
					JOptionPane.showMessageDialog(this, "Xóa giảng viên thành công!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
					loadTableData(); // Tải lại dữ liệu sau khi xóa
				} else {
					JOptionPane.showMessageDialog(this, "Không tìm thấy giảng viên để xóa!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Lỗi khi xóa giảng viên: " + e.getMessage(), "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Hiển thị chi tiết thông tin giảng viên khi chọn từ bảng
	private void showLecturerDetails() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một giảng viên để xem thông tin!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Lấy dữ liệu từ hàng được chọn
		String maGV = tableModel.getValueAt(selectedRow, 0).toString();
		String hoTen = tableModel.getValueAt(selectedRow, 1).toString();
		String email = tableModel.getValueAt(selectedRow, 2).toString();
		String soDienThoai = tableModel.getValueAt(selectedRow, 3).toString();
		String monGiangDay = tableModel.getValueAt(selectedRow, 4).toString();
		String maMon = tableModel.getValueAt(selectedRow, 5).toString();

		// Tạo và hiển thị frame ThongTinGiangVien
		ThongTinGiangVien thongTinFrame = new ThongTinGiangVien(hoTen, maGV, email, soDienThoai, monGiangDay, maMon);
		thongTinFrame.setVisible(true);
	}

	//xuát excel
	private void exportToExcel() {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DanhSachGiangVien");

		Row headerRow = sheet.createRow(0);
		String[] columns = { "Mã GV", "Họ Tên", "Email", "Số Điện Thoại", "Môn Giảng Dạy", "Mã Môn" };
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
		fileChooser.setSelectedFile(new java.io.File("DanhSachGiangVien.xlsx"));
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
	// Phương thức đếm số lượng giảng viên
    public int getTeacherCount() {
    	// Truy vấn bảng giang_vien để đếm tổng số giảng viên
        int count = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM giang_vien")) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đếm giảng viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return count;
    }
}