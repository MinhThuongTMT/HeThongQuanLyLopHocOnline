package QuanLyLopHoc;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SignUp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;

	// Thông tin kết nối database
	private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
	private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
	private static final String DB_PASSWORD = "MinhThuong0808";

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				SignUp frame = new SignUp();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public SignUp() {
		// Khởi tạo bảng users khi khởi động
		initDatabase();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		setLocationRelativeTo(null);
		setTitle("Đăng Ký Tài Khoản");
		setResizable(false);

		// Panel nền với gradient
		contentPane = new JPanel() {
			private Image backgroundImage;

            {
                // Tải hình nền từ resource (thay thế "/Icon/background.jpg" bằng đường dẫn hình của bạn)
                ImageIcon icon = new ImageIcon(getClass().getResource("/Icon/truong.jpg"));
                if (icon.getImage() != null) {
                    backgroundImage = icon.getImage();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Vẽ hình nền, scale để fit khung
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panel chứa form
		JPanel formPanel = new JPanel();
		formPanel.setBounds(150, 40, 450, 400);
		formPanel.setBackground(new Color(106, 181, 255));
		formPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		formPanel.setLayout(null);
		contentPane.add(formPanel);

		// Tiêu đề
		JLabel lblSignUp = new JLabel("ĐĂNG KÝ", SwingConstants.CENTER);
		lblSignUp.setFont(new Font("Arial", Font.BOLD, 32));
		lblSignUp.setForeground(new Color(25, 25, 112));
		lblSignUp.setBounds(0, 20, 450, 50);
		formPanel.add(lblSignUp);

		// Username
		JLabel lblUsername = new JLabel("Tên đăng nhập:");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUsername.setBounds(50, 90, 120, 25);
		formPanel.add(lblUsername);

		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		textFieldUsername.setBounds(50, 115, 350, 40);
		textFieldUsername.setBorder(new LineBorder(new Color(169, 169, 169), 1, true));
		formPanel.add(textFieldUsername);

		// Password
		JLabel lblPassword = new JLabel("Mật khẩu:");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		lblPassword.setBounds(50, 165, 120, 25);
		formPanel.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordField.setBounds(50, 190, 350, 40);
		passwordField.setBorder(new LineBorder(new Color(169, 169, 169), 1, true));
		formPanel.add(passwordField);

		JButton btnShowPassword = new JButton();
		btnShowPassword.setBounds(410, 190, 30, 40);
		setupEyeButton(btnShowPassword, passwordField, "/Icon/eye_close.png");
		formPanel.add(btnShowPassword);

		// Confirm Password
		JLabel lblConfirmPassword = new JLabel("Xác nhận mật khẩu:");
		lblConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		lblConfirmPassword.setBounds(50, 240, 150, 25);
		formPanel.add(lblConfirmPassword);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
		confirmPasswordField.setBounds(50, 265, 350, 40);
		confirmPasswordField.setBorder(new LineBorder(new Color(169, 169, 169), 1, true));
		formPanel.add(confirmPasswordField);

		JButton btnShowConfirmPassword = new JButton();
		btnShowConfirmPassword.setBounds(410, 265, 30, 40);
		setupEyeButton(btnShowConfirmPassword, confirmPasswordField, "/Icon/eye_close.png");
		formPanel.add(btnShowConfirmPassword);

		// Nút Đăng ký
		JButton btnSignUp = new JButton("Đăng Ký");
		btnSignUp.setFont(new Font("Arial", Font.BOLD, 16));
		btnSignUp.setBackground(new Color(0, 71, 142));
		btnSignUp.setForeground(Color.WHITE);
		btnSignUp.setBounds(50, 329, 150, 45);
		btnSignUp.setBorderPainted(false);
		btnSignUp.setFocusPainted(false);
		addHoverEffect(btnSignUp);
		btnSignUp.addActionListener(e -> handleSignUp());
		formPanel.add(btnSignUp);

		// Nút Đăng nhập
		JButton btnBackToLogin = new JButton("Đăng Nhập");
		btnBackToLogin.setFont(new Font("Arial", Font.BOLD, 16));
		btnBackToLogin.setBackground(new Color(255, 43, 43));
		btnBackToLogin.setForeground(Color.WHITE);
		btnBackToLogin.setBounds(250, 329, 150, 45);
		btnBackToLogin.setBorderPainted(false);
		btnBackToLogin.setFocusPainted(false);
		addHoverEffect(btnBackToLogin);
		btnBackToLogin.addActionListener(e -> {
			new Login().setVisible(true);
			dispose();
		});
		formPanel.add(btnBackToLogin);
	}

	private void initDatabase() {
		try {
			// Load driver PostgreSQL
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Không tìm thấy driver PostgreSQL!");
			return;
		}

		String createTableSQL = """
				CREATE TABLE IF NOT EXISTS users (
				    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
				    username TEXT UNIQUE NOT NULL,
				    password TEXT NOT NULL,
				    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
				)
				""";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				Statement stmt = conn.createStatement()) {
			stmt.execute(createTableSQL);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tạo bảng users: " + e.getMessage());
		}
	}

	private void setupEyeButton(JButton button, JPasswordField passwordField, String initialIconPath) {
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);

		ImageIcon icon = new ImageIcon(getClass().getResource(initialIconPath));
		if (icon.getImage() != null) {
			Image scaledImg = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(scaledImg));
		}

		button.addActionListener(e -> {
			if (passwordField.getEchoChar() == 0) {
				passwordField.setEchoChar('•');
				setIcon(button, "/Icon/eye_close.png");
			} else {
				passwordField.setEchoChar((char) 0);
				setIcon(button, "/Icon/eye_open.png");
			}
		});
	}

	private void setIcon(JButton button, String iconPath) {
		ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
		if (icon.getImage() != null) {
			Image scaledImg = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(scaledImg));
		}
	}

	private void addHoverEffect(JButton button) {
		Color originalColor = button.getBackground();
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(originalColor.brighter());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(originalColor);
			}
		});
	}

	private void handleSignUp() {
		String username = textFieldUsername.getText().trim();
		String password = new String(passwordField.getPassword());
		String confirmPassword = new String(confirmPasswordField.getPassword());

		if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
		} else if (!password.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(null, "Mật khẩu không khớp!");
		} else {
			if (saveUser(username, password)) {
				JOptionPane.showMessageDialog(null, "Đăng ký thành công!");
				dispose();
				new Login().setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "Tên đăng nhập đã tồn tại!");
			}
		}
	}

	private boolean saveUser(String username, String password) {
		String query = "INSERT INTO users (username, password) VALUES (?, ?)";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, password); // Lưu ý: Nên mã hóa mật khẩu trong thực tế
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) { // Lỗi unique constraint violation trong PostgreSQL
				return false;
			} else {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Lỗi kết nối database!");
				return false;
			}
		}
	}
}

