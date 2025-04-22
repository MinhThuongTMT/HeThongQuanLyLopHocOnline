package QuanLyLopHoc;

import java.awt.Color;
import java.awt.Font;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.toedter.calendar.JDateChooser;

public class BaiTap extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField giangVienText;
    private JTextField mssvText;
    private JTextField hoTenText;
    private JLabel linkNopLabel;
    private JDateChooser ngayNopChooser;
    private TimePicker timePicker;
    private JComboBox<String> monComboBox;
    private JComboBox<String> tieuDeComboBox;
    private JTextArea noiDungTextArea;

    // Thông tin kết nối database
    private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
    private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
    private static final String DB_PASSWORD = "MinhThuong0808";

    public BaiTap(String hoTen, String mssv) {
        // Khởi tạo cơ sở dữ liệu và giao diện
        initializeDatabase();
        initializeUI(hoTen, mssv);
        checkPreviousSubmission(mssv);
    }

    // Tạo bảng Baitap nếu chưa tồn tại
    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sqlBaitap = "CREATE TABLE IF NOT EXISTS Baitap (" +
                    "id SERIAL PRIMARY KEY," +
                    "mssv VARCHAR(50) REFERENCES students(mssv)," +
                    "monhoc VARCHAR(100)," +
                    "tieude VARCHAR(100)," +
                    "trangthai VARCHAR(20)," +
                    "thoigian_nop TIMESTAMP," +
                    "ten_tep VARCHAR(255))";
            stmt.executeUpdate(sqlBaitap);
        } catch (SQLException e) {
            showErrorMessage("Lỗi khi kết nối database: " + e.getMessage());
        }
    }

    // Khởi tạo giao diện người dùng
    private void initializeUI(String hoTen, String mssv) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 700);
        setLocationRelativeTo(null);
        setTitle("Nộp Bài Tập");

        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 121));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Panel tiêu đề
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 204, 0));
        titlePanel.setBounds(0, 0, 886, 60);
        titlePanel.setLayout(null);
        contentPane.add(titlePanel);

        JLabel titleLabel = new JLabel("NỘP BÀI TẬP");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setBounds(350, 15, 200, 40);
        titlePanel.add(titleLabel);

        // Thông tin sinh viên
        JLabel mssvLabel = new JLabel("MSSV:");
        mssvLabel.setForeground(Color.WHITE);
        mssvLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        mssvLabel.setBounds(30, 80, 80, 30);
        contentPane.add(mssvLabel);

        mssvText = new JTextField(mssv);
        mssvText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        mssvText.setEditable(false);
        mssvText.setBounds(120, 80, 300, 30);
        contentPane.add(mssvText);

        JLabel hoTenLabel = new JLabel("HỌ TÊN:");
        hoTenLabel.setForeground(Color.WHITE);
        hoTenLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        hoTenLabel.setBounds(450, 80, 80, 30);
        contentPane.add(hoTenLabel);

        hoTenText = new JTextField(hoTen);
        hoTenText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        hoTenText.setEditable(false);
        hoTenText.setBounds(560, 80, 290, 30);
        contentPane.add(hoTenText);

        // Thông tin bài tập
        JLabel monHocLabel = new JLabel("MÔN HỌC:");
        monHocLabel.setForeground(Color.WHITE);
        monHocLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        monHocLabel.setBounds(30, 130, 94, 30);
        contentPane.add(monHocLabel);

     //  Khởi tạo monComboBox với danh sách môn học từ cơ sở dữ liệu
        monComboBox = new JComboBox<>();
        monComboBox.addItem(""); // Thêm mục rỗng mặc định
        loadMonHocFromDatabase(mssv); // Gọi phương thức để tải danh sách môn học
        monComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        monComboBox.setBounds(120, 130, 300, 30);
        contentPane.add(monComboBox);

        JLabel giangVienLabel = new JLabel("GIẢNG VIÊN:");
        giangVienLabel.setForeground(Color.WHITE);
        giangVienLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        giangVienLabel.setBounds(450, 130, 100, 30);
        contentPane.add(giangVienLabel);

        giangVienText = new JTextField();
        giangVienText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        giangVienText.setEditable(false);
        giangVienText.setBounds(560, 130, 290, 30);
        contentPane.add(giangVienText);

        JLabel tieuDeLabel = new JLabel("TIÊU ĐỀ:");
        tieuDeLabel.setForeground(Color.WHITE);
        tieuDeLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        tieuDeLabel.setBounds(30, 180, 80, 30);
        contentPane.add(tieuDeLabel);

        tieuDeComboBox = new JComboBox<>();
        tieuDeComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tieuDeComboBox.setBounds(120, 180, 730, 30);
        contentPane.add(tieuDeComboBox);

        JLabel noiDungLabel = new JLabel("NỘI DUNG BÀI TẬP:");
        noiDungLabel.setForeground(Color.WHITE);
        noiDungLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        noiDungLabel.setBounds(30, 220, 175, 30);
        contentPane.add(noiDungLabel);

        noiDungTextArea = new JTextArea();
        noiDungTextArea.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        noiDungTextArea.setEditable(false);
        noiDungTextArea.setLineWrap(true);
        noiDungTextArea.setWrapStyleWord(true);
        noiDungTextArea.setBounds(30, 260, 820, 200);
        contentPane.add(noiDungTextArea);

        // Chọn tệp bài nộp
        JButton chonTepButton = new JButton("Chọn Tệp");
        chonTepButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        chonTepButton.setBackground(new Color(255, 255, 255));
        chonTepButton.setBounds(30, 480, 120, 35);
        contentPane.add(chonTepButton);

        linkNopLabel = new JLabel("");
        linkNopLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        linkNopLabel.setBackground(Color.WHITE);
        linkNopLabel.setOpaque(true);
        linkNopLabel.setBorder(new LineBorder(Color.BLACK, 1));
        linkNopLabel.setBounds(160, 480, 300, 35);
        contentPane.add(linkNopLabel);

        // Hạn nộp
        JLabel hanNopLabel = new JLabel("HẠN NỘP:");
        hanNopLabel.setForeground(Color.WHITE);
        hanNopLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        hanNopLabel.setBounds(480, 480, 80, 35);
        contentPane.add(hanNopLabel);

        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setDisplaySpinnerButtons(true);
        timeSettings.setFormatForDisplayTime("hh:mm a");
        timePicker = new TimePicker(timeSettings);
        timePicker.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        timePicker.setBounds(560, 480, 100, 35);
        timePicker.setEnabled(false);
        contentPane.add(timePicker);

        ngayNopChooser = new JDateChooser();
        ngayNopChooser.setForeground(new Color(0, 0, 0));
        ngayNopChooser.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        ngayNopChooser.setDateFormatString("dd/MM/yyyy");
        ngayNopChooser.setBounds(680, 480, 170, 35);
        ngayNopChooser.setEnabled(false);
        contentPane.add(ngayNopChooser);

        // Nút điều khiển
        JButton nopBaiButton = new JButton("NỘP BÀI");
        nopBaiButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        nopBaiButton.setBackground(new Color(0, 204, 0));
        nopBaiButton.setForeground(Color.WHITE);
        nopBaiButton.setBounds(279, 550, 140, 40);
        contentPane.add(nopBaiButton);

        JButton huyButton = new JButton("HỦY");
        huyButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        huyButton.setBackground(new Color(255, 51, 51));
        huyButton.setForeground(Color.WHITE);
        huyButton.setBounds(520, 550, 140, 40);
        contentPane.add(huyButton);

        // Thêm sự kiện
        monComboBox.addActionListener(e -> updateTieuDeComboBox());
        tieuDeComboBox.addActionListener(e -> updateAssignmentDetails());
        chonTepButton.addActionListener(e -> chooseFile());
        nopBaiButton.addActionListener(e -> submitAssignment());
        huyButton.addActionListener(e -> cancelSubmission());
    }
    
 // phương thức để tải danh sách môn học từ cơ sở dữ liệu
    private void loadMonHocFromDatabase(String mssv) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Truy vấn danh sách môn học từ bảng courses dựa trên mssv
            PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT monhoc FROM courses WHERE mssv = ?");
            pstmt.setString(1, mssv);
            ResultSet rs = pstmt.executeQuery();

            // Xóa các mục cũ trong monComboBox (trừ mục rỗng mặc định)
            monComboBox.removeAllItems();
            monComboBox.addItem("");

            // Thêm các môn học từ cơ sở dữ liệu vào monComboBox
            while (rs.next()) {
                String monHoc = rs.getString("monhoc");
                if (monHoc != null && !monHoc.isEmpty()) {
                    monComboBox.addItem(monHoc);
                }
            }
            rs.close();
            pstmt.close();

            // Thông báo nếu không tìm thấy môn học
            if (monComboBox.getItemCount() <= 1) { // Chỉ có mục rỗng
                showWarningMessage("Không tìm thấy môn học nào cho MSSV: " + mssv);
            }
        } catch (SQLException ex) {
            showErrorMessage("Lỗi khi tải danh sách môn học: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Cập nhật danh sách tiêu đề bài tập và tên giảng viên dựa trên môn học
    private void updateTieuDeComboBox() {
        String selectedMon = (String) monComboBox.getSelectedItem();
        String mssv = mssvText.getText().trim();
        tieuDeComboBox.removeAllItems();
        tieuDeComboBox.addItem("");
        giangVienText.setText("");
        noiDungTextArea.setText("");
        ngayNopChooser.setDate(null);
        timePicker.setText("");
        linkNopLabel.setText("");

        if (selectedMon != null && !selectedMon.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                // Lấy tên giảng viên cho môn học
                PreparedStatement lecturerStmt = conn.prepareStatement(
                        "SELECT DISTINCT ten_giang_vien FROM giaobaitap WHERE mon_hoc = ?");
                lecturerStmt.setString(1, selectedMon);
                ResultSet lecturerRs = lecturerStmt.executeQuery();
                if (lecturerRs.next()) {
                    String tenGiangVien = lecturerRs.getString("ten_giang_vien");
                    giangVienText.setText(tenGiangVien != null ? tenGiangVien : "");
                } else {
                    showWarningMessage("Không tìm thấy thông tin giảng viên cho môn này!");
                }
                lecturerRs.close();
                lecturerStmt.close();

                // Lấy danh sách tiêu đề bài tập
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT tieu_de FROM giaobaitap WHERE mon_hoc = ?");
                pstmt.setString(1, selectedMon);
                ResultSet rs = pstmt.executeQuery();
                boolean hasAssignments = false;
                while (rs.next()) {
                    tieuDeComboBox.addItem(rs.getString("tieu_de"));
                    hasAssignments = true;
                }
                rs.close();
                pstmt.close();

                if (!hasAssignments) {
                    showWarningMessage("Không tìm thấy bài tập cho môn này!");
                } else {
                    // Kiểm tra bài nộp trước đó cho môn học này
                    PreparedStatement checkStmt = conn.prepareStatement(
                            "SELECT ten_tep, tieude FROM Baitap WHERE mssv = ? AND monhoc = ?");
                    checkStmt.setString(1, mssv);
                    checkStmt.setString(2, selectedMon);
                    ResultSet checkRs = checkStmt.executeQuery();
                    if (checkRs.next()) {
                        String tenTep = checkRs.getString("ten_tep");
                        String tieuDe = checkRs.getString("tieude");
                        linkNopLabel.setText(tenTep);
                        tieuDeComboBox.setSelectedItem(tieuDe); // Chọn tiêu đề đã nộp
                    }
                    checkRs.close();
                    checkStmt.close();
                }
            } catch (SQLException ex) {
                showErrorMessage("Lỗi khi lấy thông tin: " + ex.getMessage());
            }
        }
    }

    // Cập nhật thông tin chi tiết bài tập khi chọn tiêu đề
    private void updateAssignmentDetails() {
        String selectedMon = (String) monComboBox.getSelectedItem();
        String selectedTieuDe = (String) tieuDeComboBox.getSelectedItem();
        String mssv = mssvText.getText().trim();
        noiDungTextArea.setText("");
        ngayNopChooser.setDate(null);
        timePicker.setText("");
        linkNopLabel.setText("");

        if (selectedMon != null && !selectedMon.isEmpty() && selectedTieuDe != null && !selectedTieuDe.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                // Lấy thông tin bài tập
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT noi_dung, han_nop FROM giaobaitap WHERE mon_hoc = ? AND tieu_de = ?");
                pstmt.setString(1, selectedMon);
                pstmt.setString(2, selectedTieuDe);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String noiDung = rs.getString("noi_dung");
                    Timestamp hanNop = rs.getTimestamp("han_nop");

                    // Hiển thị nội dung bài tập
                    if (noiDung == null || noiDung.isEmpty()) {
                        showWarningMessage("Không tìm thấy nội dung bài tập!");
                    } else {
                        noiDungTextArea.setText(noiDung);
                    }
                    if (hanNop != null) {
                        ngayNopChooser.setDate(new Date(hanNop.getTime()));
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                        timePicker.setText(sdf.format(hanNop));
                    } else {
                        showWarningMessage("Không tìm thấy hạn nộp!");
                    }
                } else {
                    showWarningMessage("Không tìm thấy thông tin bài tập!");
                }
                rs.close();
                pstmt.close();

                // Kiểm tra bài nộp trước đó cho tiêu đề này
                PreparedStatement checkStmt = conn.prepareStatement(
                        "SELECT ten_tep FROM Baitap WHERE mssv = ? AND monhoc = ? AND tieude = ?");
                checkStmt.setString(1, mssv);
                checkStmt.setString(2, selectedMon);
                checkStmt.setString(3, selectedTieuDe);
                ResultSet checkRs = checkStmt.executeQuery();
                if (checkRs.next()) {
                    String tenTep = checkRs.getString("ten_tep");
                    linkNopLabel.setText(tenTep);
                }
                checkRs.close();
                checkStmt.close();
            } catch (SQLException ex) {
                showErrorMessage("Lỗi khi lấy thông tin bài tập: " + ex.getMessage());
            }
        }
    }

    // Chọn tệp bài nộp
    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Files", "java", "pdf", "docx"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            linkNopLabel.setText(selectedFile.getName());
        }
    }

    // Nộp bài tập
    private void submitAssignment() {
        String monHoc = (String) monComboBox.getSelectedItem();
        String tieuDe = (String) tieuDeComboBox.getSelectedItem();
        String mssv = mssvText.getText().trim();
        String hoTen = hoTenText.getText().trim();
        String tenTep = linkNopLabel.getText().trim();

        // Kiểm tra dữ liệu đầu vào
        if (monHoc == null || monHoc.isEmpty()) {
            showErrorMessage("Vui lòng chọn môn học!");
            return;
        }
        if (tieuDe == null || tieuDe.isEmpty()) {
            showErrorMessage("Vui lòng chọn tiêu đề bài tập!");
            return;
        }
        if (tenTep.isEmpty()) {
            showErrorMessage("Vui lòng chọn tệp bài làm!");
            return;
        }
        if (mssv.isEmpty() || hoTen.isEmpty()) {
            showErrorMessage("Thông tin sinh viên không hợp lệ!");
            return;
        }

        // Kiểm tra MSSV
        if (!isMssvExists(mssv)) {
            showErrorMessage("MSSV không tồn tại trong hệ thống!");
            return;
        }

        // Kiểm tra bài nộp trùng lặp
        if (isSubmissionExists(mssv, monHoc, tieuDe)) {
            showErrorMessage("Bạn đã nộp bài cho bài tập này!");
            return;
        }

        // Kiểm tra hạn nộp
        Date hanNop = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT han_nop FROM giaobaitap WHERE mon_hoc = ? AND tieu_de = ?")) {
            pstmt.setString(1, monHoc);
            pstmt.setString(2, tieuDe);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("han_nop");
                    if (timestamp != null) {
                        hanNop = new Date(timestamp.getTime());
                    }
                }
            }
        } catch (SQLException ex) {
            showErrorMessage("Lỗi khi kiểm tra hạn nộp: " + ex.getMessage());
            return;
        }

        if (hanNop == null) {
            showErrorMessage("Không tìm thấy thông tin hạn nộp!");
            return;
        }

        Date thoiGianNop = new Date();
        if (thoiGianNop.after(hanNop)) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bài tập đã quá hạn nộp. Bạn vẫn muốn nộp bài muộn?",
                    "Xác nhận nộp muộn", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // Xác định trạng thái nộp bài
        String trangThai = thoiGianNop.after(hanNop) ? "NỘP MUỘN" : "ĐÃ NỘP";

        // Lưu bài nộp vào cơ sở dữ liệu
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO Baitap (mssv, monhoc, tieude, trangthai, thoigian_nop, ten_tep) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, mssv);
            pstmt.setString(2, monHoc);
            pstmt.setString(3, tieuDe);
            pstmt.setString(4, trangThai);
            pstmt.setTimestamp(5, new Timestamp(thoiGianNop.getTime()));
            pstmt.setString(6, tenTep);
            pstmt.executeUpdate();

            // Hiển thị thông báo thành công
            showInfoMessage("Nộp bài thành công!\n" +
                    "MSSV: " + mssv + "\n" +
                    "Họ tên: " + hoTen + "\n" +
                    "Môn: " + monHoc + "\n" +
                    "Tiêu đề: " + tieuDe + "\n" +
                    "Tệp: " + tenTep + "\n" +
                    "Trạng thái: " + trangThai);
            dispose();
        } catch (SQLException ex) {
            showErrorMessage("Lỗi khi lưu bài nộp: " + ex.getMessage());
        }
    }

    // Hủy bài nộp và xóa tệp đã nộp
    private void cancelSubmission() {
        String monHoc = (String) monComboBox.getSelectedItem();
        String tieuDe = (String) tieuDeComboBox.getSelectedItem();
        String mssv = mssvText.getText().trim();

        if (monHoc != null && !monHoc.isEmpty() && tieuDe != null && !tieuDe.isEmpty()) {
            // Kiểm tra xem có bài nộp nào cho môn học và tiêu đề này không
            if (isSubmissionExists(mssv, monHoc, tieuDe)) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc muốn xóa bài nộp cho bài tập này?",
                        "Xác nhận xóa bài nộp", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                         PreparedStatement pstmt = conn.prepareStatement(
                                 "DELETE FROM Baitap WHERE mssv = ? AND monhoc = ? AND tieude = ?")) {
                        pstmt.setString(1, mssv);
                        pstmt.setString(2, monHoc);
                        pstmt.setString(3, tieuDe);
                        pstmt.executeUpdate();

                        // Cập nhật lại giao diện
                        linkNopLabel.setText("");
                        showInfoMessage("Đã xóa bài nộp thành công!");
                        updateTieuDeComboBox(); // Làm mới danh sách tiêu đề
                        updateAssignmentDetails(); // Làm mới chi tiết bài tập
                    } catch (SQLException ex) {
                        showErrorMessage("Lỗi khi xóa bài nộp: " + ex.getMessage());
                    }
                }
            }
        }
        dispose(); // Đóng cửa sổ
    }

    // Kiểm tra MSSV có tồn tại trong bảng students
    private boolean isMssvExists(String mssv) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT 1 FROM students WHERE mssv = ?")) {
            pstmt.setString(1, mssv);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            showErrorMessage("Lỗi khi kiểm tra MSSV: " + ex.getMessage());
            return false;
        }
    }

    // Kiểm tra xem sinh viên đã nộp bài cho bài tập này chưa
    private boolean isSubmissionExists(String mssv, String monHoc, String tieuDe) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT 1 FROM Baitap WHERE mssv = ? AND monhoc = ? AND tieude = ?")) {
            pstmt.setString(1, mssv);
            pstmt.setString(2, monHoc);
            pstmt.setString(3, tieuDe);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            showErrorMessage("Lỗi khi kiểm tra bài nộp: " + ex.getMessage());
            return false;
        }
    }

    // Kiểm tra các bài tập đã nộp trước đó
    private void checkPreviousSubmission(String mssv) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT tieude, monhoc, trangthai, ten_tep, thoigian_nop FROM Baitap WHERE mssv = ?")) {
            pstmt.setString(1, mssv);
            try (ResultSet rs = pstmt.executeQuery()) {
                StringBuilder message = new StringBuilder("Danh sách bài đã nộp:\n\n");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                boolean hasSubmission = false;
                while (rs.next()) {
                    hasSubmission = true;
                    message.append("Môn: ").append(rs.getString("monhoc")).append("\n")
                            .append("Tiêu đề: ").append(rs.getString("tieude")).append("\n")
                            .append("Tệp: ").append(rs.getString("ten_tep")).append("\n")
                            .append("Thời gian nộp: ").append(sdf.format(rs.getTimestamp("thoigian_nop"))).append("\n")
                            .append("Trạng thái: ").append(rs.getString("trangthai")).append("\n\n");
                }
                if (hasSubmission) {
                    showInfoMessage(message.toString());
                }
            }
        } catch (SQLException ex) {
            showErrorMessage("Lỗi khi kiểm tra bài đã nộp: " + ex.getMessage());
        }
    }

    // Hiển thị thông báo lỗi
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    // Hiển thị thông báo cảnh báo
    private void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }

    // Hiển thị thông báo thông tin
    private void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông tin", JOptionPane.INFORMATION_MESSAGE);
    }

    public BaiTap() {
        this("", "");
    }

    public static void main(String[] args) {
        new BaiTap().setVisible(true);
    }
}