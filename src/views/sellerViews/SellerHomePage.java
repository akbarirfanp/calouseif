package views.sellerViews;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.User;
import models.Item;
import views.LoginViewPage;
import controllers.ItemController;

public class SellerHomePage extends BorderPane {
    Stage stage;
    Scene scene;
    MenuBar menuBar;
    Button deleteBtn;
    Alert logoutAlert, confirmAlert, successAlert, deleteFailAlert, editFailed;
    Label titleLbl, bodyLbl;
    TableView<Item> itemTable;
    ObservableList<Item> itemsList;
    ItemController itemController;
    User user;

    private void init() {
    	titleLbl = new Label("Seller Home - View Item Seller");
        bodyLbl = new Label("Cara Edit Item: Klik item dua kali!");
        itemController = new ItemController();
        logoutAlert = new Alert(Alert.AlertType.INFORMATION, "ANDA TELAH LOGOUT!");
        successAlert = new Alert(Alert.AlertType.INFORMATION, "Item berhasil dihapus!");
        confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Apakah anda yakin ingin menghapus item ini?", ButtonType.YES, ButtonType.NO);
        deleteFailAlert = new Alert(Alert.AlertType.ERROR, "Item Gagal dihapus!");
    	editFailed = new Alert(Alert.AlertType.WARNING, "Pilih Item untuk edit!");
    	
    	
        Menu homeMenu = new Menu("Home");
        Menu activityMenu = new Menu("Seller Activities");
        Menu logoutMenu = new Menu("Logout");
        MenuItem home = new MenuItem("Home");
        MenuItem offeredItemView = new MenuItem("View Offered Item");
        MenuItem uploadItemView = new MenuItem("Upload Item");
        MenuItem logoutItem = new MenuItem("Logout");

        homeMenu.getItems().add(home);
        activityMenu.getItems().addAll(offeredItemView, uploadItemView);
        logoutMenu.getItems().add(logoutItem);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(homeMenu, activityMenu, logoutMenu);

        // OnAction Btn
        home.setOnAction(e -> new SellerHomePage(stage, user));
        offeredItemView.setOnAction(e -> new OfferedItemPage(stage, user));
        uploadItemView.setOnAction(e -> new UploadItemPage(stage, user));

        logoutItem.setOnAction(e -> {
            stage.close();
            logoutAlert.showAndWait();
            new LoginViewPage(stage);
        });

        itemTable = new TableView<>();
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

        TableColumn<Item, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            {
            	deleteBtn = new Button("Delete");
                deleteBtn.setOnAction(event -> {
                    Item selectedItem = getTableView().getItems().get(getIndex());
                    if (selectedItem != null) {
                        	confirmAlert.showAndWait();
                        if (confirmAlert.getResult() == ButtonType.YES) {
                            boolean isDeleted = itemController.deleteItem(selectedItem.getItemId());
                            if (isDeleted) {
                                itemsList.remove(selectedItem); 
                                successAlert.showAndWait();
                            } else {
                                deleteFailAlert.showAndWait();
                            }
                        }
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

        itemTable.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, statusCol, actionCol);
        itemsList = FXCollections.observableArrayList(itemController.getAllSellerItems());

        itemTable.setItems(itemsList);

        itemTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        itemTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    new EditItemPage(stage, user, selectedItem);
                } else {
                    editFailed.showAndWait();
                }
            }
        });
    }

    private void setLayout() {
        VBox bodyLyt = new VBox(15);
        bodyLyt.setPadding(new Insets(15));
        bodyLyt.getChildren().addAll(titleLbl, bodyLbl, itemTable);
        this.setTop(menuBar);
        this.setCenter(bodyLyt);
    }


    public SellerHomePage(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
        init();
        setLayout();

        scene = new Scene(this, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Seller Home Page");
        stage.show();
    }
}
