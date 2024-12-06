package main;

import database.Database;
import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
		Database.getInstance();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		primaryStage.show();
		new LoginView(primaryStage);
		
	}

}
