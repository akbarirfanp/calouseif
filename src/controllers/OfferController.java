// OfferController.java
package controllers;

import models.Offer;
import models.Item;

import java.util.ArrayList;
import java.util.Optional;

public class OfferController {

    public boolean makeOffer(int itemId, int userId, int offeredPrice) {
        Optional<Item> itemOption = Item.getItemById(itemId);
        if (itemOption.isEmpty()) {
            System.out.println("item tidak ditemukan!!");
            return false;
        }

        if (offeredPrice <= 0) {
            System.out.println("Harga tawar harus lebih dari 0");
            return false;
        }

        return Offer.makeOffer(itemId, userId, offeredPrice);
    }

    public static boolean getAcceptOffer(int offerId) {
        return Offer.acceptOffer(offerId);
    }

    public static boolean getDeclineOffer(int offerId) {
        return Offer.declineOffer(offerId);
    }

    public static ArrayList<Offer> getAllOfferedItem() {
        return Offer.getOfferedItem();
    }
    
    
}
