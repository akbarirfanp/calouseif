package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;

public class Offer {
	
	private int offerId, itemId, userId,itemPrice, offeredPrice;
	private String itemName, itemCategory, itemSize;
	
	public Offer(int offerId, int itemId, int userId, int itemPrice, int offeredPrice, String itemName, String itemCategory, String itemSize) {
		super();
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.itemSize = itemSize;
		this.offerId = offerId;
		this.itemId = itemId;
		this.userId = userId;
		this.itemPrice = itemPrice;
		this.offeredPrice = offeredPrice;
	}
	
	public static boolean acceptOffer(int offerId) {
	    Database db = Database.getInstance();
	    
	    String queryUpdateOffer = 
	    "UPDATE offers SET item_offer_status = 'accepted' WHERE offer_id = ?";
	    
	    String queryUpdateItem = 
	    "UPDATE items SET item_status = 'sold',  " +
	    "item_price = (SELECT offered_price FROM offers WHERE offer_id = ?) " +
	    "WHERE item_id = (SELECT item_id FROM offers WHERE offer_id = ?)";
	    
	    String queryInsertTransaction = 
	    "INSERT INTO transactions (user_id, item_id) SELECT user_id, item_id FROM offers  " +
	    "WHERE offer_id = ? AND NOT EXISTS (SELECT 1 FROM transactions WHERE " +
	    "item_id = (SELECT item_id FROM offers WHERE offer_id = ?))";

	    try {
	        PreparedStatement updateOfferPs = db.prepareStatement(queryUpdateOffer);
	        updateOfferPs.setInt(1, offerId);
	        int updateOfferRs = updateOfferPs.executeUpdate();

	        if (updateOfferRs > 0) {
	            PreparedStatement insertTransactionPs = db.prepareStatement(queryInsertTransaction);
	            insertTransactionPs.setInt(1, offerId);
	            insertTransactionPs.setInt(2, offerId);
	            int insertTransactionRs = insertTransactionPs.executeUpdate();

	            if (insertTransactionRs > 0) {
	                PreparedStatement updateItemPs = db.prepareStatement(queryUpdateItem);
	                updateItemPs.setInt(1, offerId);
	                updateItemPs.setInt(2, offerId);
	                int updateItemRs = updateItemPs.executeUpdate();
	                return updateItemRs > 0;
	            }
	        }
	        return false;
	        
	     } catch (SQLException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	        return false;
	    }
	}
	
	// udh diambil di BuyerHome.java
	public static boolean makeOffer(int itemId, int userId, int offeredPrice) {
	    Database db = Database.getInstance();
	    String queryIns = "INSERT INTO offers (item_id, user_id, offered_price, item_offer_status) VALUES (?, ?, ?, 'pending')";
	    String queryUpd = "UPDATE items SET item_status = 'on_offer_process' WHERE item_id = ?";
	    PreparedStatement ps1 = db.prepareStatement(queryIns);
	    PreparedStatement ps2 = db.prepareStatement(queryUpd);
	    try {	
	        ps1.setInt(1, itemId);
	        ps1.setInt(2, userId);
	        ps1.setInt(3, offeredPrice);
	        ps2.setInt(1, itemId);
	        
	        ps1.executeUpdate();
	        ps2.executeUpdate();
	        return true;
	        
	    } catch (SQLException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public static ArrayList<Offer> getOfferedItem(){
		Database db = Database.getInstance();
		ArrayList<Offer> offerList = new ArrayList<>();
		String query= "SELECT o.offer_id, o.item_id, o.user_id, o.offered_price, i.item_name, i.item_price, i.item_size, i.item_category "+
			          "FROM offers o "+ 
			          "JOIN items i ON o.item_id = i.item_id "+
			          "WHERE o.item_offer_status = 'pending' ";
		PreparedStatement ps = db.prepareStatement(query);
		
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int offerId = rs.getInt("offer_id");
				int userId = rs.getInt("user_id");
				int itemId = rs.getInt("item_id");
				int itemPrice = rs.getInt("item_price");
				int offeredPrice = rs.getInt("offered_price");
				String itemName = rs.getString("item_name");
				String itemSize = rs.getString("item_size");
				String itemCategory = rs.getString("item_category");
				Offer offers = new Offer(offerId, itemId, userId, itemPrice, offeredPrice, itemName, itemCategory, itemSize);
				offerList.add(offers);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return offerList;
	}

	
	public static boolean declineOffer(int offerId) {
		Database db = Database.getInstance();
		String query = "UPDATE offers SET item_offer_status = 'rejected' WHERE offer_id = ?";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setInt(1, offerId);
			int r = ps.executeUpdate();
			return r > 0;
		} catch (SQLException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
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
	
	public int getOfferId() {
		return offerId;
	}
	
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	
	public int getItemPrice() {
		return itemPrice;
	}
	
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
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
	
	public int getOfferedPrice() {
		return offeredPrice;
	}
	
	public void setOfferedPrice(int offeredPrice) {
		this.offeredPrice = offeredPrice;
	}
	
}
