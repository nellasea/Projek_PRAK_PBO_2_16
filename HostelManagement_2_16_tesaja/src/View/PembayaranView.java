/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Controller.PembayaranController;
import Model.Pembayaran;
import Util.NumberHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class PembayaranView extends JFrame {
    private JTable tbl = new JTable();
    private DefaultTableModel model = new DefaultTableModel(new String[]{"ID","ID Res","Jumlah","Metode","Status"},0);
    private JTextField txtIdRes=new JTextField(), txtJml=new JTextField();
    private JComboBox<String> cbMetode = new JComboBox<>(new String[]{"Cash","Transfer","E-Wallet"});
    private PembayaranController ctrl = new PembayaranController();

    public PembayaranView() {
        setTitle("Pembayaran");
        setSize(600,400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));
        tbl.setModel(model); add(new JScrollPane(tbl), BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout());
        form.add(new JLabel("ID Reservasi:")); form.add(txtIdRes);
        form.add(new JLabel("Jumlah:")); form.add(txtJml);
        form.add(new JLabel("Metode:")); form.add(cbMetode);
        JButton btnPay = new JButton("Bayar");
        form.add(btnPay);
        add(form, BorderLayout.NORTH);

        btnPay.addActionListener(e -> {
            if(txtIdRes.getText().isEmpty()||txtJml.getText().isEmpty()) { JOptionPane.showMessageDialog(this,"Lengkapi!"); return; }
            Pembayaran p = new Pembayaran();
            p.setIdReservasi(Integer.parseInt(txtIdRes.getText()));
            p.setJumlahBayar(new BigDecimal(txtJml.getText()));
            p.setMetodePembayaran(cbMetode.getSelectedItem().toString());
            p.setStatus("Lunas");
            if(ctrl.add(p)) { JOptionPane.showMessageDialog(this,"Pembayaran Berhasil"); load(); }
        });
        load();
    }

    private void load() {
        model.setRowCount(0);
        // Untuk simplicity, ambil dari semua pembayaran (bisa difilter by ID Res jika perlu)
        for(int i=1; i<=10; i++) { // Dummy loop karena method getByReservasi butuh ID spesifik
             // Ganti dengan logic real sesuai kebutuhan UI Anda
        }
    }
}