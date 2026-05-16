/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Controller.ReservasiController;
import Model.Reservasi;
import Util.DateHelper;
import Util.NumberHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class ReservasiView extends JFrame {
    private JTable tbl = new JTable();
    private DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Tamu","Kamar","In","Out","Malam","Total","Status"},0);
    private JTextField txtIdTamu=new JTextField(), txtIdKamar=new JTextField(), txtIn=new JTextField(), txtOut=new JTextField(), txtId=new JTextField();
    private JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Booking","Check-In","Check-Out"});
    private ReservasiController ctrl = new ReservasiController();

    public ReservasiView() {
        setTitle("Manajemen Reservasi");
        setSize(900,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));
        tbl.setModel(model); add(new JScrollPane(tbl), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2,4,5,5));
        form.add(new JLabel("ID:")); form.add(txtId); txtId.setEditable(false);
        form.add(new JLabel("ID Tamu:")); form.add(txtIdTamu);
        form.add(new JLabel("ID Kamar:")); form.add(txtIdKamar);
        form.add(new JLabel("Check-In:")); form.add(txtIn);
        form.add(new JLabel("Check-Out:")); form.add(txtOut);
        form.add(new JLabel("Status:")); form.add(cbStatus);

        JPanel btns = new JPanel();
        JButton btnBook=new JButton("Booking"), btnStatus=new JButton("Update Status"), btnRef=new JButton("Refresh");
        btns.add(btnBook); btns.add(btnStatus); btns.add(btnRef);
        add(form, BorderLayout.NORTH); add(btns, BorderLayout.SOUTH);

        tbl.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting() && tbl.getSelectedRow()>=0) {
                int r=tbl.getSelectedRow();
                txtId.setText(model.getValueAt(r,0).toString());
                txtIdTamu.setText(model.getValueAt(r,1).toString());
                txtIdKamar.setText(model.getValueAt(r,3).toString());
                cbStatus.setSelectedItem(model.getValueAt(r,7).toString());
            }
        });

        btnRef.addActionListener(e -> load());
        btnBook.addActionListener(e -> {
            if(txtIdTamu.getText().isEmpty()||txtIdKamar.getText().isEmpty()||txtIn.getText().isEmpty()||txtOut.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Lengkapi!"); return; }
            Reservasi r = new Reservasi();
            r.setIdTamu(Integer.parseInt(txtIdTamu.getText()));
            r.setIdKamar(Integer.parseInt(txtIdKamar.getText()));
            r.setTanggalCheckIn(DateHelper.parse(txtIn.getText()));
            r.setTanggalCheckOut(DateHelper.parse(txtOut.getText()));
            r.setJumlahMalam(DateHelper.daysBetween(r.getTanggalCheckIn(), r.getTanggalCheckOut()));
            r.setTotalHarga(BigDecimal.valueOf(r.getJumlahMalam() * 150000)); // Harga dummy, sesuaikan logic
            r.setStatus("Booking");
            if(ctrl.add(r)) { JOptionPane.showMessageDialog(this,"Booking Berhasil"); load(); }
        });
        btnStatus.addActionListener(e -> {
            if(txtId.getText().isEmpty()) return;
            if(ctrl.updateStatus(Integer.parseInt(txtId.getText()), cbStatus.getSelectedItem().toString())) { JOptionPane.showMessageDialog(this,"Status Updated"); load(); }
        });
        load();
    }

    private void load() {
        model.setRowCount(0);
        for(Reservasi r : ctrl.getAll()) {
            model.addRow(new Object[]{r.getId(), r.getNamaTamu(), r.getNomorKamar(), DateHelper.format(r.getTanggalCheckIn()), DateHelper.format(r.getTanggalCheckOut()), r.getJumlahMalam(), NumberHelper.format(r.getTotalHarga().doubleValue()), r.getStatus()});
        }
    }
}