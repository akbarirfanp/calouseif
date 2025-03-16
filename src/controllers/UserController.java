package controllers;

import models.User;

public class UserController {

    public static User getRegister(String username, String password, String phone_number, String address, String role) {
        
        if (!(role.equals("seller") || role.equals("buyer") || role.equals("admin"))) {
            System.out.println("Anda harus memilih salah satu role!");
            return null;
        }
        
        if (username.length() <= 3) {
            System.out.println("Username harus lebih dari 3 karakter");
            return null;
        }
        
        if (!phone_number.startsWith("+62") || phone_number.length() != 14) {
            System.out.println("Nomor telepon harus dimulai dari +62 dan panjangnya 14 karakter termasuk +62.");
            return null;
        }

        if (password.length() <= 8 || !password.matches(".*[!@#$%^&*(),.?\\\":{}|<>].*")) {
            System.out.println("Password harus lebih dari 8 karakter dan harus ada satu karakter special($, %, &, _, ()");
            return null;
        }
        
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || phone_number == null || phone_number.isEmpty() || address == null || address.isEmpty()) {
            System.out.println("Semua field harus terisi");
            return null;
        }
        
        return User.register(username, password, phone_number, address, role);
    }
    
    public static User getLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Semua field harus terisi!!");
            return null;
        }
        
        User user = User.login(username, password);
        if (user == null) {
            System.out.println("Tidak ditemukan datanya pada database!");
        }
        
        return User.login(username, password);
    }
    
}
