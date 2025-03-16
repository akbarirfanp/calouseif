package views.buyerViews;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import controllers.ItemController;
import controllers.TransactionController;
import controllers.WishlistController;
import models.Item;
import models.User;
import views.LoginViewPage;

public class BuyerHomePage extends BorderPane {
    Stage stage;
    MenuBar menuBar;
    User user;
    ItemController itemCT;
	TransactionController transactionCT;
	WishlistController wishlistCT;
    TableView<Item> itemTV;
    ObservableList<Item> itemList;

    ObservableList<Item> showItems() {
        ArrayList<Item> approvedItems = itemCT.getAllApprovedItems();
        itemList.clear();
        itemList.addAll(approvedItems);
        return itemList;
    }
    
	private void init() {
		itemList = FXCollections.observableArrayList();
        itemTV = new TableView<>();
        itemCT = new ItemController();
        wishlistCT = new WishlistController();
        transactionCT = new TransactionController();
        
		Menu homeMenu = new Menu("Home");
		Menu logoutMenu = new Menu("Logout ->");
        Menu activityMenu = new Menu("Buyer Activities");
        
        MenuItem home = new MenuItem("Home");
        MenuItem transactionHistory = new MenuItem("Transaction History");
        MenuItem wishlist = new MenuItem("Wishlist");
        MenuItem logoutItem = new MenuItem("Logout ->");
        logoutItem.setOnAction(e -> {
            showAlert("Logout!", "Anda telah Logout!");
            new LoginViewPage(stage);
        });
        
        // handler lcick menu
        home.setOnAction(e -> new BuyerHomePage(stage, user));
        wishlist.setOnAction(e -> new WishlistPage(stage, user));
        transactionHistory.setOnAction(e -> new PurchaseHistoryPage(stage, user));
        
        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        
        TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        
        TableColumn<Item, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        
        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Item, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(act -> new TableCell<>() {
        	HBox actionBtns = new HBox(11);
        	Button purchaseBtn = new Button("Purchase");
            Button offerBtn = new Button("Offer");
            Button addWishlistBtn = new Button("Add to Wishlist");
            {
            	addWishlistBtn.setOnAction(e -> {
                     Item item = getTableView().getItems().get(getIndex());
                     if (item != null) {
                         String checks = wishlistCT.getAddToWishlist(item, user);
                         if ("Item berhasil ditambahkan ke wishlist!".equals(checks)) {
                             showAlert("Berhasil!", "Item telah ditambahkan ke wishlist");
                         } else {
                             showAlert("Terjadi kesalahan!", checks);
                         }
                     }
                 });
            	offerBtn.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    if (item != null) {
                        new MakeOfferPage(stage, user);
                    }else {
                    	System.out.println("Terjadi kesalahan!");
                    }
                });
                purchaseBtn.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    if (item != null) {
                        String checks = transactionCT.purchaseItem(item, user);
                        if ("Item berhasil dibeli!".equals(checks)) {
                            showAlert("Berhasil!", "Anda telah membeli item yang dipilih!");
                            itemTV.setItems(showItems());
                        } else {
                            showAlert("Terjadi kesalahan!", checks);
                        }
                    }
                });
                actionBtns.getChildren().addAll(addWishlistBtn, offerBtn, purchaseBtn);
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBtns);
                }
            }
        });

        homeMenu.getItems().add(home);
        activityMenu.getItems().addAll(wishlist, transactionHistory);
        logoutMenu.getItems().add(logoutItem);
        
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(homeMenu, activityMenu, logoutMenu);
        itemTV.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, actionsCol);
        itemTV.setItems(showItems());
    }

    private void setLayout() {
    	this.setCenter(itemTV);
        this.setTop(menuBar);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public BuyerHomePage(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
        init();
        setLayout();
        Scene scene = new Scene(this, 600, 420);
        stage.setScene(scene);
        stage.setTitle("Buyer Home Page");
        stage.show();
    }
    
}
