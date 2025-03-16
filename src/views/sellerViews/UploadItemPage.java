package views.sellerViews;


import controllers.ItemController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.User;

public class UploadItemPage extends GridPane {
	Stage stage;
	User user;
	Scene scene;
	MenuBar menuBar;
	Label nameLbl, categoryLbl, sizeLbl, priceLbl;
	TextField nameField, categoryField, priceField, sizeField;
	Button uploadItemBtn;
	Alert success, failed;
	
	public void init() {
		success = new Alert(Alert.AlertType.INFORMATION, "Berhasil mengupload Item!");
		failed = new Alert(Alert.AlertType.ERROR, "Gagal mengupload item!");
	
        nameLbl = new Label("Item Name:");
        nameField = new TextField();
        categoryLbl = new Label("Item Category:");
        categoryField = new TextField();
        sizeLbl = new Label("Item Size:");
        sizeField = new TextField();
        priceLbl = new Label("Item Price:");
        priceField = new TextField();
        uploadItemBtn = new Button("Upload");
        

        uploadItemBtn.setOnAction(e -> {
            String itemName = nameField.getText();
            String itemCategory = categoryField.getText();
            String itemSize = sizeField.getText();
            String priceText = priceField.getText();
            Integer itemPrice = null; 
            
            try {
                if (!priceText.isEmpty()) {
                    itemPrice = Integer.parseInt(priceText); 
                }
            } catch (NumberFormatException ex) {
                System.out.println("Harga item harus angka.");
                failed.setContentText("Harga item harus angka."); 
                failed.showAndWait();
                return; 
            }

            boolean valid = ItemController.uploadItem(itemName, itemSize, itemPrice, itemCategory);
            if (!valid) {
                failed.showAndWait();
            } else {
                success.showAndWait();
                new SellerHomePage(stage, user);
            }
        });

        
        
	}
	
	public void setLayout() {;
		this.add(nameLbl, 0, 0);
        this.add(nameField, 1, 0);
        this.add(categoryLbl, 0, 1);
        this.add(categoryField, 1, 1);
        this.add(sizeLbl, 0, 2);
        this.add(sizeField, 1, 2);
        this.add(priceLbl, 0, 3);
        this.add(priceField, 1, 3);
        this.add(uploadItemBtn, 1, 4);	
        this.setAlignment(Pos.CENTER);
	}
	

	public UploadItemPage(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
		init();
		setLayout();
		
		scene = new Scene(this, 400, 400);
		stage.setScene(scene);
        stage.setTitle("Upload Item- CaLouseIF");
        stage.show();
	}

}
