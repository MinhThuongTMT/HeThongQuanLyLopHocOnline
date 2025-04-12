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
import java.text.SimpleDateFormat;
import java.util.Date;

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
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
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
		String[] columnNames = { "ID", "Bài tập", "Giảng viên", "Môn học", "Hạn nộp" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25); // Chiều cao mỗi dòng
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
					loadDataFromDatabase(""); // Hiển thị tất cả nếu không chọn môn
				}
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

				// Lấy ID từ cột đầu tiên
				int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
				int confirm = JOptionPane.showConfirmDialog(QuanLyBaiTap.this, "Bạn có chắc chắn muốn xóa bài tập này?",
						"Xác nhận xóa", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					try {
						deleteFromDatabase(id);
						loadDataFromDatabase(""); // Tải lại dữ liệu sau khi xóa
						JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Xóa bài tập thành công!", "Thành công",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Lỗi khi xóa bài tập: " + ex.getMessage(),
								"Lỗi", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// Sự kiện nút Sửa
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Vui lòng chọn một bài tập để sửa!", "Cảnh báo",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				// Lấy thông tin từ dòng được chọn
				int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
				String tieuDe = tableModel.getValueAt(selectedRow, 1).toString();
				String tenGV = tableModel.getValueAt(selectedRow, 2).toString();
				String monHoc = tableModel.getValueAt(selectedRow, 3).toString();
				String hanNopStr = tableModel.getValueAt(selectedRow, 4).toString();

				// Lấy nội dung bài tập từ database
				String noiDung = getNoiDungFromDatabase(id);

				// Chuyển hạn nộp từ String sang Date
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
				Date hanNop = null;
				try {
					hanNop = sdf.parse(hanNopStr);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Lỗi định dạng hạn nộp: " + ex.getMessage(), "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Mở form GiaoBaiTap để chỉnh sửa
				GiaoBaiTap editForm = new GiaoBaiTap(tenGV, monHoc);
				editForm.setTitle("Sửa Bài Tập");
				editForm.setData(id, tieuDe, noiDung, hanNop); // Truyền dữ liệu vào form
				editForm.setVisible(true);

				// Tải lại dữ liệu sau khi sửa
				editForm.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosed(java.awt.event.WindowEvent windowEvent) {
						loadDataFromDatabase(""); // Tải lại dữ liệu sau khi đóng form
					}
				});
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
		tableModel.setRowCount(0); // Xóa dữ liệu cũ trong JTable
		String sql;
		if (monHocFilter.isEmpty()) {
			sql = "SELECT * FROM baitap"; // Lấy tất cả bài tập
		} else {
			sql = "SELECT * FROM baitap WHERE mon_hoc = ?"; // Lấy bài tập theo môn học
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			if (!monHocFilter.isEmpty()) {
				pstmt.setString(1, monHocFilter); // Gán giá trị môn học vào truy vấn
			}

			ResultSet rs = pstmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

			while (rs.next()) {
				int id = rs.getInt("id");
				String tieuDe = rs.getString("tieu_de");
				String tenGV = rs.getString("ten_gv");
				String monHoc = rs.getString("mon_hoc");
				String hanNop = sdf.format(rs.getTimestamp("han_nop"));

				// Thêm dòng dữ liệu vào JTable
				tableModel.addRow(new Object[] { id, tieuDe, tenGV, monHoc, hanNop });
			}

			if (tableModel.getRowCount() == 0) {
				tableModel.addRow(new Object[] { "", "Không tìm thấy bài tập nào.", "", "", "" });
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Phương thức xóa bài tập từ database
	private void deleteFromDatabase(int id) throws SQLException {
		String sql = "DELETE FROM baitap WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}

	// Phương thức lấy nội dung bài tập từ database
	private String getNoiDungFromDatabase(int id) {
		String sql = "SELECT noi_dung FROM baitap WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("noi_dung");
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Lỗi khi lấy nội dung bài tập: " + ex.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
		return "";
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

				// Tạo hàng tiêu đề
				Row headerRow = sheet.createRow(0);
				String[] columns = { "ID", "Bài tập", "Giảng viên", "Môn học", "Hạn nộp" };
				for (int i = 0; i < columns.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(columns[i]);
					CellStyle headerStyle = workbook.createCellStyle();
					org.apache.poi.ss.usermodel.Font font = workbook.createFont();
					font.setBold(true);
					headerStyle.setFont(font);
					cell.setCellStyle(headerStyle);
				}

				// Thêm dữ liệu từ JTable vào Excel
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

				// Ghi file Excel
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