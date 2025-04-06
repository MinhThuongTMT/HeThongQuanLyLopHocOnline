package HeThongQuanLyLopHocOnline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.text.DateFormatter;

public class SinhVien extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField HoTen_text;
    private JTextField Mssv_test;
    private JTextField Email_test;
    private JFormattedTextField ngaySinhField;
    private JTextField textField;    // Thời gian
    private JTextField textField_1;  // Mã môn
    private JTextField textField_2;  // Số tín
    private JComboBox<String> Lop_ComboBox;
    private JComboBox<String> GioiTinh_ComboBox;
    private JComboBox<String> MonHoc_ComboBox;

    public SinhVien() {
        setBackground(new Color(0, 0, 121));
        setBounds(81, 11, 895, 652);
        setLayout(null);

        JLabel lblNewLabel = new JLabel("Sinh Viên");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(10, 28, 227, 36);
        add(lblNewLabel);

        // Panel nhập thông tin sinh viên
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

        JLabel GiowiTinh_Label = new JLabel("GIỚI TÍNH :");
        GiowiTinh_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        GiowiTinh_Label.setBounds(243, 243, 94, 29);
        panel.add(GiowiTinh_Label);

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

        Email_test = new JTextField();
        Email_test.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        Email_test.setColumns(10);
        Email_test.setBounds(97, 357, 297, 30);
        panel.add(Email_test);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ngaySinhField = new JFormattedTextField(new DateFormatter(dateFormat));
        ngaySinhField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        ngaySinhField.setBounds(121, 242, 120, 30);
        ngaySinhField.setText("dd/MM/yyyy");
        panel.add(ngaySinhField);

        String[] items = {"", "Nam", "Nữ"};
        GioiTinh_ComboBox = new JComboBox<>(items);
        GioiTinh_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        GioiTinh_ComboBox.setBounds(339, 244, 55, 29);
        panel.add(GioiTinh_ComboBox);

        String[] items2 = {"", "D21CQVTHI-01", "D21CQVTVT-01", "D21CQVT-01N"};
        Lop_ComboBox = new JComboBox<>(items2);
        Lop_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        Lop_ComboBox.setBounds(98, 187, 296, 29);
        panel.add(Lop_ComboBox);

        // Panel nhập thông tin khóa học
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
        HoTen_Label_1.setBounds(24, 71, 103, 29);
        panel_1.add(HoTen_Label_1);

        textField = new JTextField();
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textField.setColumns(10);
        textField.setBounds(114, 260, 296, 30);
        panel_1.add(textField);

        textField_1 = new JTextField();
        textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textField_1.setColumns(10);
        textField_1.setBounds(114, 124, 296, 30);
        panel_1.add(textField_1);

        JLabel Lop_Label_1 = new JLabel("MÃ MÔN :");
        Lop_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Lop_Label_1.setBounds(24, 125, 81, 29);
        panel_1.add(Lop_Label_1);

        textField_2 = new JTextField();
        textField_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textField_2.setColumns(10);
        textField_2.setBounds(114, 180, 296, 30);
        panel_1.add(textField_2);

        JLabel Sdt_Label_1 = new JLabel("THỜI GIAN:");
        Sdt_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Sdt_Label_1.setBounds(24, 261, 93, 29);
        panel_1.add(Sdt_Label_1);

        MonHoc_ComboBox = new JComboBox<>();
        MonHoc_ComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"", "Cảm Biến", "Java", "Android"}));
        MonHoc_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        MonHoc_ComboBox.setBounds(114, 71, 296, 29);
        panel_1.add(MonHoc_ComboBox);

        JLabel SoTin_Label_1_1 = new JLabel("SỐ TÍN :");
        SoTin_Label_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        SoTin_Label_1_1.setBounds(25, 180, 103, 29);
        panel_1.add(SoTin_Label_1_1);

        // Xử lý khi chọn môn học
        MonHoc_ComboBox.addItemListener(e -> {
            String selectedSubject = (String) MonHoc_ComboBox.getSelectedItem();
            if ("Cảm Biến".equals(selectedSubject)) {
                textField_1.setText("iot");         // Mã môn
                textField_2.setText("3");           // Số tín
                textField.setText("4/5-6-8");       // Thời gian
            } else if ("Java".equals(selectedSubject)) {
                textField_1.setText("jv");          // Mã môn
                textField_2.setText("2");           // Số tín
                textField.setText("6/7-7/8");       // Thời gian
            } else {
                textField_1.setText("");            // Xóa nếu không chọn
                textField_2.setText("");
                textField.setText("");
            }
        });

        // Nút LƯU
        JButton Luu_button = new JButton("LƯU");
        Luu_button.setBackground(new Color(255, 0, 0));
        Luu_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Luu_button.setBounds(226, 563, 120, 44);
        add(Luu_button);

        Luu_button.addActionListener(e -> {
            // Kiểm tra các trường thông tin sinh viên
            if (HoTen_text.getText().trim().isEmpty() ||
                Mssv_test.getText().trim().isEmpty() ||
                Lop_ComboBox.getSelectedIndex() == 0 || // "" được chọn
                ngaySinhField.getText().trim().isEmpty() ||
                GioiTinh_ComboBox.getSelectedIndex() == 0 || // "" được chọn
                Email_test.getText().trim().isEmpty() ||
                // Kiểm tra các trường thông tin khóa học
                MonHoc_ComboBox.getSelectedIndex() == 0 || // "" được chọn
                textField_1.getText().trim().isEmpty() || // Mã môn
                textField_2.getText().trim().isEmpty() || // Số tín
                textField.getText().trim().isEmpty()) { // Thời gian

                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ tất cả các thông tin!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Nếu tất cả các trường đã được điền, hiển thị thông báo thành công
                JOptionPane.showMessageDialog(this, "Lưu thông tin thành công!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

                // Ở đây bạn có thể thêm logic để lưu dữ liệu vào cơ sở dữ liệu hoặc file
            }
        });

        // Nút XUẤT
        JButton xuat_button = new JButton("XUẤT");
        xuat_button.setBackground(new Color(255, 204, 0));
        xuat_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        xuat_button.setBounds(558, 563, 106, 44);
        add(xuat_button);

        xuat_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lấy dữ liệu từ các trường
                String hoTen = HoTen_text.getText();
                String mssv = Mssv_test.getText();
                String lop = (String) Lop_ComboBox.getSelectedItem();
                String ngaySinh = ngaySinhField.getText();
                String gioiTinh = (String) GioiTinh_ComboBox.getSelectedItem();
                String email = Email_test.getText();
                String monHoc = (String) MonHoc_ComboBox.getSelectedItem();
                String maMon = textField_1.getText();
                String soTin = textField_2.getText();
                String thoiGian = textField.getText();

                // Mở frame ThongTinSinhVien và truyền dữ liệu
                EventQueue.invokeLater(() -> {
                    ThongTinSinhVien frame = new ThongTinSinhVien(hoTen, mssv, lop, ngaySinh, gioiTinh, email, monHoc, maMon, soTin, thoiGian);
                    frame.setVisible(true);
                });
            }
        });
    }
}
