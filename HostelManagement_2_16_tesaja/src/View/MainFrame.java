package View;

import Model.User;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel sidebar;
    
    public MainFrame(User user) {
        setTitle("Hotel Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        // Sidebar
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(50, 50, 50));
        sidebar.setPreferredSize(new Dimension(200, 0));
        
        JLabel lblLogo = new JLabel("HOTEL SYS");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 18));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(new Color(70, 70, 70));
        userPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblNama = new JLabel(user.getNamaLengkap());
        lblNama.setFont(new Font("Arial", Font.BOLD, 13));
        lblNama.setForeground(Color.WHITE);
        
        userPanel.add(lblNama);
        
        // Menu buttons
        JButton btnDashboard = createMenuButton("DASHBOARD");
        JButton btnKamar = createMenuButton("KAMAR");
        JButton btnTamu = createMenuButton("TAMU");
        JButton btnReservasi = createMenuButton("RESERVASI");
        JButton btnPembayaran = createMenuButton("PEMBAYARAN");
        JButton btnLaporan = createMenuButton("REKAP PENDAPATAN");
        
        sidebar.add(lblLogo);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(userPanel);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(btnDashboard);
        sidebar.add(btnKamar);
        sidebar.add(btnTamu);
        sidebar.add(btnReservasi);
        sidebar.add(btnPembayaran);
        sidebar.add(btnLaporan);
        sidebar.add(Box.createVerticalGlue());
        
        JButton btnLogout = createMenuButton("LOGOUT");
        btnLogout.setBackground(new Color(200, 60, 50));
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalStrut(20));
        
        // Content Panel
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);
        
        contentPanel.add(new DashboardView(user), "DASHBOARD");
        contentPanel.add(new KamarView(), "KAMAR");
        contentPanel.add(new TamuView(), "TAMU");
        contentPanel.add(new ReservasiView(), "RESERVASI");
        contentPanel.add(new PembayaranView(), "PEMBAYARAN");
        contentPanel.add(new LaporanView(), "LAPORAN");
        
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        
        // Event
        btnDashboard.addActionListener(e -> cardLayout.show(contentPanel, "DASHBOARD"));
        btnKamar.addActionListener(e -> cardLayout.show(contentPanel, "KAMAR"));
        btnTamu.addActionListener(e -> cardLayout.show(contentPanel, "TAMU"));
        btnReservasi.addActionListener(e -> cardLayout.show(contentPanel, "RESERVASI"));
        btnPembayaran.addActionListener(e -> cardLayout.show(contentPanel, "PEMBAYARAN"));
        btnLaporan.addActionListener(e -> cardLayout.show(contentPanel, "LAPORAN"));
        
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                dispose();
            }
        });
        
        cardLayout.show(contentPanel, "DASHBOARD");
    }
    
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(50, 50, 50));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setPreferredSize(new Dimension(200, 45));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(70, 70, 70));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(50, 50, 50));
            }
        });
        return btn;
    }
}