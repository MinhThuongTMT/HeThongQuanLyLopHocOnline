package HeThongQuanLyLopHocOnline;

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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QuanLyBaiTap extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox<String> TimMon_comboBox;
	private JTable table;
	private DefaultTableModel tableModel;

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true&prepareThreshold=0";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public QuanLyBaiTap() {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);

		// Tiêu đề
		JLabel lblNewLabel = new JLabel("QUẢN LÝ BÀI TẬP");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(300, 10, 300, 40);
		add(lblNewLabel);

		// Nút Bài Tập Đã Nộp
		JButton DaNop_btn = new JButton("Bài Tập Đã Nộp");
		DaNop_btn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		DaNop_btn.setBackground(new Color(255, 204, 0));
		DaNop_btn.setForeground(Color.BLACK);
		DaNop_btn.setBounds(45, 566, 150, 40);
		add(DaNop_btn);

		// Nút Bài Tập Chưa Nộp
		JButton ChuaNop_btn = new JButton("Bài Tập Chưa Nộp");
		ChuaNop_btn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		ChuaNop_btn.setBackground(new Color(255, 204, 0));
		ChuaNop_btn.setForeground(Color.BLACK);
		ChuaNop_btn.setBounds(210, 566, 150, 40);
		add(ChuaNop_btn);

		// Nút Xuất Excel
		JButton btnXuat = new JButton("Xuất Excel");
		btnXuat.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnXuat.setBackground(new Color(0, 204, 0));
		btnXuat.setForeground(Color.WHITE);
		btnXuat.setBounds(691, 566, 150, 40);
		add(btnXuat);

		// Nút Tìm Kiếm
		ImageIcon icon = new ImageIcon("Icon/find.png"); // Đảm bảo đường dẫn đúng
		Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledImage);
		JButton btnTimKiem = new JButton("Tìm Kiếm", icon);
		btnTimKiem.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTimKiem.setForeground(Color.BLACK);
		btnTimKiem.setBounds(539, 72, 124, 40);
		btnTimKiem.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnTimKiem.setVerticalTextPosition(SwingConstants.CENTER);
		add(btnTimKiem);

		// ComboBox Tìm kiếm môn học
		TimMon_comboBox = new JComboBox<>();
		TimMon_comboBox.setModel(new DefaultComboBoxModel<>(new String[] { "", "Cảm Biến", "Java", "Android" }));
		TimMon_comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		TimMon_comboBox.setBounds(210, 72, 296, 40);
		add(TimMon_comboBox);

		// Tạo JTable với DefaultTableModel
		String[] columnNames = { "ID", "MSSV", "Tiêu đề", "Môn học", "Trạng thái", "Thời gian nộp", "Tên tệp" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);

		// Thêm JTable vào JScrollPane để hỗ trợ cuộn
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(28, 151, 841, 381);
		add(scrollPane);

		// Nút Sửa
		JButton btnEdit = new JButton("Sửa", null);
		btnEdit.setBackground(new Color(0, 64, 128));
		btnEdit.setVerticalTextPosition(SwingConstants.CENTER);
		btnEdit.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnEdit.setForeground(new Color(255, 255, 255));
		btnEdit.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnEdit.setBounds(714, 72, 67, 40);
		add(btnEdit);

		// Nút Xóa
		JButton btnDelete = new JButton("Xóa", null);
		btnDelete.setVerticalTextPosition(SwingConstants.CENTER);
		btnDelete.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnDelete.setForeground(new Color(255, 255, 255));
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDelete.setBackground(new Color(255, 0, 0));
		btnDelete.setBounds(802, 72, 67, 40);
		add(btnDelete);

		JLabel labelMonHoc = new JLabel("Môn học:");
		labelMonHoc.setForeground(Color.WHITE);
		labelMonHoc.setFont(new Font("Times New Roman", Font.BOLD, 15));
		labelMonHoc.setBounds(115, 72, 85, 40);
		add(labelMonHoc);

		// Tải dữ liệu ban đầu từ database
		loadDataFromDatabase("");

		// Sự kiện nút Tìm Kiếm
		btnTimKiem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String monHoc = (String) TimMon_comboBox.getSelectedItem();
				if (monHoc != null && !monHoc.isEmpty()) {
					loadDataFromDatabase(monHoc);
				} else {
					loadDataFromDatabase("");
				}
			}
		});

		// Sự kiện nút Bài Tập Đã Nộp
		DaNop_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadDataByStatus("ĐÃ NỘP", "NỘP MUỘN");
			}
		});

		// Sự kiện nút Bài Tập Chưa Nộp
		ChuaNop_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadDataChuaNop();
			}
		});

		// Sự kiện nút Xóa
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Vui lòng chọn một bài tập để xóa!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
				int confirm = JOptionPane.showConfirmDialog(QuanLyBaiTap.this, "Bạn có chắc chắn muốn xóa bài tập này?",
						"Xác nhận xóa", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					try {
						deleteFromDatabase(id);
						loadDataFromDatabase("");
						JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Xóa bài tập thành công!", "Thành công",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Lỗi khi xóa bài tập: " + ex.getMessage(),
								"Lỗi", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}
			}
		});

		// Sự kiện nút Sửa
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Chức năng sửa bài tập chưa được hỗ trợ!", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Sự kiện nút Xuất Excel
		btnXuat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportToExcel();
			}
		});
	}

	// Phương thức tải dữ liệu từ database
	private void loadDataFromDatabase(String monHocFilter) {
		tableModel.setRowCount(0);
		String sql = "SELECT id, mssv, monhoc, tieude, trangthai, thoigian_nop, ten_tep FROM Baitap";
		if (!monHocFilter.isEmpty()) {
			sql += " WHERE monhoc = ?";
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			pstmt = conn.prepareStatement(sql);

			if (!monHocFilter.isEmpty()) {
				pstmt.setString(1, monHocFilter);
			}

			rs = pstmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

			while (rs.next()) {
				int id = rs.getInt("id");
				String mssv = rs.getString("mssv");
				String monHoc = rs.getString("monhoc");
				String tieuDe = rs.getString("tieude");
				String trangThai = rs.getString("trangthai");
				Timestamp thoiGianNop = rs.getTimestamp("thoigian_nop");
				String tenTep = rs.getString("ten_tep");

				tableModel.addRow(new Object[] { id, mssv, tieuDe, monHoc, trangThai,
						thoiGianNop != null ? sdf.format(thoiGianNop) : "Chưa nộp", tenTep });
			}

			if (tableModel.getRowCount() == 0) {
				tableModel.addRow(new Object[] { "", "", "Không tìm thấy bài tập nào.", "", "", "", "" });
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database: " + ex.getMessage(), "Lỗi",
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
			} catch (SQLException closeEx) {
				closeEx.printStackTrace();
			}
		}
	}

	// Phương thức tải dữ liệu theo trạng thái (Đã Nộp hoặc Nộp Muộn)
	private void loadDataByStatus(String... statuses) {
		tableModel.setRowCount(0);
		String sql = "SELECT id, mssv, monhoc, tieude, trangthai, thoigian_nop, ten_tep FROM Baitap WHERE trangthai IN (";
		for (int i = 0; i < statuses.length; i++) {
			sql += "?";
			if (i < statuses.length - 1)
				sql += ",";
		}
		sql += ")";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < statuses.length; i++) {
				pstmt.setString(i + 1, statuses[i]);
			}

			rs = pstmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

			while (rs.next()) {
				int id = rs.getInt("id");
				String mssv = rs.getString("mssv");
				String monHoc = rs.getString("monhoc");
				String tieuDe = rs.getString("tieude");
				String trangThai = rs.getString("trangthai");
				Timestamp thoiGianNop = rs.getTimestamp("thoigian_nop");
				String tenTep = rs.getString("ten_tep");

				tableModel.addRow(new Object[] { id, mssv, tieuDe, monHoc, trangThai,
						thoiGianNop != null ? sdf.format(thoiGianNop) : "Chưa nộp", tenTep });
			}

			if (tableModel.getRowCount() == 0) {
				tableModel.addRow(new Object[] { "", "", "Không tìm thấy bài tập nào.", "", "", "", "" });
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database: " + ex.getMessage(), "Lỗi",
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
			} catch (SQLException closeEx) {
				closeEx.printStackTrace();
			}
		}
	}

	// Phương thức tải danh sách bài tập chưa nộp
	private void loadDataChuaNop() {
		tableModel.setRowCount(0);
		Connection conn = null;
		PreparedStatement pstmtStudents = null;
		PreparedStatement pstmtCheck = null;
		ResultSet rsStudents = null;
		ResultSet rsCheck = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sqlStudents = "SELECT mssv FROM students";
			pstmtStudents = conn.prepareStatement(sqlStudents);
			rsStudents = pstmtStudents.executeQuery();

			while (rsStudents.next()) {
				String mssv = rsStudents.getString("mssv");
				for (int i = 1; i < TimMon_comboBox.getItemCount(); i++) {
					String monHoc = TimMon_comboBox.getItemAt(i);
					String sqlCheck = "SELECT tieude FROM Baitap WHERE mssv = ? AND monhoc = ?";
					pstmtCheck = conn.prepareStatement(sqlCheck);
					pstmtCheck.setString(1, mssv);
					pstmtCheck.setString(2, monHoc);
					rsCheck = pstmtCheck.executeQuery();

					if (!rsCheck.next()) {
						tableModel.addRow(new Object[] { "", mssv, "Chưa nộp bài", monHoc, "CHƯA NỘP", "", "" });
					}
					rsCheck.close();
					pstmtCheck.close();
				}
			}

			if (tableModel.getRowCount() == 0) {
				tableModel.addRow(new Object[] { "", "", "Không tìm thấy bài tập chưa nộp.", "", "", "", "" });
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} finally {
			try {
				if (rsCheck != null)
					rsCheck.close();
				if (pstmtCheck != null)
					pstmtCheck.close();
				if (rsStudents != null)
					rsStudents.close();
				if (pstmtStudents != null)
					pstmtStudents.close();
				if (conn != null)
					conn.close();
			} catch (SQLException closeEx) {
				closeEx.printStackTrace();
			}
		}
	}

	// Phương thức xóa bài tập từ database
	private void deleteFromDatabase(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmtCheck = null;
		PreparedStatement pstmtDelete = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			String sqlCheck = "SELECT COUNT(*) FROM Baitap WHERE id = ?";
			pstmtCheck = conn.prepareStatement(sqlCheck);
			pstmtCheck.setInt(1, id);
			rs = pstmtCheck.executeQuery();
			if (rs.next() && rs.getInt(1) == 0) {
				throw new SQLException("ID bài tập không tồn tại!");
			}

			String sqlDelete = "DELETE FROM Baitap WHERE id = ?";
			pstmtDelete = conn.prepareStatement(sqlDelete);
			pstmtDelete.setInt(1, id);
			pstmtDelete.executeUpdate();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmtCheck != null)
					pstmtCheck.close();
				if (pstmtDelete != null)
					pstmtDelete.close();
				if (conn != null)
					conn.close();
			} catch (SQLException closeEx) {
				closeEx.printStackTrace();
			}
		}
	}

	// Phương thức xuất dữ liệu ra file Excel
	private void exportToExcel() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

		int userSelection = fileChooser.showSaveDialog(this);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
			if (!filePath.toLowerCase().endsWith(".xlsx")) {
				filePath += ".xlsx";
			}

			try (Workbook workbook = new XSSFWorkbook()) {
				Sheet sheet = workbook.createSheet("Danh sách bài tập");

				Row headerRow = sheet.createRow(0);
				String[] columns = { "ID", "MSSV", "Tiêu đề", "Môn học", "Trạng thái", "Thời gian nộp", "Tên tệp" };
				for (int i = 0; i < columns.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(columns[i]);
					CellStyle headerStyle = workbook.createCellStyle();
					org.apache.poi.ss.usermodel.Font font = workbook.createFont();
					font.setBold(true);
					headerStyle.setFont(font);
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

				try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
					workbook.write(fileOut);
					JOptionPane.showMessageDialog(this, "Xuất file Excel thành công tại: " + filePath, "Thành công",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}
}