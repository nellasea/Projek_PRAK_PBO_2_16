/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Controller.LoginController;
import Model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {
    private JTextField txtUser = new JTextField();
    private JPasswordField txtPass = new JPasswordField();
    private JButton btnLogin = new JButton("LOGIN");

    public LoginView() {
        setTitle("Hostel Management - Login");
        setSize(350, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));
        add(new JLabel("Username:")); add(txtUser);
        add(new JLabel("Password:")); add(txtPass);
        add(new JLabel()); add(btnLogin);

        btnLogin.addActionListener(e -> {
            User u = new LoginController().login(txtUser.getText(), new String(txtPass.getPassword()));
            if (u != null) {
                new MainFrame(u.getRole()).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username/Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}