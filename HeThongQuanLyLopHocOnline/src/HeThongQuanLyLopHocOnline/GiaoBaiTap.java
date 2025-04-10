package HeThongQuanLyLopHocOnline;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

public class GiaoBaiTap extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTieuDe;
	private JTextField txtGiaoVien; // ⬅ Thêm mới
	private JLabel lblFileName;
	private JDateChooser dateChooser;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GiaoBaiTap frame = new GiaoBaiTap();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GiaoBaiTap() {
		setTitle("Giao Bài Tập");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 330);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 160));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTieuDe = new JLabel("Tiêu đề bài tập:");
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblTieuDe.setForeground(new Color(255, 255, 0));
		lblTieuDe.setBounds(10, 11, 127, 14);
		contentPane.add(lblTieuDe);

		txtTieuDe = new JTextField();
		txtTieuDe.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtTieuDe.setBounds(157, 8, 213, 20);
		contentPane.add(txtTieuDe);
		txtTieuDe.setColumns(10);

		JLabel lblGiaoVien = new JLabel("Tên giáo viên:");
		lblGiaoVien.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblGiaoVien.setForeground(Color.YELLOW);
		lblGiaoVien.setBounds(10, 36, 127, 14);
		contentPane.add(lblGiaoVien);

		txtGiaoVien = new JTextField();
		txtGiaoVien.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtGiaoVien.setBounds(157, 33, 213, 20);
		contentPane.add(txtGiaoVien);
		txtGiaoVien.setColumns(10);

		JLabel lblNoiDung = new JLabel("Nội dung/miêu tả:");
		lblNoiDung.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblNoiDung.setForeground(new Color(255, 255, 0));
		lblNoiDung.setBounds(10, 61, 127, 14);
		contentPane.add(lblNoiDung);

		JTextArea txtNoiDung = new JTextArea();
		txtNoiDung.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtNoiDung.setBounds(10, 86, 416, 32);
		contentPane.add(txtNoiDung);

		JLabel lblHanNop = new JLabel("Hạn nộp:");
		lblHanNop.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblHanNop.setForeground(new Color(255, 255, 0));
		lblHanNop.setBounds(10, 129, 49, 14);
		contentPane.add(lblHanNop);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(69, 126, 127, 30);
		contentPane.add(dateChooser);

		JLabel lblTep = new JLabel("Tệp đính kèm:");
		lblTep.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblTep.setForeground(new Color(255, 255, 0));
		lblTep.setBounds(219, 129, 92, 14);
		contentPane.add(lblTep);

		JButton btnChonTep = new JButton("Chọn tệp");
		btnChonTep.setFont(new Font("Times New Roman", Font.BOLD, 11));
		btnChonTep.setBackground(new Color(255, 255, 0));
		btnChonTep.setBounds(219, 154, 105, 18);
		btnChonTep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					lblFileName.setText(selectedFile.getName());
				}
			}
		});
		contentPane.add(btnChonTep);

		lblFileName = new JLabel("Chưa chọn tệp");
		lblFileName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFileName.setForeground(new Color(255, 255, 0));
		lblFileName.setBounds(219, 182, 217, 31);
		contentPane.add(lblFileName);

		JButton btnGuiBaiTap = new JButton("Gửi bài tập");
		btnGuiBaiTap.setFont(new Font("Times New Roman", Font.BOLD, 11));
		btnGuiBaiTap.setBackground(new Color(255, 255, 0));
		btnGuiBaiTap.setBounds(221, 228, 103, 23);
		btnGuiBaiTap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tieuDe = txtTieuDe.getText();
				String giaoVien = txtGiaoVien.getText();
				String noiDung = txtNoiDung.getText();
				String fileName = lblFileName.getText();

				if (dateChooser.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày hạn nộp!");
					return;
				}

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String hanNop = sdf.format(dateChooser.getDate());

				String thongBao = "📌 Bài tập đã được gửi!\n\n" + "Tiêu đề: " + tieuDe + "\n" + "Giáo viên: " + giaoVien
						+ "\n" + "Hạn nộp: " + hanNop + "\n" + "File: " + fileName;

				JOptionPane.showMessageDialog(null, thongBao);
			}
		});
		contentPane.add(btnGuiBaiTap);
	}
}
