/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import View.LoginView;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main Application - Hostel Management System
 * Entry point aplikasi
 */
public class Main {
    
    public static void main(String[] args) {
        // Set tampilan modern (Look and Feel)
        try {
            // Gunakan sistem look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Atau gunakan Nimbus (cross-platform)
            // for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            //     if ("Nimbus".equals(info.getName())) {
            //         UIManager.setLookAndFeel(info.getClassName());
            //         break;
            //     }
            // }
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Look and Feel error: " + e.getMessage());
        }
        
        // Jalankan aplikasi di Event Dispatch Thread (EDT)
        // Penting untuk Swing agar thread-safe
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Tampilkan form login
                new LoginView().setVisible(true);
            }
        });
    }
}