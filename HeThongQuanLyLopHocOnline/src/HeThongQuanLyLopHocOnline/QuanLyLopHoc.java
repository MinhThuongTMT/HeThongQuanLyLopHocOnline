package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class QuanLyLopHoc extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel;

	public QuanLyLopHoc() {
		setBackground(new Color(0, 0, 121)); // Màu nền đồng bộ với TrangChu
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		// Tiêu đề
		JLabel lblTitle = new JLabel("QUẢN LÝ LỚP HỌC");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(300, 20, 300, 40);
		add(lblTitle);

		// Bảng danh sách lớp học
		String[] columnNames = { "Mã Lớp", "Tên Lớp", "Môn Học", "Giảng Viên", "Sĩ Số" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);

		// Thêm dữ liệu mẫu
		addSampleData();

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 80, 795, 400);
		add(scrollPane);

		// Nút Thêm lớp học
		JButton btnAdd = new JButton("Thêm Lớp Học");
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAdd.setBackground(new Color(255, 204, 0));
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setBounds(50, 500, 150, 40);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewClass();
			}
		});
		add(btnAdd);

		// Nút Sửa lớp học
		JButton btnEdit = new JButton("Sửa Lớp Học");
		btnEdit.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnEdit.setBackground(new Color(255, 204, 0));
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setBounds(220, 500, 150, 40);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editClass();
			}
		});
		add(btnEdit);

		// Nút Xóa lớp học
		JButton btnDelete = new JButton("Xóa Lớp Học");
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDelete.setBackground(new Color(255, 204, 0));
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBounds(390, 500, 150, 40);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteClass();
			}
		});
		add(btnDelete);

		// Nút Xem chi tiết
		JButton btnDetails = new JButton("Xem Chi Tiết");
		btnDetails.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDetails.setBackground(new Color(0, 204, 0)); // Màu xanh để nổi bật
		btnDetails.setForeground(Color.WHITE);
		btnDetails.setBounds(695, 500, 150, 40);
		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewClassDetails();
			}
		});
		add(btnDetails);
	}

	// Thêm dữ liệu mẫu vào bảng
	private void addSampleData() {
		Object[] row1 = { "LH001", "Lớp Toán 1", "Toán", "Nguyễn Văn A", "30" };
		Object[] row2 = { "LH002", "Lớp Văn 1", "Văn", "Trần Thị B", "25" };
		Object[] row3 = { "LH003", "Lớp Anh 1", "Anh", "Lê Văn C", "28" };
		tableModel.addRow(row1);
		tableModel.addRow(row2);
		tableModel.addRow(row3);
	}

	// Logic thêm lớp học
	private void addNewClass() {
		Object[] newRow = { "LH00" + (tableModel.getRowCount() + 1), "Lớp mới", "Môn học", "Giảng viên", "0" };
		tableModel.addRow(newRow);
	}

	// Logic sửa lớp học
	private void editClass() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			tableModel.setValueAt("Đã chỉnh sửa", selectedRow, 1); // Ví dụ sửa tên lớp
			javax.swing.JOptionPane.showMessageDialog(this, "Đã chỉnh sửa lớp học!");
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học để sửa!");
		}
	}

	// Logic xóa lớp học
	private void deleteClass() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa lớp học này?",
					"Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);
			if (confirm == javax.swing.JOptionPane.YES_OPTION) {
				tableModel.removeRow(selectedRow);
			}
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học để xóa!");
		}
	}

	// Logic xem chi tiết lớp học
	private void viewClassDetails() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			String classInfo = "Mã Lớp: " + tableModel.getValueAt(selectedRow, 0) + "\n" + "Tên Lớp: "
					+ tableModel.getValueAt(selectedRow, 1) + "\n" + "Môn Học: " + tableModel.getValueAt(selectedRow, 2)
					+ "\n" + "Giảng Viên: " + tableModel.getValueAt(selectedRow, 3) + "\n" + "Sĩ Số: "
					+ tableModel.getValueAt(selectedRow, 4);
			javax.swing.JOptionPane.showMessageDialog(this, classInfo, "Chi Tiết Lớp Học",
					javax.swing.JOptionPane.INFORMATION_MESSAGE);
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học để xem chi tiết!");
		}
	}

	// Để test riêng class này
	public static void main(String[] args) {
		javax.swing.JFrame frame = new javax.swing.JFrame("Quản Lý Lớp Học");
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 900, 600);
		frame.setContentPane(new QuanLyLopHoc());
		frame.setVisible(true);
	}
}