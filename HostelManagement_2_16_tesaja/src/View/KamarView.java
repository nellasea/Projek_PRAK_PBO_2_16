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

public class KamarView extends JFrame {
    private JTable tbl = new JTable();
    private DefaultTableModel model = new DefaultTableModel(new String[]{"ID","No","Tipe","Harga","Status","Fasilitas"},0);
    private JTextField txtNo=new JTextField(), txtTipe=new JTextField(), txtHarga=new JTextField(), txtFas=new JTextField(), txtId=new JTextField();
    private JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Tersedia","Terisi","Maintenance"});
    private KamarController ctrl = new KamarController();

    public KamarView() {
        setTitle("Manajemen Kamar");
        setSize(800,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));
        tbl.setModel(model); add(new JScrollPane(tbl), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(3,3,5,5));
        form.add(new JLabel("ID:")); form.add(txtId); txtId.setEditable(false);
        form.add(new JLabel("No Kamar:")); form.add(txtNo);
        form.add(new JLabel("Tipe:")); form.add(txtTipe);
        form.add(new JLabel("Harga:")); form.add(txtHarga);
        form.add(new JLabel("Status:")); form.add(cbStatus);
        form.add(new JLabel("Fasilitas:")); form.add(txtFas);

        JPanel btns = new JPanel();
        JButton btnAdd=new JButton("Tambah"), btnEdit=new JButton("Edit"), btnDel=new JButton("Hapus"), btnRef=new JButton("Refresh");
        btns.add(btnAdd); btns.add(btnEdit); btns.add(btnDel); btns.add(btnRef);
        add(form, BorderLayout.NORTH); add(btns, BorderLayout.SOUTH);

        tbl.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting() && tbl.getSelectedRow()>=0) {
                int r=tbl.getSelectedRow();
                txtId.setText(model.getValueAt(r,0).toString());
                txtNo.setText(model.getValueAt(r,1).toString());
                txtTipe.setText(model.getValueAt(r,2).toString());
                txtHarga.setText(model.getValueAt(r,3).toString().replace("Rp","").replace(".","").trim());
                cbStatus.setSelectedItem(model.getValueAt(r,4).toString());
                txtFas.setText(model.getValueAt(r,5).toString());
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
        for(Kamar k : ctrl.getAll()) {
            model.addRow(new Object[]{k.getId(), k.getNomorKamar(), k.getTipe(), NumberHelper.format(k.getHargaPerMalam().doubleValue()), k.getStatus(), k.getFasilitas()});
        }
    }

    private void save(boolean isEdit) {
        if(txtNo.getText().isEmpty() || txtHarga.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Lengkapi data!"); return; }
        Kamar k = new Kamar();
        if(isEdit) k.setId(Integer.parseInt(txtId.getText()));
        k.setNomorKamar(txtNo.getText()); k.setTipe(txtTipe.getText());
        k.setHargaPerMalam(new BigDecimal(txtHarga.getText()));
        k.setStatus(cbStatus.getSelectedItem().toString()); k.setFasilitas(txtFas.getText());
        if((isEdit?ctrl.edit(k):ctrl.add(k))) { JOptionPane.showMessageDialog(this,"Berhasil"); load(); }
    }
}