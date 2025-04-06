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

public class ThongTinGiangVien extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThongTinGiangVien frame = new ThongTinGiangVien();
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
	public ThongTinGiangVien() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 450); // Tăng kích thước cho giao diện thoáng
		setTitle("Thông Tin Giảng Viên");
		setResizable(false); // Không cho thay đổi kích thước để giữ bố cục

		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 121)); // Nền xanh đậm
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15)); // Padding lớn hơn
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Panel tiêu đề với gradient
		JPanel titlePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(new Color(255, 204, 0)); // Màu vàng
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		titlePanel.setBounds(0, 0, 650, 60);
		titlePanel.setLayout(null);
		contentPane.add(titlePanel);

		JLabel lblTitle = new JLabel("THÔNG TIN GIẢNG VIÊN");
		lblTitle.setForeground(Color.BLACK); // Đổi màu chữ cho nổi trên gradient
		lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
		lblTitle.setBounds(180, 15, 375, 30); // Căn giữa
		titlePanel.add(lblTitle);

		// Panel chứa hình ảnh giảng viên
		JPanel imagePanel = new JPanel();
		imagePanel.setBackground(new Color(0, 0, 121));
		imagePanel.setBounds(50, 90, 180, 180); // Tăng kích thước ảnh
		imagePanel.setBorder(new LineBorder(Color.WHITE, 3, true)); // Viền trắng bo tròn
		imagePanel.setLayout(null);
		contentPane.add(imagePanel);

		// Hình ảnh giảng viên
		JLabel lblHinhAnh = new JLabel("");
		lblHinhAnh.setBounds(0, 0, 180, 180); // Đầy panel
		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Icon/giangvien1.jpg"));
		if (logoIcon.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
			lblHinhAnh.setIcon(logoIcon);
		} else {
			System.out.println("Lỗi: Không tìm thấy ảnh tại /Icon/giangvien1.jpg. Kiểm tra đường dẫn!");
			lblHinhAnh.setText("Ảnh không tải được");
			lblHinhAnh.setForeground(Color.RED);
			lblHinhAnh.setHorizontalAlignment(JLabel.CENTER);
		}
		imagePanel.add(lblHinhAnh);

		// Panel chứa thông tin giảng viên
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(new Color(0, 0, 150)); // Xanh đậm nhạt hơn chút
		infoPanel.setBounds(260, 90, 340, 300);
		infoPanel.setBorder(new LineBorder(Color.WHITE, 2, true)); // Viền trắng bo tròn
		infoPanel.setLayout(null);
		contentPane.add(infoPanel);

		// Thông tin giảng viên
		int yOffset = 30; // Khoảng cách ban đầu
		int lineHeight = 40; // Chiều cao mỗi dòng

		JLabel lblMaGV = new JLabel("Mã GV:");
		lblMaGV.setForeground(Color.WHITE);
		lblMaGV.setFont(new Font("Arial", Font.BOLD, 16));
		lblMaGV.setBounds(20, 42, 100, 20);
		infoPanel.add(lblMaGV);

		JLabel lblMaGVValue = new JLabel("GV001");
		lblMaGVValue.setForeground(Color.WHITE);
		lblMaGVValue.setFont(new Font("Arial", Font.PLAIN, 16));
		lblMaGVValue.setBounds(130, 42, 200, 20);
		infoPanel.add(lblMaGVValue);

		yOffset += lineHeight;
		JLabel lblTen = new JLabel("Tên:");
		lblTen.setForeground(Color.WHITE);
		lblTen.setFont(new Font("Arial", Font.BOLD, 16));
		lblTen.setBounds(20, 84, 100, 20);
		infoPanel.add(lblTen);

		JLabel lblTenValue = new JLabel("Nguyễn Văn A");
		lblTenValue.setForeground(Color.WHITE);
		lblTenValue.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTenValue.setBounds(130, 84, 200, 20);
		infoPanel.add(lblTenValue);

		yOffset += lineHeight;
		JLabel lblMonDay = new JLabel("Môn Dạy:");
		lblMonDay.setForeground(Color.WHITE);
		lblMonDay.setFont(new Font("Arial", Font.BOLD, 16));
		lblMonDay.setBounds(20, 132, 100, 20);
		infoPanel.add(lblMonDay);

		JLabel lblMonDayValue = new JLabel("Lập trình Java");
		lblMonDayValue.setForeground(Color.WHITE);
		lblMonDayValue.setFont(new Font("Arial", Font.PLAIN, 16));
		lblMonDayValue.setBounds(130, 132, 200, 20);
		infoPanel.add(lblMonDayValue);

		yOffset += lineHeight;
		JLabel lblSoLop = new JLabel("Số Lớp:");
		lblSoLop.setForeground(Color.WHITE);
		lblSoLop.setFont(new Font("Arial", Font.BOLD, 16));
		lblSoLop.setBounds(20, 181, 100, 20);
		infoPanel.add(lblSoLop);

		JLabel lblSoLopValue = new JLabel("3");
		lblSoLopValue.setForeground(Color.WHITE);
		lblSoLopValue.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSoLopValue.setBounds(130, 181, 200, 20);
		infoPanel.add(lblSoLopValue);

		yOffset += lineHeight;
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
		lblEmail.setBounds(20, 232, 100, 20);
		infoPanel.add(lblEmail);

		JLabel lblEmailValue = new JLabel("nguyenvana@example.com");
		lblEmailValue.setForeground(Color.WHITE);
		lblEmailValue.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEmailValue.setBounds(130, 232, 200, 20);
		infoPanel.add(lblEmailValue);
	}
}