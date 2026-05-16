package View;

import Controller.KamarController;
import Model.Kamar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;

public class KamarView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtNomor, txtTipe, txtHarga, txtFasilitas;
    private JComboBox<String> cbStatus;
    private KamarController ctrl;
    
    public KamarView() {
        ctrl = new KamarController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        
        // Title
        JLabel lblTitle = new JLabel("DATA KAMAR");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.BLACK);
        
        // Table
        String[] cols = {"ID", "No Kamar", "Tipe", "Harga/Malam", "Status", "Fasilitas"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(25);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Kamar"));
        
        txtId = new JTextField();
        txtNomor = new JTextField();
        txtTipe = new JTextField();
        txtHarga = new JTextField();
        txtFasilitas = new JTextField();
        cbStatus = new JComboBox<>(new String[]{"Tersedia", "Terisi", "Maintenance"});
        
        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("No Kamar:"));
        formPanel.add(txtNomor);
        formPanel.add(new JLabel("Tipe:"));
        formPanel.add(txtTipe);
        formPanel.add(new JLabel("Harga/Malam:"));
        formPanel.add(txtHarga);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(cbStatus);
        formPanel.add(new JLabel("Fasilitas:"));
        formPanel.add(txtFasilitas);
        
        // Button Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Hapus");
        JButton btnRefresh = new JButton("Refresh");
        
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(lblTitle, BorderLayout.WEST);
        
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Events
        btnRefresh.addActionListener(e -> loadTable());
        btnAdd.addActionListener(e -> save());
        btnEdit.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtNomor.setText(model.getValueAt(row, 1).toString());
                txtTipe.setText(model.getValueAt(row, 2).toString());
                txtHarga.setText(model.getValueAt(row, 3).toString().replace("Rp", "").replace(".", "").trim());
                cbStatus.setSelectedItem(model.getValueAt(row, 4).toString());
                txtFasilitas.setText(model.getValueAt(row, 5).toString());
            }
        });
        
        loadTable();
    }
    
    private void loadTable() {
        model.setRowCount(0);
        for (Kamar k : ctrl.getAllKamar()) {
            model.addRow(new Object[]{
                k.getId(), k.getNomorKamar(), k.getTipe(),
                "Rp " + k.getHargaPerMalam(), k.getStatus(), k.getFasilitas()
            });
        }
    }
    
    private void save() {
        Kamar k = new Kamar();
        k.setNomorKamar(txtNomor.getText());
        k.setTipe(txtTipe.getText());
        k.setHargaPerMalam(new BigDecimal(txtHarga.getText()));
        k.setStatus(cbStatus.getSelectedItem().toString());
        k.setFasilitas(txtFasilitas.getText());
        
        if (ctrl.insertKamar(k)) {
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan");
            loadTable();
            clearForm();
        }
    }
    
    private void update() {
        Kamar k = new Kamar();
        k.setId(Integer.parseInt(txtId.getText()));
        k.setNomorKamar(txtNomor.getText());
        k.setTipe(txtTipe.getText());
        k.setHargaPerMalam(new BigDecimal(txtHarga.getText()));
        k.setStatus(cbStatus.getSelectedItem().toString());
        k.setFasilitas(txtFasilitas.getText());
        
        if (ctrl.updateKamar(k)) {
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
            loadTable();
            clearForm();
        }
    }
    
    private void delete() {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (ctrl.deleteKamar(Integer.parseInt(txtId.getText()))) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                loadTable();
                clearForm();
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
    }
}