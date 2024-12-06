package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

public class User {
    private int user_id;
    private String username;
    private String password;
    private String phone_number;
    private String address;            
    private String role;

    // Constructor lengkap
    public User(int user_id, String username, String password, String phone_number, String address, String role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.address = address;
        this.role = role;
    }

    // Constructor minimal (untuk login)
    public User(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    // Method untuk registrasi user
    public static User register(String username, String password, String phone_number, String address, String role) {
        Database db = Database.getInstance();
        String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?)";
        User user = null;

        try (PreparedStatement ps = db.prepareStatement(query)) {
            // Set parameter for query
            ps.setString(1, username);
            ps.setString(2, password); // Hash the password before saving (optional)
            ps.setString(3, phone_number);
            ps.setString(4, address);
            ps.setString(5, role);

            // Execute the query
            int rowsAffected = ps.executeUpdate();

            // If registration is successful, retrieve the user_id manually
            if (rowsAffected > 0) {
                // You can fetch the user_id by querying the database again
                String selectQuery = "SELECT user_id FROM users WHERE username = ?";
                try (PreparedStatement selectPs = db.prepareStatement(selectQuery)) {
                    selectPs.setString(1, username);
                    try (ResultSet rs = selectPs.executeQuery()) {
                        if (rs.next()) {
                            int user_id = rs.getInt("user_id");
                            user = new User(user_id, username, password, phone_number, address, role);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    // Method untuk login user
    public static User login(String username, String password) {
        Database db = Database.getInstance();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        User user = null;

        try (PreparedStatement ps = db.prepareStatement(query)) {
            ps.setString(1, username); 
            ps.setString(2, password);

            // Eksekusi query
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int user_id = rs.getInt("user_id");
                    String dbUsername = rs.getString("username");
                    String dbPassword = rs.getString("password");
                    user = new User(user_id, dbUsername, dbPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user; 
    }

    // Getter dan Setter
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
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
