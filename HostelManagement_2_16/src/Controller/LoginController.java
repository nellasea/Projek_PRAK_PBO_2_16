/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;

public class LoginController {
    
    private UserDAO dao = new UserDAO();
    
    public boolean validateLogin(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty()) {
            return false;
        }
        User user = dao.validateUser(username, password);
        return user != null;
    }
    
    // FIXED: Added method to get user role after login
    public String getUserRole(String username, String password) {
        User user = dao.validateUser(username, password);
        return user != null ? user.getRole() : null;
    }
    
    // FIXED: Added method to get user data
    public User getUser(String username, String password) {
        return dao.validateUser(username, password);
    }
}