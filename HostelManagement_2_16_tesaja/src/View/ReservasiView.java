package View;

import Controller.ReservasiController;
import Model.Reservasi;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;

public class ReservasiView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtIdTamu, txtIdKamar, txtIn, txtOut, txtTotal;
    private JComboBox<String> cbStatus;
    private ReservasiController ctrl;
    
    public ReservasiView() {
        ctrl = new ReservasiController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        
        JLabel lblTitle = new JLabel("DATA RESERVASI");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.BLACK);
        
        String[] cols = {"ID", "Tamu", "Kamar", "Check-In", "Check-Out", "Malam", "Total", "Status"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Reservasi"));
        
        txtId = new JTextField();
        txtIdTamu = new JTextField();
        txtIdKamar = new JTextField();
        txtIn = new JTextField();
        txtOut = new JTextField();
        txtTotal = new JTextField();
        cbStatus = new JComboBox<>(new String[]{"Booking", "Check-In", "Check-Out"});
        
        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("ID Tamu:"));
        formPanel.add(txtIdTamu);
        formPanel.add(new JLabel("ID Kamar:"));
        formPanel.add(txtIdKamar);
        formPanel.add(new JLabel("Check-In (YYYY-MM-DD):"));
        formPanel.add(txtIn);
        formPanel.add(new JLabel("Check-Out (YYYY-MM-DD):"));
        formPanel.add(txtOut);
        formPanel.add(new JLabel("Total Harga:"));
        formPanel.add(txtTotal);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(cbStatus);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Booking");
        JButton btnCheckIn = new JButton("Check-In");
        JButton btnCheckOut = new JButton("Check-Out");
        JButton btnRefresh = new JButton("Refresh");
        
        btnPanel.add(btnAdd);
        btnPanel.add(btnCheckIn);
        btnPanel.add(btnCheckOut);
        btnPanel.add(btnRefresh);
        
        add(lblTitle, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
        
        btnRefresh.addActionListener(e -> loadTable());
        btnAdd.addActionListener(e -> save());
        btnCheckIn.addActionListener(e -> updateStatus("Check-In"));
        btnCheckOut.addActionListener(e -> updateStatus("Check-Out"));
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtIdTamu.setText(model.getValueAt(row, 1).toString());
                txtIdKamar.setText(model.getValueAt(row, 2).toString());
                txtIn.setText(model.getValueAt(row, 3).toString());
                txtOut.setText(model.getValueAt(row, 4).toString());
                cbStatus.setSelectedItem(model.getValueAt(row, 7).toString());
            }
        });
        
        loadTable();
    }
    
    private void loadTable() {
        model.setRowCount(0);
        for (Reservasi r : ctrl.getAllReservasi()) {
            model.addRow(new Object[]{
                r.getId(), r.getNamaTamu(), r.getNomorKamar(),
                r.getTanggalCheckIn(), r.getTanggalCheckOut(), r.getJumlahMalam(),
                "Rp " + r.getTotalHarga(), r.getStatus()
            });
        }
    }
    
    private void save() {
        Reservasi r = new Reservasi();
        r.setIdTamu(Integer.parseInt(txtIdTamu.getText()));
        r.setIdKamar(Integer.parseInt(txtIdKamar.getText()));
        r.setTanggalCheckIn(Date.valueOf(txtIn.getText()));
        r.setTanggalCheckOut(Date.valueOf(txtOut.getText()));
        
        long diff = r.getTanggalCheckOut().getTime() - r.getTanggalCheckIn().getTime();
        int days = (int)(diff / (1000 * 60 * 60 * 24));
        r.setJumlahMalam(days);
        r.setTotalHarga(new BigDecimal(txtTotal.getText()));
        r.setStatus("Booking");
        
        if (ctrl.addReservasi(r)) {
            JOptionPane.showMessageDialog(this, "Booking berhasil!");
            loadTable();
            clearForm();
        }
    }
    
    private void updateStatus(String status) {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }
        if (ctrl.updateStatus(Integer.parseInt(txtId.getText()), status)) {
            JOptionPane.showMessageDialog(this, "Status diubah menjadi " + status);
            loadTable();
            clearForm();
        }
    }
    
    private void clearForm() {
        txtId.setText("");
        txtIdTamu.setText("");
        txtIdKamar.setText("");
        txtIn.setText("");
        txtOut.setText("");
        txtTotal.setText("");
    }
}