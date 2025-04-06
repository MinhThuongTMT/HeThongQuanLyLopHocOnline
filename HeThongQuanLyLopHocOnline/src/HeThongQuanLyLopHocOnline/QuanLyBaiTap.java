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

public class QuanLyBaiTap extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel;

	public QuanLyBaiTap() {
		setBackground(new Color(0, 0, 121)); // Màu nền đồng bộ với TrangChu
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		// Tiêu đề
		JLabel lblTitle = new JLabel("QUẢN LÝ BÀI TẬP");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(300, 20, 300, 40);
		add(lblTitle);

		// Bảng danh sách bài tập
		String[] columnNames = { "Mã BT", "Tên Bài Tập", "Môn Học", "Hạn Nộp", "Trạng Thái" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(255, 204, 0));
		table.getTableHeader().setForeground(Color.BLACK);

		// Thêm dữ liệu mẫu (có thể thay bằng dữ liệu thực từ database)
		addSampleData();

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 80, 795, 400);
		add(scrollPane);

		// Nút Thêm bài tập
		JButton btnAdd = new JButton("Thêm Bài Tập");
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAdd.setBackground(new Color(255, 204, 0));
		btnAdd.setForeground(Color.BLACK);
		btnAdd.setBounds(50, 500, 150, 40);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Logic thêm bài tập (có thể mở form nhập thông tin)
				addNewAssignment();
			}
		});
		add(btnAdd);

		// Nút Sửa bài tập
		JButton btnEdit = new JButton("Sửa Bài Tập");
		btnEdit.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnEdit.setBackground(new Color(255, 204, 0));
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setBounds(220, 500, 150, 40);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Logic sửa bài tập
				editAssignment();
			}
		});
		add(btnEdit);

		// Nút Xóa bài tập
		JButton btnDelete = new JButton("Xóa Bài Tập");
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDelete.setBackground(new Color(255, 204, 0));
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBounds(390, 500, 150, 40);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Logic xóa bài tập
				deleteAssignment();
			}
		});
		add(btnDelete);

		// Nút Nộp bài
		JButton btnSubmit = new JButton("Nộp Bài");
		btnSubmit.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSubmit.setBackground(new Color(0, 204, 0)); // Màu xanh để nổi bật
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setBounds(695, 500, 150, 40);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Logic nộp bài
				submitAssignment();
			}
		});
		add(btnSubmit);
	}

	// Thêm dữ liệu mẫu vào bảng
	private void addSampleData() {
		Object[] row1 = { "BT001", "Bài tập 1", "Toán", "15/04/2025", "Chưa nộp" };
		Object[] row2 = { "BT002", "Bài tập 2", "Văn", "20/04/2025", "Đã nộp" };
		Object[] row3 = { "BT003", "Bài tập 3", "Anh", "25/04/2025", "Chưa nộp" };
		tableModel.addRow(row1);
		tableModel.addRow(row2);
		tableModel.addRow(row3);
	}

	// Logic thêm bài tập
	private void addNewAssignment() {
		// Ví dụ: Thêm một dòng mới (có thể thay bằng form nhập liệu)
		Object[] newRow = { "BT00" + (tableModel.getRowCount() + 1), "Bài tập mới", "Môn học", "DD/MM/YYYY",
				"Chưa nộp" };
		tableModel.addRow(newRow);
	}

	// Logic sửa bài tập
	private void editAssignment() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			// Ví dụ: Cập nhật trạng thái (có thể thay bằng form chỉnh sửa)
			tableModel.setValueAt("Đã chỉnh sửa", selectedRow, 4);
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn bài tập để sửa!");
		}
	}

	// Logic xóa bài tập
	private void deleteAssignment() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa bài tập này?",
					"Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);
			if (confirm == javax.swing.JOptionPane.YES_OPTION) {
				tableModel.removeRow(selectedRow);
			}
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn bài tập để xóa!");
		}
	}

	// Logic nộp bài
	private void submitAssignment() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			tableModel.setValueAt("Đã nộp", selectedRow, 4);
			javax.swing.JOptionPane.showMessageDialog(this, "Đã nộp bài tập thành công!");
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn bài tập để nộp!");
		}
	}

	// Để test riêng class này
	public static void main(String[] args) {
		javax.swing.JFrame frame = new javax.swing.JFrame("Quản Lý Bài Tập");
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 900, 600);
		frame.setContentPane(new QuanLyBaiTap());
		frame.setVisible(true);
	}
}