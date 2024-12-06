

//package views;
//
//import java.util.ArrayList;
//
//import controller.ProductController;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.Scene;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//import models.Product;
//import models.User;
//
//public class HomeView extends BorderPane {
//	
//	Stage stage;
//	User user;
//	
//	TableView<Product> productTV;
//	TableColumn<Product, Integer> idColumn;
//	TableColumn<Product, String> nameColumn;
//	TableColumn<Product, Integer> priceColumn;
//	
//	private void viewAllProducts() {
//		ArrayList<Product> products = ProductController.getAllProduct();
//		ObservableList<Product> productOL = FXCollections.observableArrayList(products);
//		productTV.setItems(productOL);
//	}
//	
//	private void init() {
//		productTV = new TableView<>();
//	
//		idColumn = new TableColumn<>("id");
//		idColumn.setCellValueFactory(
//				new PropertyValueFactory<>("id")
//		);
//		
//		nameColumn = new TableColumn<>("name");
//		nameColumn.setCellValueFactory(
//				new PropertyValueFactory<>("name")
//		);
//		
//		priceColumn = new TableColumn<>("price");
//		priceColumn.setCellValueFactory(
//				new PropertyValueFactory<>("price")
//		);
//		
//		productTV.getColumns().add(idColumn);
//		productTV.getColumns().add(nameColumn);
//		productTV.getColumns().add(priceColumn);
//		viewAllProducts();
//	}
//	
//	private void setLayout() {
//		this.setCenter(productTV);
//	}
//	
//	public HomeView(Stage stage, User user) {
//		this.stage = stage;
//		this.user = user;
//		init();
//		
//		setLayout();
//		
//		Scene scene = new Scene(this, 400, 250);
//		stage.setScene(scene);
//		stage.setTitle("Home View");
//		stage.show();
//		
//	}
//	
//	
//}
