package HeThongQuanLyLopHocOnline;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class GiangVien extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField txtHoTen;
    private JTextField txtMonGiangDay;
    private JTextField txtMaGV;
    private JTextField txtThngTinGing;
    private JTextField txtEmail;
    private JTextField txtSoDienThoai;
    private JTextField txtMaMon;
	private String hoTen;
	private String maGV;
	private String email;
	private String soDienThoai;
	private String monGiangDay;
	private String maMon;
	private javax.swing.JComboBox<String> comboBoxMonHoc;
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setMaGV(String maGV) { this.maGV = maGV; }
    public void setEmail(String email) { this.email = email; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public void setMonGiangDay(String monGiangDay) { this.monGiangDay = monGiangDay; }
    public void setMaMon(String maMon) { this.maMon = maMon; }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                GiangVien frame = new GiangVien();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public GiangVien() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        setTitle("Quản Lý Giảng Viên");

        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 125));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        textField = new JTextField("Giảng Viên");
        textField.setEditable(false);
        textField.setForeground(Color.WHITE);
        textField.setBackground(new Color(0, 0, 128));
        textField.setFont(new Font("Times New Roman", Font.BOLD, 20));
        textField.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(new Color(0, 102, 204), 1));
        panel.setLayout(new GridLayout(6, 2, 10, 10)); // 5 hàng, 2 cột
        
        JLabel lblHVTn = new JLabel("HỌ VÀ TÊN");
        lblHVTn.setFont(new Font("Times New Roman", Font.BOLD, 13));
        panel.add(lblHVTn);
        txtHoTen = new JTextField();
        txtHoTen.setColumns(15);
        txtHoTen.setHorizontalAlignment(SwingConstants.LEFT); // Đặt căn chỉnh bên trái cho phù hợp
        panel.add(txtHoTen);

        JLabel lblMnGingDy = new JLabel("MÔN GIẢNG DẠY:");
        lblMnGingDy.setFont(new Font("Times New Roman", Font.BOLD, 13));
        panel.add(lblMnGingDy);

        // ComboBox thay cho textField nhập tay
        comboBoxMonHoc = new javax.swing.JComboBox<>();
        comboBoxMonHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"", "Cảm Biến", "Java", "Android"}));
        comboBoxMonHoc.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        panel.add(comboBoxMonHoc);
        
        comboBoxMonHoc.addItemListener(e -> { 
            String selectedSubject = (String) comboBoxMonHoc.getSelectedItem();
            txtMonGiangDay.setText(selectedSubject); // Gán vào textField ẩn hoặc để lưu

            if ("Cảm Biến".equals(selectedSubject)) {
                txtMaMon.setText("iot");
            } else if ("Java".equals(selectedSubject)) {
                txtMaMon.setText("jv");
            } else if ("Android".equals(selectedSubject)) {
                txtMaMon.setText("adr");
            } else {
                txtMaMon.setText("");
            }
        });


        // TextField hiển thị tên môn (ẩn hoặc tự động điền nếu bạn vẫn cần)
        txtMonGiangDay = new JTextField();
        txtMonGiangDay.setVisible(false); // Ẩn nếu không cần người dùng chỉnh sửa tay


        JLabel lblMGingVin = new JLabel("MÃ GIẢNG VIÊN:");
        lblMGingVin.setFont(new Font("Times New Roman", Font.BOLD, 13));
        panel.add(lblMGingVin);
        txtMaGV = new JTextField();
        txtMaGV.setColumns(25);
        panel.add(txtMaGV);
        
        JLabel lblMaMon = new JLabel("MÃ MÔN HỌC:");
        lblMaMon.setFont(new Font("Times New Roman", Font.BOLD, 13));
        panel.add(lblMaMon);
        txtMaMon = new JTextField();
        txtMaMon.setColumns(25);
        panel.add(txtMaMon);

        JLabel lblEmail = new JLabel("EMAIL:");
        lblEmail.setFont(new Font("Times New Roman", Font.BOLD, 13));
        panel.add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setColumns(25);
        panel.add(txtEmail);
        
        JLabel lblSDT = new JLabel("SỐ ĐIỆN THOẠI:");
        lblSDT.setFont(new Font("Times New Roman", Font.BOLD, 13));
        panel.add(lblSDT);
        txtSoDienThoai = new JTextField();
        txtSoDienThoai.setColumns(25);
        panel.add(txtSoDienThoai);

        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(new Color(245, 245, 245));
        panelButtons.setLayout(new GridLayout(1, 2, 20, 0));

        JButton btnLuu = new JButton("Lưu");
        btnLuu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnLuu.setBackground(Color.YELLOW);
        btnLuu.setForeground(Color.BLACK);
        btnLuu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String hoTen = txtHoTen.getText().trim();
                String monGiangDay = txtMonGiangDay.getText().trim();
                String maGV = txtMaGV.getText().trim();
                String email = txtEmail.getText().trim();
                String soDienThoai = txtSoDienThoai.getText().trim();
                String maMon = (txtMaMon != null) ? txtMaMon.getText().trim() : ""; // nếu bạn đã thêm txtMaMon

                if (hoTen.isEmpty() || monGiangDay.isEmpty() || maGV.isEmpty()
                    || email.isEmpty() || soDienThoai.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                        "Vui lòng nhập đầy đủ tất cả thông tin!", 
                        "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Dữ liệu đã được lưu thành công!\n" +
                        "Họ tên: " + hoTen + "\n" +
                        "Môn: " + monGiangDay + "\n" +
                        "Mã môn: " + maMon + "\n" +
                        "Mã GV: " + maGV + "\n" +
                        "Email: " + email + "\n" +
                        "SĐT: " + soDienThoai,
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        panelButtons.add(btnLuu);

        JButton btnXuat = new JButton("Xuất");
        btnXuat.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnXuat.setBackground(Color.YELLOW);
        btnXuat.setForeground(Color.BLACK);
        panelButtons.add(btnXuat);
        btnXuat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Tạo đối tượng cửa sổ ThongTinGiangVien và truyền chính đối tượng hiện tại
                ThongTinGiangVien thongTin = new ThongTinGiangVien(GiangVien.this);
                thongTin.setVisible(true);  // Hiển thị cửa sổ thông tin
            }
        });

        txtThngTinGing = new JTextField();
        txtThngTinGing.setHorizontalAlignment(SwingConstants.CENTER);
        txtThngTinGing.setFont(new Font("Times New Roman", Font.BOLD, 16));
        txtThngTinGing.setText("NHẬP THÔNG TIN GIẢNG VIÊN");
        txtThngTinGing.setColumns(10);
        txtThngTinGing.setEditable(false);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.CENTER)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(textField, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGap(103)
        					.addComponent(txtThngTinGing, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGap(29)
        					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGap(132)
        					.addComponent(panelButtons, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(37, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addComponent(textField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(txtThngTinGing, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(panelButtons, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(21))
        );

        contentPane.setLayout(gl_contentPane);
    }

	public String getMaGV() {
		return txtMaGV.getText();
	}

	public String getHoTen() {
		return txtHoTen.getText();
	}

	public String getMonGiangDay() {
		return txtMonGiangDay.getText();
	}

	public String getEmail() {
		return txtEmail.getText();
	}

	public String getSoDienThoai() {
		return txtSoDienThoai.getText();
	}
	
	public String getMaMon() {
	    return txtMaMon.getText();
	}
}
