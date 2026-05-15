package View;

import Controller.TamuController;
import Model.Tamu;
import Util.DateHelper;
import Util.ValidationHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class TamuView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtNama, txtKtp, txtTelp, txtAlamat, txtTglLahir;
    private TamuController ctrl;
    private JButton btnAdd, btnEdit, btnDel, btnRefresh;

    public TamuView() {
        ctrl = new TamuController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel Refresh
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRefresh = new JButton("Refresh Data");
        topPanel.add(btnRefresh);
        add(topPanel, BorderLayout.NORTH);

        // Tabel
        String[] cols = {"ID", "Nama", "No KTP", "No Telepon", "Alamat", "Tanggal Lahir"};
        model = new DefaultTableModel(cols, 0) { 
            public boolean isCellEditable(int r, int c) { return false; } 
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form
        JPanel form = new JPanel(new GridLayout(3, 3, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Form Data Tamu"));
        
        form.add(new JLabel("ID:")); 
        txtId = new JTextField(); 
        txtId.setEditable(false);
        form.add(txtId);
        
        form.add(new JLabel("Nama:*")); 
        txtNama = new JTextField(); 
        form.add(txtNama);
        
        form.add(new JLabel("No KTP:*")); 
        txtKtp = new JTextField(); 
        form.add(txtKtp);
        
        form.add(new JLabel("No Telepon:")); 
        txtTelp = new JTextField(); 
        form.add(txtTelp);
        
        form.add(new JLabel("Alamat:")); 
        txtAlamat = new JTextField(); 
        form.add(txtAlamat);
        
        form.add(new JLabel("Tgl Lahir (yyyy-MM-dd):")); 
        txtTglLahir = new JTextField(); 
        form.add(txtTglLahir);

        // Tombol
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAdd = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnDel = new JButton("Hapus");
        
        btnAdd.setBackground(new Color(60, 52, 137));
        btnAdd.setForeground(Color.WHITE);
        btnEdit.setBackground(new Color(255, 165, 0));
        btnDel.setBackground(new Color(220, 53, 69));
        btnDel.setForeground(Color.WHITE);
        
        btns.add(btnAdd); 
        btns.add(btnEdit); 
        btns.add(btnDel);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(form, BorderLayout.CENTER);
        bottomPanel.add(btns, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Event
        btnRefresh.addActionListener(e -> loadTable());
        btnAdd.addActionListener(e -> save(false));
        btnEdit.addActionListener(e -> save(true));
        btnDel.addActionListener(e -> delete());

        // Klik tabel
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(model.getValueAt(row, 0).toString());
                    txtNama.setText(model.getValueAt(row, 1).toString());
                    txtKtp.setText(model.getValueAt(row, 2).toString());
                    txtTelp.setText(model.getValueAt(row, 3).toString());
                    txtAlamat.setText(model.getValueAt(row, 4).toString());
                    txtTglLahir.setText(""); // Kosongkan tanggal
                }
            }
        });

        loadTable();
    }

    private void loadTable() {
        model.setRowCount(0);
        try {
            List<Tamu> list = ctrl.getAllTamu();
            for (Tamu t : list) {
                model.addRow(new Object[]{
                    t.getId(), 
                    t.getNama(), 
                    t.getNoKtp(), 
                    t.getNoTelepon() != null ? t.getNoTelepon() : "-", 
                    t.getAlamat() != null ? t.getAlamat() : "-", 
                    t.getTanggalLahir() != null ? DateHelper.formatToIndonesian(t.getTanggalLahir()) : "-"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void save(boolean isEdit) {
        if (txtNama.getText().trim().isEmpty() || txtKtp.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan No KTP wajib diisi!");
            return;
        }
        
        if (!ValidationHelper.isValidKTP(txtKtp.getText().trim())) {
            JOptionPane.showMessageDialog(this, "No KTP harus 16 digit angka!");
            return;
        }
        
        try {
            Tamu t = new Tamu();
            if (isEdit) {
                if (txtId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit!");
                    return;
                }
                t.setId(Integer.parseInt(txtId.getText()));
            }
            
            t.setNama(txtNama.getText().trim());
            t.setNoKtp(txtKtp.getText().trim());
            t.setNoTelepon(txtTelp.getText().trim());
            t.setAlamat(txtAlamat.getText().trim());
            
            Date tglLahir = null;
            if (!txtTglLahir.getText().trim().isEmpty()) {
                tglLahir = DateHelper.parseToDate(txtTglLahir.getText().trim());
            }
            t.setTanggalLahir(tglLahir);

            boolean success = isEdit ? ctrl.updateTamu(t) : ctrl.insertTamu(t);
            
            if (success) {
                JOptionPane.showMessageDialog(this, isEdit ? "Data berhasil diupdate!" : "Data berhasil ditambahkan!");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // FIXED: Ini yang benar untuk TamuView!
    private void delete() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Yakin ingin menghapus tamu: " + txtNama.getText() + "?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                
                // PANGGIL deleteTamu, BUKAN deleteKamar!
                boolean success = ctrl.deleteTamu(id);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus data!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
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