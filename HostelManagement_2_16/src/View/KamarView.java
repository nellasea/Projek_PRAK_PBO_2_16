/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.KamarController;
import Model.Kamar;
import Util.NumberHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class KamarView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtNomor, txtTipe, txtHarga, txtFasilitas, txtId, txtCari;
    private JComboBox<String> cbStatus;
    private KamarController ctrl;
    private JButton btnAdd, btnEdit, btnDel, btnRefresh, btnSearch;

    public KamarView() {
        ctrl = new KamarController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari:"));
        txtCari = new JTextField(15);
        btnSearch = new JButton("Cari");
        btnRefresh = new JButton("Refresh");
        searchPanel.add(txtCari);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);
        add(searchPanel, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "No Kamar", "Tipe", "Harga/Malam", "Status", "Fasilitas"};
        model = new DefaultTableModel(cols, 0) { 
            public boolean isCellEditable(int r, int c) { return false; } 
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form Input
        JPanel form = new JPanel(new GridLayout(3, 4, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Form Kamar"));
        
        form.add(new JLabel("ID (Edit/Hapus):")); 
        txtId = new JTextField(); 
        txtId.setEditable(false);
        form.add(txtId);
        
        form.add(new JLabel("Nomor Kamar:*")); 
        txtNomor = new JTextField(); 
        form.add(txtNomor);
        
        form.add(new JLabel("Tipe:")); 
        txtTipe = new JTextField(); 
        form.add(txtTipe);
        
        form.add(new JLabel("Harga/Malam:*")); 
        txtHarga = new JTextField(); 
        form.add(txtHarga);
        
        form.add(new JLabel("Status:")); 
        cbStatus = new JComboBox<>(new String[]{"Tersedia", "Terisi", "Maintenance"}); 
        form.add(cbStatus);
        
        form.add(new JLabel("Fasilitas:")); 
        txtFasilitas = new JTextField(); 
        form.add(txtFasilitas);

        // Buttons
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

        // Layout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(form, BorderLayout.CENTER);
        bottomPanel.add(btns, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Events
        btnRefresh.addActionListener(e -> loadTable());
        btnSearch.addActionListener(e -> searchKamar());
        btnAdd.addActionListener(e -> save(false));
        btnEdit.addActionListener(e -> save(true));
        btnDel.addActionListener(e -> delete());
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if(row >= 0) {
                    txtId.setText(model.getValueAt(row, 0).toString());
                    txtNomor.setText(model.getValueAt(row, 1).toString());
                    txtTipe.setText(model.getValueAt(row, 2).toString());
                    String hargaStr = model.getValueAt(row, 3).toString();
                    hargaStr = hargaStr.replace("Rp", "").replace(".", "").trim();
                    txtHarga.setText(hargaStr);
                    cbStatus.setSelectedItem(model.getValueAt(row, 4).toString());
                    txtFasilitas.setText(model.getValueAt(row, 5).toString());
                }
            }
        });

        loadTable();
    }

    private void loadTable() {
        model.setRowCount(0);
        try {
            List<Kamar> list = ctrl.getAllKamar();
            for(Kamar k : list) {
                model.addRow(new Object[]{
                    k.getId(), 
                    k.getNomorKamar(), 
                    k.getTipe(), 
                    NumberHelper.formatRupiah(k.getHargaPerMalam().doubleValue()), 
                    k.getStatus(), 
                    k.getFasilitas()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void searchKamar() {
        String keyword = txtCari.getText().trim();
        if (keyword.isEmpty()) {
            loadTable();
            return;
        }
        
        model.setRowCount(0);
        try {
            List<Kamar> list = ctrl.getAllKamar();
            for(Kamar k : list) {
                if (k.getNomorKamar().toLowerCase().contains(keyword.toLowerCase()) ||
                    k.getTipe().toLowerCase().contains(keyword.toLowerCase())) {
                    model.addRow(new Object[]{
                        k.getId(), 
                        k.getNomorKamar(), 
                        k.getTipe(), 
                        NumberHelper.formatRupiah(k.getHargaPerMalam().doubleValue()), 
                        k.getStatus(), 
                        k.getFasilitas()
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void save(boolean isEdit) {
        if (txtNomor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nomor Kamar wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            txtNomor.requestFocus();
            return;
        }
        
        if (txtHarga.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harga wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            txtHarga.requestFocus();
            return;
        }
        
        try {
            Kamar k = new Kamar();
            if(isEdit) {
                if(txtId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit terlebih dahulu!");
                    return;
                }
                k.setId(Integer.parseInt(txtId.getText()));
            }
            
            k.setNomorKamar(txtNomor.getText().trim());
            k.setTipe(txtTipe.getText().trim());
            k.setHargaPerMalam(new BigDecimal(txtHarga.getText().trim()));
            k.setStatus(cbStatus.getSelectedItem().toString());
            k.setFasilitas(txtFasilitas.getText().trim());

            boolean success;
            if(isEdit) {
                success = ctrl.updateKamar(k);
            } else {
                success = ctrl.insertKamar(k);
            }
            
            if(success) { 
                JOptionPane.showMessageDialog(this, isEdit ? "Data berhasil diupdate!" : "Data berhasil ditambahkan!");
                loadTable(); 
                clearForm(); 
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Format harga tidak valid! Masukkan angka yang benar.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // FIXED: Perbaikan method delete
    private void delete() {
        // Cek apakah ada data yang dipilih
        if(txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Silakan pilih data yang akan dihapus terlebih dahulu!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Konfirmasi penghapusan
        String nomorKamar = txtNomor.getText();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus kamar dengan nomor: " + nomorKamar + "?\nData yang sudah dihapus tidak dapat dikembalikan.", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if(confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                boolean success = ctrl.deleteKamar(id);
                
                if(success) {
                    JOptionPane.showMessageDialog(this, "Data kamar berhasil dihapus!"); 
                    loadTable(); 
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Gagal menghapus data!\nMungkin kamar sedang digunakan dalam reservasi.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "ID tidak valid: " + txtId.getText(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Terjadi error saat menghapus: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtNomor.setText("");
        txtTipe.setText("");
        txtHarga.setText("");
        txtFasilitas.setText("");
        cbStatus.setSelectedIndex(0);
        txtNomor.requestFocus();
    }
}