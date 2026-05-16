/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private String role;
    public MainFrame(String role) {
        this.role = role;
        setTitle("Hostel Management System");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar bar = new JMenuBar();
        JMenu menuData = new JMenu("Data Master");
        JMenuItem miKamar = new JMenuItem("Data Kamar");
        JMenuItem miTamu = new JMenuItem("Data Tamu");
        JMenuItem miRes = new JMenuItem("Reservasi");
        JMenuItem miPay = new JMenuItem("Pembayaran");
        JMenuItem miLap = new JMenuItem("Laporan");
        JMenuItem miLogout = new JMenuItem("Logout");

        if("staff".equals(role)) {
            miKamar.setEnabled(false); miTamu.setEnabled(false); miRes.setEnabled(false);
        }

        menuData.add(miKamar); menuData.add(miTamu);
        bar.add(menuData);
        JMenu menuTrans = new JMenu("Transaksi");
        menuTrans.add(miRes); menuTrans.add(miPay);
        bar.add(menuTrans);
        JMenu menuRep = new JMenu("Laporan");
        menuRep.add(miLap);
        bar.add(menuRep);
        bar.add(miLogout);
        setJMenuBar(bar);

        add(new JLabel("SELAMAT DATANG, " + role.toUpperCase(), SwingConstants.CENTER), BorderLayout.CENTER);

        miKamar.addActionListener(e -> new KamarView().setVisible(true));
        miTamu.addActionListener(e -> new TamuView().setVisible(true));
        miRes.addActionListener(e -> new ReservasiView().setVisible(true));
        miPay.addActionListener(e -> new PembayaranView().setVisible(true));
        miLap.addActionListener(e -> new LaporanView().setVisible(true));
        miLogout.addActionListener(e -> { dispose(); new LoginView().setVisible(true); });
    }
}