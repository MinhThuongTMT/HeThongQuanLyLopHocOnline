package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QuanLySinhVien extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField txtMaSV;
	private JTextField txtTenSV;
	private JTextField txtNgaySinh;
	private JTextField txtGioiTinh;
	private JTextField txtLop;
	private JTextField txtChuyenNganh;
	private JTextField txtEmail;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuanLySinhVien frame = new QuanLySinhVien();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public QuanLySinhVien() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 121));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblTitle = new JLabel("QUẢN LÝ SINH VIÊN");
		lblTitle.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 25));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(300, 10, 300, 40);
		contentPane.add(lblTitle);

		JLabel lblMaSV = new JLabel("Mã Sinh Viên:");
		lblMaSV.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));
		lblMaSV.setForeground(Color.WHITE);
		lblMaSV.setBounds(50, 60, 100, 25);
		contentPane.add(lblMaSV);

		txtMaSV = new JTextField();
		txtMaSV.setBounds(150, 60, 200, 25);
		contentPane.add(txtMaSV);

		JLabel lblTenSV = new JLabel("Tên Sinh Viên:");
		lblTenSV.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));
		lblTenSV.setForeground(Color.WHITE);
		lblTenSV.setBounds(50, 95, 100, 25);
		contentPane.add(lblTenSV);

		txtTenSV = new JTextField();
		txtTenSV.setBounds(150, 95, 200, 25);
		contentPane.add(txtTenSV);

		JLabel lblNgaySinh = new JLabel("Ngày Sinh:");
		lblNgaySinh.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));
		lblNgaySinh.setForeground(Color.WHITE);
		lblNgaySinh.setBounds(50, 130, 100, 25);
		contentPane.add(lblNgaySinh);

		txtNgaySinh = new JTextField();
		txtNgaySinh.setBounds(150, 130, 200, 25);
		contentPane.add(txtNgaySinh);

		JLabel lblGioiTinh = new JLabel("Giới Tính:");
		lblGioiTinh.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));
		lblGioiTinh.setForeground(Color.WHITE);
		lblGioiTinh.setBounds(400, 60, 100, 25);
		contentPane.add(lblGioiTinh);

		txtGioiTinh = new JTextField();
		txtGioiTinh.setBounds(500, 60, 200, 25);
		contentPane.add(txtGioiTinh);

		JLabel lblLop = new JLabel("Lớp:");
		lblLop.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));
		lblLop.setForeground(Color.WHITE);
		lblLop.setBounds(400, 95, 100, 25);
		contentPane.add(lblLop);

		txtLop = new JTextField();
		txtLop.setBounds(500, 95, 200, 25);
		contentPane.add(txtLop);

		JLabel lblChuyenNganh = new JLabel("Chuyên Ngành:");
		lblChuyenNganh.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));
		lblChuyenNganh.setForeground(Color.WHITE);
		lblChuyenNganh.setBounds(400, 130, 100, 25);
		contentPane.add(lblChuyenNganh);

		txtChuyenNganh = new JTextField();
		txtChuyenNganh.setBounds(500, 130, 200, 25);
		contentPane.add(txtChuyenNganh);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setBounds(50, 165, 100, 25);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(150, 165, 200, 25);
		contentPane.add(txtEmail);

		JButton btnAdd = new JButton("THÊM");
		btnAdd.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 14));
		btnAdd.setBackground(new Color(255, 204, 0));
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setBounds(750, 60, 100, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewStudent();
			}
		});
		contentPane.add(btnAdd);

		JButton btnEdit = new JButton("SỬA");
		btnEdit.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 14));
		btnEdit.setBackground(new Color(255, 204, 0));
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setBounds(750, 100, 100, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editStudent();
			}
		});
		contentPane.add(btnEdit);

		JButton btnDelete = new JButton("XÓA");
		btnDelete.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 14));
		btnDelete.setBackground(new Color(255, 204, 0));
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBounds(750, 140, 100, 30);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteStudent();
			}
		});
		contentPane.add(btnDelete);

		JButton btnExport = new JButton("XUẤT EXCEL");
		btnExport.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 14));
		btnExport.setBackground(new Color(0, 255, 0));
		btnExport.setForeground(Color.WHITE);
		btnExport.setBounds(692, 180, 158, 30);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportToExcel();
			}
		});
		contentPane.add(btnExport);

		String[] columnNames = { "Mã SV", "Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Chuyên Ngành", "Email" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);

		addSampleData();

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					txtMaSV.setText(tableModel.getValueAt(selectedRow, 0).toString());
					txtTenSV.setText(tableModel.getValueAt(selectedRow, 1).toString());
					txtNgaySinh.setText(tableModel.getValueAt(selectedRow, 2).toString());
					txtGioiTinh.setText(tableModel.getValueAt(selectedRow, 3).toString());
					txtLop.setText(tableModel.getValueAt(selectedRow, 4).toString());
					txtChuyenNganh.setText(tableModel.getValueAt(selectedRow, 5).toString());
					txtEmail.setText(tableModel.getValueAt(selectedRow, 6).toString());
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 220, 800, 400);
		contentPane.add(scrollPane);
	}

	private void addSampleData() {
		Object[] row1 = { "SV001", "Nguyễn Văn A", "01/01/2000", "Nam", "Lớp 1", "CNTT", "nguyenvana@edu.vn" };
		Object[] row2 = { "SV002", "Trần Thị B", "02/02/2000", "Nữ", "Lớp 2", "Kinh Tế", "tranthib@edu.vn" };
		Object[] row3 = { "SV003", "Lê Văn C", "03/03/2000", "Nam", "Lớp 3", "Marketing", "levanc@edu.vn" };
		tableModel.addRow(row1);
		tableModel.addRow(row2);
		tableModel.addRow(row3);
	}

	private void addNewStudent() {
		String maSV = txtMaSV.getText();
		String tenSV = txtTenSV.getText();
		String ngaySinh = txtNgaySinh.getText();
		String gioiTinh = txtGioiTinh.getText();
		String lop = txtLop.getText();
		String chuyenNganh = txtChuyenNganh.getText();
		String email = txtEmail.getText();

		if (maSV.isEmpty() || tenSV.isEmpty() || ngaySinh.isEmpty() || gioiTinh.isEmpty() || lop.isEmpty()
				|| chuyenNganh.isEmpty() || email.isEmpty()) {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
			return;
		}

		Object[] newRow = { maSV, tenSV, ngaySinh, gioiTinh, lop, chuyenNganh, email };
		tableModel.addRow(newRow);
		clearFields();
	}

	private void editStudent() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			String maSV = txtMaSV.getText();
			String tenSV = txtTenSV.getText();
			String ngaySinh = txtNgaySinh.getText();
			String gioiTinh = txtGioiTinh.getText();
			String lop = txtLop.getText();
			String chuyenNganh = txtChuyenNganh.getText();
			String email = txtEmail.getText();

			if (maSV.isEmpty() || tenSV.isEmpty() || ngaySinh.isEmpty() || gioiTinh.isEmpty() || lop.isEmpty()
					|| chuyenNganh.isEmpty() || email.isEmpty()) {
				javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
				return;
			}

			tableModel.setValueAt(maSV, selectedRow, 0);
			tableModel.setValueAt(tenSV, selectedRow, 1);
			tableModel.setValueAt(ngaySinh, selectedRow, 2);
			tableModel.setValueAt(gioiTinh, selectedRow, 3);
			tableModel.setValueAt(lop, selectedRow, 4);
			tableModel.setValueAt(chuyenNganh, selectedRow, 5);
			tableModel.setValueAt(email, selectedRow, 6);
			javax.swing.JOptionPane.showMessageDialog(this, "Đã chỉnh sửa sinh viên!");
			clearFields();
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên để sửa!");
		}
	}

	private void deleteStudent() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sinh viên này?",
					"Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);
			if (confirm == javax.swing.JOptionPane.YES_OPTION) {
				tableModel.removeRow(selectedRow);
				clearFields();
			}
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên để xóa!");
		}
	}

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
				Sheet sheet = workbook.createSheet("Danh sách sinh viên");

				Row headerRow = sheet.createRow(0);
				String[] columns = { "Mã SV", "Tên", "Ngày Sinh", "Giới Tính", "Lớp", "Chuyên Ngành", "Email" };
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
					javax.swing.JOptionPane.showMessageDialog(this, "Xuất file Excel thành công tại: " + filePath);
				}

			} catch (IOException ex) {
				javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi",
						javax.swing.JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}

	private void clearFields() {
		txtMaSV.setText("");
		txtTenSV.setText("");
		txtNgaySinh.setText("");
		txtGioiTinh.setText("");
		txtLop.setText("");
		txtChuyenNganh.setText("");
		txtEmail.setText("");
	}
}
