package views.adminViews;

import controllers.ItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Item;
import models.User;
import views.LoginViewPage;

import java.util.ArrayList;
// VIEW REQUESTED ITEM, APPROVE AND DECLINE APPROVE DONE!!!!
public class AdminHomePage extends BorderPane {
    Stage stage;
    MenuBar menuBar;
    User user;

    TableView<Item> itemTable;
    Button approveBtn, declineBtn;
    ItemController itemController;
    ObservableList<Item> itemsList;
    Alert infoAlert, warningAlert, logoutAlert;
    
    private ObservableList<Item> showPendingItems() {
		ArrayList<Item> pendingItems = itemController.getPendingItems();
        itemsList.clear();
        itemsList.addAll(pendingItems);
        return itemsList;
    }

    private void init() {
        Menu logoutMenu = new Menu("Logout");
        MenuItem logoutItem = new MenuItem("Logout");
        logoutMenu.getItems().add(logoutItem);
        menuBar = new MenuBar();
        menuBar.getMenus().add(logoutMenu);
        logoutAlert = new Alert(Alert.AlertType.INFORMATION, "ANDA TELAH LOGOUT!");
        
        logoutItem.setOnAction(e -> {
            stage.close();
            logoutAlert.showAndWait();
            new LoginViewPage(stage);
        });

        itemTable = new TableView<>();
        approveBtn = new Button("Approve Item");
        declineBtn = new Button("Decline");
        itemController = new ItemController();
        itemsList = FXCollections.observableArrayList();

        infoAlert = new Alert(Alert.AlertType.INFORMATION);
        warningAlert = new Alert(Alert.AlertType.WARNING);

        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        TableColumn<Item, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        itemTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, statusCol);
        itemTable.setItems(showPendingItems());

        approveBtn.setOnAction(e -> {
            Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                warningAlert.setContentText("Pilih item untuk approve!");
                warningAlert.showAndWait();
                return;
            }
            String result = itemController.approvingItem(selectedItem.getItemId());
            infoAlert.setContentText(result);
            infoAlert.showAndWait();
            itemTable.setItems(showPendingItems());
        });

        declineBtn.setOnAction(e -> {
            Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                warningAlert.setContentText("Pilih item untuk decline!");
                warningAlert.showAndWait();
                return;
            }
            String result = itemController.declineItem(selectedItem.getItemId());
            infoAlert.setContentText(result);
            infoAlert.showAndWait();
            itemTable.setItems(showPendingItems());
        });
    }

    private void setLayout() {
        VBox bodyLyt = new VBox(10);
        Label titleLbl = new Label("Home - View Request Item Page");
        bodyLyt.setPadding(new Insets(10));
        bodyLyt.getChildren().addAll(titleLbl, itemTable, new ToolBar(approveBtn, declineBtn));
        this.setTop(menuBar);
        this.setCenter(bodyLyt);
    }

    public AdminHomePage(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
        init();
        setLayout();

        Scene scene = new Scene(this, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Admin Home Page - CaLouseIF");
        stage.show();
    }
}