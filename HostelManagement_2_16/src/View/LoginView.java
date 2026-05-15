/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import controller.LoginController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {
        setTitle("Login - Hostel Management");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JPanel panelInput = new JPanel(new GridLayout(2, 2, 10, 10));
        panelInput.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panelInput.add(txtUsername);
        panelInput.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panelInput.add(txtPassword);
        add(panelInput);

        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnLogin);

        btnLogin.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username & Password wajib diisi!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LoginController controller = new LoginController();
        if (controller.validateLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Berhasil! Selamat Datang.");
            SwingUtilities.invokeLater(() -> {
                new MainFrame().setVisible(true);
                dispose();
            });
        } else {
            JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }
}