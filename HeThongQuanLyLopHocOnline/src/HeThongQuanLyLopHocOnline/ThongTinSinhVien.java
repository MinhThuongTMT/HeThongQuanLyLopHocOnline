package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ThongTinSinhVien extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThongTinSinhVien frame = new ThongTinSinhVien();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ThongTinSinhVien() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 594);
		setTitle("Thông Tin Sinh Viên");
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 121));
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Panel tiêu đề
		JPanel titlePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(new Color(255, 204, 0)); // Vàng
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		titlePanel.setBounds(0, 0, 700, 60);
		titlePanel.setLayout(null);
		contentPane.add(titlePanel);

		JLabel lblTitle = new JLabel("THÔNG TIN SINH VIÊN");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
		lblTitle.setBounds(200, 15, 400, 30);
		titlePanel.add(lblTitle);

		// Panel hình ảnh sinh viên
		JPanel imagePanel = new JPanel();
		imagePanel.setBackground(new Color(0, 0, 121));
		imagePanel.setBounds(478, 90, 180, 207);
		imagePanel.setBorder(new LineBorder(Color.WHITE, 3, true));
		imagePanel.setLayout(null);
		contentPane.add(imagePanel);

		JLabel lblImage = new JLabel("");
		lblImage.setBounds(0, 0, 190, 215);
		imagePanel.add(lblImage);
		ImageIcon icon = new ImageIcon(getClass().getResource("/Icon/giangvien1.jpg"));
		if (icon.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
			lblImage.setIcon(icon);
		} else {
			lblImage.setText("Không có ảnh");
			lblImage.setForeground(Color.RED);
			lblImage.setHorizontalAlignment(JLabel.CENTER);
		}

		// Panel thông tin sinh viên
		JPanel studentPanel = new JPanel();
		studentPanel.setBackground(new Color(0, 0, 150));
		studentPanel.setBounds(10, 90, 430, 207);
		studentPanel.setBorder(new LineBorder(Color.WHITE, 2, true));
		studentPanel.setLayout(null);
		contentPane.add(studentPanel);

		addInfoLabel(studentPanel, "Mã SV:", "SV001", 20);
		addInfoLabel(studentPanel, "Tên:", "Trần Thị B", 55);
		addInfoLabel(studentPanel, "Ngày sinh:", "01/01/2002", 90);
		addInfoLabel(studentPanel, "Giới tính:", "Nữ", 125);
		addInfoLabel(studentPanel, "Email:", "tranthib@example.com", 160);

		// Panel thông tin khóa học
		JPanel coursePanel = new JPanel();
		coursePanel.setBackground(new Color(0, 0, 150));
		coursePanel.setBounds(10, 330, 430, 207);
		coursePanel.setBorder(new LineBorder(Color.WHITE, 2, true));
		coursePanel.setLayout(null);
		contentPane.add(coursePanel);

		addCourseLabel(coursePanel, "Lớp:", "IT01", 20);
		addCourseLabel(coursePanel, "Chuyên ngành:", "Công nghệ phần mềm", 55);
		addCourseLabel(coursePanel, "Môn học:", "Lập trình Java", 90);
		addCourseLabel(coursePanel, "Tín chỉ:", "3", 125);

		JLabel lblNewLabel = new JLabel("Thông tin sinh viên");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(20, 67, 115, 13);
		contentPane.add(lblNewLabel);

		JLabel lblThngTinKha = new JLabel("Thông tin khóa học");
		lblThngTinKha.setForeground(Color.WHITE);
		lblThngTinKha.setBounds(20, 307, 115, 13);
		contentPane.add(lblThngTinKha);
	}

	// Hàm thêm dòng thông tin sinh viên
	private void addInfoLabel(JPanel panel, String label, String value, int y) {
		JLabel lbl = new JLabel(label);
		lbl.setForeground(Color.WHITE);
		lbl.setFont(new Font("Arial", Font.BOLD, 16));
		lbl.setBounds(20, y, 120, 25);
		panel.add(lbl);

		JLabel lblValue = new JLabel(value);
		lblValue.setForeground(Color.WHITE);
		lblValue.setFont(new Font("Arial", Font.PLAIN, 16));
		lblValue.setBounds(150, y, 250, 25);
		panel.add(lblValue);
	}

	// Hàm thêm dòng thông tin khóa học
	private void addCourseLabel(JPanel panel, String label, String value, int y) {
		JLabel lbl = new JLabel(label);
		lbl.setForeground(Color.WHITE);
		lbl.setFont(new Font("Arial", Font.BOLD, 16));
		lbl.setBounds(20, y, 140, 25);
		panel.add(lbl);

		JLabel lblValue = new JLabel(value);
		lblValue.setForeground(Color.WHITE);
		lblValue.setFont(new Font("Arial", Font.PLAIN, 16));
		lblValue.setBounds(160, y, 400, 25);
		panel.add(lblValue);
	}
}
