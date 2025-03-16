package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import database.Database;

public class Item {
	private int itemId;
	private Integer itemPrice; // harus pakai int primitif agar bisa validasi item.Price == null/ ""
	private String itemName;
	private String itemSize;
	private String itemCategory;
	private String itemStatus;

	public Item(int itemId, String itemName, String itemSize, Integer itemPrice, String itemCategory, String itemStatus) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory; 
		this.itemStatus = itemStatus; 
		
	}
	
	// delete item yg avail di SellerHome
	public static boolean deleteItem(int itemId) {
		Database db = Database.getInstance();
	    String query = "DELETE FROM items WHERE item_id = ? ";
	    try {
	        PreparedStatement ps = db.prepareStatement(query);
	        ps.setInt(1, itemId);
	        return ps.executeUpdate() > 0; 
	    } catch (SQLException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	        return false;
	    }
	}
	
	// fitur upload item
	public static boolean upload(String itemName, String itemSize, Integer itemPrice, String itemCategory) {
	    Database db = Database.getInstance();
	    String query = "INSERT INTO items (item_id, item_name, item_category, item_size, item_price, item_status) VALUES (0, ?, ?, ?, ?, 'pending')";
	    PreparedStatement ps = db.prepareStatement(query);
	    try {
	        ps.setString(1, itemName);
	        ps.setString(2, itemCategory);
	        ps.setString(3, itemSize);
	        
	        if (itemPrice == null) {
	            ps.setNull(4, java.sql.Types.INTEGER);
	        } else {
	            ps.setInt(4, itemPrice);
	        }

	        ps.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return false;
	}

	// approving item utk admin
	public static boolean approveItem(int itemId) {
	    Database db = Database.getInstance();
	    String query = "UPDATE items SET item_status = 'available' WHERE item_id = ?";
	    try {
	        PreparedStatement ps = db.prepareStatement(query);
	        ps.setInt(1, itemId); 
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	        return false;
	    }
	}

	// decline item utk admin
	public static boolean declineItem(int itemId) {
	    Database db = Database.getInstance();
	    String query = "UPDATE items SET item_status = 'declined' WHERE item_id = ?";
	    try {
	        PreparedStatement ps = db.prepareStatement(query);
	        ps.setInt(1, itemId);
	        return ps.executeUpdate() > 0; 
	    } catch (SQLException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static ArrayList<Item> showApprovedItems() {
        Database db = Database.getInstance();
        ArrayList<Item> approvedItems = new ArrayList<>();
        String query = "SELECT * FROM items WHERE item_status = 'available'";
        PreparedStatement ps = db.prepareStatement(query);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                String itemSize = rs.getString("item_size");
                int itemPrice = rs.getInt("item_price");
                String itemCategory = rs.getString("item_category");
                String itemStatus = rs.getString("item_status");

                approvedItems.add(new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus));
            }
        } catch (SQLException e) {
        	// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return approvedItems;
    }
	
	public static ArrayList<Item> showSellerItems() {
        Database db = Database.getInstance();
        ArrayList<Item> approvedItems = new ArrayList<>();
        String query = "SELECT * FROM items WHERE item_status IN ('available', 'pending', 'declined')";
        PreparedStatement ps = db.prepareStatement(query);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                String itemSize = rs.getString("item_size");
                int itemPrice = rs.getInt("item_price");
                String itemCategory = rs.getString("item_category");
                String itemStatus = rs.getString("item_status");

                approvedItems.add(new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus));
            }
        } catch (SQLException e) {
        	// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return approvedItems;
    }
	
	// Admin ni
	public static ArrayList<Item> getPendingItems() {
        Database db = Database.getInstance();
        ArrayList<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items WHERE item_status = 'pending'";

        try (PreparedStatement ps = db.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                String itemSize = rs.getString("item_size");
                int itemPrice = rs.getInt("item_price");
                String itemCategory = rs.getString("item_category");
                String itemStatus = rs.getString("item_status");

                items.add(new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus));
            }
        } catch (SQLException e) {
        	// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return items;
    }
	
	
	// utk slection offer harus optional
	public static Optional<Item> getItemById(int itemId) {
        Database db = Database.getInstance();
        String query = "SELECT * FROM items WHERE item_id = ?";
        PreparedStatement ps = db.prepareStatement(query);

        try {
            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("item_id");
                String name = rs.getString("item_name");
                String size = rs.getString("item_size");
                int price = rs.getInt("item_price");
                String category = rs.getString("item_category");
                String status = rs.getString("item_status");

                Item item = new Item(id, name, size, price, category, status);
                return Optional.of(item);
            }
        } catch (SQLException e) {
        	// TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Optional.empty(); 
    }
	
	public static boolean updateItem(int itemId, String itemName, String itemSize, int itemPrice, String itemCategory) {
	    Database db = Database.getInstance();
	    String query = "UPDATE items SET item_name = ?, item_category = ?, item_size = ?, item_price = ? WHERE item_id = ?";
	    PreparedStatement ps = db.prepareStatement(query);
	    try {
	        ps.setString(1, itemName);
	        ps.setString(2, itemCategory);
	        ps.setString(3, itemSize);
	        ps.setInt(4, itemPrice);
	        ps.setInt(5, itemId); 
	        return ps.executeUpdate() > 0; 
	    } catch (SQLException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public Integer getItemPrice() {
		return itemPrice;
	}
	
	public void setItemPrice(Integer itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public String getItemCategory() {
		return itemCategory;
	}
	
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	
	public String getItemStatus() {
		return itemStatus;
	}
	
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public int getItemId() {
		return itemId;
	}
	
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemSize() {
		return itemSize;
	}
	
	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

}
	

