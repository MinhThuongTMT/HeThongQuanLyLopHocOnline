package HeThongQuanLyLopHocOnline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class ThongTinGiangVien extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private GiangVien giangVien;
    private JLabel lblImage;

    public ThongTinGiangVien(GiangVien giangVien) {
        this.giangVien = giangVien;

        setTitle("Thông Tin Giảng Viên");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 350);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 128));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("THÔNG TIN GIẢNG VIÊN", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 16));
        contentPane.add(lblTitle, BorderLayout.NORTH);

        // Panel chứa ảnh giảng viên
        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(120, 150));
        lblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setVerticalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon("avatar.png");
            Image img = icon.getImage().getScaledInstance(120, 150, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            lblImage.setText("No Image");
        }
        contentPane.add(lblImage, BorderLayout.WEST);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(new Color(255, 255, 255));
        contentPane.add(panel, BorderLayout.CENTER);

        Font labelFont = new Font("Times New Roman", Font.BOLD, 14);

        JLabel lblHoTen = new JLabel("HỌ VÀ TÊN:", SwingConstants.RIGHT);
        lblHoTen.setFont(labelFont);
        panel.add(lblHoTen);
        textField = new JTextField();
        panel.add(textField);

        JLabel lblMon = new JLabel("MÔN GIẢNG DẠY:", SwingConstants.RIGHT);
        lblMon.setFont(labelFont);
        panel.add(lblMon);
        textField_4 = new JTextField();
        panel.add(textField_4);

        JLabel lblMaGV = new JLabel("MÃ GIẢNG VIÊN:", SwingConstants.RIGHT);
        lblMaGV.setFont(labelFont);
        panel.add(lblMaGV);
        textField_1 = new JTextField();
        panel.add(textField_1);

        JLabel lblMaMH = new JLabel("MÃ MÔN HỌC:", SwingConstants.RIGHT);
        lblMaMH.setFont(labelFont);
        panel.add(lblMaMH);
        textField_5 = new JTextField();
        panel.add(textField_5);

        JLabel lblEmail = new JLabel("EMAIL:", SwingConstants.RIGHT);
        lblEmail.setFont(labelFont);
        panel.add(lblEmail);
        textField_2 = new JTextField();
        panel.add(textField_2);

        JLabel lblSDT = new JLabel("SỐ ĐIỆN THOẠI:", SwingConstants.RIGHT);
        lblSDT.setFont(labelFont);
        panel.add(lblSDT);
        textField_3 = new JTextField();
        panel.add(textField_3);

        textField.setText(giangVien.getHoTen());
        textField_4.setText(giangVien.getMonGiangDay());
        textField_1.setText(giangVien.getMaGV());
        textField_2.setText(giangVien.getEmail());
        textField_3.setText(giangVien.getSoDienThoai());
        textField_5.setText(giangVien.getMaMon());

        textField.setEditable(false);
        textField_1.setEditable(false);
        textField_2.setEditable(false);
        textField_3.setEditable(false);
        textField_4.setEditable(false);
        textField_5.setEditable(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonPanel.setBackground(new Color(0, 0, 128));
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton btnSua = new JButton("Sửa");
        JButton btnChamDiem = new JButton("Chấm Điểm");
        JButton btnGiaoBai = new JButton("Giao Bài");
        JButton btnDiemDanh = new JButton("Điểm Danh");

        ActionListener buttonHighlight = e -> {
            JButton btn = (JButton) e.getSource();
            Color currentColor = btn.getBackground();
            Color defaultColor = UIManager.getColor("Button.background");
            btn.setBackground(currentColor.equals(Color.YELLOW) ? defaultColor : Color.YELLOW);
        };

        btnChamDiem.addActionListener(buttonHighlight);
        btnGiaoBai.addActionListener(buttonHighlight);
        btnDiemDanh.addActionListener(buttonHighlight);

        btnSua.addActionListener(new ActionListener() {
            private boolean isEditing = false;
            private final Color defaultColor = UIManager.getColor("Button.background");

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();

                if (!isEditing) {
                    textField.setEditable(true);
                    textField_1.setEditable(true);
                    textField_2.setEditable(true);
                    textField_3.setEditable(true);
                    textField_4.setEditable(true);
                    textField_5.setEditable(true);

                    btn.setText("Lưu");
                    btn.setBackground(Color.YELLOW);
                    isEditing = true;
                } else {
                    if (textField.getText().isEmpty() || textField_1.getText().isEmpty() ||
                        textField_2.getText().isEmpty() || textField_3.getText().isEmpty() ||
                        textField_4.getText().isEmpty() || textField_5.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(ThongTinGiangVien.this, "Vui lòng nhập đầy đủ thông tin.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    giangVien.setHoTen(textField.getText());
                    giangVien.setMaGV(textField_1.getText());
                    giangVien.setEmail(textField_2.getText());
                    giangVien.setSoDienThoai(textField_3.getText());
                    giangVien.setMonGiangDay(textField_4.getText());
                    giangVien.setMaMon(textField_5.getText());

                    textField.setEditable(false);
                    textField_1.setEditable(false);
                    textField_2.setEditable(false);
                    textField_3.setEditable(false);
                    textField_4.setEditable(false);
                    textField_5.setEditable(false);

                    btn.setText("Sửa");
                    btn.setBackground(defaultColor);
                    isEditing = false;

                    JOptionPane.showMessageDialog(ThongTinGiangVien.this, "Thông tin đã được lưu!");
                }
            }
        });

        buttonPanel.add(btnSua);
        buttonPanel.add(btnChamDiem);
        buttonPanel.add(btnGiaoBai);
        buttonPanel.add(btnDiemDanh);
    }
}
