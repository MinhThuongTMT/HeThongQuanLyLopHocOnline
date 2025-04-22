package QuanLyLopHoc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QuanLyBaiTap extends JPanel {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> TimMon_comboBox;
    private JTable tableNopBai;
    private JTable tableBaiTap;
    private DefaultTableModel tableModelNopBai;
    private DefaultTableModel tableModelBaiTap;

    private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true&prepareThreshold=0";
    private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
    private static final String DB_PASSWORD = "MinhThuong0808";

    public QuanLyBaiTap() {
        setBackground(new Color(0, 0, 121));
        setBounds(81, 11, 895, 652);
        setLayout(null);

        JLabel lblNewLabel = new JLabel("QUẢN LÝ BÀI TẬP");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(300, 10, 242, 40);
        add(lblNewLabel);

        JButton btnXuat = new JButton("Xuất Excel");
        btnXuat.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnXuat.setBackground(new Color(0, 204, 0));
        btnXuat.setForeground(Color.WHITE);
        btnXuat.setBounds(724, 369, 150, 30);
        add(btnXuat);

        ImageIcon icon = new ImageIcon(getClass().getResource("/Icon/find.png"));
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        JButton btnTimKiem = new JButton("Tìm Kiếm", icon);
        btnTimKiem.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnTimKiem.setForeground(Color.BLACK);
        btnTimKiem.setBounds(576, 72, 131, 30);
        btnTimKiem.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnTimKiem.setVerticalTextPosition(SwingConstants.CENTER);
        add(btnTimKiem);

        TimMon_comboBox = new JComboBox<>();
        TimMon_comboBox.setModel(new DefaultComboBoxModel<>(new String[] { "", "Lập Trình Hướng Đối Tượng",
                "Hệ Thống nhúng IOT", "Hệ Thống Cảm Biến", "Điện Toán Đám Mây", "Phát Triển ứng Dụng", "Tiếng Anh" }));
        TimMon_comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        TimMon_comboBox.setBounds(236, 72, 296, 30);
        add(TimMon_comboBox);

        String[] columnNamesNopBai = { "MSSV", "Họ tên", "Môn học", "Tiêu đề", "Tên tệp", "Thời gian nộp", "Trạng thái" };
        tableModelNopBai = new DefaultTableModel(columnNamesNopBai, 0);
        tableNopBai = new JTable(tableModelNopBai);
        tableNopBai.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tableNopBai.setRowHeight(25);
        tableNopBai.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        tableNopBai.getTableHeader().setBackground(new Color(255, 204, 0));
        tableNopBai.getTableHeader().setForeground(Color.BLACK);

        // Thiết lập kích thước cột cho tableNopBai theo yêu cầu
        tableNopBai.getColumnModel().getColumn(0).setPreferredWidth(130);  // MSSV
        tableNopBai.getColumnModel().getColumn(1).setPreferredWidth(150);  // Họ tên
        tableNopBai.getColumnModel().getColumn(2).setPreferredWidth(250);  // Môn học
        tableNopBai.getColumnModel().getColumn(3).setPreferredWidth(150);  // Tiêu đề
        tableNopBai.getColumnModel().getColumn(4).setPreferredWidth(200);  // Tên tệp
        tableNopBai.getColumnModel().getColumn(5).setPreferredWidth(150);  // Thời gian nộp
        tableNopBai.getColumnModel().getColumn(6).setPreferredWidth(100);   // Trạng thái


        JScrollPane HienThiTrangThai = new JScrollPane(tableNopBai);
        HienThiTrangThai.setBounds(21, 410, 853, 219);
        add(HienThiTrangThai);

        String[] columnNamesBaiTap = { "STT", "Môn học", "Tiêu đề", "Hạn nộp", "Tình Trạng" };
        tableModelBaiTap = new DefaultTableModel(columnNamesBaiTap, 0);
        tableBaiTap = new JTable(tableModelBaiTap);
        tableBaiTap.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tableBaiTap.setRowHeight(25);
        tableBaiTap.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        tableBaiTap.getTableHeader().setBackground(new Color(255, 204, 0));
        tableBaiTap.getTableHeader().setForeground(Color.BLACK);

        tableBaiTap.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableBaiTap.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableBaiTap.getColumnModel().getColumn(2).setPreferredWidth(250);
        tableBaiTap.getColumnModel().getColumn(3).setPreferredWidth(150);
        tableBaiTap.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane HienThiBT = new JScrollPane(tableBaiTap);
        HienThiBT.setBounds(21, 147, 853, 214);
        add(HienThiBT);

        JLabel labelMonHoc = new JLabel("Môn học:");
        labelMonHoc.setForeground(Color.WHITE);
        labelMonHoc.setFont(new Font("Times New Roman", Font.BOLD, 15));
        labelMonHoc.setBounds(141, 72, 85, 30);
        add(labelMonHoc);

        JLabel lblNewLabel_1 = new JLabel("DANH SÁCH NỘP BÀI");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setBounds(21, 372, 172, 27);
        add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("BÀI TẬP ĐÃ GIAO");
        lblNewLabel_1_1.setForeground(Color.WHITE);
        lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel_1_1.setBounds(21, 113, 172, 27);
        add(lblNewLabel_1_1);
        
        JButton btnXoa = new JButton("XÓA");
        btnXoa.setBackground(new Color(255, 128, 0));
        btnXoa.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnXoa.setForeground(new Color(0, 0, 0));
        btnXoa.setBounds(765, 113, 109, 23);
        add(btnXoa);

        loadDataFromDatabase("");
        loadBaiTapDaGiao("");

        btnXoa.addActionListener(e -> Xoa());
        btnTimKiem.addActionListener(e -> timKiem());
        btnXuat.addActionListener(e -> exportToExcel());

        tableBaiTap.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tableBaiTap.getSelectedRow();
                    if (selectedRow != -1) {
                        String monHoc = tableModelBaiTap.getValueAt(selectedRow, 1).toString();
                        String tieuDe = tableModelBaiTap.getValueAt(selectedRow, 2).toString();
                        if (!monHoc.isEmpty() && !tieuDe.equals("Không tìm thấy bài tập nào.")) {
                            loadStudentSubmissionsForAssignment(monHoc, tieuDe);
                        } else {
                            tableModelNopBai.setRowCount(0);
                        }
                    }
                }
            }
        });
    }
    
    private void Xoa() {
    	int selectedRow = tableBaiTap.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Vui lòng chọn một bài tập để xóa!", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String monHoc = tableModelBaiTap.getValueAt(selectedRow, 1).toString();
        String tieuDe = tableModelBaiTap.getValueAt(selectedRow, 2).toString();

        if (monHoc.isEmpty() || tieuDe.equals("Không tìm thấy bài tập nào.")) {
            JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Không có bài tập hợp lệ để xóa!", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(QuanLyBaiTap.this,
                "Bạn có chắc muốn xóa bài tập '" + tieuDe + "' của môn '" + monHoc + "'? Toàn bộ dữ liệu liên quan sẽ bị xóa!",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Connection conn = null;
        PreparedStatement pstmtGiaobaitap = null;
        PreparedStatement pstmtBaitap = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Xóa dữ liệu từ bảng baitap
            String sqlBaitap = "DELETE FROM baitap WHERE monhoc = ? AND tieude = ?";
            pstmtBaitap = conn.prepareStatement(sqlBaitap);
            pstmtBaitap.setString(1, monHoc);
            pstmtBaitap.setString(2, tieuDe);
            pstmtBaitap.executeUpdate();

            // Xóa dữ liệu từ bảng giaobaitap
            String sqlGiaobaitap = "DELETE FROM giaobaitap WHERE mon_hoc = ? AND tieu_de = ?";
            pstmtGiaobaitap = conn.prepareStatement(sqlGiaobaitap);
            pstmtGiaobaitap.setString(1, monHoc);
            pstmtGiaobaitap.setString(2, tieuDe);
            pstmtGiaobaitap.executeUpdate();

            conn.commit(); // Commit transaction

            // Cập nhật lại bảng
            loadBaiTapDaGiao("");
            loadDataFromDatabase("");
            tableModelNopBai.setRowCount(0); // Xóa dữ liệu bảng nộp bài

            JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Xóa bài tập thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(QuanLyBaiTap.this, "Lỗi khi xóa bài tập: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            try {
                if (pstmtBaitap != null) pstmtBaitap.close();
                if (pstmtGiaobaitap != null) pstmtGiaobaitap.close();
                if (conn != null) conn.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
    
    private void timKiem() {
    	String monHoc = (String) TimMon_comboBox.getSelectedItem();
        if (monHoc != null && !monHoc.isEmpty()) {
            loadDataFromDatabase(monHoc);
            loadBaiTapDaGiao(monHoc);
        } else {
            loadDataFromDatabase("");
            loadBaiTapDaGiao("");
        }
    }

    private void loadBaiTapDaGiao(String monHocFilter) {
        tableModelBaiTap.setRowCount(0);
        String sql = "SELECT mon_hoc, tieu_de, han_nop FROM giaobaitap WHERE mon_hoc = ? OR ? = ''";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, monHocFilter);
            pstmt.setString(2, monHocFilter);

            rs = pstmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            int stt = 1;

            while (rs.next()) {
                String monHoc = rs.getString("mon_hoc");
                String tieuDe = rs.getString("tieu_de");
                Timestamp hanNop = rs.getTimestamp("han_nop");

                String tinhTrang = "";
                if (hanNop != null) {
                    Date currentTime = new Date();
                    if (hanNop.before(currentTime)) {
                        tinhTrang = "Hết hạn";
                    }
                }

                tableModelBaiTap.addRow(new Object[] { stt, monHoc, tieuDe, hanNop != null ? sdf.format(hanNop) : "", tinhTrang });
                stt++;
            }

            if (tableModelBaiTap.getRowCount() == 0) {
                tableModelBaiTap.addRow(new Object[] { "", "", "Không tìm thấy bài tập nào.", "", "" });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách bài tập: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    private void loadDataFromDatabase(String monHocFilter) {
        tableModelNopBai.setRowCount(0);
        String sql = "SELECT s.mssv, s.hoten, b.tieude, b.monhoc, b.trangthai, b.thoigian_nop, b.ten_tep "
                + "FROM students s " + "LEFT JOIN baitap b ON s.mssv = b.mssv " + "WHERE b.monhoc = ? OR ? = ''";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, monHocFilter);
            pstmt.setString(2, monHocFilter);

            rs = pstmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

            while (rs.next()) {
                String mssv = rs.getString("mssv");
                String hoTen = rs.getString("hoten");
                String tieuDe = rs.getString("tieude");
                String monHoc = rs.getString("monhoc");
                String trangThai = rs.getString("trangthai");
                Timestamp thoiGianNop = rs.getTimestamp("thoigian_nop");
                String tenTep = rs.getString("ten_tep");

                tableModelNopBai.addRow(new Object[] { mssv, hoTen, monHoc != null ? monHoc : "",
                        tieuDe != null ? tieuDe : "", tenTep != null ? tenTep : "",
                        thoiGianNop != null ? sdf.format(thoiGianNop) : "", trangThai != null ? trangThai : "CHƯA NỘP" });
            }

            if (tableModelNopBai.getRowCount() == 0) {
                tableModelNopBai.addRow(new Object[] { "", "", "", "Không tìm thấy bài tập nào.", "", "", "" });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ database: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    private void loadStudentSubmissionsForAssignment(String monHoc, String tieuDe) {
        tableModelNopBai.setRowCount(0);
        String sql = "SELECT s.mssv, s.hoten, g.mon_hoc, g.tieu_de, b.ten_tep, b.thoigian_nop, g.han_nop " +
                     "FROM students s " +
                     "CROSS JOIN giaobaitap g " +
                     "LEFT JOIN baitap b ON s.mssv = b.mssv AND b.monhoc = g.mon_hoc AND b.tieude = g.tieu_de " +
                     "WHERE g.mon_hoc = ? AND g.tieu_de = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, monHoc);
            pstmt.setString(2, tieuDe);
            rs = pstmt.executeQuery();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date currentTime = new Date();

            while (rs.next()) {
                String mssv = rs.getString("mssv");
                String hoTen = rs.getString("hoten");
                String monHocResult = rs.getString("mon_hoc");
                String tieuDeResult = rs.getString("tieu_de");
                String tenTep = rs.getString("ten_tep");
                Timestamp thoiGianNop = rs.getTimestamp("thoigian_nop");
                Timestamp hanNop = rs.getTimestamp("han_nop");

                String trangThai = "CHƯA NỘP";
                if (tenTep != null) {
                    trangThai = "ĐÃ NỘP";
                } else if (hanNop != null && hanNop.before(currentTime)) {
                    trangThai = "CHƯA NỘP";
                }

                tableModelNopBai.addRow(new Object[] {
                    mssv,
                    hoTen,
                    monHocResult,
                    tieuDeResult,
                    tenTep != null ? tenTep : "",
                    thoiGianNop != null ? sdf.format(thoiGianNop) : "",
                    trangThai
                });
            }

            if (tableModelNopBai.getRowCount() == 0) {
                tableModelNopBai.addRow(new Object[] { "", "", "", "Không tìm thấy sinh viên nào.", "", "", "" });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu sinh viên: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Danh sách bài tập");

                Row headerRow = sheet.createRow(0);
                String[] columns = { "MSSV", "Họ tên", "Môn học", "Tiêu đề", "Tên tệp", "Thời gian nộp", "Trạng thái" };
                for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    CellStyle headerStyle = workbook.createCellStyle();
                    org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                    font.setBold(true);
                    headerStyle.setFont(font);
                    cell.setCellStyle(headerStyle);
                }

                for (int i = 0; i < tableModelNopBai.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < tableModelNopBai.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        Object value = tableModelNopBai.getValueAt(i, j);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                }

                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công tại: " + filePath, "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public int getAssignmentCount() {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String sql = "SELECT COUNT(*) FROM giaobaitap WHERE han_nop >= CURRENT_TIMESTAMP";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đếm bài tập: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
        return count;
    }
}