package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

public class User {
    private int userId;
    private String username, password, phoneNumber, address, role;

    // ini buat register
    public User(int userId, String username, String password, String phoneNumber, String address, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }
    
    // note: cek kode sesi 10
    public static User register(String username, String password, String phoneNumber, String address, String role) {
        Database db = Database.getInstance();
        String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?)";
        User user = null;

        try (PreparedStatement ps = db.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password); 
            ps.setString(4, address);
            ps.setString(5, role);
            ps.setString(3, phoneNumber);
           
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                String selectQuery = "SELECT user_id FROM users WHERE username = ?";
                try (PreparedStatement selectPS = db.prepareStatement(selectQuery)) {
                    selectPS.setString(1, username);
                    try (ResultSet rs = selectPS.executeQuery()) {
                        if (rs.next()) {
                            int userId = rs.getInt("user_id");
                            user = new User(userId, username, password, phoneNumber, address, role);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    // ini buat login
    public User(int userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static User login(String username, String password) {
        Database db = Database.getInstance();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        User user = null;

        try (PreparedStatement ps = db.prepareStatement(query)) {
            ps.setString(1, username); 
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               int userId = rs.getInt("user_id");
               String qrole = rs.getString("role");
               String qusername = rs.getString("username");
               String qpassword = rs.getString("password");
               user = new User(userId, qusername, qpassword, qrole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user; 
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	
}
