package controllers;

import java.util.ArrayList;
import models.Item;
import models.Transaction;
import models.User;
import models.Wishlist;

public class TransactionController {

	public String purchaseItem(Item selectedItem, User user) {
	    if (selectedItem == null) {
	        return "Tidak ada item yang anda dipilih! Klik dua kali untuk memilih!";
	    }
	    boolean isPurchased = Transaction.purchaseItem(selectedItem.getItemId(), user.getUserId());
	    if (isPurchased) {
	        Wishlist.deleteWishlist(selectedItem.getItemId()); 
	        return "Item berhasil dibeli!";
	    } else {
	        return "Gagal membeli item!";
	    }
	}
	
	public static ArrayList<Transaction> getAllTransaction(int userId) {
        return Transaction.getAllTransaction(userId);
    }
}
