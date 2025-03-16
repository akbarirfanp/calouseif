package controllers;

import java.util.ArrayList;

import models.Item;


public class ItemController {

	
	// untuk Fitur Seller
	public static boolean uploadItem(String itemName, String itemSize, Integer itemPrice, String itemCategory) {
		if(itemName.isEmpty() && itemCategory.isEmpty() && itemSize.isEmpty() && itemPrice == null) {
			System.out.println("Semua field harus terisi!");
			return false;
		}
		
	    if (itemName.isEmpty() || itemName.length() < 3) {
	        System.out.println("Nama item tidak boleh kosong dan harus minimal 3 karakter");
	        return false;
	    }
	    
	    if (itemCategory.isEmpty() || itemCategory.length() < 3) {
	        System.out.println("Kategori item tidak boleh kosong dan harus memiliki minimal 3 karakter");
	        return false;
	    }
	    
	    if (itemPrice <= 0) {
	        System.out.println("Harga item tidak boleh kosong dan nol");
	        return false;
	    }
	    
	    if (itemSize.isEmpty()) {
	        System.out.println("Size item tidak boleh kosong");
	        return false;
	    }
	    
	    return Item.upload(itemName, itemSize, itemPrice, itemCategory);
	}
	
	public static boolean updateItem(Item selectedItem, String itemName, String itemSize, int itemPrice, String itemCategory) {
		if (itemName.isEmpty() || itemName.length() < 3) {
	        System.out.println("Nama item tidak boleh kosong dan harus memiliki setidaknya 3 karakter");
	        return false;
	    }

	    if (itemCategory.isEmpty() || itemCategory.length() < 3) {
	        System.out.println("Kategori item tidak boleh kosong dan harus memiliki setidaknya 3 karakter");
	        return false;
	    }

	    if (itemSize.isEmpty()) {
	        System.out.println("Ukuran item tidak boleh kosong");
	        return false;
	    }

	    if (itemPrice <= 0) {
	        System.out.println("Harga item tidak boleh kosong atau nol");
	        return false;
	    }

	    int itemId = selectedItem.getItemId(); 
	    return Item.updateItem(itemId, itemName, itemSize, itemPrice, itemCategory);
	}

	
	public static boolean deleteItem(int itemId) {
		return Item.deleteItem(itemId);
	}
	
	public ArrayList<Item> getAllSellerItems() {
	    return Item.showSellerItems();
	}
	
	// Batas Fitur Seller
	
	
	// untuk Fitur Buyer 
	public ArrayList<Item> getAllApprovedItems() {
	    return Item.showApprovedItems();
	}
	// Batas Akhir Fitur Buyer
	
	
	// Dibawah ini for Fitur Admin
    public static ArrayList<Item> getPendingItems() {
        return Item.getPendingItems();
    }

    public String approvingItem(int itemId) {
        boolean valid = Item.approveItem(itemId);
        return valid ? "Item berhasil diapprove" : "Item gagal di approve!";
    }

    public String declineItem(int itemId) {
        boolean valid = Item.declineItem(itemId);
        return valid ? "Item dengan ID " + itemId + " berhasil ditolak." : "Gagal menolak item dengan ID " + itemId + ".";
    }
    // Batas Akhir Fitur Admin
	


}
