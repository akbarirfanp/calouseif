package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import database.Database;

public class Transaction {
	private String itemName, itemCategory, itemSize;
	private int transactionId, itemId, userId, itemPrice;

	public Transaction(int transactionId, int itemId, int userId, String itemName, String itemCategory, String itemSize, int itemPrice) {
		super();
		this.transactionId = transactionId;
		this.itemId = itemId;
		this.userId = userId;
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
	}
	
	public static boolean purchaseItem(int itemId, int userId) {
	    Database db = Database.getInstance();
	    String insertTransactionQuery = "INSERT INTO transactions (user_id, item_id) VALUES (?, ?)";
	    String updateItemQuery = "UPDATE items SET item_status = 'sold' WHERE item_id = ?";

	    try {
	        PreparedStatement insertPs = db.prepareStatement(insertTransactionQuery);
	        insertPs.setInt(1, userId);
	        insertPs.setInt(2, itemId);
	        insertPs.executeUpdate();

	        PreparedStatement updatePs = db.prepareStatement(updateItemQuery);
	        updatePs.setInt(1, itemId);
	        updatePs.executeUpdate();
	        
	        return true;
	        
	    } catch (SQLException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public static ArrayList<Transaction> getAllTransaction(int userId) {
        Database db = Database.getInstance();
        List<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT t.transaction_id, t.item_id, t.user_id, i.item_name, i.item_price, i.item_size, i.item_category " +
                       "FROM transactions t " +
                       "JOIN items i ON t.item_id = i.item_id " +
                       "WHERE t.user_id = ? AND i.item_status = 'sold' ";
        
        try (PreparedStatement ps = db.prepareStatement(query)) {
            ps.setInt(1, userId); 
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                int itemId = rs.getInt("item_id");
                int quserId = rs.getInt("user_id");
                int itemPrice = rs.getInt("item_price");
                String itemName = rs.getString("item_name");
                String itemCategory = rs.getString("item_category");
                String itemSize = rs.getString("item_size");
                // nyasr??
                Transaction transaction = new Transaction(transactionId, itemId, quserId, itemName, itemCategory, itemSize, itemPrice);
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
        	// TODO Auto-generated catch block
            e.printStackTrace(); 
        }
        
        return (ArrayList<Transaction>) transactionList;
    }
	

	public int getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
	public int getItemId() {
		return itemId;
	}
	
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemCategory() {
		return itemCategory;
	}
	
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	public String getItemSize() {
		return itemSize;
	}
	
	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}
	
	public int getItemPrice() {
		return itemPrice;
	}
	
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	
}
