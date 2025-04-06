package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class QuanLyGiangVien extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField txtMaGV;
	private JTextField txtTenGV;
	private JTextField txtMonDay;
	private JTextField txtSoLop;
	private JTextField txtEmail;

	public QuanLyGiangVien() {
		setBackground(new Color(0, 0, 121)); // Màu nền đồng bộ với TrangChu
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		// Tiêu đề
		JLabel lblTitle = new JLabel("QUẢN LÝ GIẢNG VIÊN");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(300, 10, 300, 40);
		add(lblTitle);

		// Khu vực nhập liệu
		JLabel lblMaGV = new JLabel("Mã Giảng Viên:");
		lblMaGV.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMaGV.setForeground(Color.WHITE);
		lblMaGV.setBounds(50, 60, 100, 25);
		add(lblMaGV);

		txtMaGV = new JTextField();
		txtMaGV.setBounds(150, 60, 200, 25);
		add(txtMaGV);

		JLabel lblTenGV = new JLabel("Tên Giảng Viên:");
		lblTenGV.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblTenGV.setForeground(Color.WHITE);
		lblTenGV.setBounds(50, 95, 100, 25);
		add(lblTenGV);

		txtTenGV = new JTextField();
		txtTenGV.setBounds(150, 95, 200, 25);
		add(txtTenGV);

		JLabel lblMonDay = new JLabel("Môn Dạy:");
		lblMonDay.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMonDay.setForeground(Color.WHITE);
		lblMonDay.setBounds(50, 130, 100, 25);
		add(lblMonDay);

		txtMonDay = new JTextField();
		txtMonDay.setBounds(150, 130, 200, 25);
		add(txtMonDay);

		JLabel lblSoLop = new JLabel("Số Lớp:");
		lblSoLop.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblSoLop.setForeground(Color.WHITE);
		lblSoLop.setBounds(400, 60, 100, 25);
		add(lblSoLop);

		txtSoLop = new JTextField();
		txtSoLop.setBounds(500, 60, 200, 25);
		add(txtSoLop);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setBounds(400, 95, 100, 25);
		add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(500, 95, 200, 25);
		add(txtEmail);

		// Khu vực nút chức năng
		JButton btnAdd = new JButton("THÊM");
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAdd.setBackground(new Color(255, 204, 0));
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setBounds(750, 60, 100, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewLecturer();
			}
		});
		add(btnAdd);

		JButton btnEdit = new JButton("SỬA");
		btnEdit.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnEdit.setBackground(new Color(255, 204, 0));
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setBounds(750, 100, 100, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editLecturer();
			}
		});
		add(btnEdit);

		JButton btnDelete = new JButton("XÓA");
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDelete.setBackground(new Color(255, 204, 0));
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBounds(750, 140, 100, 30);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteLecturer();
			}
		});
		add(btnDelete);

		JButton btnExport = new JButton("XUẤT EXCEL");
		btnExport.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnExport.setBackground(new Color(0, 255, 0)); // Màu hồng giống trong hình
		btnExport.setForeground(Color.WHITE);
		btnExport.setBounds(692, 180, 158, 30);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportToExcel();
			}
		});
		add(btnExport);

		// Bảng danh sách giảng viên
		String[] columnNames = { "Mã Giảng Viên", "Tên Giảng Viên", "Môn Dạy", "Số Lớp", "Email" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);

		// Thêm dữ liệu mẫu
		addSampleData();

		// Khi nhấp vào bảng, điền thông tin vào các trường nhập liệu
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					txtMaGV.setText(tableModel.getValueAt(selectedRow, 0).toString());
					txtTenGV.setText(tableModel.getValueAt(selectedRow, 1).toString());
					txtMonDay.setText(tableModel.getValueAt(selectedRow, 2).toString());
					txtSoLop.setText(tableModel.getValueAt(selectedRow, 3).toString());
					txtEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 220, 800, 400);
		add(scrollPane);

		// Sửa phần nút THÔNG TIN CHI TIẾT
		JButton btnTTGV = new JButton("THÔNG TIN CHI TIẾT");
		btnTTGV.setForeground(Color.BLACK);
		btnTTGV.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTTGV.setBackground(Color.GREEN);
		btnTTGV.setBounds(50, 180, 210, 30);
		add(btnTTGV); // Sửa từ contentPane.add thành add

		// Thêm sự kiện click cho nút
		btnTTGV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Tạo và hiển thị frame ThongTinGiangVien
				ThongTinGiangVien thongTinFrame = new ThongTinGiangVien();
				thongTinFrame.setVisible(true);
			}
		});
	}

	// Thêm dữ liệu mẫu
	private void addSampleData() {
		Object[] row1 = { "GV001", "Nguyễn Văn A", "Toán", "3", "nguyenvana@edu.vn" };
		Object[] row2 = { "GV002", "Trần Thị B", "Văn", "2", "tranthib@edu.vn" };
		Object[] row3 = { "GV003", "Lê Văn C", "Anh", "4", "levanc@edu.vn" };
		tableModel.addRow(row1);
		tableModel.addRow(row2);
		tableModel.addRow(row3);
	}

	// Logic thêm giảng viên
	private void addNewLecturer() {
		String maGV = txtMaGV.getText();
		String tenGV = txtTenGV.getText();
		String monDay = txtMonDay.getText();
		String soLop = txtSoLop.getText();
		String email = txtEmail.getText();

		if (maGV.isEmpty() || tenGV.isEmpty() || monDay.isEmpty() || soLop.isEmpty() || email.isEmpty()) {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
			return;
		}

		Object[] newRow = { maGV, tenGV, monDay, soLop, email };
		tableModel.addRow(newRow);
		clearFields();
	}

	// Logic sửa giảng viên
	private void editLecturer() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			String maGV = txtMaGV.getText();
			String tenGV = txtTenGV.getText();
			String monDay = txtMonDay.getText();
			String soLop = txtSoLop.getText();
			String email = txtEmail.getText();

			if (maGV.isEmpty() || tenGV.isEmpty() || monDay.isEmpty() || soLop.isEmpty() || email.isEmpty()) {
				javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
				return;
			}

			tableModel.setValueAt(maGV, selectedRow, 0);
			tableModel.setValueAt(tenGV, selectedRow, 1);
			tableModel.setValueAt(monDay, selectedRow, 2);
			tableModel.setValueAt(soLop, selectedRow, 3);
			tableModel.setValueAt(email, selectedRow, 4);
			javax.swing.JOptionPane.showMessageDialog(this, "Đã chỉnh sửa giảng viên!");
			clearFields();
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn giảng viên để sửa!");
		}
	}

	// Logic xóa giảng viên
	private void deleteLecturer() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa giảng viên này?",
					"Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);
			if (confirm == javax.swing.JOptionPane.YES_OPTION) {
				tableModel.removeRow(selectedRow);
				clearFields();
			}
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn giảng viên để xóa!");
		}
	}

	// Logic xuất Excel (chỉ là placeholder)
	private void exportToExcel() {
		javax.swing.JOptionPane.showMessageDialog(this, "Chức năng xuất Excel đang được phát triển!");
	}

	// Xóa các trường nhập liệu
	private void clearFields() {
		txtMaGV.setText("");
		txtTenGV.setText("");
		txtMonDay.setText("");
		txtSoLop.setText("");
		txtEmail.setText("");
	}

	// Để test riêng class này
	public static void main(String[] args) {
		javax.swing.JFrame frame = new javax.swing.JFrame("Quản Lý Giảng Viên");
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 900, 700);
		frame.setContentPane(new QuanLyGiangVien());
		frame.setVisible(true);
	}
}