package HeThongQuanLyLopHocOnline;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ThongTinSinhVien extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel ThongTinSinhVien;
    private JTextField Lop_text;
    private JTextField MSSV_text;
    private JTextField HoTen_text;
    private JTextField MaMon_text;
    private JTextField LopHoc_text;
    private JTextField MonHoc_text;
    private JTextField NgaySinh_text;
    private JTextField GioiTinh_text;
    private JTextField textField;    // Email
    private JTextField textField_1;  // Thời gian

    // Constructor mặc định
    public ThongTinSinhVien() {
        initialize();
    }

    // Constructor nhận dữ liệu từ SinhVien
    public ThongTinSinhVien(String hoTen, String mssv, String lop, String ngaySinh, String gioiTinh, String email, String monHoc, String maMon, String soTin, String thoiGian) {
        initialize();
        // Gán dữ liệu vào các trường
        HoTen_text.setText(hoTen);
        MSSV_text.setText(mssv);
        Lop_text.setText(lop);
        NgaySinh_text.setText(ngaySinh);
        GioiTinh_text.setText(gioiTinh);
        textField.setText(email);       // Email
        MonHoc_text.setText(monHoc);
        MaMon_text.setText(maMon);
        LopHoc_text.setText(soTin);     // Số tín
        textField_1.setText(thoiGian);  // Thời gian
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Chỉ đóng frame này, không thoát chương trình
        setBounds(100, 100, 895, 652);
        ThongTinSinhVien = new JPanel();
        ThongTinSinhVien.setBackground(new Color(0, 0, 121));
        ThongTinSinhVien.setBorder(new EmptyBorder(5, 5, 5, 5));
        ThongTinSinhVien.setLayout(null);
        setContentPane(ThongTinSinhVien);

        JLabel lblTitle = new JLabel("THÔNG TIN SINH VIÊN");
        lblTitle.setForeground(new Color(255, 255, 255));
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblTitle.setBounds(283, 22, 302, 37);
        ThongTinSinhVien.add(lblTitle);

        // Khởi tạo các trường JTextField
        HoTen_text = new JTextField();
        HoTen_text.setBounds(539, 95, 310, 30);
        ThongTinSinhVien.add(HoTen_text);
        HoTen_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        HoTen_text.setColumns(10);

        MSSV_text = new JTextField();
        MSSV_text.setBounds(540, 146, 309, 30);
        ThongTinSinhVien.add(MSSV_text);
        MSSV_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        MSSV_text.setColumns(10);

        Lop_text = new JTextField();
        Lop_text.setBounds(538, 202, 311, 30);
        ThongTinSinhVien.add(Lop_text);
        Lop_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        Lop_text.setColumns(10);

        NgaySinh_text = new JTextField();
        NgaySinh_text.setBounds(544, 260, 120, 30);
        ThongTinSinhVien.add(NgaySinh_text);
        NgaySinh_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        NgaySinh_text.setColumns(10);

        GioiTinh_text = new JTextField();
        GioiTinh_text.setBounds(793, 260, 56, 30);
        ThongTinSinhVien.add(GioiTinh_text);
        GioiTinh_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        GioiTinh_text.setColumns(10);

        textField = new JTextField(); // Email
        textField.setBounds(538, 317, 311, 30);
        ThongTinSinhVien.add(textField);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textField.setColumns(10);

        MonHoc_text = new JTextField();
        MonHoc_text.setBounds(538, 375, 311, 30);
        ThongTinSinhVien.add(MonHoc_text);
        MonHoc_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        MonHoc_text.setColumns(10);

        LopHoc_text = new JTextField(); // Số tín
        LopHoc_text.setBounds(538, 436, 311, 30);
        ThongTinSinhVien.add(LopHoc_text);
        LopHoc_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        LopHoc_text.setColumns(10);

        MaMon_text = new JTextField();
        MaMon_text.setBounds(537, 498, 312, 30);
        ThongTinSinhVien.add(MaMon_text);
        MaMon_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        MaMon_text.setColumns(10);

        textField_1 = new JTextField(); // Thời gian
        textField_1.setBounds(537, 560, 312, 30);
        ThongTinSinhVien.add(textField_1);
        textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textField_1.setColumns(10);

        // Các nhãn (Label)
        JLabel HoTen_Label = new JLabel("HỌ TÊN :");
        HoTen_Label.setForeground(new Color(255, 255, 255));
        HoTen_Label.setBounds(448, 96, 81, 29);
        ThongTinSinhVien.add(HoTen_Label);
        HoTen_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel MSSV_Label = new JLabel("MSSV  :");
        MSSV_Label.setForeground(new Color(255, 255, 255));
        MSSV_Label.setBounds(449, 147, 81, 29);
        ThongTinSinhVien.add(MSSV_Label);
        MSSV_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel Lop_Label = new JLabel("LỚP :");
        Lop_Label.setForeground(new Color(255, 255, 255));
        Lop_Label.setBounds(448, 203, 81, 29);
        ThongTinSinhVien.add(Lop_Label);
        Lop_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel NgaySinh_label = new JLabel("NGÀY SINH:");
        NgaySinh_label.setForeground(new Color(255, 255, 255));
        NgaySinh_label.setBounds(448, 261, 103, 29);
        ThongTinSinhVien.add(NgaySinh_label);
        NgaySinh_label.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel NgaySinh_label_2 = new JLabel("GIỚI TÍNH:");
        NgaySinh_label_2.setForeground(new Color(255, 255, 255));
        NgaySinh_label_2.setBounds(690, 261, 93, 29);
        ThongTinSinhVien.add(NgaySinh_label_2);
        NgaySinh_label_2.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel Email_Label_1 = new JLabel("EMAIL :");
        Email_Label_1.setForeground(new Color(255, 255, 255));
        Email_Label_1.setBounds(449, 318, 66, 29);
        ThongTinSinhVien.add(Email_Label_1);
        Email_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel HoTen_Label_1 = new JLabel("MÔN HỌC :");
        HoTen_Label_1.setForeground(new Color(255, 255, 255));
        HoTen_Label_1.setBounds(448, 376, 93, 29);
        ThongTinSinhVien.add(HoTen_Label_1);
        HoTen_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel MSSV_Label_1 = new JLabel("SỐ TÍN:");
        MSSV_Label_1.setForeground(new Color(255, 255, 255));
        MSSV_Label_1.setBounds(448, 437, 81, 29);
        ThongTinSinhVien.add(MSSV_Label_1);
        MSSV_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel Lop_Label_1 = new JLabel("MÃ MÔN :");
        Lop_Label_1.setForeground(new Color(255, 255, 255));
        Lop_Label_1.setBounds(448, 499, 81, 29);
        ThongTinSinhVien.add(Lop_Label_1);
        Lop_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JLabel Sdt_Label_1_1 = new JLabel("THỜI GIAN:");
        Sdt_Label_1_1.setForeground(new Color(255, 255, 255));
        Sdt_Label_1_1.setBounds(448, 561, 93, 29);
        ThongTinSinhVien.add(Sdt_Label_1_1);
        Sdt_Label_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));

        // Các nút
        JButton Sua_button_1 = new JButton("SỬA");
        Sua_button_1.setBounds(61, 360, 120, 44);
        ThongTinSinhVien.add(Sua_button_1);
        Sua_button_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Sua_button_1.setBackground(new Color(238, 119, 0));

        JButton Xoa_button_1_1 = new JButton("LƯU");
        Xoa_button_1_1.setBounds(252, 360, 120, 44);
        ThongTinSinhVien.add(Xoa_button_1_1);
        Xoa_button_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Xoa_button_1_1.setBackground(new Color(238, 119, 0));

        JButton NopBaiTap_button_2_1 = new JButton("NỘP BÀI TẬP");
        NopBaiTap_button_2_1.setBounds(139, 471, 162, 44);
        ThongTinSinhVien.add(NopBaiTap_button_2_1);
        NopBaiTap_button_2_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        NopBaiTap_button_2_1.setBackground(new Color(238, 119, 0));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ThongTinSinhVien frame = new ThongTinSinhVien();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
