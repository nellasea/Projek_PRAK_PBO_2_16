/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import DAO.UserDAO;
import Model.User;
public class LoginController {
    public User login(String u, String p) { return new UserDAO().login(u, p); }
}