package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

public class SinhVien extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField HoTen_text;
	private JTextField Mssv_test;
	private JTextField Lop_test;
	private JTextField Sdt_test;
	private JTextField Email_test;

	private JFormattedTextField ngaySinhField;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_5;

	public SinhVien() {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 915, 652);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Sinh Viên");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(10, 28, 227, 36);
		add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(12, 85, 432, 448);
		add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_2_1 = new JLabel("NHẬP THÔNG TIN SINH VIÊN");
		lblNewLabel_2_1.setForeground(new Color(0, 0, 121));
		lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_2_1.setBounds(51, 0, 299, 69);
		panel.add(lblNewLabel_2_1);

		JLabel HoTen_Label = new JLabel("HỌ TÊN :");
		HoTen_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		HoTen_Label.setBounds(18, 74, 81, 29);
		panel.add(HoTen_Label);

		JLabel MSSV_Label = new JLabel("MSSV  :");
		MSSV_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		MSSV_Label.setBounds(19, 127, 81, 29);
		panel.add(MSSV_Label);

		JLabel Lop_Label = new JLabel("LỚP :");
		Lop_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Lop_Label.setBounds(20, 183, 81, 29);
		panel.add(Lop_Label);

		JLabel NgaySinh_label = new JLabel("NGÀY SINH:");
		NgaySinh_label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		NgaySinh_label.setBounds(18, 243, 103, 29);
		panel.add(NgaySinh_label);

		JLabel GiơiTinh_Label = new JLabel("GIỚI TÍNH :");
		GiơiTinh_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		GiơiTinh_Label.setBounds(243, 243, 94, 29);
		panel.add(GiơiTinh_Label);

		JLabel Sdt_Label = new JLabel("SDT :");
		Sdt_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Sdt_Label.setBounds(18, 304, 81, 29);
		panel.add(Sdt_Label);

		JLabel Email_Label = new JLabel("EMAIL :");
		Email_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Email_Label.setBounds(17, 357, 81, 29);
		panel.add(Email_Label);

		HoTen_text = new JTextField();
		HoTen_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		HoTen_text.setBounds(98, 72, 296, 30);
		panel.add(HoTen_text);
		HoTen_text.setColumns(10);

		Mssv_test = new JTextField();
		Mssv_test.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Mssv_test.setColumns(10);
		Mssv_test.setBounds(98, 127, 296, 30);
		panel.add(Mssv_test);

		Lop_test = new JTextField();
		Lop_test.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Lop_test.setColumns(10);
		Lop_test.setBounds(98, 180, 296, 30);
		panel.add(Lop_test);

		Sdt_test = new JTextField();
		Sdt_test.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Sdt_test.setColumns(10);
		Sdt_test.setBounds(97, 305, 297, 30);
		panel.add(Sdt_test);

		Email_test = new JTextField();
		Email_test.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Email_test.setColumns(10);
		Email_test.setBounds(97, 357, 297, 30);
		panel.add(Email_test);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		ngaySinhField = new JFormattedTextField(new DateFormatter(dateFormat));
		ngaySinhField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ngaySinhField.setBounds(121, 242, 120, 30); // Điều chỉnh kích thước
		ngaySinhField.setText("");
		panel.add(ngaySinhField);

		String[] items = { "", "Nam", "Nữ" };
		JComboBox<String> GioiTinh_ComboBox = new JComboBox<>(items);
		GioiTinh_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		GioiTinh_ComboBox.setBounds(339, 244, 55, 29);
		panel.add(GioiTinh_ComboBox);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(461, 84, 432, 448);
		add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2_1_1 = new JLabel("NHẬP THÔNG TIN KHÓA HỌC");
		lblNewLabel_2_1_1.setForeground(new Color(0, 0, 121));
		lblNewLabel_2_1_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_2_1_1.setBounds(66, 0, 299, 69);
		panel_1.add(lblNewLabel_2_1_1);

		JLabel HoTen_Label_1 = new JLabel("MÔN HỌC :");
		HoTen_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		HoTen_Label_1.setBounds(24, 72, 103, 29);
		panel_1.add(HoTen_Label_1);

		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField.setColumns(10);
		textField.setBounds(114, 71, 296, 30);
		panel_1.add(textField);

		JLabel MSSV_Label_1 = new JLabel("LỚP HỌC:");
		MSSV_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		MSSV_Label_1.setBounds(25, 125, 102, 29);
		panel_1.add(MSSV_Label_1);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField_1.setColumns(10);
		textField_1.setBounds(114, 124, 296, 30);
		panel_1.add(textField_1);

		JLabel Lop_Label_1 = new JLabel("MÃ LỚP :");
		Lop_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Lop_Label_1.setBounds(26, 181, 81, 29);
		panel_1.add(Lop_Label_1);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField_2.setColumns(10);
		textField_2.setBounds(114, 180, 296, 30);
		panel_1.add(textField_2);

		JLabel Sdt_Label_1 = new JLabel("THỜI GIAN:");
		Sdt_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Sdt_Label_1.setBounds(24, 302, 93, 29);
		panel_1.add(Sdt_Label_1);

		JLabel Email_Label_1 = new JLabel("HỌC PHÍ:");
		Email_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Email_Label_1.setBounds(23, 355, 81, 29);
		panel_1.add(Email_Label_1);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField_4.setColumns(10);
		textField_4.setBounds(113, 354, 297, 30);
		panel_1.add(textField_4);

		JLabel NgaySinh_label_1 = new JLabel("TÊN GV:");
		NgaySinh_label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		NgaySinh_label_1.setBounds(24, 241, 103, 29);
		panel_1.add(NgaySinh_label_1);

		textField_5 = new JTextField();
		textField_5.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField_5.setColumns(10);
		textField_5.setBounds(113, 240, 297, 30);
		panel_1.add(textField_5);

		String[] items2 = { "", "7h-9h(1/4-1/7)", "13h-15h(5/4-5/7)", "17h-19h(5/4-5/7)" };
		JComboBox<String> ThoiGian_ComboBox = new JComboBox<>(items2);
		ThoiGian_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		ThoiGian_ComboBox.setBounds(114, 302, 296, 29);
		panel_1.add(ThoiGian_ComboBox);

		JButton Luu_button = new JButton("LƯU");
		Luu_button.setBackground(new Color(255, 0, 0));
		Luu_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Luu_button.setBounds(161, 563, 120, 44);
		add(Luu_button);

		// Thêm ActionListener cho nút Lưu
		Luu_button.addActionListener(e -> {
			// Kiểm tra các trường thông tin sinh viên
			if (HoTen_text.getText().trim().isEmpty() || Mssv_test.getText().trim().isEmpty()
					|| Lop_test.getText().trim().isEmpty() || ngaySinhField.getText().trim().isEmpty()
					|| GioiTinh_ComboBox.getSelectedIndex() == 0 || // "" được chọn
					Sdt_test.getText().trim().isEmpty() || Email_test.getText().trim().isEmpty() ||
			// Kiểm tra các trường thông tin khóa học
					textField.getText().trim().isEmpty() || // Môn học
					textField_1.getText().trim().isEmpty() || // Lớp học
					textField_2.getText().trim().isEmpty() || // Mã lớp
					textField_5.getText().trim().isEmpty() || // Tên GV
					ThoiGian_ComboBox.getSelectedIndex() == 0 || // "" được chọn
					textField_4.getText().trim().isEmpty()) { // Học phí

				JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ tất cả các thông tin!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			} else {
				// Nếu tất cả các trường đã được điền, hiển thị thông báo thành công
				JOptionPane.showMessageDialog(this, "Lưu thông tin thành công!", "Thành công",
						JOptionPane.INFORMATION_MESSAGE);

				// Ở đây bạn có thể thêm logic để lưu dữ liệu vào cơ sở dữ liệu hoặc file
			}
		});

		JButton xuat_button = new JButton("XUẤT");
		xuat_button.setBackground(new Color(255, 204, 0));
		xuat_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
		xuat_button.setBounds(628, 563, 106, 44);
		add(xuat_button);
	}
}