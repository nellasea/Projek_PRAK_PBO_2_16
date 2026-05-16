package View;

import Model.User;
import config.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

public class DashboardView extends JPanel {
    private JLabel lblTotalKamar, lblKamarTersedia, lblTotalTamu, lblPendapatan;
    private JTable recentTable;
    private DefaultTableModel recentModel;
    
    public DashboardView(User user) {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        
        JLabel lblTitle = new JLabel("HALAMAN UTAMA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.BLACK);
        
        JLabel lblWelcome = new JLabel("Selamat datang, " + user.getNamaLengkap());
        lblWelcome.setFont(new Font("Arial", Font.PLAIN, 14));
        lblWelcome.setForeground(Color.BLACK);
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblWelcome, BorderLayout.EAST);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBackground(Color.WHITE);
        
        lblTotalKamar = createStatCard(statsPanel, "Total Kamar", "0");
        lblKamarTersedia = createStatCard(statsPanel, "Kamar Tersedia", "0");
        lblTotalTamu = createStatCard(statsPanel, "Total Tamu", "0");
        lblPendapatan = createStatCard(statsPanel, "Pendapatan Bulan Ini", "Rp 0");
        
        String[] cols = {"Jam", "Aktivitas", "Tamu", "Status"};
        recentModel = new DefaultTableModel(cols, 0);
        recentTable = new JTable(recentModel);
        recentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        recentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        recentTable.setRowHeight(30);
        
        JScrollPane scroll = new JScrollPane(recentTable);
        scroll.setBorder(BorderFactory.createTitledBorder("Aktivitas Terbaru"));
        
        add(headerPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
        
        loadStats();
        loadRecentActivities();
    }
    
    private JLabel createStatCard(JPanel parent, String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTitle.setForeground(Color.BLACK);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.BOLD, 22));
        lblValue.setForeground(Color.BLACK);
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        parent.add(card);
        
        return lblValue;
    }
    
    private void loadStats() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps1 = conn.prepareStatement("SELECT COUNT(*) FROM kamar");
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) lblTotalKamar.setText(String.valueOf(rs1.getInt(1)));
            
            PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) FROM kamar WHERE status = 'Tersedia'");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) lblKamarTersedia.setText(String.valueOf(rs2.getInt(1)));
            
            PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) FROM tamu");
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) lblTotalTamu.setText(String.valueOf(rs3.getInt(1)));
            
            PreparedStatement ps4 = conn.prepareStatement("SELECT SUM(total_harga) FROM reservasi WHERE MONTH(tanggal_check_out) = MONTH(CURDATE()) AND status = 'Check-Out'");
            ResultSet rs4 = ps4.executeQuery();
            if (rs4.next()) {
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                lblPendapatan.setText(format.format(rs4.getDouble(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadRecentActivities() {
        recentModel.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT DATE_FORMAT(r.tanggal_booking, '%H:%i') as waktu, " +
                 "CONCAT('Reservasi Kamar ', k.nomor_kamar) as aktivitas, " +
                 "t.nama as tamu, r.status as status " +
                 "FROM reservasi r JOIN tamu t ON r.id_tamu = t.id " +
                 "JOIN kamar k ON r.id_kamar = k.id ORDER BY r.tanggal_booking DESC LIMIT 10")) {
            while (rs.next()) {
                recentModel.addRow(new Object[]{
                    rs.getString("waktu"),
                    rs.getString("aktivitas"),
                    rs.getString("tamu"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}