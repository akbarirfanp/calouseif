package views.sellerViews;

import java.util.ArrayList;

import controllers.OfferController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*; // mengambil semua lib yg ada di /control/
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Offer;
import models.User;
import views.LoginViewPage;


public class OfferedItemPage extends BorderPane {
	Stage stage;
	MenuBar menuBar;
	User user;
    Alert successDecline, failedDecline, successAccept, failedAccept;
   
	TableView<Offer> offerTV;
    TableColumn<Offer, String> itemNameColumn;
    TableColumn<Offer, String> itemCategoryColumn;
    TableColumn<Offer, String> itemSizeColumn;
    TableColumn<Offer, Integer> itemPriceColumn;
    TableColumn<Offer, Integer> offeredPriceColumn;
    TableColumn<Offer, Void> acceptColumn;
    TableColumn<Offer, Void> declineColumn;

    public void viewOfferedItem() {
    	ArrayList<Offer> offers = OfferController.getAllOfferedItem();
    	ObservableList<Offer> offerOL = FXCollections.observableArrayList(offers);
    	
    	offerTV.setItems(offerOL);
    }
    
	@SuppressWarnings("unchecked")
	public void init() {
		menuBar = new MenuBar();
	    offerTV = new TableView<>();	
		successDecline = new Alert(Alert.AlertType.INFORMATION, "Tawaran berhasil ditolak!");
		failedDecline = new Alert(Alert.AlertType.ERROR, "Gagal menolak tawaran!");
		successAccept = new Alert(Alert.AlertType.INFORMATION, "Berhasil menerima tawaran!!");
		failedAccept = new Alert(Alert.AlertType.ERROR, "Gagal menerima tawaran!");
		
		Menu homeMenu = new Menu("Home");
		Menu menuItemManagement = new Menu("Item Management");
        Menu logoutMenu = new Menu("Logout");
        
        MenuItem home = new MenuItem("Home");

        MenuItem viewOfferedItemPage = new MenuItem("View Offered Item");
        MenuItem uploadItemPage = new MenuItem("Upload Item");
        MenuItem logoutItem = new MenuItem("Logout");
        
        homeMenu.getItems().addAll(home);
        menuItemManagement.getItems().addAll(viewOfferedItemPage, uploadItemPage);
        logoutMenu.getItems().add(logoutItem);
        
        itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        
        itemCategoryColumn = new TableColumn<>("Item Category");
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        
        itemSizeColumn = new TableColumn<>("Item Size");
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        
        itemPriceColumn = new TableColumn<>("Initial Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        
        offeredPriceColumn = new TableColumn<>("Offered Price");
        offeredPriceColumn.setCellValueFactory(new PropertyValueFactory<>("offeredPrice"));
        
        acceptColumn = new TableColumn<>("Accept Offer");
        acceptColumn.setCellFactory(c -> new TableCell<>() {
        	Button acceptBtn = new Button("Accept Offer");
        	{
        		acceptBtn.setOnAction(e -> {
        			Offer offer = getTableView().getItems().get(getIndex());
        			boolean valid = OfferController.getAcceptOffer(offer.getOfferId());
        			if(valid) {
        				successAccept.showAndWait();
        				getTableView().getItems().remove(offer);
        			}else {
        				failedAccept.showAndWait();
        			}
        		});
        	}
        	
        	protected void updateItem(Void item, boolean empty) {
        		super.updateItem(item, empty);
        		if(empty) {
        			setGraphic(null);
        		}else {
        			setGraphic(acceptBtn);
        		}
        	}
        	
        });
 
        declineColumn = new TableColumn<>("Decline Offer");
        declineColumn.setCellFactory(c -> new TableCell<>() {
        	Button declineBtn = new Button("Decline Offer");
        	{
        		declineBtn.setOnAction(e -> {
        			Offer offer = getTableView().getItems().get(getIndex());
        			boolean valid = OfferController.getDeclineOffer(offer.getOfferId());
        			if(valid) {  				
                        successDecline.showAndWait();
        				getTableView().getItems().remove(offer);
        			}else {
                        failedDecline.showAndWait();
        			}
        		});
        	}
        	
        	protected void updateItem(Void item, boolean empty) {
        		super.updateItem(item, empty);
        		if(empty) {
        			setGraphic(null);
        		} else {
        			setGraphic(declineBtn);
        		}
        	}
        	
        });
        
        home.setOnAction(e -> {
        	new SellerHomePage(stage, user);
        });
        
        uploadItemPage.setOnAction(e -> {
            new UploadItemPage(stage, user);
        });
        
        viewOfferedItemPage.setOnAction(e -> {
            new OfferedItemPage(stage, user);
        });

        logoutItem.setOnAction(e -> {
            stage.close();
            System.out.println("--- Anda berhasil logout! ---");
            new LoginViewPage(stage);
        });
        
        menuBar.getMenus().addAll(homeMenu, menuItemManagement, logoutMenu);
        offerTV.getColumns().addAll(itemNameColumn, itemCategoryColumn, itemSizeColumn, itemPriceColumn, offeredPriceColumn, acceptColumn, declineColumn);
	}
	
	
	public void setLayout() {
		this.setTop(menuBar);
		this.setCenter(offerTV);

	}
	
	public OfferedItemPage(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
        init();     
        setLayout();
        viewOfferedItem();

        Scene scene = new Scene(this, 700, 400);
        stage.setScene(scene);
        stage.setTitle("Offered Item Page - Seller - CaLouseIF");
        stage.show();
	}
	
	
}





























// Author: Irfan Akbar Pramana Putra, Vincent Sebastian Gunawan
