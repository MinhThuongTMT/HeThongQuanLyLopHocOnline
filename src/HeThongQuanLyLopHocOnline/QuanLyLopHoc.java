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
import java.text.SimpleDateFormat;
import java.util.Date;

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

	public QuanLyLopHoc() {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);

		// Tiêu đề
		JLabel lblNewLabel = new JLabel("QUẢN LÝ LỚP HỌC");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(334, 10, 253, 40);
		add(lblNewLabel);

		// Tạo JPopupMenu cho nút Quản Lý
		JPopupMenu manageLopMenu = new JPopupMenu("Danh Sách Lớp Học");
		manageLopMenu.setFont(new Font("Times New Roman", Font.BOLD, 14));
		manageLopMenu.setBackground(new Color(255, 204, 0));
		manageLopMenu.setForeground(Color.BLACK);
		JMenuItem lop1 = new JMenuItem("D21CQVT01-N");
		JMenuItem lop2 = new JMenuItem("D21CQVTHI01-N");
		JMenuItem lop3 = new JMenuItem("D21CQVTVT01-N");
		manageLopMenu.add(lop1);
		manageLopMenu.add(lop2);
		manageLopMenu.add(lop3);

		// Nút Danh Sách Lớp Học
		JButton Lop_bnt = new JButton("Danh Sách Lớp Học");
		Lop_bnt.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Lop_bnt.setBackground(new Color(255, 215, 0));
		Lop_bnt.setForeground(Color.BLACK);
		Lop_bnt.setBounds(51, 61, 187, 40);
		add(Lop_bnt);

		// Tạo JPopupMenu cho nút Danh Sách Môn Học
		JPopupMenu subjectsMenu = new JPopupMenu("Danh Sách Môn Học");
		subjectsMenu.setFont(new Font("Times New Roman", Font.BOLD, 15));
		subjectsMenu.setBackground(new Color(255, 204, 0));
		subjectsMenu.setForeground(Color.BLACK);
		JMenuItem mon1 = new JMenuItem("Toán Cao Cấp");
		JMenuItem mon2 = new JMenuItem("Lập Trình Java");
		JMenuItem mon3 = new JMenuItem("Cơ Sở Dữ Liệu");
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

		// Danh sách điểm danh
		JPopupMenu subjectsMenu1 = new JPopupMenu("Danh Sách Môn Học");
		subjectsMenu1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		subjectsMenu1.setBackground(new Color(255, 204, 0));
		subjectsMenu1.setForeground(Color.BLACK);
		JMenuItem DiemDanh1 = new JMenuItem("Toán Cao Cấp");
		JMenuItem DiemDanh2 = new JMenuItem("Lập Trình Java");
		JMenuItem DiemDanh3 = new JMenuItem("Cơ Sở Dữ Liệu");
		subjectsMenu1.add(DiemDanh1);
		subjectsMenu1.add(DiemDanh2);
		subjectsMenu1.add(DiemDanh3);

		JButton diemDanh_btn = new JButton("Danh Sách Điểm Danh");
		diemDanh_btn.setForeground(Color.BLACK);
		diemDanh_btn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		diemDanh_btn.setBackground(new Color(255, 215, 0));
		diemDanh_btn.setBounds(613, 61, 187, 40);
		add(diemDanh_btn);

		// Nút Xuất Excel
		JButton btnDetails = new JButton("Xuất Excel");
		btnDetails.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDetails.setBackground(new Color(0, 204, 0));
		btnDetails.setForeground(Color.WHITE);
		btnDetails.setBounds(714, 125, 150, 31);
		add(btnDetails);

		// Khởi tạo JTable
		String[] defaultColumns = { "Mã SV", "Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Môn học", "Email" };
		tableModel = new DefaultTableModel(defaultColumns, 0);
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
		add(HienThi);
		HienThi.setColumns(10);

		// Hàm xử lý màu khi nhấn nút
		ActionListener buttonColorHandler = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton clickedButton = (JButton) e.getSource();
				if (lastClickedButton != null && lastClickedButton != clickedButton) {
					if (lastClickedButton == btnDetails) {
						lastClickedButton.setBackground(new Color(0, 204, 0));
					} else {
						lastClickedButton.setBackground(new Color(255, 215, 0));
					}
				}
				if (clickedButton == btnDetails) {
					clickedButton.setBackground(new Color(0, 153, 0));
				} else {
					clickedButton.setBackground(new Color(255, 153, 0));
				}
				lastClickedButton = clickedButton;

				if (clickedButton == Lop_bnt) {
					manageLopMenu.show(Lop_bnt, 0, Lop_bnt.getHeight());
				} else if (clickedButton == Mon_bnt) {
					subjectsMenu.show(Mon_bnt, 0, Mon_bnt.getHeight());
				} else if (clickedButton == diemDanh_btn) {
					subjectsMenu1.show(diemDanh_btn, 0, diemDanh_btn.getHeight());
				}
			}
		};

		// Gán sự kiện cho các nút
		Lop_bnt.addActionListener(buttonColorHandler);
		Mon_bnt.addActionListener(buttonColorHandler);
		diemDanh_btn.addActionListener(buttonColorHandler);

		// Sự kiện nút Xuất Excel
		btnDetails.addActionListener(e -> {
			exportToExcel();
			if (lastClickedButton != null && lastClickedButton != btnDetails) {
				if (lastClickedButton == btnDetails) {
					lastClickedButton.setBackground(new Color(0, 204, 0));
				} else {
					lastClickedButton.setBackground(new Color(255, 215, 0));
				}
			}
			btnDetails.setBackground(new Color(0, 153, 0));
			lastClickedButton = btnDetails;
		});

		// Thêm sự kiện khi chọn lớp
		String[] allLops = { "D21CQVTVT-01", "D21CQVTHI-01", "D21CQVT-01N" };
		lop1.addActionListener(e -> {
			HienThi.setText("Danh Sách Lớp: D21CQVT-01N");
			loadStudentData(new String[] { "D21CQVT-01N" });
		});
		lop2.addActionListener(e -> {
			HienThi.setText("Danh Sách Lớp: D21CQVTHI-01");
			loadStudentData(new String[] { "D21CQVTHI-01" });
		});
		lop3.addActionListener(e -> {
			HienThi.setText("Danh Sách Lớp: D21CQVTVT-01");
			loadStudentData(new String[] { "D21CQVTVT-01" });
		});

		// Thêm sự kiện khi chọn tất cả lớp
		JMenuItem allLopsItem = new JMenuItem("Tất cả lớp");
		manageLopMenu.add(allLopsItem);
		allLopsItem.addActionListener(e -> {
			HienThi.setText("Danh Sách Tất Cả Lớp");
			loadStudentData(allLops);
		});

		// Thêm sự kiện khi chọn môn
		mon1.addActionListener(e -> {
			HienThi.setText("Danh Sách Lớp Môn: Toán Cao Cấp");
			JOptionPane.showMessageDialog(this, "Chức năng này chưa được triển khai.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		});
		mon2.addActionListener(e -> {
			HienThi.setText("Danh Sách Lớp Môn: Lập Trình Java");
			JOptionPane.showMessageDialog(this, "Chức năng này chưa được triển khai.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		});
		mon3.addActionListener(e -> {
			HienThi.setText("Danh Sách Lớp Môn: Cơ Sở Dữ Liệu");
			JOptionPane.showMessageDialog(this, "Chức năng này chưa được triển khai.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		});

		// Thêm sự kiện khi chọn môn điểm danh
		DiemDanh1.addActionListener(e -> {
			HienThi.setText("Danh Sách Điểm Danh Môn: Toán Cao Cấp");
			JOptionPane.showMessageDialog(this, "Chức năng này chưa được triển khai.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		});
		DiemDanh2.addActionListener(e -> {
			HienThi.setText("Danh Sách Điểm Danh Môn: Lập Trình Java");
			JOptionPane.showMessageDialog(this, "Chức năng này chưa được triển khai.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		});
		DiemDanh3.addActionListener(e -> {
			HienThi.setText("Danh Sách Điểm Danh Môn: Cơ Sở Dữ Liệu");
			JOptionPane.showMessageDialog(this, "Chức năng này chưa được triển khai.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		});
	}

	// Tải dữ liệu sinh viên theo danh sách lớp và hiển thị môn học từ bảng courses
	private void loadStudentData(String[] lopList) {
		String[] columnNames = { "Mã SV", "Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Môn học", "Email" };
		tableModel.setColumnIdentifiers(columnNames);
		tableModel.setRowCount(0);

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			System.out.println("Kết nối database thành công");

			// Tạo placeholder cho IN
			String placeholders = String.join(",", java.util.Collections.nCopies(lopList.length, "?"));
			String sql = """
					SELECT
					    s.mssv,
					    s.hoten,
					    s.ngaysinh,
					    s.gioitinh,
					    s.lop,
					    STRING_AGG(COALESCE(c.monhoc, ''), ', ') AS monhoc,
					    s.email
					FROM
					    students s
					LEFT JOIN
					    courses c ON s.mssv = c.mssv
					WHERE
					    s.lop IN (%s)
					GROUP BY
					    s.mssv,
					    s.hoten,
					    s.ngaysinh,
					    s.gioitinh,
					    s.lop,
					    s.email
					""".formatted(placeholders);

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				// Gán giá trị cho các placeholder
				for (int i = 0; i < lopList.length; i++) {
					pstmt.setString(i + 1, lopList[i]);
				}

				try (ResultSet rs = pstmt.executeQuery()) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					boolean hasData = false;

					while (rs.next()) {
						String mssv = rs.getString("mssv");
						String hoten = rs.getString("hoten");
						Date ngaysinhDate = rs.getDate("ngaysinh");
						String ngaysinh = ngaysinhDate != null ? sdf.format(ngaysinhDate) : "";
						String gioitinh = rs.getString("gioitinh");
						String lopHoc = rs.getString("lop");
						String monhoc = rs.getString("monhoc") != null ? rs.getString("monhoc") : "";
						String email = rs.getString("email") != null ? rs.getString("email") : "";

						System.out.println("Dữ liệu: " + mssv + ", " + hoten + ", " + monhoc);
						tableModel.addRow(new Object[] { mssv, hoten, ngaysinh, gioitinh, lopHoc, monhoc, email });
						hasData = true;
					}

					if (!hasData) {
						String lopDisplay = lopList.length == 1 ? lopList[0] : "các lớp " + String.join(", ", lopList);
						System.out.println("Không tìm thấy dữ liệu cho " + lopDisplay);
						JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên trong " + lopDisplay, "Thông báo",
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

	// Xuất dữ liệu bảng ra Excel
	private void exportToExcel() {
		if (tableModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Workbook workbook = new XSSFWorkbook();
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

		// Thêm dữ liệu từ bảng
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
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			sheet.autoSizeColumn(i);
		}

		// Hiển thị hộp thoại lưu file
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
		String fileName = HienThi.getText().isEmpty() ? "DanhSach" : HienThi.getText().replace(" ", "_");
		fileChooser.setSelectedFile(new java.io.File(fileName + ".xlsx"));
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
}
