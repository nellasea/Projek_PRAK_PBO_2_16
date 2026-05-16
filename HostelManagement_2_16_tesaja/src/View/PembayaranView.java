package View;

import Controller.PembayaranController;
import Model.Pembayaran;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;

public class PembayaranView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtIdRes, txtJumlah;
    private JComboBox<String> cbMetode;
    private PembayaranController ctrl;
    
    public PembayaranView() {
        ctrl = new PembayaranController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        
        JLabel lblTitle = new JLabel("PEMBAYARAN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.BLACK);
        
        String[] cols = {"ID", "ID Reservasi", "Jumlah", "Metode", "Tanggal", "Status"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Pembayaran Baru"));
        
        txtIdRes = new JTextField(10);
        txtJumlah = new JTextField(15);
        cbMetode = new JComboBox<>(new String[]{"Cash", "Transfer Bank", "Kartu Kredit"});
        JButton btnBayar = new JButton("Proses Bayar");
        JButton btnRefresh = new JButton("Refresh");
        
        formPanel.add(new JLabel("ID Reservasi:"));
        formPanel.add(txtIdRes);
        formPanel.add(new JLabel("Jumlah:"));
        formPanel.add(txtJumlah);
        formPanel.add(new JLabel("Metode:"));
        formPanel.add(cbMetode);
        formPanel.add(btnBayar);
        formPanel.add(btnRefresh);
        
        add(lblTitle, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
        
        btnRefresh.addActionListener(e -> loadTable());
        btnBayar.addActionListener(e -> processPayment());
        
        loadTable();
    }
    
    private void loadTable() {
        model.setRowCount(0);
        for (Pembayaran p : ctrl.getAllPembayaran()) {
            model.addRow(new Object[]{
                p.getId(), p.getIdReservasi(), "Rp " + p.getJumlahBayar(),
                p.getMetodePembayaran(), p.getTanggalBayar(), p.getStatus()
            });
        }
    }
    
    private void processPayment() {
        if (txtIdRes.getText().isEmpty() || txtJumlah.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Reservasi dan Jumlah harus diisi!");
            return;
        }
        
        try {
            Pembayaran p = new Pembayaran();
            p.setIdReservasi(Integer.parseInt(txtIdRes.getText()));
            p.setJumlahBayar(new BigDecimal(txtJumlah.getText()));
            p.setMetodePembayaran(cbMetode.getSelectedItem().toString());
            p.setStatus("Lunas");
            
            if (ctrl.insertPembayaran(p)) {
                JOptionPane.showMessageDialog(this, "Pembayaran berhasil!");
                loadTable();
                txtIdRes.setText("");
                txtJumlah.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Pembayaran gagal!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Format angka tidak valid!");
        }
    }
}