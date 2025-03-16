package main;

import database.Database;
import javafx.application.Application;
import javafx.stage.Stage;
import views.LoginViewPage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
		Database.getInstance();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		new LoginViewPage(primaryStage);
	}

}
