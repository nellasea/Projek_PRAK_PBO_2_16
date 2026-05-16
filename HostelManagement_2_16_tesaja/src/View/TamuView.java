/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package View;

import Controller.TamuController;
import Model.Tamu;
import Util.DateHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TamuView extends JFrame {
    private JTable tbl = new JTable();
    private DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Nama","NIK","HP","Alamat","Tgl Lahir"},0);
    private JTextField txtNama=new JTextField(), txtNik=new JTextField(), txtHp=new JTextField(), txtAlmt=new JTextField(), txtTgl=new JTextField(), txtId=new JTextField();
    private TamuController ctrl = new TamuController();

    public TamuView() {
        setTitle("Manajemen Tamu");
        setSize(800,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));
        tbl.setModel(model); add(new JScrollPane(tbl), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(3,3,5,5));
        form.add(new JLabel("ID:")); form.add(txtId); txtId.setEditable(false);
        form.add(new JLabel("Nama:")); form.add(txtNama);
        form.add(new JLabel("NIK:")); form.add(txtNik);
        form.add(new JLabel("HP:")); form.add(txtHp);
        form.add(new JLabel("Tgl Lahir (yyyy-MM-dd):")); form.add(txtTgl);
        form.add(new JLabel("Alamat:")); form.add(txtAlmt);

        JPanel btns = new JPanel();
        JButton btnAdd=new JButton("Tambah"), btnEdit=new JButton("Edit"), btnDel=new JButton("Hapus"), btnRef=new JButton("Refresh");
        btns.add(btnAdd); btns.add(btnEdit); btns.add(btnDel); btns.add(btnRef);
        add(form, BorderLayout.NORTH); add(btns, BorderLayout.SOUTH);

        tbl.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting() && tbl.getSelectedRow()>=0) {
                int r=tbl.getSelectedRow();
                txtId.setText(model.getValueAt(r,0).toString());
                txtNama.setText(model.getValueAt(r,1).toString());
                txtNik.setText(model.getValueAt(r,2).toString());
                txtHp.setText(model.getValueAt(r,3).toString());
                txtAlmt.setText(model.getValueAt(r,4).toString());
                txtTgl.setText(model.getValueAt(r,5).toString().equals("-")?"":model.getValueAt(r,5).toString());
            }
        });

        btnRef.addActionListener(e -> load());
        btnAdd.addActionListener(e -> save(false));
        btnEdit.addActionListener(e -> save(true));
        btnDel.addActionListener(e -> { if(!txtId.getText().isEmpty() && JOptionPane.showConfirmDialog(this,"Hapus?")==0) { if(ctrl.delete(Integer.parseInt(txtId.getText()))) { JOptionPane.showMessageDialog(this,"Berhasil"); load(); } }});
        load();
    }

    private void load() {
        model.setRowCount(0);
        for(Tamu t : ctrl.getAll()) {
            model.addRow(new Object[]{t.getId(), t.getNama(), t.getNoKtp(), t.getNoTelepon(), t.getAlamat(), DateHelper.format(t.getTanggalLahir())});
        }
    }

    private void save(boolean isEdit) {
        if(txtNama.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Nama wajib!"); return; }
        Tamu t = new Tamu();
        if(isEdit) t.setId(Integer.parseInt(txtId.getText()));
        t.setNama(txtNama.getText()); t.setNoKtp(txtNik.getText()); t.setNoTelepon(txtHp.getText());
        t.setAlamat(txtAlmt.getText()); t.setTanggalLahir(DateHelper.parse(txtTgl.getText()));
        if((isEdit?ctrl.edit(t):ctrl.add(t))) { JOptionPane.showMessageDialog(this,"Berhasil"); load(); }
    }
}