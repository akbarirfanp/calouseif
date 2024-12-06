package controller;

import helper.ValidationHelper;
import models.User;

public class UserController {

    public static User login(String username, String password) {
        if (ValidationHelper.isNullOrEmpty(username) || ValidationHelper.isNullOrEmpty(password)) {
            System.out.println("Username dan password harus terisi!");
            return null;
        }
        return User.login(username, password);
    }
    
    public static User register(String username, String password, String phone_number, String address, String role) {
        if (ValidationHelper.isNullOrEmpty(username) || ValidationHelper.isNullOrEmpty(password) || 
            ValidationHelper.isNullOrEmpty(phone_number) || ValidationHelper.isNullOrEmpty(address)) {
            System.out.println("Username, password, phone number, and address harus terisi.");
            return null;
        }

        if (!ValidationHelper.isValidUsername(username)) {
            System.out.println("Username harus lebih dari 3 karakter");
            return null;
        }

        if (!ValidationHelper.isValidPassword(password)) {
            System.out.println("Password harus lebih dari 8 karakter dan mengandung setidaknya satu karakter special($, %, &, _, ()");
            return null;
        }

        if (!ValidationHelper.isValidPhoneNumber(phone_number)) {
            System.out.println("Nomor telepon harus dimulai dgn +62 dan panjangnya 13 karakter.");
            return null;
        }

        if (!ValidationHelper.isValidRole(role)) {
            System.out.println("Anda harus memilih salah satu role!");
            return null;
        }

        return User.register(username, password, phone_number, address, role);
    }
}
