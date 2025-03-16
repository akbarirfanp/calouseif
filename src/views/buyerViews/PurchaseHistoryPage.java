package views.buyerViews;


import java.util.ArrayList;

import controllers.TransactionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Transaction;
import models.User;
import views.LoginViewPage;

public class PurchaseHistoryPage extends BorderPane{
	Stage stage;
    MenuBar menuBar;
    User user;
    
    TableView<Transaction> transactionTV;
    TableColumn<Transaction, Integer> transactionIdColumn;
    TableColumn<Transaction, String> itemNameColumn;
    TableColumn<Transaction, String> itemCategoryColumn;
    TableColumn<Transaction, String> itemSizeColumn;
    TableColumn<Transaction, Integer> itemPriceColumn;

    public void viewAllPurchase(User user) {
    	ArrayList<Transaction> transactions = TransactionController.getAllTransaction(user.getUserId());
        ObservableList<Transaction> transactionOL = FXCollections.observableArrayList(transactions);

        transactionTV.setItems(transactionOL);
    }
   
	@SuppressWarnings("unchecked")
	public void init() {
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
        
        //
        
        transactionIdColumn = new TableColumn<>("Transaction ID");
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));

        itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        itemCategoryColumn = new TableColumn<>("Item Category");
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        
        itemSizeColumn = new TableColumn<>("Item Size");
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        
        itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        
        //
        
        home.setOnAction(e -> {
            new BuyerHomePage(stage, user);
        });

        wishlist.setOnAction(e -> {
            new WishlistPage(stage, user);
        });

        purchaseHistory.setOnAction(e -> {
            new PurchaseHistoryPage(stage, user);
        });

        logoutItem.setOnAction(e -> {
            stage.close();
            System.out.println("--- Anda berhasil logout! ---");
            new LoginViewPage(stage);
        });
        
        transactionTV = new TableView<>();
        menuBar = new MenuBar();
        transactionTV.getColumns().addAll(transactionIdColumn, itemNameColumn, itemCategoryColumn, itemSizeColumn, itemPriceColumn);;
        menuBar.getMenus().addAll(homeMenu, activityMenu, accountMenu);
        
	}
	
	public void setLayout() {
		this.setTop(menuBar);
		this.setCenter(transactionTV);
	}
	
	public PurchaseHistoryPage(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
		init();
        setLayout();
        viewAllPurchase(user);
        
        Scene scene = new Scene(this, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Purchase History Page- CaLouseIF");
        stage.show();
	}

}
