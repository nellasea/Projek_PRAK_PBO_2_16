/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
//CRUD
import controller.KamarController;
import model.Kamar;
import util.NumberHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class KamarView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtNomor, txtTipe, txtHarga, txtFasilitas, txtId;
    private JComboBox<String> cbStatus;
    private KamarController ctrl;

    public KamarView() {
        ctrl = new KamarController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table
        String[] cols = {"ID", "No Kamar", "Tipe", "Harga/Malam", "Status", "Fasilitas"};
        model = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form Input
        JPanel form = new JPanel(new GridLayout(3, 4, 8, 8));
        form.add(new JLabel("ID (Edit/Hapus):")); txtId = new JTextField(); form.add(txtId);
        form.add(new JLabel("Nomor Kamar:")); txtNomor = new JTextField(); form.add(txtNomor);
        form.add(new JLabel("Tipe:")); txtTipe = new JTextField(); form.add(txtTipe);
        form.add(new JLabel("Harga:")); txtHarga = new JTextField(); form.add(txtHarga);
        form.add(new JLabel("Status:")); cbStatus = new JComboBox<>(new String[]{"Tersedia", "Terisi", "Maintenance"}); form.add(cbStatus);
        form.add(new JLabel("Fasilitas:")); txtFasilitas = new JTextField(); form.add(txtFasilitas);

        // Buttons
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnDel = new JButton("Hapus");
        JButton btnRefresh = new JButton("Refresh");
        btns.add(btnAdd); btns.add(btnEdit); btns.add(btnDel); btns.add(btnRefresh);

        add(form, BorderLayout.NORTH);
        add(btns, BorderLayout.SOUTH);

        // Events
        btnRefresh.addActionListener(e -> loadTable());
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
                    txtHarga.setText(model.getValueAt(row, 3).toString().replace("Rp", "").replace(".", "").trim());
                    cbStatus.setSelectedItem(model.getValueAt(row, 4).toString());
                    txtFasilitas.setText(model.getValueAt(row, 5).toString());
                }
            }
        });

        loadTable();
    }

    private void loadTable() {
        model.setRowCount(0);
        for(Kamar k : ctrl.getAllKamar()) {
            model.addRow(new Object[]{k.getId(), k.getNomorKamar(), k.getTipe(), 
                NumberHelper.formatRupiah(k.getHargaPerMalam().doubleValue()), k.getStatus(), k.getFasilitas()});
        }
    }

    private void save(boolean isEdit) {
        if(txtNomor.getText().isEmpty() || txtHarga.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lengkapi data!"); return;
        }
        Kamar k = new Kamar();
        if(isEdit) k.setId(Integer.parseInt(txtId.getText()));
        k.setNomorKamar(txtNomor.getText());
        k.setTipe(txtTipe.getText());
        k.setHargaPerMalam(new BigDecimal(txtHarga.getText()));
        k.setStatus(cbStatus.getSelectedItem().toString());
        k.setFasilitas(txtFasilitas.getText());

        boolean success = isEdit ? ctrl.updateKamar(k) : ctrl.insertKamar(k);
        if(success) { JOptionPane.showMessageDialog(this, "Berhasil!"); loadTable(); clearForm(); }
    }

    private void delete() {
        if(txtId.getText().isEmpty()) return;
        int opt = JOptionPane.showConfirmDialog(this, "Hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(opt == JOptionPane.YES_OPTION && ctrl.deleteKamar(Integer.parseInt(txtId.getText()))) {
            JOptionPane.showMessageDialog(this, "Terhapus!"); loadTable(); clearForm();
        }
    }

    private void clearForm() {
        txtId.setText(""); txtNomor.setText(""); txtTipe.setText(""); 
        txtHarga.setText(""); txtFasilitas.setText(""); cbStatus.setSelectedIndex(0);
    }
}
