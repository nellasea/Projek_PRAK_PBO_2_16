/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.LoginController;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblTitle;
    private LoginController loginController;

    public LoginView() {
        loginController = new LoginController();
        
        setTitle("Login - Hostel Management System");
        setSize(450, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel dengan BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Title
        lblTitle = new JLabel("HOSTEL MANAGEMENT SYSTEM", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(60, 52, 137));
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        
        // Input Panel
        JPanel panelInput = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        
        panelInput.add(lblUsername);
        panelInput.add(txtUsername);
        panelInput.add(lblPassword);
        panelInput.add(txtPassword);
        
        mainPanel.add(panelInput, BorderLayout.CENTER);
        
        // Button Panel
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(60, 52, 137));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(120, 40));
        
        panelButton.add(btnLogin);
        mainPanel.add(panelButton, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Action listener
        btnLogin.addActionListener(e -> handleLogin());
        
        // Enter key listener
        txtPassword.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        System.out.println("Attempting login for: " + username); // Debug

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username dan Password wajib diisi!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // FIXED: Better error handling
        try {
            if (loginController.validateLogin(username, password)) {
                JOptionPane.showMessageDialog(this, 
                    "Login Berhasil! Selamat Datang " + username, 
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Open MainFrame
                SwingUtilities.invokeLater(() -> {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                    dispose(); // Close login window
                });
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Username atau Password salah!\nSilakan coba lagi.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
                txtUsername.requestFocus();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error koneksi database: " + ex.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    // FIXED: Main method for testing
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}