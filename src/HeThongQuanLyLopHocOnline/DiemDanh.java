package HeThongQuanLyLopHocOnline;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class DiemDanh extends JFrame {

	private JTextField txtBuoi, txtGiaoVien, txtThoiGian, txtCoSo, txtSySo;
	private ArrayList<JCheckBox> checkboxes = new ArrayList<>();
	private String[] hocViens = { "Học Viên 01", "Học Viên 02", "Học Viên 03", "Học Viên 04", "Học Viên 05" };

	public DiemDanh() {
		setTitle("Điểm Danh Lớp Học");
		setSize(450, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBackground(new Color(0, 0, 160));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Panel nhập thông tin lớp học
		JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
		inputPanel.setBackground(new Color(255, 255, 255));
		txtBuoi = new JTextField(); // để trống
		txtBuoi.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtGiaoVien = new JTextField(); // để trống
		txtGiaoVien.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtThoiGian = new JTextField(); // để trống
		txtThoiGian.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtCoSo = new JTextField(); // để trống
		txtCoSo.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtSySo = new JTextField(); // để trống
		txtSySo.setFont(new Font("Times New Roman", Font.PLAIN, 11));

		JLabel label = new JLabel("Buổi học:");
		label.setFont(new Font("Times New Roman", Font.BOLD, 11));
		inputPanel.add(label);
		inputPanel.add(txtBuoi);
		JLabel label_1 = new JLabel("Giáo viên:");
		label_1.setFont(new Font("Times New Roman", Font.BOLD, 11));
		inputPanel.add(label_1);
		inputPanel.add(txtGiaoVien);
		JLabel label_2 = new JLabel("Thời gian:");
		label_2.setFont(new Font("Times New Roman", Font.BOLD, 11));
		inputPanel.add(label_2);
		inputPanel.add(txtThoiGian);
		JLabel label_3 = new JLabel("Cơ sở:");
		label_3.setFont(new Font("Times New Roman", Font.BOLD, 11));
		inputPanel.add(label_3);
		inputPanel.add(txtCoSo);
		JLabel label_4 = new JLabel("Sĩ số:");
		label_4.setFont(new Font("Times New Roman", Font.BOLD, 11));
		inputPanel.add(label_4);
		inputPanel.add(txtSySo);

		mainPanel.add(inputPanel, BorderLayout.NORTH);

		// Danh sách học viên
		JPanel danhSachPanel = new JPanel();
		danhSachPanel.setBackground(new Color(255, 255, 255));
		danhSachPanel.setLayout(new BoxLayout(danhSachPanel, BoxLayout.Y_AXIS));

		for (String hocVien : hocViens) {
			JCheckBox checkBox = new JCheckBox(hocVien);
			checkboxes.add(checkBox);
			danhSachPanel.add(checkBox);
		}

		JScrollPane scrollPane = new JScrollPane(danhSachPanel);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		// Nút xác nhận điểm danh
		JButton btnXacNhan = new JButton("Xác nhận điểm danh");
		btnXacNhan.setFont(new Font("Times New Roman", Font.BOLD, 11));
		btnXacNhan.setBackground(new Color(255, 255, 0));
		btnXacNhan.addActionListener(e -> xuLyDiemDanh());
		mainPanel.add(btnXacNhan, BorderLayout.SOUTH);

		getContentPane().add(mainPanel);
	}

	private void xuLyDiemDanh() {
		String buoi = txtBuoi.getText().trim();
		String giaoVien = txtGiaoVien.getText().trim();
		String thoiGian = txtThoiGian.getText().trim();
		String coSo = txtCoSo.getText().trim();
		String sySo = txtSySo.getText().trim();

		if (buoi.isEmpty() || giaoVien.isEmpty() || thoiGian.isEmpty() || coSo.isEmpty() || sySo.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin buổi học!", "Thiếu thông tin",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("📋 Thông tin buổi học:\n").append("Buổi: ").append(buoi).append("\n").append("Giáo viên: ")
				.append(giaoVien).append("\n").append("Thời gian: ").append(thoiGian).append("\n").append("Cơ sở: ")
				.append(coSo).append(" | Sỹ số: ").append(sySo).append("\n\n");

		sb.append("📌 Danh sách điểm danh:\n");
		for (int i = 0; i < hocViens.length; i++) {
			sb.append(hocViens[i]).append(": ").append(checkboxes.get(i).isSelected() ? "✅ Có mặt\n" : "❌ Vắng mặt\n");
		}

		JOptionPane.showMessageDialog(this, sb.toString(), "Kết quả điểm danh", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new DiemDanh().setVisible(true));
	}
}
