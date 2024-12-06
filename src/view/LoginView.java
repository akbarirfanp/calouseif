package view;

import controller.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.User;

public class LoginView extends GridPane {
    Stage stage;
    TextField usernameTF;
    PasswordField passwordPF;
    Button loginBtn;

    private void init() {
        usernameTF = new TextField();
        passwordPF = new PasswordField();
        loginBtn = new Button("Login");

        loginBtn.setOnAction(e -> {
            String username = usernameTF.getText();
            String password = passwordPF.getText();
            User user = UserController.login(username, password);

            if (user == null) {
                System.out.println("User not found");
            } else {
                System.out.println("User found");
                // new HomeView(stage, user);
            }
        });

        // Hyperlink untuk registrasi
        Hyperlink registerLink = new Hyperlink("You don't have an account? Register here!");
        registerLink.setOnAction(e -> {
            // Navigasi ke RegisterView
            new RegisterView(stage);
        });

        // Add the hyperlink to the layout and make it span both columns
        this.add(registerLink, 0, 3, 2, 1); // Spans across two columns
    }

    private void setLayout() {

        Label usernameLbl = new Label("Username:");
        Label passwordLbl = new Label("Password:");

        usernameTF.setPrefWidth(200);
        passwordPF.setPrefWidth(200);

        this.add(usernameLbl, 0, 0); 
        this.add(usernameTF, 1, 0);    
        this.add(passwordLbl, 0, 1); 
        this.add(passwordPF, 1, 1);    
        this.add(loginBtn, 1, 2);

        // Center align the controls in the grid
        GridPane.setHalignment(usernameLbl, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(passwordLbl, javafx.geometry.HPos.RIGHT);
        GridPane.setHalignment(usernameTF, javafx.geometry.HPos.LEFT);
        GridPane.setHalignment(passwordPF, javafx.geometry.HPos.LEFT);
        GridPane.setHalignment(loginBtn, javafx.geometry.HPos.CENTER);

        
        this.setPadding(new javafx.geometry.Insets(20));
        this.setVgap(10);  
        this.setHgap(10);  
    }

    public LoginView(Stage stage) {
        this.stage = stage;
        init();
        setLayout();
        Scene scene = new Scene(this, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Login Page - CaLouseIF");
        stage.show();
    }
}
