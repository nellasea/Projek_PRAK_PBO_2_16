package View;

import config.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

public class LaporanView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbBulan, cbTahun;
    private JLabel lblTotal;
    
    public LaporanView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        
        JLabel lblTitle = new JLabel("REKAPAN PENDAPATAN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.BLACK);
        
        // Panel Filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createTitledBorder("Pilih Periode"));
        
        String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
                         "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        cbBulan = new JComboBox<>(bulan);
        cbTahun = new JComboBox<>(new String[]{"2023", "2024", "2025", "2026"});
        JButton btnTampilkan = new JButton("Tampilkan");
        JButton btnRefresh = new JButton("Refresh");
        
        filterPanel.add(new JLabel("Bulan:"));
        filterPanel.add(cbBulan);
        filterPanel.add(new JLabel("Tahun:"));
        filterPanel.add(cbTahun);
        filterPanel.add(btnTampilkan);
        filterPanel.add(btnRefresh);
        
        // Tabel
        String[] cols = {"No", "Tamu", "Kamar", "Check-In", "Check-Out", "Malam", "Total"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        
        // Panel Total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(Color.WHITE);
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel lblTotalLabel = new JLabel("Total Pendapatan: ");
        lblTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalLabel.setForeground(Color.BLACK);
        
        lblTotal = new JLabel("Rp 0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(new Color(0, 100, 0));
        
        totalPanel.add(lblTotalLabel);
        totalPanel.add(lblTotal);
        
        // Susun Layout
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.add(lblTitle, BorderLayout.NORTH);
        northPanel.add(filterPanel, BorderLayout.CENTER);
        
        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH);
        
        // Event
        btnTampilkan.addActionListener(e -> loadReport());
        btnRefresh.addActionListener(e -> loadReport());
        
        // Set default ke bulan saat ini
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cbBulan.setSelectedIndex(cal.get(java.util.Calendar.MONTH));
        cbTahun.setSelectedItem(String.valueOf(cal.get(java.util.Calendar.YEAR)));
        
        loadReport();
    }
    
    private void loadReport() {
        int bulan = cbBulan.getSelectedIndex() + 1;
        String tahun = cbTahun.getSelectedItem().toString();
        
        model.setRowCount(0);
        double total = 0;
        int no = 1;
        
        String sql = "SELECT r.id, t.nama, k.nomor_kamar, " +
                     "r.tanggal_check_in, r.tanggal_check_out, " +
                     "r.jumlah_malam, r.total_harga " +
                     "FROM reservasi r " +
                     "JOIN tamu t ON r.id_tamu = t.id " +
                     "JOIN kamar k ON r.id_kamar = k.id " +
                     "WHERE MONTH(r.tanggal_check_out) = ? " +
                     "AND YEAR(r.tanggal_check_out) = ? " +
                     "AND r.status = 'Check-Out' " +
                     "ORDER BY r.tanggal_check_out DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bulan);
            stmt.setInt(2, Integer.parseInt(tahun));
            ResultSet rs = stmt.executeQuery();
            
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            
            while (rs.next()) {
                double harga = rs.getDouble("total_harga");
                total += harga;
                
                model.addRow(new Object[]{
                    no++,
                    rs.getString("nama"),
                    rs.getString("nomor_kamar"),
                    rs.getDate("tanggal_check_in"),
                    rs.getDate("tanggal_check_out"),
                    rs.getInt("jumlah_malam"),
                    format.format(harga)
                });
            }
            
            if (model.getRowCount() == 0) {
                model.addRow(new Object[]{"-", "Tidak ada data", "-", "-", "-", "-", "-"});
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        lblTotal.setText(format.format(total));
    }
}