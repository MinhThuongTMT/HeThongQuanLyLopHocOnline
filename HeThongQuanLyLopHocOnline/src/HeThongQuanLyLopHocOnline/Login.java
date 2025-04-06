package HeThongQuanLyLopHocOnline;

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

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Login frame = new Login();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Login() {
		setTitle("Đăng Nhập Hệ Thống");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		setLocationRelativeTo(null);
		setResizable(false);

		// Panel nền với gradient
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(),
						new Color(173, 216, 230));
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panel chứa form
		JPanel formPanel = new JPanel();
		formPanel.setBounds(150, 40, 450, 400);
		formPanel.setBackground(new Color(255, 255, 255, 230));
		formPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		formPanel.setLayout(null);
		contentPane.add(formPanel);

		// Tiêu đề
		JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
		lblTitle.setForeground(new Color(25, 25, 112));
		lblTitle.setBounds(0, 48, 450, 50);
		formPanel.add(lblTitle);

		// Username
		JLabel lblUsername = new JLabel("Tên đăng nhập:");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUsername.setBounds(50, 118, 120, 25);
		formPanel.add(lblUsername);

		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		textFieldUsername.setBounds(50, 143, 350, 40);
		textFieldUsername.setBorder(new LineBorder(new Color(169, 169, 169), 1, true));
		formPanel.add(textFieldUsername);

		// Password
		JLabel lblPassword = new JLabel("Mật khẩu:");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		lblPassword.setBounds(50, 193, 120, 25);
		formPanel.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordField.setBounds(50, 218, 350, 40);
		passwordField.setBorder(new LineBorder(new Color(169, 169, 169), 1, true));
		formPanel.add(passwordField);

		JButton btnShowPassword = new JButton();
		btnShowPassword.setBounds(410, 218, 30, 40);
		setupEyeButton(btnShowPassword, passwordField, "/Icon/eye_close.png");
		formPanel.add(btnShowPassword);

		// Nút Đăng nhập
		JButton btnLogin = new JButton("Đăng Nhập");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
		btnLogin.setBackground(new Color(65, 105, 225));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBounds(50, 308, 150, 45);
		btnLogin.setBorderPainted(false);
		btnLogin.setFocusPainted(false);
		addHoverEffect(btnLogin);
		btnLogin.addActionListener(e -> handleLogin());
		formPanel.add(btnLogin);

		// Nút Đăng ký
		JButton btnSignUp = new JButton("Đăng Ký");
		btnSignUp.setFont(new Font("Arial", Font.BOLD, 16));
		btnSignUp.setBackground(new Color(70, 130, 180));
		btnSignUp.setForeground(Color.WHITE);
		btnSignUp.setBounds(250, 308, 150, 45);
		btnSignUp.setBorderPainted(false);
		btnSignUp.setFocusPainted(false);
		addHoverEffect(btnSignUp);
		btnSignUp.addActionListener(e -> {
			new SignUp().setVisible(true);
			dispose();
		});
		formPanel.add(btnSignUp);

		// Nút Quên mật khẩu
		JButton btnForgot = new JButton("Quên mật khẩu?");
		btnForgot.setFont(new Font("Arial", Font.ITALIC, 13));
		btnForgot.setForeground(new Color(65, 105, 225));
		btnForgot.setBounds(250, 273, 150, 25);
		btnForgot.setBorderPainted(false);
		btnForgot.setContentAreaFilled(false);
		btnForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnForgot.addActionListener(e -> handleForgotPassword());
		formPanel.add(btnForgot);
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

	private void handleLogin() {
		String username = textFieldUsername.getText();
		String password = new String(passwordField.getPassword());
		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
		} else {
			JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
			dispose();
			new TrangChu().setVisible(true);
		}
	}

	private void handleForgotPassword() {
		String emailOrUser = JOptionPane.showInputDialog(this, "Nhập email hoặc tên đăng nhập:");
		if (emailOrUser != null && !emailOrUser.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Hệ thống sẽ gửi liên kết đặt lại mật khẩu đến email của bạn (mô phỏng)");
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin!");
		}
	}
}