/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("Hostel Management System");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuModul = new JMenu("Modul");
        JMenuItem miKamar = new JMenuItem("Data Kamar");
        JMenuItem miTamu = new JMenuItem("Data Tamu");
        JMenuItem miReservasi = new JMenuItem("Reservasi");
        JMenuItem miLaporan = new JMenuItem("Laporan Pendapatan");
        JMenuItem miLogout = new JMenuItem("Logout");

        menuModul.add(miKamar);
        menuModul.add(miTamu);
        menuModul.add(miReservasi);
        menuModul.add(miLaporan);
        menuBar.add(menuModul);
        menuBar.add(miLogout);
        setJMenuBar(menuBar);

        // Main Content with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        JLabel welcome = new JLabel("🏨 SELAMAT DATANG DI HOSTEL MANAGEMENT SYSTEM", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcome.setForeground(new Color(60, 52, 137));
        mainPanel.add(welcome, "home");

        add(mainPanel, BorderLayout.CENTER);

        // Menu Actions
        ActionListener switchPanel = e -> {
            String cmd = ((JMenuItem)e.getSource()).getText();
            switch(cmd) {
                case "Data Kamar": addOrShow(new KamarView(), "kamar"); break;
                case "Data Tamu": addOrShow(new TamuView(), "tamu"); break;
                case "Reservasi": addOrShow(new ReservasiView(), "reservasi"); break;
                case "Laporan Pendapatan": addOrShow(new LaporanView(), "laporan"); break;
                case "Logout": 
                    int opt = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if(opt == JOptionPane.YES_OPTION) {
                        dispose();
                        new LoginView().setVisible(true);
                    }
                    break;
            }
        };

        miKamar.addActionListener(switchPanel);
        miTamu.addActionListener(switchPanel);
        miReservasi.addActionListener(switchPanel);
        miLaporan.addActionListener(switchPanel);
        miLogout.addActionListener(switchPanel);
    }

    private void addOrShow(JPanel panel, String name) {
        if (!mainPanel.getComponentCount() > 0 || !mainPanel.getComponent(0).equals(panel)) {
            mainPanel.add(panel, name);
        }
        cardLayout.show(mainPanel, name);
    }
}