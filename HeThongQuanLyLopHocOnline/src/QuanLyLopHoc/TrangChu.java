package QuanLyLopHoc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class TrangChu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel TrangChinh;
    private Component[] initialComponents;
    private JButton[] menuButtons;
    private Color defaultColor = new Color(255, 204, 0);
    private Color selectedColor = new Color(255, 255, 0);
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");
    private JTable calendarTable;
    private JLabel monthYearLabel;
    private DefaultTableModel tableModel;
    private Calendar today = Calendar.getInstance();
    private JPanel panel_SV;
    private JPanel panel_GV;
    private JLabel lbl_studentCount; 
    private JLabel lbl_teacherCount; 
    private JLabel lbl_assignmentCount;

    // Khởi tạo cửa sổ chính (dashboard)
    public TrangChu() {
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        setLocationRelativeTo(null); 
   
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 121)); 
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); 
        setContentPane(contentPane);
        contentPane.setLayout(null); 

        JPanel panel = new JPanel();
        panel.setBackground(defaultColor);
        panel.setBounds(0, 0, 76, 663); 
        contentPane.add(panel);
        panel.setLayout(null); 

        JButton TrangChu_Button = createButton("/Icon/house.png", 93); 
        JButton Student_Button = createButton("/Icon/student.png", 159); 
        JButton Teach_Button = createButton("/Icon/teach.png", 246); 
        JButton Manage_Button = createButton("/Icon/manager.png", 319); 
        JButton BaiTap_Button = createButton("/Icon/BaiTap.png", 394); 
        JButton Exit_Button = createButton("/Icon/EXIT.png", 586);

        // Lưu các nút vào mảng để quản lý dễ dàng
        menuButtons = new JButton[] { TrangChu_Button, Student_Button, Teach_Button, Manage_Button, BaiTap_Button,
                Exit_Button };

        // Thêm các nút vào bảng điều hướng
        for (JButton login_bnt : menuButtons) {
            panel.add(login_bnt);
        }

        // Tạo menu ngữ cảnh (popup menu) cho nút Quản Lý
        JPopupMenu manageMenu = new JPopupMenu();
        JMenuItem manageStudentItem = new JMenuItem("Quản Lý Sinh Viên"); 
        JMenuItem manageTeacherItem = new JMenuItem("Quản Lý Giảng Viên"); 
        JMenuItem manageClassItem = new JMenuItem("Quản Lý Lớp Học"); 

        manageMenu.add(manageStudentItem);
        manageMenu.add(manageTeacherItem);
        manageMenu.add(manageClassItem);

        // Thêm MouseListener để hiển thị menu ngữ cảnh khi nhấp vào nút Quản Lý
        Manage_Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manageMenu.show(Manage_Button, 0, Manage_Button.getHeight()); 
            }
        });

        // ActionListener cho mục Quản Lý Sinh Viên
        manageStudentItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButtonColors(); 
                Manage_Button.setBackground(selectedColor); 
                TrangChinh.removeAll();
                QuanLySinhVien QLstudentPanel = new QuanLySinhVien(); 
                QLstudentPanel.setBounds(0, 0, 895, 652); 
                TrangChinh.add(QLstudentPanel); 
                TrangChinh.revalidate(); 
                TrangChinh.repaint(); 
            }
        });

        // ActionListener cho mục Quản Lý Giảng Viên
        manageTeacherItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButtonColors(); 
                Manage_Button.setBackground(selectedColor); 
                TrangChinh.removeAll(); 
                QuanLyGiangVien QLteachPanel = new QuanLyGiangVien(); 
                QLteachPanel.setBounds(0, 0, 895, 652); 
                TrangChinh.add(QLteachPanel); 
                TrangChinh.revalidate(); 
                TrangChinh.repaint(); 
            }
        });

        // ActionListener cho mục Quản Lý Lớp Học
        manageClassItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButtonColors(); 
                Manage_Button.setBackground(selectedColor); 
                TrangChinh.removeAll(); 
                QuanLyLopHoc QLlopPanel = new QuanLyLopHoc(); 
                QLlopPanel.setBounds(0, 0, 895, 652); 
                TrangChinh.add(QLlopPanel);
                TrangChinh.revalidate(); 
                TrangChinh.repaint(); 
            }
        });

        // ActionListener cho nút Trang Chủ
        TrangChu_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetButtonColors();
                TrangChu_Button.setBackground(selectedColor); 
                TrangChinh.removeAll(); 
                for (Component comp : initialComponents) {
                    TrangChinh.add(comp); 
                }
                // Cập nhật các nhãn thống kê
                updateStudentCountLabel(); // Cập nhật số lượng sinh viên
                updateTeacherCountLabel(); // Cập nhật số lượng giảng viên
                updateAssignmentCountLabel(); // Cập nhật số lượng bài tập còn
                TrangChinh.revalidate(); 
                TrangChinh.repaint(); 
            }
        });

        // ActionListener cho nút Sinh Viên
        Student_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetButtonColors(); 
                Student_Button.setBackground(selectedColor); 
                TrangChinh.removeAll(); 
                SinhVien studentPanel = new SinhVien(); 
                studentPanel.setBounds(0, 0, 895, 652);
                TrangChinh.add(studentPanel); 
                TrangChinh.revalidate(); 
                TrangChinh.repaint(); 
            }
        });

        // ActionListener cho nút Giảng Viên
        Teach_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetButtonColors(); 
                Teach_Button.setBackground(selectedColor); 
                TrangChinh.removeAll(); 
                GiangVien managePanel = new GiangVien(); 
                managePanel.setBounds(0, 0, 895, 652); 
                TrangChinh.add(managePanel); 
                TrangChinh.revalidate(); 
                TrangChinh.repaint(); 
            }
        });

        // ActionListener cho nút Bài Tập
        BaiTap_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetButtonColors(); 
                BaiTap_Button.setBackground(selectedColor); 
                TrangChinh.removeAll(); 
                QuanLyBaiTap baiTapPanel = new QuanLyBaiTap(); 
                baiTapPanel.setBounds(0, 0, 895, 652); 
                TrangChinh.add(baiTapPanel); 
                TrangChinh.revalidate(); 
                TrangChinh.repaint(); 
            }
        });

        // ActionListener cho nút Thoát
        Exit_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetButtonColors(); 
                Exit_Button.setBackground(selectedColor); 
                int confirm = JOptionPane.showConfirmDialog(TrangChu.this, "Bạn có chắc chắn muốn thoát không?",
                        "Xác nhận thoát", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Thêm logo vào bảng điều hướng
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Icon/logo-.png"));
        Image scaledShopp = logoIcon.getImage().getScaledInstance(76, 69, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(scaledShopp));
        logo.setBounds(0, 23, 76, 69); 
        panel.add(logo);

        // Tạo bảng nội dung chính
        TrangChinh = new JPanel();
        TrangChinh.setBounds(81, 11, 895, 652); 
        TrangChinh.setBackground(new Color(0, 0, 121)); 
        contentPane.add(TrangChinh);
        TrangChinh.setLayout(null); 

        // Thêm nhãn "TRANG CHỦ"
        JLabel TrangChuLabel = new JLabel("TRANG CHỦ");
        TrangChuLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        TrangChuLabel.setForeground(new Color(255, 255, 255)); // Màu chữ trắng
        TrangChuLabel.setBounds(10, 26, 161, 43); // Đặt vị trí
        TrangChinh.add(TrangChuLabel);

        // Tạo nút đăng xuất
        JButton login_bnt = new JButton();
        login_bnt.setBorderPainted(false);
        login_bnt.setContentAreaFilled(false);
        ImageIcon originalIcon = new ImageIcon(TrangChu.class.getResource("/Icon/login.png"));
        Image scaledImg = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImg);
        login_bnt.setIcon(resizedIcon);
        login_bnt.setBounds(824, 11, 50, 43); 
        TrangChinh.add(login_bnt);
        login_bnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(TrangChu.this,
                        "Bạn có muốn thoát trang không?",
                        "Xác nhận thoát",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE); // Hiển thị hộp thoại xác nhận
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose(); // Đóng cửa sổ hiện tại
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                Login loginFrame = new Login(); // Mở cửa sổ đăng nhập
                                loginFrame.setVisible(true);
                            } catch (Exception ex) {
                                ex.printStackTrace(); // In lỗi nếu có
                            }
                        }
                    });
                }
            }
        });

        // Tạo nút thông báo
        JButton ThongBao_bnt = new JButton();
        ThongBao_bnt.setBorderPainted(false);
        ThongBao_bnt.setContentAreaFilled(false);
        ImageIcon originalIcon1 = new ImageIcon(TrangChu.class.getResource("/Icon/chuong2.png"));
        Image scaledImg1 = originalIcon1.getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon1 = new ImageIcon(scaledImg1);
        ThongBao_bnt.setIcon(resizedIcon1);
        ThongBao_bnt.setBounds(745, 11, 57, 39); 
        TrangChinh.add(ThongBao_bnt);
        ThongBao_bnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(TrangChu.this,
                        "Bạn không có thông báo mới!",
                        "Thông Báo",
                        JOptionPane.INFORMATION_MESSAGE); 
            }
        });

        // Tạo nút ngôn ngữ (Vie)
        JButton btn_Vie = new JButton("Vie", null);
        btn_Vie.setBorderPainted(false);
        btn_Vie.setContentAreaFilled(false);
        ImageIcon originalIcon2 = new ImageIcon(TrangChu.class.getResource("/Icon/co.png"));
        Image scaledImg2 = originalIcon2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon2 = new ImageIcon(scaledImg2);
        btn_Vie.setIcon(resizedIcon2);
        btn_Vie.setBackground(new Color(0, 0, 128)); 
        btn_Vie.setForeground(new Color(255, 255, 255)); 
        btn_Vie.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btn_Vie.setBounds(645, 11, 102, 43); 
        TrangChinh.add(btn_Vie);

        // Tạo bảng thông tin nhóm
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 90, 443, 267); 
        TrangChinh.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("NHÓM 8");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblNewLabel_1.setForeground(new Color(255, 0, 0));
        lblNewLabel_1.setBounds(173, 11, 101, 24); 
        panel_1.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("QUẢN LÝ LỚP HỌC ONLINE");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_2.setForeground(new Color(0, 0, 160)); 
        lblNewLabel_2.setBounds(88, 46, 284, 24);
        panel_1.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("1. Đặng Thu Huyền                  - N21DCVT040");
        lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel_3.setBounds(66, 97, 322, 24); 
        panel_1.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("2. Trần Minh Thương             - N21DCVT101");
        lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel_4.setBounds(66, 132, 322, 28); 
        panel_1.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("3. Trần Nguyễn Tuấn Khanh  - N21DCVT044");
        lblNewLabel_5.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel_5.setBounds(66, 171, 337, 28);
        panel_1.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("4. Nguyễn Đình lân                  - N21DCVT057");
        lblNewLabel_6.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel_6.setBounds(66, 213, 322, 24); 
        panel_1.add(lblNewLabel_6);

        // Tạo bảng thống kê
        JPanel panel_1_1 = new JPanel();
        panel_1_1.setBounds(10, 368, 875, 259); 
        TrangChinh.add(panel_1_1);
        panel_1_1.setLayout(null);

        // Tạo bảng thống kê giảng viên
        panel_GV = new JPanel();
        panel_GV.setBackground(new Color(255, 138, 21)); 
        panel_GV.setBounds(300, 48, 283, 187); 
        panel_1_1.add(panel_GV);
        panel_GV.setLayout(null);

        JPanel panel_2_1 = new JPanel();
        panel_2_1.setBounds(142, 34, 4, 119); 
        panel_GV.add(panel_2_1);

        JLabel lbl_GV = new JLabel("GIẢNG VIÊN");
        lbl_GV.setForeground(Color.WHITE);
        lbl_GV.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lbl_GV.setBounds(156, 110, 100, 26); 
        panel_GV.add(lbl_GV);

        ImageIcon gvIcon = new ImageIcon(getClass().getResource("/Icon/teacher.png"));
        Image scaledShopp2 = gvIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel gv = new JLabel(new ImageIcon(scaledShopp2));
        gv.setBounds(32, 51, 85, 85); 
        panel_GV.add(gv);

        // Thêm nhãn hiển thị số lượng giảng viên
        QuanLyGiangVien quanLyGiangVien = new QuanLyGiangVien();
        int teacherCount = quanLyGiangVien.getTeacherCount(); // Lấy số lượng giảng viên
        lbl_teacherCount = new JLabel(String.valueOf(teacherCount));
        lbl_teacherCount.setForeground(new Color(255, 255, 255)); 
        lbl_teacherCount.setFont(new Font("Times New Roman", Font.BOLD, 40));
        lbl_teacherCount.setBounds(180, 51, 64, 48); 
        panel_GV.add(lbl_teacherCount);

        // Tạo bảng thống kê sinh viên
        panel_SV = new JPanel();
        panel_SV.setBackground(new Color(255, 36, 36)); 
        panel_SV.setBounds(10, 48, 280, 187); 
        panel_1_1.add(panel_SV);
        panel_SV.setLayout(null);

        JLabel lbl_sv = new JLabel("SINH VIÊN");
        lbl_sv.setForeground(new Color(255, 255, 255)); 
        lbl_sv.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lbl_sv.setBounds(152, 111, 100, 26); 
        panel_SV.add(lbl_sv);

        ImageIcon svIcon = new ImageIcon(getClass().getResource("/Icon/sv.png"));
        Image scaledShopp1 = svIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel sv = new JLabel(new ImageIcon(scaledShopp1));
        sv.setBounds(24, 52, 85, 85); 
        panel_SV.add(sv);

        JPanel panel_2_1_1 = new JPanel();
        panel_2_1_1.setBounds(138, 35, 4, 119); 
        panel_SV.add(panel_2_1_1);

        // Thêm nhãn hiển thị số lượng sinh viên
        QuanLySinhVien quanLySinhVien = new QuanLySinhVien();
        int studentCount = quanLySinhVien.getStudentCount(); // Lấy số lượng sinh viên
        lbl_studentCount = new JLabel(String.valueOf(studentCount));
        lbl_studentCount.setForeground(new Color(255, 255, 255));
        lbl_studentCount.setFont(new Font("Times New Roman", Font.BOLD, 40));
        lbl_studentCount.setBounds(176, 52, 59, 48); 
        panel_SV.add(lbl_studentCount);

        // Tạo bảng thống kê bài tập
        JPanel panel_BT = new JPanel();
        panel_BT.setBackground(new Color(38, 147, 255)); 
        panel_BT.setBounds(593, 48, 272, 187); 
        panel_1_1.add(panel_BT);
        panel_BT.setLayout(null);

        JPanel panel_2_2 = new JPanel();
        panel_2_2.setBounds(144, 37, 4, 119); 
        panel_BT.add(panel_2_2);

        JLabel lbl_GV_1 = new JLabel("BÀI TẬP CÒN");
        lbl_GV_1.setForeground(Color.WHITE); 
        lbl_GV_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lbl_GV_1.setBounds(158, 111, 114, 26); 
        panel_BT.add(lbl_GV_1);

        ImageIcon btIcon = new ImageIcon(getClass().getResource("/Icon/book.png"));
        Image scaledShopp3 = btIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel bt = new JLabel(new ImageIcon(scaledShopp3));
        bt.setBounds(36, 53, 85, 85); 
        panel_BT.add(bt);

        // Thêm nhãn hiển thị số lượng bài tập
        lbl_assignmentCount = new JLabel("0");
        lbl_assignmentCount.setForeground(new Color(255, 255, 255));
        lbl_assignmentCount.setFont(new Font("Times New Roman", Font.BOLD, 40));
        lbl_assignmentCount.setBounds(176, 53, 60, 37); 
        panel_BT.add(lbl_assignmentCount);
        updateAssignmentCountLabel(); // Cập nhật số lượng bài tập

        // Thêm nhãn "THỐNG KÊ"
        JLabel lblNewLabel = new JLabel("THỐNG KÊ");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel.setForeground(new Color(0, 0, 160)); 
        lblNewLabel.setBackground(new Color(0, 0, 160));
        lblNewLabel.setBounds(392, 11, 126, 26); 
        panel_1_1.add(lblNewLabel);

        // Khởi tạo bảng lịch
        JPanel panel_1_2 = new JPanel();
        panel_1_2.setBounds(468, 90, 417, 267); 
        panel_1_2.setBackground(new Color(255, 255, 255)); 
        panel_1_2.setLayout(new BorderLayout());
        TrangChinh.add(panel_1_2);

        // Tạo phần đầu lịch (Tháng Trước, Tháng/Năm, Tháng Sau)
        JPanel headerPanel = new JPanel();
        headerPanel.setForeground(new Color(0, 0, 121)); 
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        headerPanel.setBackground(new Color(255, 204, 0)); 
        headerPanel.setPreferredSize(new Dimension(417, 35)); 

        // Nút chuyển tháng trước
        JButton prevButton = new JButton("<");
        prevButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        prevButton.setBackground(defaultColor); 
        prevButton.setForeground(new Color(0, 0, 121)); 
        prevButton.setPreferredSize(new Dimension(40, 25)); 
        prevButton.setBorder(new EmptyBorder(0, 0, 0, 0)); 
        prevButton.setHorizontalAlignment(SwingConstants.CENTER);
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.MONTH, -1); // Giảm tháng hiện tại đi 1
                updateCalendar(); // Cập nhật lịch
            }
        });

        // Nhãn hiển thị tháng và năm
        monthYearLabel = new JLabel(monthYearFormat.format(calendar.getTime()));
        monthYearLabel.setBackground(new Color(0, 0, 0)); 
        monthYearLabel.setForeground(new Color(0, 0, 0)); 
        monthYearLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));

        // Nút chuyển tháng sau
        JButton nextButton = new JButton(">");
        nextButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        nextButton.setBackground(defaultColor); 
        nextButton.setForeground(new Color(0, 0, 121)); 
        nextButton.setPreferredSize(new Dimension(40, 25)); 
        nextButton.setBorder(new EmptyBorder(0, 0, 0, 0)); 
        nextButton.setHorizontalAlignment(SwingConstants.CENTER);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.MONTH, 1); // Tăng tháng hiện tại lên 1
                updateCalendar(); // Cập nhật lịch
            }
        });

        headerPanel.add(prevButton); // Thêm nút tháng trước
        headerPanel.add(monthYearLabel); // Thêm nhãn tháng/năm
        headerPanel.add(nextButton); // Thêm nút tháng sau
        panel_1_2.add(headerPanel, BorderLayout.NORTH); // Thêm phần đầu vào bảng lịch

        // Tạo bảng lịch
        String[] columns = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"}; // Cột: Chủ Nhật đến Thứ Bảy
        tableModel = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa ô
            }
        };
        calendarTable = new JTable(tableModel);
        calendarTable.setRowHeight(38); // Đặt chiều cao hàng
        calendarTable.setGridColor(Color.LIGHT_GRAY); 
        calendarTable.setShowGrid(true); 
        calendarTable.setBackground(Color.WHITE); 
        calendarTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        calendarTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 12));
        calendarTable.getTableHeader().setBackground(new Color(200, 200, 200)); 
        calendarTable.getTableHeader().setForeground(new Color(0, 0, 121)); 

        // Tùy chỉnh renderer để tô sáng ngày hiện tại
        calendarTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.BLACK); 
                c.setBackground(Color.WHITE); 

                if (value != null && !value.toString().isEmpty()) {
                    int day = Integer.parseInt(value.toString()); // Lấy giá trị ngày
                    Calendar cellCal = (Calendar) calendar.clone();
                    cellCal.set(Calendar.DAY_OF_MONTH, day);
                    if (cellCal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                        cellCal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                        cellCal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                        c.setForeground(Color.RED); // Tô đỏ ngày hiện tại
                        c.setFont(new Font("Times New Roman", Font.BOLD, 15)); // In đậm
                    }
                }
                setHorizontalAlignment(CENTER); // Căn giữa nội dung
                return c;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText() != null && !getText().isEmpty()) {
                    int day = Integer.parseInt(getText()); // Lấy giá trị ngày
                    Calendar cellCal = (Calendar) calendar.clone();
                    cellCal.set(Calendar.DAY_OF_MONTH, day);
                    if (cellCal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                        cellCal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                        cellCal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setColor(new Color(0, 0, 121)); // Màu vòng tròn xanh đậm
                        g2d.setStroke(new BasicStroke(1));
                        int size = Math.min(getWidth(), getHeight()) - 10; // Kích thước vòng tròn
                        int x = (getWidth() - size) / 2;
                        int y = (getHeight() - size) / 2;
                        g2d.drawOval(x, y, size, size); // Vẽ vòng tròn quanh ngày hiện tại
                        g2d.dispose();
                    }
                }
            }
        });

        // Điều chỉnh chiều rộng cột
        for (int i = 0; i < calendarTable.getColumnCount(); i++) {
            calendarTable.getColumnModel().getColumn(i).setPreferredWidth(417 / 7); // Chia đều chiều rộng
        }

        JScrollPane scrollPane = new JScrollPane(calendarTable);
        panel_1_2.add(scrollPane, BorderLayout.CENTER); // Thêm bảng lịch vào trung tâm

        // Cập nhật lịch lần đầu
        updateCalendar();
        initialComponents = TrangChinh.getComponents(); // Lưu các thành phần ban đầu
    }

    // Hàm Cập nhật hiển thị lịch khi chuyển tháng hoặc khởi tạo
    private void updateCalendar() {
        monthYearLabel.setText(monthYearFormat.format(calendar.getTime())); // Cập nhật nhãn tháng/năm
        tableModel.setRowCount(0); // Xóa tất cả hàng trong bảng

        Calendar tempCal = (Calendar) calendar.clone(); 
        tempCal.set(Calendar.DAY_OF_MONTH, 1); 
        int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1; // Lấy thứ của ngày đầu tháng
        int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH); // Lấy số ngày trong tháng

        int totalCells = firstDayOfWeek + daysInMonth; // Tổng số ô cần hiển thị
        int rows = (int) Math.ceil(totalCells / 7.0); // Tính số hàng cần thiết

        int day = 1; // Bắt đầu từ ngày 1
        for (int row = 0; row < rows; row++) {
            String[] rowData = new String[7]; 
            for (int col = 0; col < 7; col++) {
                int cellIndex = row * 7 + col;
                if (cellIndex < firstDayOfWeek || day > daysInMonth) {
                    rowData[col] = ""; // Để trống ô trước/sau tháng
                } else {
                    rowData[col] = String.valueOf(day); // Gán ngày
                    day++;
                }
            }
            tableModel.addRow(rowData); // Thêm hàng vào bảng
        }

        initialComponents = TrangChinh.getComponents(); // Cập nhật danh sách thành phần ban đầu
    }

    // Hàm Tạo nút điều hướng với biểu tượng và vị trí Y
    private JButton createButton(String iconPath, int yPos) {
        JButton button = new JButton(); // Tạo nút mới
        ImageIcon originalIcon = new ImageIcon(TrangChu.class.getResource(iconPath)); 
        Image resizedImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); 
        button.setIcon(new ImageIcon(resizedImage)); 
        button.setBounds(0, yPos, 76, 76); 
        button.setOpaque(true); 
        button.setBorderPainted(false); 
        button.setContentAreaFilled(false);
        return button; 
    }

    // Hàm Đặt lại màu nền của tất cả các nút điều hướng
    private void resetButtonColors() {
        for (JButton button : menuButtons) {
            button.setOpaque(true); 
            button.setContentAreaFilled(false); 
            button.setBackground(null); 
        }
    }

    // Hàm  Cập nhật nhãn hiển thị số lượng sinh viên
    private void updateStudentCountLabel() {
        if (lbl_studentCount != null) { // Kiểm tra nhãn không null
            QuanLySinhVien quanLySinhVien = new QuanLySinhVien(); // Tạo đối tượng quản lý sinh viên
            int studentCount = quanLySinhVien.getStudentCount(); // Lấy số lượng sinh viên
            lbl_studentCount.setText(String.valueOf(studentCount)); 
        }
    }

    // Hàm Cập nhật nhãn hiển thị số lượng giảng viên
    private void updateTeacherCountLabel() {
        if (lbl_teacherCount != null) { // Kiểm tra nhãn không null
            QuanLyGiangVien quanLyGiangVien = new QuanLyGiangVien(); // Tạo đối tượng quản lý giảng viên
            int teacherCount = quanLyGiangVien.getTeacherCount(); // Lấy số lượng giảng viên
            lbl_teacherCount.setText(String.valueOf(teacherCount)); 
        }
    }

    // Hàm Cập nhật nhãn hiển thị số lượng bài tập
    private void updateAssignmentCountLabel() {
        if (lbl_assignmentCount == null) { // Kiểm tra nhãn không null
            System.err.println("lbl_assignmentCount is null"); 
            return;
        }
        try {
            QuanLyBaiTap quanLyBaiTap = new QuanLyBaiTap(); // Tạo đối tượng quản lý bài tập
            int assignmentCount = quanLyBaiTap.getAssignmentCount(); // Lấy số lượng bài tập
            lbl_assignmentCount.setText(String.valueOf(assignmentCount)); 
        } catch (Exception e) {
            System.err.println("Error updating assignment count: " + e.getMessage()); 
            lbl_assignmentCount.setText("0"); 
        }
    }

    // Hàm main khởi chạy cửa sổ TrangChu
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TrangChu frame = new TrangChu(); 
                    frame.setVisible(true); 
                } catch (Exception e) {
                    e.printStackTrace(); 
                }
            }
        });
    }
}