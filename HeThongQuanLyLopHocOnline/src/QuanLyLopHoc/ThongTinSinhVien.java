package QuanLyLopHoc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class ThongTinSinhVien extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel ThongTinSinhVien;
    private JTextField MSSV_text1;
    private JTextField HoTen_text1;
    private JDateChooser NgaySinh_text;
    private JComboBox<String> GioiTinh_ComboBox;
    private JTextField Email_text1;
    private JComboBox<String> Lop_comboBox_1;
    private JLabel avata;
    private JTable table_khoaHoc;
    private DefaultTableModel tableModel;
    private JButton XoaMonHoc_button;

    // Các thuộc tính để lưu dữ liệu
    private String hoTen, mssv, lop, ngaySinh, gioiTinh, email;
    private List<String> monHocList, maMonList, soTinList, thoiGianList;
    private final String originalMssv;

    // Thông tin kết nối cơ sở dữ liệu
    private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true&prepareThreshold=0";
    private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
    private static final String DB_PASSWORD = "MinhThuong0808";

    // Regex để kiểm tra email
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9]+@student\\.ptithcm\\.edu\\.vn$");

    public ThongTinSinhVien() {
        this.originalMssv = "";
        this.monHocList = new ArrayList<>();
        this.maMonList = new ArrayList<>();
        this.soTinList = new ArrayList<>();
        this.thoiGianList = new ArrayList<>();
        initialize();
    }

    public ThongTinSinhVien(String hoTen, String mssv, String lop, String ngaySinh, String gioiTinh, String email,
                            String monHoc, String maMon, String soTin, String thoiGian) {
        this.hoTen = hoTen;
        this.mssv = mssv;
        this.originalMssv = mssv;
        this.lop = lop;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.monHocList = new ArrayList<>(Arrays.asList(monHoc.split(", ")));
        this.maMonList = new ArrayList<>(Arrays.asList(maMon.split(", ")));
        this.soTinList = new ArrayList<>(Arrays.asList(soTin.split(", ")));
        this.thoiGianList = new ArrayList<>(Arrays.asList(thoiGian.split(", ")));
        initialize();
        loadDataToFields();
    }

    // Khởi tạo giao diện người dùng
    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 895, 652);
        setLocationRelativeTo(null);

        ThongTinSinhVien = new JPanel();
        ThongTinSinhVien.setBackground(new Color(0, 0, 121));
        ThongTinSinhVien.setBorder(new EmptyBorder(5, 5, 5, 5));
        ThongTinSinhVien.setLayout(null);
        setContentPane(ThongTinSinhVien);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 204, 0));
        panel.setBounds(0, 0, 881, 57);
        panel.setLayout(null);
        ThongTinSinhVien.add(panel);

        JLabel lblTitle = new JLabel("THÔNG TIN SINH VIÊN");
        lblTitle.setBounds(284, 11, 302, 37);
        lblTitle.setForeground(new Color(0, 0, 0));
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
        panel.add(lblTitle);

        // Avatar
        avata = new JLabel();
        avata.setBounds(99, 95, 224, 227);
        avata.setBorder(new LineBorder(Color.WHITE, 3));
        avata.setBackground(Color.WHITE);
        avata.setOpaque(true);
        ThongTinSinhVien.add(avata);

        // Các trường thông tin
        HoTen_text1 = new JTextField();
        HoTen_text1.setBounds(539, 95, 310, 30);
        HoTen_text1.setEditable(false);
        HoTen_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        ThongTinSinhVien.add(HoTen_text1);

        MSSV_text1 = new JTextField();
        MSSV_text1.setBounds(539, 146, 310, 30);
        MSSV_text1.setEditable(false);
        MSSV_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        ThongTinSinhVien.add(MSSV_text1);

        Lop_comboBox_1 = new JComboBox<>(new String[]{"", "D21CQVTHI01-N", "D21CQVTVT01-N", "D21CQVTMD01-N", "D22CQVT01-N", "D23CQVT01-N"});
        Lop_comboBox_1.setBounds(539, 197, 310, 30);
        Lop_comboBox_1.setEnabled(false);
        Lop_comboBox_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        ThongTinSinhVien.add(Lop_comboBox_1);

        NgaySinh_text = new JDateChooser();
        NgaySinh_text.setBounds(539, 248, 150, 30);
        NgaySinh_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        NgaySinh_text.setDateFormatString("dd/MM/yyyy");
        NgaySinh_text.setEnabled(false);
        ThongTinSinhVien.add(NgaySinh_text);

        GioiTinh_ComboBox = new JComboBox<>(new String[]{"", "Nam", "Nữ"});
        GioiTinh_ComboBox.setBounds(793, 248, 56, 30);
        GioiTinh_ComboBox.setEnabled(false);
        GioiTinh_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        ThongTinSinhVien.add(GioiTinh_ComboBox);

        Email_text1 = new JTextField();
        Email_text1.setBounds(539, 299, 310, 30);
        Email_text1.setEditable(false);
        Email_text1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        ThongTinSinhVien.add(Email_text1);

        // Các nhãn
        JLabel HoTen_Label = new JLabel("HỌ TÊN :");
        HoTen_Label.setForeground(Color.WHITE);
        HoTen_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        HoTen_Label.setBounds(448, 95, 81, 29);
        ThongTinSinhVien.add(HoTen_Label);

        JLabel MSSV_Label = new JLabel("MSSV :");
        MSSV_Label.setForeground(Color.WHITE);
        MSSV_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        MSSV_Label.setBounds(448, 146, 81, 29);
        ThongTinSinhVien.add(MSSV_Label);

        JLabel Lop_Label = new JLabel("LỚP :");
        Lop_Label.setForeground(Color.WHITE);
        Lop_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Lop_Label.setBounds(448, 197, 81, 29);
        ThongTinSinhVien.add(Lop_Label);

        JLabel NgaySinh_label = new JLabel("NGÀY SINH:");
        NgaySinh_label.setForeground(Color.WHITE);
        NgaySinh_label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        NgaySinh_label.setBounds(448, 248, 103, 29);
        ThongTinSinhVien.add(NgaySinh_label);

        JLabel GioiTinh_label = new JLabel("GIỚI TÍNH:");
        GioiTinh_label.setForeground(Color.WHITE);
        GioiTinh_label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        GioiTinh_label.setBounds(700, 248, 93, 29);
        ThongTinSinhVien.add(GioiTinh_label);

        JLabel Email_Label = new JLabel("EMAIL :");
        Email_Label.setForeground(Color.WHITE);
        Email_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Email_Label.setBounds(448, 299, 81, 29);
        ThongTinSinhVien.add(Email_Label);

        JLabel MonHoc_Label = new JLabel("THÔNG TIN KHÓA HỌC :");
        MonHoc_Label.setForeground(Color.WHITE);
        MonHoc_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        MonHoc_Label.setBounds(448, 350, 211, 29);
        ThongTinSinhVien.add(MonHoc_Label);

        // Bảng thông tin khóa học
        String[] columnNames = {"Môn học", "Mã môn", "Số tín", "Thời gian"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table_khoaHoc = new JTable(tableModel);
        table_khoaHoc.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        table_khoaHoc.setRowHeight(25);
        table_khoaHoc.getTableHeader().setBackground(new Color(255, 204, 0));
        table_khoaHoc.getColumnModel().getColumn(0).setPreferredWidth(200);
        table_khoaHoc.getColumnModel().getColumn(1).setPreferredWidth(100);
        table_khoaHoc.getColumnModel().getColumn(2).setPreferredWidth(60);
        table_khoaHoc.getColumnModel().getColumn(3).setPreferredWidth(100);
        JScrollPane scrollPane = new JScrollPane(table_khoaHoc);
        scrollPane.setBounds(405, 390, 466, 214);
        ThongTinSinhVien.add(scrollPane);

        // Nút Xóa Môn Học
        XoaMonHoc_button = new JButton("XÓA MÔN HỌC");
        XoaMonHoc_button.setBounds(252, 445, 120, 44);
        XoaMonHoc_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        XoaMonHoc_button.setBackground(new Color(50, 150, 255));
        XoaMonHoc_button.setForeground(Color.BLACK);
        XoaMonHoc_button.setBorder(new LineBorder(Color.WHITE, 1));
        XoaMonHoc_button.setEnabled(false);
        ThongTinSinhVien.add(XoaMonHoc_button);

        // Các nút
        JButton Sua_button = new JButton("SỬA");
        Sua_button.setBounds(61, 370, 120, 44);
        Sua_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Sua_button.setBackground(new Color(50, 150, 255));
        Sua_button.setForeground(Color.BLACK);
        Sua_button.setBorder(new LineBorder(Color.WHITE, 1));
        ThongTinSinhVien.add(Sua_button);

        JButton Luu_button = new JButton("LƯU");
        Luu_button.setBounds(252, 370, 120, 44);
        Luu_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Luu_button.setBackground(new Color(50, 150, 255));
        Luu_button.setForeground(Color.BLACK);
        Luu_button.setBorder(new LineBorder(Color.WHITE, 1));
        ThongTinSinhVien.add(Luu_button);

        JButton Xoa_button = new JButton("XÓA");
        Xoa_button.setBounds(61, 445, 120, 44);
        Xoa_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Xoa_button.setBackground(new Color(255, 50, 50));
        Xoa_button.setForeground(Color.BLACK);
        Xoa_button.setBorder(new LineBorder(Color.WHITE, 1));
        ThongTinSinhVien.add(Xoa_button);

        JButton NopBaiTap_button = new JButton("NỘP BÀI TẬP");
        NopBaiTap_button.setBounds(139, 527, 162, 44);
        NopBaiTap_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        NopBaiTap_button.setBackground(new Color(255, 165, 80));
        NopBaiTap_button.setForeground(Color.BLACK);
        NopBaiTap_button.setBorder(new LineBorder(Color.WHITE, 1));
        ThongTinSinhVien.add(NopBaiTap_button);

        // Gắn sự kiện cho các nút
        Sua_button.addActionListener(e -> Edit());
        Luu_button.addActionListener(e -> Save());
        Xoa_button.addActionListener(e -> Xoa());
        XoaMonHoc_button.addActionListener(e -> XoaMonHoc());
        NopBaiTap_button.addActionListener(e -> NopBaiTap());
    }

    // Xử lý sự kiện nút Sửa
    private void Edit() {
        loadDataFromDatabase();
        HoTen_text1.setEditable(true);
        NgaySinh_text.setEnabled(true);
        GioiTinh_ComboBox.setEnabled(true);
        Email_text1.setEditable(true);
        Lop_comboBox_1.setEnabled(true);
        XoaMonHoc_button.setEnabled(true);
    }

    // Xử lý sự kiện nút Lưu
    private void Save() {
        if (!validateInput()) {
            return;
        }

        hoTen = HoTen_text1.getText().trim();
        mssv = MSSV_text1.getText().trim();
        lop = (String) Lop_comboBox_1.getSelectedItem();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ngaySinh = sdf.format(NgaySinh_text.getDate());
        gioiTinh = (String) GioiTinh_ComboBox.getSelectedItem();
        email = Email_text1.getText().trim();

        saveToDatabase();

        HoTen_text1.setEditable(false);
        NgaySinh_text.setEnabled(false);
        GioiTinh_ComboBox.setEnabled(false);
        Email_text1.setEditable(false);
        Lop_comboBox_1.setEnabled(false);
        XoaMonHoc_button.setEnabled(false);

        updateAvatar(gioiTinh);
    }

    // Xử lý sự kiện nút Xóa
    private void Xoa() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa toàn bộ thông tin của sinh viên này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            deleteStudentFromDatabase();
            HoTen_text1.setText("");
            MSSV_text1.setText("");
            Lop_comboBox_1.setSelectedIndex(0);
            NgaySinh_text.setDate(null);
            GioiTinh_ComboBox.setSelectedIndex(0);
            Email_text1.setText("");
            tableModel.setRowCount(0);
            monHocList.clear();
            maMonList.clear();
            soTinList.clear();
            thoiGianList.clear();
            avata.setIcon(null);
            avata.setText("");
            JOptionPane.showMessageDialog(this,
                    "Đã xóa thông tin sinh viên thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Xử lý sự kiện nút Xóa Môn Học
    private void XoaMonHoc() {
        int[] selectedRows = table_khoaHoc.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một môn học để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa các môn học đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        List<Integer> rowsToRemove = new ArrayList<>();
        for (int row : selectedRows) {
            rowsToRemove.add(row);
        }
        rowsToRemove.sort((a, b) -> b - a);
        for (int row : rowsToRemove) {
            monHocList.remove(row);
            maMonList.remove(row);
            soTinList.remove(row);
            thoiGianList.remove(row);
            tableModel.removeRow(row);
        }

        JOptionPane.showMessageDialog(this, "Đã xóa môn học thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    // Xử lý sự kiện nút Nộp Bài Tập
    private void NopBaiTap() {
        String hoTen = getHoTen();
        String mssv = getMssv();
        BaiTap baiTapFrame = new BaiTap(hoTen, mssv);
        baiTapFrame.setVisible(true);
    }

    // Hàm kiểm tra điều kiện để lưu
    private boolean validateInput() {
        if (HoTen_text1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!HoTen_text1.getText().trim().matches("^[a-zA-Z\\sÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠạẢảẤấẦầẨẩẪẫẬậẮắẰằẲẳẴẵẶặẸẹẺẻẼẽẾếỀềỂểỄễỆệỈỉỊịỌọỎỏỐốỒồỔổỖỗỘộỚớỜờỞởỠỡỢợỤụỦủỨứỪừỬửỮữỰựỲỳỴỵỶỷỸỹ]*$")) {
            JOptionPane.showMessageDialog(this, "Họ tên chỉ được chứa chữ cái và khoảng trắng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (MSSV_text1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "MSSV không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!MSSV_text1.getText().matches("^N\\d{2}DCVT\\d{3}$")) {
            JOptionPane.showMessageDialog(this, "MSSV phải có định dạng N + 2 số + DCVT + 3 số (VD: N21DCVT101)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String selectedLop = (String) Lop_comboBox_1.getSelectedItem();
        if (selectedLop == null || selectedLop.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (NgaySinh_text.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Date today = new Date();
        if (NgaySinh_text.getDate().after(today)) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được là ngày trong tương lai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (GioiTinh_ComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (Email_text1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!EMAIL_PATTERN.matcher(Email_text1.getText().trim()).matches()) {
            JOptionPane.showMessageDialog(this, "Email phải có định dạng [số/chữ]@student.ptithcm.edu.vn", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (monHocList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phải có ít nhất một môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!checkScheduleConflict()) {
            JOptionPane.showMessageDialog(this, "Có môn học bị trùng thời gian. Vui lòng kiểm tra lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean checkScheduleConflict() {
        Set<String> selectedTimes = new HashSet<>();
        for (String time : thoiGianList) {
            if (!selectedTimes.add(time)) {
                return false;
            }
        }
        return true;
    }

    // Hàm cập nhật avatar khi thay đổi giới tính
    private void updateAvatar(String gioiTinh) {
        String avatarPath = "/Icon/avata_nam.png";
        if ("Nữ".equalsIgnoreCase(gioiTinh)) {
            avatarPath = "/Icon/avata_nu.png";
        }
        try {
            ImageIcon avatarIcon = new ImageIcon(getClass().getResource(avatarPath));
            Image scaledImage = avatarIcon.getImage().getScaledInstance(224, 227, Image.SCALE_SMOOTH);
            avata.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            avata.setText("Không tìm thấy avatar");
            avata.setHorizontalAlignment(JLabel.CENTER);
        }
    }

    // Hiển thị dữ liệu từ các biến vào giao diện
    private void loadDataToFields() {
        HoTen_text1.setText(hoTen);
        MSSV_text1.setText(mssv);
        Lop_comboBox_1.setSelectedItem(lop);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (ngaySinh != null && !ngaySinh.isEmpty()) {
                NgaySinh_text.setDate(sdf.parse(ngaySinh));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GioiTinh_ComboBox.setSelectedItem(gioiTinh);
        Email_text1.setText(email);

        tableModel.setRowCount(0);
        for (int i = 0; i < monHocList.size(); i++) {
            tableModel.addRow(new Object[]{
                    monHocList.get(i),
                    maMonList.get(i),
                    soTinList.get(i),
                    thoiGianList.get(i)
            });
        }
        updateAvatar(gioiTinh);
    }

    // Tải dữ liệu từ cơ sở dữ liệu và cập nhật giao diện
    private void loadDataFromDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Tải thông tin sinh viên
            try (PreparedStatement pstmtStudent = conn.prepareStatement("SELECT * FROM students WHERE mssv = ?")) {
                pstmtStudent.setString(1, originalMssv);
                try (ResultSet rsStudent = pstmtStudent.executeQuery()) {
                    if (rsStudent.next()) {
                        hoTen = rsStudent.getString("hoten");
                        lop = rsStudent.getString("lop");
                        gioiTinh = rsStudent.getString("gioitinh");
                        email = rsStudent.getString("email");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        ngaySinh = rsStudent.getDate("ngaysinh") != null ? sdf.format(rsStudent.getDate("ngaysinh")) : "";

                        HoTen_text1.setText(hoTen);
                        MSSV_text1.setText(originalMssv);
                        Lop_comboBox_1.setSelectedItem(lop != null ? lop : "");
                        GioiTinh_ComboBox.setSelectedItem(gioiTinh);
                        Email_text1.setText(email);
                        if (!ngaySinh.isEmpty()) {
                            NgaySinh_text.setDate(sdf.parse(ngaySinh));
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin sinh viên với MSSV: " + originalMssv, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            // Tải thông tin khóa học
            monHocList.clear();
            maMonList.clear();
            soTinList.clear();
            thoiGianList.clear();
            tableModel.setRowCount(0);

            try (PreparedStatement pstmtCourse = conn.prepareStatement("SELECT * FROM courses WHERE mssv = ?")) {
                pstmtCourse.setString(1, originalMssv);
                try (ResultSet rsCourse = pstmtCourse.executeQuery()) {
                    while (rsCourse.next()) {
                        String monHoc = rsCourse.getString("monhoc");
                        String maMon = rsCourse.getString("mamon");
                        String soTin = String.valueOf(rsCourse.getInt("sotin"));
                        String thoiGian = rsCourse.getString("thoigian");

                        monHocList.add(monHoc);
                        maMonList.add(maMon);
                        soTinList.add(soTin);
                        thoiGianList.add(thoiGian);

                        tableModel.addRow(new Object[]{monHoc, maMon, soTin, thoiGian});
                    }
                }
            }

            if (monHocList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khóa học với MSSV: " + originalMssv, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày sinh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Lưu thông tin đã chỉnh sửa vào cơ sở dữ liệu
    private void saveToDatabase() {
        Connection conn = null;
        PreparedStatement pstmtBaitap = null;
        PreparedStatement pstmtCourses = null;
        PreparedStatement pstmtDiem = null;
        PreparedStatement pstmtDiemDanh = null;
        PreparedStatement pstmtChamDiem = null;
        PreparedStatement pstmtStudents = null;
        PreparedStatement pstmtInsertStudent = null;
        PreparedStatement pstmtInsertCourse = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            conn.setAutoCommit(false);

            // Xóa bản ghi trong các bảng phụ thuộc trước khi xóa students
            // 1. Xóa bản ghi trong bảng baitap
            String sqlBaitap = "DELETE FROM baitap WHERE mssv = ?";
            pstmtBaitap = conn.prepareStatement(sqlBaitap);
            pstmtBaitap.setString(1, originalMssv);
            pstmtBaitap.executeUpdate();

            // 2. Xóa bản ghi trong bảng diem (nếu tồn tại)
            String sqlDiem = "DELETE FROM diem WHERE mssv = ?";
            pstmtDiem = conn.prepareStatement(sqlDiem);
            pstmtDiem.setString(1, originalMssv);
            pstmtDiem.executeUpdate();

            // 3. Xóa bản ghi trong bảng diemdanh (nếu tồn tại)
            String sqlDiemDanh = "DELETE FROM diemdanh WHERE mssv = ?";
            pstmtDiemDanh = conn.prepareStatement(sqlDiemDanh);
            pstmtDiemDanh.setString(1, originalMssv);
            pstmtDiemDanh.executeUpdate();

            // 4. Xóa bản ghi trong bảng chamdiem (nếu tồn tại)
            String sqlChamDiem = "DELETE FROM chamdiem WHERE mssv = ?";
            pstmtChamDiem = conn.prepareStatement(sqlChamDiem);
            pstmtChamDiem.setString(1, originalMssv);
            pstmtChamDiem.executeUpdate();

            // 5. Xóa bản ghi trong bảng courses
            String sqlCourses = "DELETE FROM courses WHERE mssv = ?";
            pstmtCourses = conn.prepareStatement(sqlCourses);
            pstmtCourses.setString(1, originalMssv);
            pstmtCourses.executeUpdate();

            // 6. Xóa bản ghi trong bảng students
            String sqlStudents = "DELETE FROM students WHERE mssv = ?";
            pstmtStudents = conn.prepareStatement(sqlStudents);
            pstmtStudents.setString(1, originalMssv);
            pstmtStudents.executeUpdate();

            // Thêm thông tin sinh viên mới
            String sqlInsertStudent = "INSERT INTO students (mssv, hoten, ngaysinh, gioitinh, lop, email) VALUES (?, ?, ?, ?, ?, ?)";
            pstmtInsertStudent = conn.prepareStatement(sqlInsertStudent);
            pstmtInsertStudent.setString(1, mssv);
            pstmtInsertStudent.setString(2, hoTen);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = NgaySinh_text.getDate();
            pstmtInsertStudent.setDate(3, date != null ? new java.sql.Date(date.getTime()) : null);
            pstmtInsertStudent.setString(4, gioiTinh);
            pstmtInsertStudent.setString(5, lop);
            pstmtInsertStudent.setString(6, email);
            pstmtInsertStudent.executeUpdate();

            // Thêm thông tin khóa học mới
            String sqlInsertCourse = "INSERT INTO courses (mssv, monhoc, mamon, sotin, thoigian) VALUES (?, ?, ?, ?, ?)";
            pstmtInsertCourse = conn.prepareStatement(sqlInsertCourse);
            for (int i = 0; i < monHocList.size(); i++) {
                pstmtInsertCourse.setString(1, mssv);
                pstmtInsertCourse.setString(2, monHocList.get(i));
                pstmtInsertCourse.setString(3, maMonList.get(i));
                pstmtInsertCourse.setInt(4, Integer.parseInt(soTinList.get(i)));
                pstmtInsertCourse.setString(5, thoiGianList.get(i));
                pstmtInsertCourse.executeUpdate();
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu vào cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số tín phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmtBaitap != null) pstmtBaitap.close();
                if (pstmtCourses != null) pstmtCourses.close();
                if (pstmtDiem != null) pstmtDiem.close();
                if (pstmtDiemDanh != null) pstmtDiemDanh.close();
                if (pstmtChamDiem != null) pstmtChamDiem.close();
                if (pstmtStudents != null) pstmtStudents.close();
                if (pstmtInsertStudent != null) pstmtInsertStudent.close();
                if (pstmtInsertCourse != null) pstmtInsertCourse.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    // Xóa toàn bộ thông tin sinh viên khỏi cơ sở dữ liệu
    private void deleteStudentFromDatabase() {
        Connection conn = null;
        PreparedStatement pstmtBaitap = null;
        PreparedStatement pstmtCourses = null;
        PreparedStatement pstmtDiem = null;
        PreparedStatement pstmtDiemDanh = null;
        PreparedStatement pstmtChamDiem = null;
        PreparedStatement pstmtStudents = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            conn.setAutoCommit(false);

            // Xóa bản ghi trong bảng baitap
            String sqlBaitap = "DELETE FROM baitap WHERE mssv = ?";
            pstmtBaitap = conn.prepareStatement(sqlBaitap);
            pstmtBaitap.setString(1, originalMssv);
            pstmtBaitap.executeUpdate();

            // Xóa bản ghi trong bảng courses
            String sqlCourses = "DELETE FROM courses WHERE mssv = ?";
            pstmtCourses = conn.prepareStatement(sqlCourses);
            pstmtCourses.setString(1, originalMssv);
            pstmtCourses.executeUpdate();

            // Xóa bản ghi trong bảng diem (nếu tồn tại)
            String sqlDiem = "DELETE FROM diem WHERE mssv = ?";
            pstmtDiem = conn.prepareStatement(sqlDiem);
            pstmtDiem.setString(1, originalMssv);
            pstmtDiem.executeUpdate();

            // Xóa bản ghi trong bảng diemdanh (nếu tồn tại)
            String sqlDiemDanh = "DELETE FROM diemdanh WHERE mssv = ?";
            pstmtDiemDanh = conn.prepareStatement(sqlDiemDanh);
            pstmtDiemDanh.setString(1, originalMssv);
            pstmtDiemDanh.executeUpdate();

            // Xóa bản ghi trong bảng chamdiem (nếu tồn tại)
            String sqlChamDiem = "DELETE FROM chamdiem WHERE mssv = ?";
            pstmtChamDiem = conn.prepareStatement(sqlChamDiem);
            pstmtChamDiem.setString(1, originalMssv);
            pstmtChamDiem.executeUpdate();

            // Xóa bản ghi trong bảng students (sau tất cả các bảng phụ thuộc)
            String sqlStudents = "DELETE FROM students WHERE mssv = ?";
            pstmtStudents = conn.prepareStatement(sqlStudents);
            pstmtStudents.setString(1, originalMssv);
            int rowsAffected = pstmtStudents.executeUpdate();

            if (rowsAffected > 0) {
                conn.commit();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên để xóa!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                conn.rollback();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa sinh viên: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (pstmtBaitap != null) pstmtBaitap.close();
                if (pstmtCourses != null) pstmtCourses.close();
                if (pstmtDiem != null) pstmtDiem.close();
                if (pstmtDiemDanh != null) pstmtDiemDanh.close();
                if (pstmtChamDiem != null) pstmtChamDiem.close();
                if (pstmtStudents != null) pstmtStudents.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    // Trả về họ tên sinh viên
    public String getHoTen() {
        return HoTen_text1.getText().trim();
    }

    public String getMssv() {
        return MSSV_text1.getText().trim();
    }

    public String getLop() {
        return lop;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public String getMonHoc() {
        return String.join(", ", monHocList);
    }

    public String getMaMon() {
        return String.join(", ", maMonList);
    }

    public String getSoTin() {
        return String.join(", ", soTinList);
    }

    public String getThoiGian() {
        return String.join(", ", thoiGianList);
    }

    // Khởi chạy ứng dụng
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