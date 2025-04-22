package QuanLyLopHoc;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;

    // Thông tin kết nối database
    private static final String DB_URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&pgbouncer=true";
    private static final String DB_USERNAME = "postgres.vpehkzjmzpcskfzjjyql";
    private static final String DB_PASSWORD = "MinhThuong0808";

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
        // Khởi tạo bảng users khi khởi động
        initDatabase();

        setTitle("Đăng Nhập Hệ Thống");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Tạo contentPane với hình nền
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
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JPanel formPanel = new JPanel();
        formPanel.setBounds(150, 40, 450, 400);
        formPanel.setBackground(new Color(106, 181, 255)); // Giữ độ trong suốt
        formPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        formPanel.setLayout(null);
        contentPane.add(formPanel);

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setBackground(new Color(23, 139, 255));
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitle.setForeground(new Color(25, 25, 112));
        lblTitle.setBounds(0, 48, 450, 50);
        formPanel.add(lblTitle);

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsername.setBounds(50, 118, 120, 25);
        formPanel.add(lblUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        textFieldUsername.setBounds(50, 143, 350, 40);
        textFieldUsername.setBorder(new LineBorder(new Color(169, 169, 169), 1, true));
        formPanel.add(textFieldUsername);

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

        JButton btnLogin = new JButton("Đăng Nhập");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setBackground(new Color(0, 82, 164));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBounds(50, 308, 150, 45);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        addHoverEffect(btnLogin);
        btnLogin.addActionListener(e -> handleLogin());
        formPanel.add(btnLogin);

        JButton btnSignUp = new JButton("Đăng Ký");
        btnSignUp.setFont(new Font("Arial", Font.BOLD, 16));
        btnSignUp.setBackground(new Color(255, 51, 51));
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

        JButton btnForgot = new JButton("Quên mật khẩu?");
        btnForgot.setFont(new Font("Arial", Font.ITALIC, 13));
        btnForgot.setForeground(new Color(255, 255, 255));
        btnForgot.setBounds(250, 273, 150, 25);
        btnForgot.setBorderPainted(false);
        btnForgot.setContentAreaFilled(false);
        btnForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnForgot.addActionListener(e -> handleForgotPassword());
        formPanel.add(btnForgot);
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

    private boolean checkLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // Lưu ý: Trong thực tế nên mã hóa mật khẩu

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu tìm thấy bản ghi khớp

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối database!");
            return false;
        }
    }

    private void handleLogin() {
        String username = textFieldUsername.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        if (checkLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            dispose();
            new TrangChu().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng!");
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