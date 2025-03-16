package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import database.Database;

public class Wishlist {
	private String itemName, itemStatus;
    private int wishlistId, itemId, userId;
    
    
    public Wishlist(int wishlistId, int itemId, int userId, String itemName, String itemStatus) {
        this.wishlistId = wishlistId;
        this.itemId = itemId;
        this.userId = userId;
        this.itemName = itemName;
        this.itemStatus = itemStatus;
    }
     
    public static boolean addToWishlist(int userId, int itemId) {
        Database db = Database.getInstance();
        String query = "INSERT INTO wishlist (user_id, item_id) VALUES (?, ?)";
        PreparedStatement ps = db.prepareStatement(query);
        try {
            ps.setInt(1, userId);
            ps.setInt(2, itemId);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
        	// TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean deleteWishlist(int wishlistId) {
    	Database db = Database.getInstance();
    	String query = "DELETE FROM wishlist WHERE wishlist_id = ?";
    	PreparedStatement ps = db.prepareStatement(query);
    	try {
			ps.setInt(1, wishlistId);
			int result = ps.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    public static ArrayList<Wishlist> getAllWishlist(int userId) {
        Database db = Database.getInstance();
        ArrayList<Wishlist> wishlistItems = new ArrayList<>();
        String query = "SELECT w.wishlist_id, w.item_id, w.user_id, i.item_name, i.item_status "+
                       "FROM wishlist w "+
                       "JOIN items i ON w.item_id = i.item_id "+
                       "WHERE w.user_id = ? AND i.item_status = 'available' ";
        
        try (PreparedStatement ps = db.prepareStatement(query)) {
            ps.setInt(1, userId); 
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int wishlistId = rs.getInt("wishlist_id");
                int itemId = rs.getInt("item_id");
                int quserId = rs.getInt("user_id");
                String itemName = rs.getString("item_name");
                String itemStatus = rs.getString("item_status");

                Wishlist wishlist = new Wishlist(wishlistId, itemId, quserId, itemName, itemStatus);
                wishlistItems.add(wishlist);
            }
        } catch (SQLException e) {
        	// TODO Auto-generated catch block
            e.printStackTrace(); 
        }

        return wishlistItems;
    }


    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getItemId() {
        return itemId;
    }
    
    public String getItemName() {
        return itemName;
    }

    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
    
    
}

// Author Irfan - Vincent