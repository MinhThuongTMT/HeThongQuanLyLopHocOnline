package HeThongQuanLyLopHocOnline;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class ChamDiem extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtBuoi, txtGiaoVien, txtThoiGian, txtCoSo, txtSySo;
    private ArrayList<JTextField> diemFields = new ArrayList<>();

    private String[] hocViens = {
        "Nguyễn Văn A", "Trần Thị B", "Lê Văn C",
        "Phạm Thị D", "Hoàng Văn E"
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ChamDiem frame = new ChamDiem();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ChamDiem() {
        setTitle("Chấm điểm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 500);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 160));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblBuoi = new JLabel("Buổi học:");
        lblBuoi.setFont(new Font("Times New Roman", Font.BOLD, 11));
        lblBuoi.setForeground(new Color(255, 255, 0));
        lblBuoi.setBackground(new Color(255, 255, 0));
        lblBuoi.setBounds(10, 10, 100, 20);
        contentPane.add(lblBuoi);

        txtBuoi = new JTextField();
        txtBuoi.setFont(new Font("Times New Roman", Font.PLAIN, 11));
        txtBuoi.setBounds(120, 10, 200, 20);
        contentPane.add(txtBuoi);

        JLabel lblGiaoVien = new JLabel("Giáo viên:");
        lblGiaoVien.setFont(new Font("Times New Roman", Font.BOLD, 11));
        lblGiaoVien.setBackground(new Color(255, 255, 0));
        lblGiaoVien.setForeground(new Color(255, 255, 0));
        lblGiaoVien.setBounds(10, 40, 100, 20);
        contentPane.add(lblGiaoVien);

        txtGiaoVien = new JTextField();
        txtGiaoVien.setFont(new Font("Times New Roman", Font.PLAIN, 11));
        txtGiaoVien.setBounds(120, 40, 200, 20);
        contentPane.add(txtGiaoVien);

        JLabel lblThoiGian = new JLabel("Thời gian:");
        lblThoiGian.setFont(new Font("Times New Roman", Font.BOLD, 11));
        lblThoiGian.setForeground(new Color(255, 255, 0));
        lblThoiGian.setBackground(new Color(255, 255, 0));
        lblThoiGian.setBounds(10, 70, 100, 20);
        contentPane.add(lblThoiGian);

        txtThoiGian = new JTextField();
        txtThoiGian.setFont(new Font("Times New Roman", Font.PLAIN, 11));
        txtThoiGian.setBounds(120, 70, 200, 20);
        contentPane.add(txtThoiGian);

        JLabel lblCoSo = new JLabel("Cơ sở:");
        lblCoSo.setFont(new Font("Times New Roman", Font.BOLD, 11));
        lblCoSo.setForeground(new Color(255, 255, 0));
        lblCoSo.setBackground(new Color(255, 255, 0));
        lblCoSo.setBounds(10, 100, 100, 20);
        contentPane.add(lblCoSo);

        txtCoSo = new JTextField();
        txtCoSo.setFont(new Font("Times New Roman", Font.PLAIN, 11));
        txtCoSo.setBounds(120, 100, 200, 20);
        contentPane.add(txtCoSo);

        JLabel lblSySo = new JLabel("Sĩ số:");
        lblSySo.setFont(new Font("Times New Roman", Font.BOLD, 11));
        lblSySo.setBackground(new Color(255, 255, 0));
        lblSySo.setForeground(new Color(255, 255, 0));
        lblSySo.setBounds(10, 130, 100, 20);
        contentPane.add(lblSySo);

        txtSySo = new JTextField();
        txtSySo.setFont(new Font("Times New Roman", Font.PLAIN, 11));
        txtSySo.setBounds(120, 130, 200, 20);
        contentPane.add(txtSySo);

        JLabel lblDanhSach = new JLabel("Danh sách học viên:");
        lblDanhSach.setFont(new Font("Times New Roman", Font.BOLD, 11));
        lblDanhSach.setForeground(new Color(255, 255, 0));
        lblDanhSach.setBackground(new Color(255, 255, 0));
        lblDanhSach.setBounds(10, 170, 200, 20);
        contentPane.add(lblDanhSach);

        JPanel panelHocVien = new JPanel();
        panelHocVien.setLayout(new GridLayout(hocViens.length, 2, 5, 5));

        for (String hv : hocViens) {
            JLabel lbl = new JLabel(hv);
            JTextField txt = new JTextField();
            diemFields.add(txt);
            panelHocVien.add(lbl);
            panelHocVien.add(txt);
        }

        JScrollPane scrollPane = new JScrollPane(panelHocVien);
        scrollPane.setBounds(10, 200, 500, 170);
        contentPane.add(scrollPane);

        JButton btnLuu = new JButton("Lưu điểm");
        btnLuu.setFont(new Font("Times New Roman", Font.BOLD, 11));
        btnLuu.setBackground(new Color(255, 255, 0));
        btnLuu.setBounds(400, 400, 100, 30);
        btnLuu.addActionListener(this::xuLyChamDiem);
        contentPane.add(btnLuu);
    }

    private void xuLyChamDiem(ActionEvent e) {
        String buoi = txtBuoi.getText().trim();
        String giaoVien = txtGiaoVien.getText().trim();
        String thoiGian = txtThoiGian.getText().trim();
        String coSo = txtCoSo.getText().trim();
        String sySo = txtSySo.getText().trim();

        if (buoi.isEmpty() || giaoVien.isEmpty() || thoiGian.isEmpty() || coSo.isEmpty() || sySo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin buổi học!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("📋 Thông tin buổi học:\n")
          .append("Buổi: ").append(buoi).append("\n")
          .append("Giáo viên: ").append(giaoVien).append("\n")
          .append("Thời gian: ").append(thoiGian).append("\n")
          .append("Cơ sở: ").append(coSo).append(" | Sỹ số: ").append(sySo).append("\n\n")
          .append("📌 Kết quả chấm điểm:\n");

        for (int i = 0; i < hocViens.length; i++) {
            String diem = diemFields.get(i).getText().trim();
            if (diem.isEmpty()) diem = "(Chưa chấm)";
            sb.append(hocViens[i]).append(": ").append(diem).append("\n");
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file chấm điểm");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                Files.write(fileToSave.toPath(), sb.toString().getBytes());
                JOptionPane.showMessageDialog(this, "💾 Đã lưu file thành công:\n" + fileToSave.getAbsolutePath(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Lỗi khi lưu file:\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
