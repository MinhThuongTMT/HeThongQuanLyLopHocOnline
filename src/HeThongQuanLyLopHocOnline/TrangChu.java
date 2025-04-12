package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

public class TrangChu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel TrangChinh;
	private Component[] initialComponents;
	private JButton[] menuButtons;
	private Color defaultColor = new Color(255, 204, 0);
	private Color selectedColor = new Color(255, 255, 0);

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

		menuButtons = new JButton[] { TrangChu_Button, Student_Button, Teach_Button, Manage_Button, BaiTap_Button,
				Exit_Button };

		for (JButton button : menuButtons) {
			panel.add(button);
		}

		// Tạo JPopupMenu cho nút Quản Lý
		JPopupMenu manageMenu = new JPopupMenu();
		JMenuItem manageStudentItem = new JMenuItem("Quản Lý Sinh Viên");
		JMenuItem manageTeacherItem = new JMenuItem("Quản Lý Giảng Viên");
		JMenuItem manageClassItem = new JMenuItem("Quản Lý Lớp Học");

		manageMenu.add(manageStudentItem);
		manageMenu.add(manageTeacherItem);
		manageMenu.add(manageClassItem);

		// Thêm MouseListener để hiển thị menu ngữ cảnh khi nhấn nút Quản Lý
		Manage_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manageMenu.show(Manage_Button, 0, Manage_Button.getHeight());
			}
		});

		// ActionListener cho các mục trong menu ngữ cảnh
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

		TrangChu_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetButtonColors();
				TrangChu_Button.setBackground(selectedColor);
				TrangChinh.removeAll();
				for (Component comp : initialComponents) {
					TrangChinh.add(comp);
				}
				TrangChinh.revalidate();
				TrangChinh.repaint();
			}
		});

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

		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Icon/logo-.png"));
		Image scaledShopp = logoIcon.getImage().getScaledInstance(76, 69, Image.SCALE_SMOOTH);
		JLabel logo = new JLabel(new ImageIcon(scaledShopp));
		logo.setBounds(0, 23, 76, 69);
		panel.add(logo);

		TrangChinh = new JPanel();
		TrangChinh.setBounds(81, 11, 895, 652);
		TrangChinh.setBackground(new Color(0, 0, 121));
		contentPane.add(TrangChinh);
		TrangChinh.setLayout(null);

		JLabel TrangChuLabel = new JLabel("TRANG CHỦ");
		TrangChuLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		TrangChuLabel.setForeground(new Color(255, 255, 255));
		TrangChuLabel.setBounds(10, 39, 161, 43);
		TrangChinh.add(TrangChuLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 109, 443, 267);
		TrangChinh.add(panel_1);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(10, 387, 875, 254);
		TrangChinh.add(panel_1_1);

		JPanel panel_1_2 = new JPanel();
		panel_1_2.setBounds(468, 109, 417, 267);
		TrangChinh.add(panel_1_2);

		initialComponents = TrangChinh.getComponents();
	}

	private JButton createButton(String iconPath, int yPos) {
		JButton button = new JButton();
		ImageIcon originalIcon = new ImageIcon(TrangChu.class.getResource(iconPath));
		Image resizedImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(resizedImage));
		button.setBounds(0, yPos, 76, 76);
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		return button;
	}

	private void resetButtonColors() {
		for (JButton button : menuButtons) {
			button.setOpaque(true);
			button.setContentAreaFilled(false);
			button.setBackground(null);
		}
	}
}