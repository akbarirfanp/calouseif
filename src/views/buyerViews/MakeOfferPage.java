package views.buyerViews;

import controllers.ItemController;
import controllers.OfferController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Item;
import models.User;

import java.util.ArrayList;

public class MakeOfferPage extends GridPane {
    Stage stage;
    User user;
    TableView<Item> itemTable;
    TextField offerPriceField;
    Button makeOfferBtn;
    OfferController offerController;
    ItemController itemController;
    ObservableList<Item> itemsList;
    Alert warningAlert, successAlert, logoutAlert;
    
    private ObservableList<Item> showItems() {
        ArrayList<Item> approvedItems = itemController.getAllApprovedItems();
        itemsList.clear();
        itemsList.addAll(approvedItems);
        return itemsList;
    }

    @SuppressWarnings("unchecked")
    private void init() {
        itemTable = new TableView<>();
        offerPriceField = new TextField();
        makeOfferBtn = new Button("Make Offer");
        offerController = new OfferController();
        itemController = new ItemController();

        itemsList = FXCollections.observableArrayList();

        warningAlert = new Alert(Alert.AlertType.WARNING);
        successAlert = new Alert(Alert.AlertType.INFORMATION);

        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Item, Integer> priceCol = new TableColumn<>("Current Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        itemTable.getColumns().addAll(nameCol, categoryCol, priceCol);
        itemTable.setItems(showItems());

        makeOfferBtn.setOnAction(e -> {
            Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                warningAlert.setContentText("Pilih item untuk membuat tawaran!");
                warningAlert.showAndWait();
                return;
            }
            try {
                int offerPrice = Integer.parseInt(offerPriceField.getText());

                boolean success = offerController.makeOffer(selectedItem.getItemId(), user.getUserId(), offerPrice);
                if (success) {
                    successAlert.setContentText("Anda berhasil menawar silahkan tunggu respon dari seller!");
                    successAlert.showAndWait();
                    new BuyerHomePage(stage, user); 
                    
                } else {
                    warningAlert.setContentText("Gagal memmbuat tawaran! Coba cek input lagi!");
                    warningAlert.showAndWait();
                }
                
            } catch (NumberFormatException ex) {
                warningAlert.setContentText("Harga offer harus valid! (ex: integer & offered_price > currPrice)");
                warningAlert.showAndWait();
            }
        });
    }

    private void setLayout() {
        this.setPadding(new Insets(10));
        this.setVgap(10);
        this.setHgap(10);

        this.add(new Label("Item yang available dapat ditawar!"), 0, 0);
        this.add(itemTable, 0, 1, 2, 1);
        this.add(new Label("Offer Price:"), 0, 2);
        this.add(offerPriceField, 1, 2);
        this.add(makeOfferBtn, 1, 3);
    }

    public MakeOfferPage(Stage stage, User user) {
        this.stage = stage;
        this.user = user;

        init();
        setLayout();

        Scene scene = new Scene(this, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Make Offer - CaLouseIF");
        stage.show();
    }
}
