package views.sellerViews;

import controllers.ItemController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Item;
import models.User;

public class EditItemPage extends GridPane {
    Stage stage;
    User user;
    private Item selectedItem;
    ItemController itemController;

    TextField nameField, categoryField, sizeField, priceField;
    Button updateBtn;
    Alert success, failed;

    private void init() {
        nameField = new TextField(selectedItem.getItemName());
        categoryField = new TextField(selectedItem.getItemCategory());
        sizeField = new TextField(selectedItem.getItemSize());
        priceField = new TextField(String.valueOf(selectedItem.getItemPrice()));
        failed = new Alert(Alert.AlertType.WARNING);
        success = new Alert(Alert.AlertType.INFORMATION);
        
        updateBtn = new Button("Update");
        updateBtn.setOnAction(e -> {
			boolean result = itemController.updateItem(
                selectedItem, 
                nameField.getText(), 
                sizeField.getText(), 
                Integer.parseInt(priceField.getText()), 
                categoryField.getText()
            );

            if (result) {
                success.setContentText("Item berhasil di update!!");
                success.showAndWait();
                new SellerHomePage(stage, user); 
            } else {
                failed.setContentText("Item Gagal di Update!");
                failed.showAndWait();
            }
        });
    }

    private void setLayout() {
        this.setPadding(new Insets(10));
        this.add(new Label("Item Name:"), 0, 0);
        this.add(nameField, 1, 0);
        this.add(new Label("Item Category:"), 0, 1);
        this.add(categoryField, 1, 1);
        this.add(new Label("Item Size:"), 0, 2);
        this.add(sizeField, 1, 2);
        this.add(new Label("Item Price:"), 0, 3);
        this.add(priceField, 1, 3);
        this.add(updateBtn, 1, 4);
    }

    public EditItemPage(Stage stage, User user, Item selectedItem) {
        this.stage = stage;
        this.user = user;
        this.selectedItem = selectedItem;
        this.itemController = new ItemController();
        init();   
        setLayout();

        Scene scene = new Scene(this, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Edit Item - CaLouseIF");
        stage.show();
    }
}
