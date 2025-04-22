package QuanLyLopHoc;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

public class SinhVien extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField HoTen_text;
    private JTextField Mssv_text;
    private JTextField Email_text;
    private JDateChooser NgaySinh;
    private JTextField ThoiGian_text;
    private JTextField MaMon_text;
    private JTextField SoTin_text;
    private JComboBox<String> Lop_ComboBox;
    private JComboBox<String> GioiTinh_ComboBox;
    private List<JCheckBox> subjectCheckBoxes;
    private ThongTinSinhVien tempStudentInfo;

    // Thông tin kết nối database
    private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
    private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
    private static final String DB_PASSWORD = "MinhThuong0808";

    // Danh sách môn học
    private final String[][] subjects = {
            {"Lập Trình Hướng Đối Tượng", "TEL1448", "3", "Thứ 3,Tiết 7-10"},
            {"Hệ Thống nhúng IOT", "TEL1457", "3", "Thứ 2,Tiết 7-10"},
            {"Hệ Thống Cảm Biến", "TEL1467", "3", "Thứ 2,Tiết 1-4"},
            {"Điện Toán Đám Mây", "TEL1447", "2", "Thứ 5,Tiết 7-10"},
            {"Phát Triển ứng Dụng", "TEL1461", "3", "Thứ 4,Tiết 7-10"},
            {"Tiếng Anh", "BAS1160", "4", "Thứ 7,Tiết 1-4"}
    };

    public SinhVien() {
        setBackground(new Color(0, 0, 121));
        setBounds(81, 11, 895, 652);
        setLayout(null);

        JLabel lblNewLabel = new JLabel("SINH VIÊN");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(10, 39, 132, 43);
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

        JLabel MSSV_Label = new JLabel("MSSV :");
        MSSV_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        MSSV_Label.setBounds(19, 127, 81, 29);
        panel.add(MSSV_Label);

        JLabel Lop_Label = new JLabel("LỚP :");
        Lop_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Lop_Label.setBounds(18, 187, 81, 29);
        panel.add(Lop_Label);

        JLabel NgaySinh_label = new JLabel("NGÀY SINH:");
        NgaySinh_label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        NgaySinh_label.setBounds(18, 250, 103, 29);
        panel.add(NgaySinh_label);

        JLabel GioiTinh_Label = new JLabel("GIỚI TÍNH:");
        GioiTinh_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        GioiTinh_Label.setBounds(256, 250, 94, 29);
        panel.add(GioiTinh_Label);

        JLabel Email_Label = new JLabel("EMAIL :");
        Email_Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Email_Label.setBounds(18, 314, 81, 29);
        panel.add(Email_Label);

        HoTen_text = new JTextField();
        HoTen_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        HoTen_text.setBounds(98, 72, 310, 30);
        panel.add(HoTen_text);
        HoTen_text.setColumns(10);

        Mssv_text = new JTextField();
        Mssv_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        Mssv_text.setColumns(10);
        Mssv_text.setBounds(98, 127, 310, 30);
        panel.add(Mssv_text);

        Email_text = new JTextField();
        Email_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        Email_text.setColumns(10);
        Email_text.setBounds(98, 314, 310, 30);
        panel.add(Email_text);

        NgaySinh = new JDateChooser();
        NgaySinh.setBounds(112, 249, 130, 30);
        NgaySinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        NgaySinh.setDateFormatString("dd/MM/yyyy");
        panel.add(NgaySinh);

        String[] items = {"", "Nam", "Nữ"};
        GioiTinh_ComboBox = new JComboBox<>(items);
        GioiTinh_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        GioiTinh_ComboBox.setBounds(343, 250, 65, 29);
        panel.add(GioiTinh_ComboBox);

        String[] items2 = {"", "D21CQVTHI01-N", "D21CQVTVT01-N", "D21CQVTMD01-N", "D22CQVT01-N", "D23CQVT01-N"};
        Lop_ComboBox = new JComboBox<>(items2);
        Lop_ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        Lop_ComboBox.setBounds(98, 187, 310, 29);
        panel.add(Lop_ComboBox);

        // Panel nhập thông tin khóa học
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(454, 85, 432, 448);
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

        subjectCheckBoxes = new ArrayList<>();
        int yPosition = 71;
        for (String[] subject : subjects) {
            JCheckBox checkBox = new JCheckBox(subject[0]);
            checkBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
            checkBox.setBounds(110, yPosition, 296, 30);
            panel_1.add(checkBox);
            subjectCheckBoxes.add(checkBox);
            yPosition += 40;
        }

        ThoiGian_text = new JTextField();
        ThoiGian_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        ThoiGian_text.setColumns(10);
        ThoiGian_text.setBounds(114, 390, 296, 30);
        ThoiGian_text.setEditable(false);
        panel_1.add(ThoiGian_text);

        MaMon_text = new JTextField();
        MaMon_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        MaMon_text.setColumns(10);
        MaMon_text.setBounds(114, 310, 296, 30);
        MaMon_text.setEditable(false);
        panel_1.add(MaMon_text);

        JLabel Lop_Label_1 = new JLabel("MÃ MÔN :");
        Lop_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Lop_Label_1.setBounds(24, 311, 81, 29);
        panel_1.add(Lop_Label_1);

        SoTin_text = new JTextField();
        SoTin_text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        SoTin_text.setColumns(10);
        SoTin_text.setBounds(114, 350, 296, 30);
        SoTin_text.setEditable(false);
        panel_1.add(SoTin_text);

        JLabel Sdt_Label_1 = new JLabel("THỜI GIAN:");
        Sdt_Label_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Sdt_Label_1.setBounds(24, 391, 93, 29);
        panel_1.add(Sdt_Label_1);

        JLabel SoTin_Label_1_1 = new JLabel("SỐ TÍN :");
        SoTin_Label_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        SoTin_Label_1_1.setBounds(24, 351, 103, 29);
        panel_1.add(SoTin_Label_1_1);

        for (JCheckBox checkBox : subjectCheckBoxes) {
            checkBox.addActionListener(e -> updateCourseInfo());
        }

        createTables();

        JButton Luu_button = new JButton("LƯU");
        Luu_button.setBackground(new Color(255, 140, 0));
        Luu_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Luu_button.setBounds(226, 563, 120, 44);
        add(Luu_button);

        JButton xuat_button = new JButton("XUẤT");
        xuat_button.setBackground(new Color(255, 215, 0));
        xuat_button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        xuat_button.setBounds(572, 563, 120, 44);
        xuat_button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(xuat_button);

     // Gắn sự kiện cho các nút
        Luu_button.addActionListener(e -> Luu());
        xuat_button.addActionListener(e -> Xuat());
    }
    
 // Xử lý sự kiện nút LƯU
    private void Luu() {
        if (!validateInput()) {
            return;
        }
        saveToDatabase();
    }

    // Xử lý sự kiện nút XUẤT
    private void Xuat() {
        if (tempStudentInfo == null) {
            JOptionPane.showMessageDialog(this,
                    "Chưa có dữ liệu nào được lưu. Vui lòng nhấn LƯU trước!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            EventQueue.invokeLater(() -> {
                ThongTinSinhVien frame = new ThongTinSinhVien(
                        tempStudentInfo.getHoTen(),
                        tempStudentInfo.getMssv(),
                        tempStudentInfo.getLop(),
                        tempStudentInfo.getNgaySinh(),
                        tempStudentInfo.getGioiTinh(),
                        tempStudentInfo.getEmail(),
                        tempStudentInfo.getMonHoc(),
                        tempStudentInfo.getMaMon(),
                        tempStudentInfo.getSoTin(),
                        tempStudentInfo.getThoiGian());
                frame.setVisible(true);
            });
        }
    }

    //Kiểm tra tính hợp lệ của dữ liệu nhập vào trước khi lưu vào cơ sở dữ liệu
    private boolean validateInput() {
        // Kiểm tra trường rỗng
        if (HoTen_text.getText().trim().isEmpty() ||
                Mssv_text.getText().trim().isEmpty() ||
                Lop_ComboBox.getSelectedIndex() == 0 ||
                NgaySinh.getDate() == null ||
                GioiTinh_ComboBox.getSelectedIndex() == 0 ||
                Email_text.getText().trim().isEmpty() ||
                !isAnySubjectSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ tất cả các thông tin và chọn ít nhất một môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra định dạng họ tên
        String hoTen = HoTen_text.getText().trim();
        if (!hoTen.matches("^[a-zA-Z\\sÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠạẢảẤấẦầẨẩẪẫẬậẮắẰằẲẳẴẵẶặẸẹẺẻẼẽẾếỀềỂểỄễỆệỈỉỊịỌọỎỏỐốỒồỔổỖỗỘộỚớỜờỞởỠỡỢợỤụỦủỨứỪừỬửỮữỰựỲỳỴỵỶỷỸỹ]*$")) {
            JOptionPane.showMessageDialog(this, "Họ tên chỉ được chứa chữ cái và khoảng trắng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra định dạng MSSV
        String mssv = Mssv_text.getText().trim();
        if (!mssv.matches("^N\\d{2}DCVT\\d{3}$")) {
            JOptionPane.showMessageDialog(this, "MSSV phải có định dạng N + 2 số + DCVT + 3 số (VD: N21DCVT101)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra định dạng Email
        String email = Email_text.getText().trim();
        if (!email.matches("^[a-zA-Z0-9]+@student\\.ptithcm\\.edu\\.vn$")) {
            JOptionPane.showMessageDialog(this, "Email phải có định dạng [số/chữ]@student.ptithcm.edu.vn", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày sinh không trong tương lai
        Date today = new Date();
        if (NgaySinh.getDate().after(today)) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được là ngày trong tương lai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra xung đột thời gian môn học
        if (!checkScheduleConflict()) {
            JOptionPane.showMessageDialog(this, "Có môn học bị trùng thời gian. Vui lòng chọn lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    //Kiểm tra xem các môn học được chọn có trùng thời gian hay không
    private boolean checkScheduleConflict() {
        Set<String> selectedTimes = new HashSet<>();
        for (int i = 0; i < subjectCheckBoxes.size(); i++) {
            if (subjectCheckBoxes.get(i).isSelected()) {
                String time = subjects[i][3];
                if (!selectedTimes.add(time)) {
                    return false; // Trùng thời gian
                }
            }
        }
        return true;
    }

    //Cập nhật thông tin môn học khi người dùng chọn môn
    private void updateCourseInfo() {
        List<String> selectedMaMon = new ArrayList<>();
        List<String> selectedSoTin = new ArrayList<>();
        List<String> selectedThoiGian = new ArrayList<>();

        for (int i = 0; i < subjectCheckBoxes.size(); i++) {
            if (subjectCheckBoxes.get(i).isSelected()) {
                selectedMaMon.add(subjects[i][1]);
                selectedSoTin.add(subjects[i][2]);
                selectedThoiGian.add(subjects[i][3]);
            }
        }

        MaMon_text.setText(String.join(", ", selectedMaMon));
        SoTin_text.setText(String.join(", ", selectedSoTin));
        ThoiGian_text.setText(String.join(", ", selectedThoiGian));
    }

    //Kiểm tra xem có ít nhất một môn học được chọn
    private boolean isAnySubjectSelected() {
        for (JCheckBox checkBox : subjectCheckBoxes) {
            if (checkBox.isSelected()) {
                return true;
            }
        }
        return false;
    }

    //Tạo hai bảng students và courses trong cơ sở dữ liệu
    private void createTables() {
        Connection conn = null; 
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            stmt = conn.createStatement();

            //Tạo bảng students để lưu thông tin sinh viên
            String sqlStudents = "CREATE TABLE IF NOT EXISTS students (" +
                    "mssv VARCHAR(50) PRIMARY KEY," +
                    "hoten VARCHAR(100)," +
                    "ngaysinh DATE," +
                    "gioitinh VARCHAR(10)," +
                    "lop VARCHAR(50)," +
                    "email VARCHAR(100))";
            stmt.executeUpdate(sqlStudents);

            //Tạo bảng courses để lưu thông tin môn học của sinh viên
            String sqlCourses = "CREATE TABLE IF NOT EXISTS courses (" +
                    "id SERIAL PRIMARY KEY," +
                    "mssv VARCHAR(50) REFERENCES students(mssv)," +
                    "monhoc VARCHAR(100)," +
                    "mamon VARCHAR(50)," +
                    "sotin INTEGER," +
                    "thoigian VARCHAR(50))";
            stmt.executeUpdate(sqlCourses);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo bảng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveToDatabase() {
        Connection conn = null;
        PreparedStatement pstmtStudent = null;
        PreparedStatement pstmtCourse = null;
        PreparedStatement pstmtCheck = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            String mssv = Mssv_text.getText().trim();

            // Kiểm tra MSSV đã tồn tại chưa
            String checkMssvSql = "SELECT mssv FROM students WHERE mssv = ?";
            pstmtCheck = conn.prepareStatement(checkMssvSql);
            pstmtCheck.setString(1, mssv);
            rs = pstmtCheck.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "MSSV đã tồn tại trong cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                conn.rollback();
                return;
            }

            // Lưu thông tin sinh viên
            String sqlStudent = "INSERT INTO students (mssv, hoten, ngaysinh, gioitinh, lop, email) VALUES (?, ?, ?, ?, ?, ?)";
            pstmtStudent = conn.prepareStatement(sqlStudent);
            pstmtStudent.setString(1, mssv);
            pstmtStudent.setString(2, HoTen_text.getText().trim());
            
            Date date = NgaySinh.getDate();
            if (date != null) {
                pstmtStudent.setDate(3, new java.sql.Date(date.getTime()));
            } else {
                pstmtStudent.setNull(3, java.sql.Types.DATE);
            }
            
            pstmtStudent.setString(4, GioiTinh_ComboBox.getSelectedItem().toString());
            pstmtStudent.setString(5, Lop_ComboBox.getSelectedItem().toString());
            pstmtStudent.setString(6, Email_text.getText().trim());
            pstmtStudent.executeUpdate();

            // Lưu thông tin môn học
            String sqlCourse = "INSERT INTO courses (mssv, monhoc, mamon, sotin, thoigian) VALUES (?, ?, ?, ?, ?)";
            pstmtCourse = conn.prepareStatement(sqlCourse);
            
            List<String> selectedSubjects = new ArrayList<>();
            List<String> selectedMaMon = new ArrayList<>();
            List<String> selectedSoTin = new ArrayList<>();
            List<String> selectedThoiGian = new ArrayList<>();

            for (int i = 0; i < subjectCheckBoxes.size(); i++) {
                if (subjectCheckBoxes.get(i).isSelected()) {
                    pstmtCourse.setString(1, mssv);
                    pstmtCourse.setString(2, subjects[i][0]);
                    pstmtCourse.setString(3, subjects[i][1]);
                    pstmtCourse.setInt(4, Integer.parseInt(subjects[i][2]));
                    pstmtCourse.setString(5, subjects[i][3]);
                    pstmtCourse.executeUpdate();

                    selectedSubjects.add(subjects[i][0]);
                    selectedMaMon.add(subjects[i][1]);
                    selectedSoTin.add(subjects[i][2]);
                    selectedThoiGian.add(subjects[i][3]);
                }
            }

            // Lưu dữ liệu vào biến tạm
            SimpleDateFormat sdfDisplay = new SimpleDateFormat("dd/MM/yyyy");
    String ngaySinhDisplay = date != null ? sdfDisplay.format(date) : "";
            tempStudentInfo = new ThongTinSinhVien(
                    HoTen_text.getText().trim(),
                    mssv,
                    Lop_ComboBox.getSelectedItem().toString(),
                    ngaySinhDisplay,
                    GioiTinh_ComboBox.getSelectedItem().toString(),
                    Email_text.getText().trim(),
                    String.join(", ", selectedSubjects),
                    String.join(", ", selectedMaMon),
                    String.join(", ", selectedSoTin),
                    String.join(", ", selectedThoiGian));

            conn.commit(); // Hoàn tất giao dịch
            JOptionPane.showMessageDialog(this, "Lưu thông tin thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            clearForm();

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu vào cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số tín phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmtCheck != null) pstmtCheck.close();
                if (pstmtStudent != null) pstmtStudent.close();
                if (pstmtCourse != null) pstmtCourse.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    //Xóa toàn bộ dữ liệu trên giao diện để chuẩn bị cho lần nhập mới
    private void clearForm() {
        HoTen_text.setText("");
        Mssv_text.setText("");
        Email_text.setText("");
        NgaySinh.setDate(null);
        ThoiGian_text.setText("");
        MaMon_text.setText("");
        SoTin_text.setText("");
        Lop_ComboBox.setSelectedIndex(0);
        GioiTinh_ComboBox.setSelectedIndex(0);
        for (JCheckBox checkBox : subjectCheckBoxes) {
            checkBox.setSelected(false);
        }
    }
}