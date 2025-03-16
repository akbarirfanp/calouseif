package views.buyerViews;

import java.util.ArrayList;
import controllers.WishlistController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.User;
import models.Wishlist;
import views.LoginViewPage;

public class WishlistPage extends BorderPane {
    Stage stage;
    Alert success, failed, logout;
    User user;
    MenuBar menuBar;
    TableView<Wishlist> wishlistTV;
    TableColumn<Wishlist, Integer> wishlistIdColumn;
    TableColumn<Wishlist, String> itemStatusColumn;
    TableColumn<Wishlist, String> itemNameColumn;
    TableColumn<Wishlist, Void> deleteColumn;

    public void init() {
    	failed = new Alert(Alert.AlertType.ERROR, "Anda gagal menghapus wishlist!");
        success = new Alert(Alert.AlertType.INFORMATION, "Item telah dihapus pada wishlist!");
        logout = new Alert(Alert.AlertType.INFORMATION, "ANDA TELAH LOGOUT!");
    	Menu homeMenu = new Menu("Home");
        Menu activityMenu = new Menu("Activity");
        Menu accountMenu = new Menu("Logout");
        MenuItem home = new MenuItem("Home");
        MenuItem wishlist = new MenuItem("Wishlist");
        MenuItem purchaseHistory = new MenuItem("Purchase History");
        MenuItem logoutItem = new MenuItem("Logout");
        homeMenu.getItems().add(home);
        activityMenu.getItems().addAll(wishlist, purchaseHistory);
        accountMenu.getItems().add(logoutItem);
        
        wishlistIdColumn = new TableColumn<>("Wishlist ID");
        wishlistIdColumn.setCellValueFactory(new PropertyValueFactory<>("wishlistId"));

        itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        itemStatusColumn = new TableColumn<>("Item Status");
        itemStatusColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        deleteColumn = new TableColumn<>("Actions");
        deleteColumn.setCellFactory(c -> new TableCell<>() {
        	
            Button deleteBtn = new Button("Delete");
            {
                deleteBtn.setOnAction(e -> {
                    Wishlist wishlist = getTableView().getItems().get(getIndex());
                    boolean valid = WishlistController.getDeleteWishlist(wishlist.getWishlistId());
                    if (valid) {
                        success.showAndWait();
                        getTableView().getItems().remove(wishlist);
                    } else {
                        failed.showAndWait();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
            
        });
        
        wishlistTV = new TableView<>();
        wishlistTV.getColumns().addAll(wishlistIdColumn, itemNameColumn, itemStatusColumn, deleteColumn);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(homeMenu, activityMenu, accountMenu);
        
        // event handler menu
        home.setOnAction(e -> new BuyerHomePage(stage, user));
        wishlist.setOnAction(e -> new WishlistPage(stage, user));
        purchaseHistory.setOnAction(e -> new PurchaseHistoryPage(stage, user));
        logoutItem.setOnAction(e -> {
            stage.close();
            logout.showAndWait();
            new LoginViewPage(stage);
        });
    }

    public void setLayout() {
    	this.setCenter(wishlistTV);
        this.setTop(menuBar);
    }
    
    // mote cek sesi 10
    public void getWishlist(User user) {
        ArrayList<Wishlist> wishlists = WishlistController.getAllWishlist(user.getUserId());
        ObservableList<Wishlist> wishlistOL = FXCollections.observableArrayList(wishlists);
        wishlistTV.setItems(wishlistOL);
    }

    public WishlistPage(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
        init();
        setLayout();
        getWishlist(user);

        Scene scene = new Scene(this, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Wishlist Page - CaLouseIF");
        stage.show();
    }
}