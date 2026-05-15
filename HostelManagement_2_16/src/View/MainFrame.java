/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Map<String, JPanel> panelMap;

    public MainFrame() {
        setTitle("Hostel Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuModul = new JMenu("Modul");
        JMenuItem miKamar = new JMenuItem("Data Kamar");
        JMenuItem miTamu = new JMenuItem("Data Tamu");
        JMenuItem miReservasi = new JMenuItem("Reservasi");
        JMenuItem miPembayaran = new JMenuItem("Pembayaran");
        JMenuItem miLaporan = new JMenuItem("Laporan Pendapatan");
        JMenuItem miLogout = new JMenuItem("Logout");

        menuModul.add(miKamar);
        menuModul.add(miTamu);
        menuModul.add(miReservasi);
        menuModul.add(miPembayaran);
        menuModul.add(miLaporan);
        menuBar.add(menuModul);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(miLogout);
        setJMenuBar(menuBar);

        // Main Content with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        panelMap = new HashMap<>();
        
        // Welcome panel
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("🏨 SELAMAT DATANG DI HOSTEL MANAGEMENT SYSTEM", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcome.setForeground(new Color(60, 52, 137));
        welcomePanel.add(welcome, BorderLayout.CENTER);
        
        mainPanel.add(welcomePanel, "home");
        panelMap.put("home", welcomePanel);

        add(mainPanel, BorderLayout.CENTER);

        // Menu Actions
        miKamar.addActionListener(e -> showPanel("kamar", new KamarView()));
        miTamu.addActionListener(e -> showPanel("tamu", new TamuView()));
        miReservasi.addActionListener(e -> showPanel("reservasi", new ReservasiView()));
        miPembayaran.addActionListener(e -> showPanel("pembayaran", new PembayaranView()));
        miLaporan.addActionListener(e -> showPanel("laporan", new LaporanView()));
        miLogout.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if(opt == JOptionPane.YES_OPTION) {
                dispose();
                new LoginView().setVisible(true);
            }
        });
    }

    private void showPanel(String name, JPanel panel) {
        if (!panelMap.containsKey(name)) {
            mainPanel.add(panel, name);
            panelMap.put(name, panel);
        }
        cardLayout.show(mainPanel, name);
    }
}