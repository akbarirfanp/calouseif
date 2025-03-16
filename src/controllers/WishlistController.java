package controllers;

import java.util.ArrayList;

import models.Item;
import models.User;
import models.Wishlist;

public class WishlistController {

    public static ArrayList<Wishlist> getAllWishlist(int userId) {
        return Wishlist.getAllWishlist(userId);
    }
    
    public String getAddToWishlist(Item item, User user) {
        boolean success = Wishlist.addToWishlist(user.getUserId(), item.getItemId());
        if (success) {
            return "Item berhasil ditambahkan ke wishlist!";
        } else {
            return "Gagal menambahkan item ke wishlist!";
        }
    }
    
    public static boolean getDeleteWishlist(int wishlistId) {
    	return Wishlist.deleteWishlist(wishlistId);
    }
    
    
    
}
