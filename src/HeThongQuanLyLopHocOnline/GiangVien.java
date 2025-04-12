package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GiangVien extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField HoTen_text;
	private JTextField MaGV_text;
	private JTextField Email_text;
	private JTextField txtSoDienThoai;
	private JTextField txtMaMon;
	private JComboBox<String> comboBoxMonHoc;

	public GiangVien() {
		setBackground(new Color(0, 0, 121));
		setBounds(81, 11, 895, 652);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("GIẢNG VIÊN");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(10, 39, 219, 43);
		add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(12, 85, 873, 458);
		add(panel);
		panel.setLayout(null);

		JLabel lblThongTinGiangVien = new JLabel("NHẬP THÔNG TIN GIẢNG VIÊN");
		lblThongTinGiangVien.setForeground(new Color(0, 0, 121));
		lblThongTinGiangVien.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblThongTinGiangVien.setBounds(321, 0, 312, 69);
		panel.add(lblThongTinGiangVien);

		JLabel lblHoTen = new JLabel("HỌ TÊN:");
		lblHoTen.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblHoTen.setBounds(65, 81, 81, 29);
		panel.add(lblHoTen);

		JLabel lblMaGV = new JLabel("MÃ GV:");
		lblMaGV.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblMaGV.setBounds(65, 139, 81, 29);
		panel.add(lblMaGV);

		JLabel lblEmail = new JLabel("EMAIL:");
		lblEmail.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEmail.setBounds(65, 196, 81, 29);
		panel.add(lblEmail);

		JLabel lblSoDienThoai = new JLabel("SỐ ĐIỆN THOẠI:");
		lblSoDienThoai.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSoDienThoai.setBounds(65, 255, 131, 29);
		panel.add(lblSoDienThoai);

		HoTen_text = new JTextField();
		HoTen_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		HoTen_text.setBounds(212, 80, 586, 30);
		panel.add(HoTen_text);
		HoTen_text.setColumns(10);

		MaGV_text = new JTextField();
		MaGV_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		MaGV_text.setColumns(10);
		MaGV_text.setBounds(212, 138, 586, 30);
		panel.add(MaGV_text);

		Email_text = new JTextField();
		Email_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Email_text.setColumns(10);
		Email_text.setBounds(212, 195, 586, 30);
		panel.add(Email_text);

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSoDienThoai.setBounds(212, 254, 586, 30);
		panel.add(txtSoDienThoai);

		JLabel lblMonGiangDay = new JLabel("MÔN GIẢNG DẠY:");
		lblMonGiangDay.setBounds(65, 317, 151, 29);
		panel.add(lblMonGiangDay);
		lblMonGiangDay.setFont(new Font("Times New Roman", Font.BOLD, 15));

		comboBoxMonHoc = new JComboBox<>();
		comboBoxMonHoc.setBounds(212, 317, 586, 29);
		panel.add(comboBoxMonHoc);
		comboBoxMonHoc.setModel(new DefaultComboBoxModel<>(new String[] { "", "Cảm Biến", "Java", "Android" }));
		comboBoxMonHoc.setFont(new Font("Times New Roman", Font.PLAIN, 15));

		JLabel lblMaMon = new JLabel("MÃ MÔN:");
		lblMaMon.setBounds(65, 377, 81, 29);
		panel.add(lblMaMon);
		lblMaMon.setFont(new Font("Times New Roman", Font.BOLD, 15));

		txtMaMon = new JTextField();
		txtMaMon.setBounds(212, 376, 586, 30);
		panel.add(txtMaMon);
		txtMaMon.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtMaMon.setColumns(10);

		String[] lopItems = { "", "D21CQVTHI-01", "D21CQVTVT-01", "D21CQVT-01N" };

		// Xử lý khi chọn môn học
		comboBoxMonHoc.addItemListener(e -> {
			String selectedSubject = (String) comboBoxMonHoc.getSelectedItem();
			if ("Cảm Biến".equals(selectedSubject)) {
				txtMaMon.setText("iot"); // Mã môn
			} else if ("Java".equals(selectedSubject)) {
				txtMaMon.setText("jv"); // Mã môn
			} else if ("Android".equals(selectedSubject)) {
				txtMaMon.setText("ad"); // Mã môn
			} else {
				txtMaMon.setText(""); // Xóa nếu không chọn
			}
		});
		// Nút LƯU
		JButton btnLuu = new JButton("LƯU");
		btnLuu.setBackground(new Color(255, 140, 0));
		btnLuu.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnLuu.setBounds(227, 569, 120, 44);
		add(btnLuu);

		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Kiểm tra các trường thông tin
				if (HoTen_text.getText().trim().isEmpty() || MaGV_text.getText().trim().isEmpty()
						|| Email_text.getText().trim().isEmpty() || comboBoxMonHoc.getSelectedIndex() == 0 || // "" được
																												// chọn
						txtMaMon.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(GiangVien.this, "Vui lòng điền đầy đủ tất cả các thông tin!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// Nếu tất cả các trường đã được điền, hiển thị thông báo thành công
					JOptionPane.showMessageDialog(GiangVien.this, "Lưu thông tin thành công!", "Thành công",
							JOptionPane.INFORMATION_MESSAGE);
					// Ở đây bạn có thể thêm logic để lưu dữ liệu vào cơ sở dữ liệu hoặc file
				}
			}
		});

		// Nút XUẤT
		JButton btnXuat = new JButton("XUẤT");
		btnXuat.setBackground(new Color(255, 215, 0));
		btnXuat.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnXuat.setBounds(561, 569, 120, 44);
		add(btnXuat);

		btnXuat.addActionListener(e -> {
			String hoTen = HoTen_text.getText();
			String maGV = MaGV_text.getText();
			String email = Email_text.getText();
			String soDienThoai = txtSoDienThoai.getText();
			String monGiangDay = (String) comboBoxMonHoc.getSelectedItem();
			String maMon = txtMaMon.getText();

			ThongTinGiangVien thongTin = new ThongTinGiangVien(hoTen, maGV, email, soDienThoai, monGiangDay, maMon);
			thongTin.setVisible(true);
		});
	}
}