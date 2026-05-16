package View;

import Controller.TamuController;
import Model.Tamu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;

public class TamuView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtNama, txtKtp, txtTelp, txtAlamat, txtTglLahir;
    private TamuController ctrl;
    
    public TamuView() {
        ctrl = new TamuController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        
        JLabel lblTitle = new JLabel("DATA TAMU");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.BLACK);
        
        String[] cols = {"ID", "Nama", "No KTP", "Telepon", "Alamat", "Tgl Lahir"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Tamu"));
        
        txtId = new JTextField();
        txtNama = new JTextField();
        txtKtp = new JTextField();
        txtTelp = new JTextField();
        txtAlamat = new JTextField();
        txtTglLahir = new JTextField();
        
        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Nama:"));
        formPanel.add(txtNama);
        formPanel.add(new JLabel("No KTP:"));
        formPanel.add(txtKtp);
        formPanel.add(new JLabel("Telepon:"));
        formPanel.add(txtTelp);
        formPanel.add(new JLabel("Alamat:"));
        formPanel.add(txtAlamat);
        formPanel.add(new JLabel("Tgl Lahir (YYYY-MM-DD):"));
        formPanel.add(txtTglLahir);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Hapus");
        JButton btnRefresh = new JButton("Refresh");
        
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);
        
        add(lblTitle, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
        
        btnRefresh.addActionListener(e -> loadTable());
        btnAdd.addActionListener(e -> save());
        btnEdit.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtKtp.setText(model.getValueAt(row, 2).toString());
                txtTelp.setText(model.getValueAt(row, 3).toString());
                txtAlamat.setText(model.getValueAt(row, 4).toString());
                txtTglLahir.setText(model.getValueAt(row, 5).toString());
            }
        });
        
        loadTable();
    }
    
    private void loadTable() {
        model.setRowCount(0);
        for (Tamu t : ctrl.getAllTamu()) {
            model.addRow(new Object[]{t.getId(), t.getNama(), t.getNoKtp(), t.getNoTelepon(), t.getAlamat(), t.getTanggalLahir()});
        }
    }
    
    private void save() {
        Tamu t = new Tamu();
        t.setNama(txtNama.getText());
        t.setNoKtp(txtKtp.getText());
        t.setNoTelepon(txtTelp.getText());
        t.setAlamat(txtAlamat.getText());
        t.setTanggalLahir(Date.valueOf(txtTglLahir.getText()));
        
        if (ctrl.insertTamu(t)) {
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan");
            loadTable();
            clearForm();
        }
    }
    
    private void update() {
        Tamu t = new Tamu();
        t.setId(Integer.parseInt(txtId.getText()));
        t.setNama(txtNama.getText());
        t.setNoKtp(txtKtp.getText());
        t.setNoTelepon(txtTelp.getText());
        t.setAlamat(txtAlamat.getText());
        t.setTanggalLahir(Date.valueOf(txtTglLahir.getText()));
        
        if (ctrl.updateTamu(t)) {
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
            loadTable();
            clearForm();
        }
    }
    
    private void delete() {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (ctrl.deleteTamu(Integer.parseInt(txtId.getText()))) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                loadTable();
                clearForm();
            }
        }
    }
    
    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtKtp.setText("");
        txtTelp.setText("");
        txtAlamat.setText("");
        txtTglLahir.setText("");
    }
}