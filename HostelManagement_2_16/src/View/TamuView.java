/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import controller.TamuController;
import model.Tamu;
import util.DateHelper;
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

    public TamuView() {
        ctrl = new TamuController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Tabel Data Tamu
        String[] cols = {"ID", "Nama", "No KTP", "No Telepon", "Alamat", "Tanggal Lahir"};
        model = new DefaultTableModel(cols, 0) { 
            public boolean isCellEditable(int r, int c) { return false; } 
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 2. Form Input
        JPanel form = new JPanel(new GridLayout(3, 4, 8, 8));
        form.add(new JLabel("ID (Edit/Hapus):")); txtId = new JTextField(); form.add(txtId);
        form.add(new JLabel("Nama Lengkap:")); txtNama = new JTextField(); form.add(txtNama);
        form.add(new JLabel("No KTP:")); txtKtp = new JTextField(); form.add(txtKtp);
        form.add(new JLabel("No Telepon:")); txtTelp = new JTextField(); form.add(txtTelp);
        form.add(new JLabel("Alamat:")); txtAlamat = new JTextField(); form.add(txtAlamat);
        form.add(new JLabel("Tgl Lahir (yyyy-MM-dd):")); txtTglLahir = new JTextField(); form.add(txtTglLahir);

        // 3. Tombol Aksi
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnDel = new JButton("Hapus");
        JButton btnRefresh = new JButton("Refresh");
        
        btns.add(btnAdd); btns.add(btnEdit); btns.add(btnDel); btns.add(btnRefresh);

        add(form, BorderLayout.NORTH);
        add(btns, BorderLayout.SOUTH);

        // 4. Event Listeners
        btnRefresh.addActionListener(e -> loadTable());
        btnAdd.addActionListener(e -> save(false));
        btnEdit.addActionListener(e -> save(true));
        btnDel.addActionListener(e -> delete());

        // Klik tabel untuk isi form
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(model.getValueAt(row, 0).toString());
                    txtNama.setText(model.getValueAt(row, 1).toString());
                    txtKtp.setText(model.getValueAt(row, 2).toString());
                    txtTelp.setText(model.getValueAt(row, 3).toString());
                    txtAlamat.setText(model.getValueAt(row, 4).toString());
                    // Format tanggal dari DB ke String yyyy-MM-dd agar bisa diedit
                    Object val = model.getValueAt(row, 5);
                    if (val instanceof Date) {
                        txtTglLahir.setText(DateHelper.parseToDate(val.toString()).toString());
                    } else {
                        txtTglLahir.setText(val.toString());
                    }
                }
            }
        });

        loadTable();
    }

    private void loadTable() {
        model.setRowCount(0);
        for (Tamu t : ctrl.getAllTamu()) {
            model.addRow(new Object[]{
                t.getId(), t.getNama(), t.getNoKtp(), t.getNoTelepon(), 
                t.getAlamat(), DateHelper.formatToIndonesian(t.getTanggalLahir())
            });
        }
    }

    private void save(boolean isEdit) {
        if (txtNama.getText().isEmpty() || txtKtp.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan No KTP wajib diisi!");
            return;
        }
        Tamu t = new Tamu();
        if (isEdit) t.setId(Integer.parseInt(txtId.getText()));
        
        t.setNama(txtNama.getText());
        t.setNoKtp(txtKtp.getText());
        t.setNoTelepon(txtTelp.getText());
        t.setAlamat(txtAlamat.getText());
        t.setTanggalLahir(DateHelper.parseToDate(txtTglLahir.getText()));

        boolean success = isEdit ? ctrl.updateTamu(t) : ctrl.insertTamu(t);
        if (success) {
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data. Cek No KTP (mungkin duplikat).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void delete() {
        if (txtId.getText().isEmpty()) return;
        int opt = JOptionPane.showConfirmDialog(this, "Yakin hapus data tamu ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            if (ctrl.deleteTamu(Integer.parseInt(txtId.getText()))) {
                JOptionPane.showMessageDialog(this, "Data terhapus!");
                loadTable();
                clearForm();
            }
        }
    }

    private void clearForm() {
        txtId.setText(""); txtNama.setText(""); txtKtp.setText("");
        txtTelp.setText(""); txtAlamat.setText(""); txtTglLahir.setText("");
    }
}
