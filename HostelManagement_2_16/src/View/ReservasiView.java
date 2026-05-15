/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.ReservasiController;  // FIXED: uppercase Controller
import Model.Reservasi;
import Model.Kamar;
import DAO.KamarDAO;
import Util.DateHelper;
import Util.NumberHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ReservasiView extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtIdTamu, txtIdKamar, txtTotal, txtIn, txtOut;
    private JComboBox<String> cbStatus;
    private ReservasiController ctrl;
    private KamarDAO kamarDao;

    public ReservasiView() {
        ctrl = new ReservasiController();
        kamarDao = new KamarDAO();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "ID Tamu", "Nama Tamu", "ID Kamar", "No Kamar", "Check-In", "Check-Out", "Malam", "Total", "Status"};
        model = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form Input
        JPanel form = new JPanel(new GridLayout(4, 4, 8, 8));
        form.add(new JLabel("ID Res:")); txtId = new JTextField(); txtId.setEditable(false); form.add(txtId);
        form.add(new JLabel("ID Tamu:")); txtIdTamu = new JTextField(); form.add(txtIdTamu);
        form.add(new JLabel("ID Kamar:")); txtIdKamar = new JTextField(); form.add(txtIdKamar);
        form.add(new JLabel("Check-In (yyyy-MM-dd):")); txtIn = new JTextField(); form.add(txtIn);
        form.add(new JLabel("Check-Out (yyyy-MM-dd):")); txtOut = new JTextField(); form.add(txtOut);
        form.add(new JLabel("Status:")); cbStatus = new JComboBox<>(new String[]{"Booking", "Check-In", "Check-Out", "Cancelled"}); form.add(cbStatus);
        form.add(new JLabel("Total Harga:")); txtTotal = new JTextField(); txtTotal.setEditable(false); form.add(txtTotal);
        
        // Auto calculate total when dates change
        txtOut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                hitungTotal();
            }
        });
        
        txtIdKamar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                hitungTotal();
            }
        });

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Booking");
        JButton btnStatus = new JButton("Update Status");
        JButton btnRefresh = new JButton("Refresh");
        btns.add(btnAdd); btns.add(btnStatus); btns.add(btnRefresh);

        add(form, BorderLayout.NORTH);
        add(btns, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadTable());
        btnAdd.addActionListener(e -> save());
        btnStatus.addActionListener(e -> updateStatus());
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if(row >= 0) {
                    txtId.setText(model.getValueAt(row, 0).toString());
                    txtIdTamu.setText(model.getValueAt(row, 1).toString());
                    txtIdKamar.setText(model.getValueAt(row, 3).toString());
                    cbStatus.setSelectedItem(model.getValueAt(row, 9).toString());
                }
            }
        });

        loadTable();
    }
    
    private void hitungTotal() {
        try {
            if(!txtIdKamar.getText().isEmpty() && !txtIn.getText().isEmpty() && !txtOut.getText().isEmpty()) {
                Date in = DateHelper.parseToDate(txtIn.getText());
                Date out = DateHelper.parseToDate(txtOut.getText());
                if(in != null && out != null && out.after(in)) {
                    int days = DateHelper.calculateDaysBetween(in, out);
                    Kamar kamar = kamarDao.getKamarById(Integer.parseInt(txtIdKamar.getText()));
                    if(kamar != null) {
                        BigDecimal total = kamar.getHargaPerMalam().multiply(new BigDecimal(days));
                        txtTotal.setText(total.toString());
                    }
                }
            }
        } catch(Exception ex) {
            // ignore
        }
    }
    
    private void hitungTotalFromRow(int idKamar, Date checkIn, Date checkOut) {
        if(checkIn != null && checkOut != null && checkOut.after(checkIn)) {
            int days = DateHelper.calculateDaysBetween(checkIn, checkOut);
            Kamar kamar = kamarDao.getKamarById(idKamar);
            if(kamar != null) {
                BigDecimal total = kamar.getHargaPerMalam().multiply(new BigDecimal(days));
                txtTotal.setText(total.toString());
            }
        }
    }

    private void save() {
        if(txtIdTamu.getText().isEmpty() || txtIdKamar.getText().isEmpty() || txtIn.getText().isEmpty() || txtOut.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lengkapi data reservasi!");
            return;
        }
        
        Reservasi r = new Reservasi();
        r.setIdTamu(Integer.parseInt(txtIdTamu.getText()));
        r.setIdKamar(Integer.parseInt(txtIdKamar.getText()));
        r.setTanggalCheckIn(DateHelper.parseToDate(txtIn.getText()));
        r.setTanggalCheckOut(DateHelper.parseToDate(txtOut.getText()));
        r.setJumlahMalam(DateHelper.calculateDaysBetween(r.getTanggalCheckIn(), r.getTanggalCheckOut()));
        r.setTotalHarga(new BigDecimal(txtTotal.getText()));
        r.setStatus(cbStatus.getSelectedItem().toString());
        
        if(ctrl.addReservasi(r)) { 
            JOptionPane.showMessageDialog(this, "Booking Berhasil!"); 
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan reservasi!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStatus() {
        if(txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih reservasi terlebih dahulu!");
            return;
        }
        if(ctrl.updateStatus(Integer.parseInt(txtId.getText()), cbStatus.getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(this, "Status Updated!"); 
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal update status!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        txtId.setText("");
        txtIdTamu.setText("");
        txtIdKamar.setText("");
        txtIn.setText("");
        txtOut.setText("");
        txtTotal.setText("");
        cbStatus.setSelectedIndex(0);
    }

    private void loadTable() {
        model.setRowCount(0);
        for(Reservasi r : ctrl.getAllReservasi()) {
            model.addRow(new Object[]{
                r.getId(), 
                r.getIdTamu(), 
                r.getNamaTamu(), 
                r.getIdKamar(), 
                r.getNomorKamar(),
                DateHelper.formatToIndonesian(r.getTanggalCheckIn()), 
                DateHelper.formatToIndonesian(r.getTanggalCheckOut()),
                r.getJumlahMalam(), 
                NumberHelper.formatRupiah(r.getTotalHarga().doubleValue()), 
                r.getStatus()
            });
        }
    }
}