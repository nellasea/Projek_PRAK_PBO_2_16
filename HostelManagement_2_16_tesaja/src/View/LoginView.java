package View;

import Controller.LoginController;
import Model.User;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JLabel lblError;
    
    public LoginView() {
        setTitle("Login - Hotel Management");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        JLabel lblTitle = new JLabel("HOTEL MANAGEMENT SYSTEM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblSubtitle = new JLabel("Silakan login untuk melanjutkan");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubtitle.setForeground(Color.BLACK);
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        txtUser = new JTextField();
        txtUser.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUser.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        txtPass = new JPasswordField();
        txtPass.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(52, 152, 219));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        lblError = new JLabel(" ");
        lblError.setFont(new Font("Arial", Font.PLAIN, 12));
        lblError.setForeground(Color.RED);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        
        mainPanel.add(lblTitle, gbc);
        mainPanel.add(Box.createVerticalStrut(5), gbc);
        mainPanel.add(lblSubtitle, gbc);
        mainPanel.add(Box.createVerticalStrut(25), gbc);
        mainPanel.add(new JLabel("Username:"), gbc);
        mainPanel.add(txtUser, gbc);
        mainPanel.add(new JLabel("Password:"), gbc);
        mainPanel.add(txtPass, gbc);
        mainPanel.add(Box.createVerticalStrut(15), gbc);
        mainPanel.add(btnLogin, gbc);
        mainPanel.add(Box.createVerticalStrut(10), gbc);
        mainPanel.add(lblError, gbc);
        
        add(mainPanel);
        
        btnLogin.addActionListener(e -> login());
        txtPass.addActionListener(e -> login());
    }
    
    private void login() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Username dan password harus diisi!");
            return;
        }
        
        btnLogin.setText("LOADING...");
        btnLogin.setEnabled(false);
        
        User user = new LoginController().login(username, password);
        
        if (user != null) {
            new MainFrame(user).setVisible(true);
            dispose();
        } else {
            lblError.setText("Username atau password salah!");
            txtPass.setText("");
            btnLogin.setText("LOGIN");
            btnLogin.setEnabled(true);
        }
    }
}