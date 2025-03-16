package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import views.adminViews.AdminHomePage;
import views.buyerViews.BuyerHomePage;
import views.sellerViews.SellerHomePage;
import controllers.UserController;
import models.User;

public class LoginViewPage extends GridPane {
    Stage stage;
    Alert success1, success2, failed;
    Label titleLbl, usernameLbl, passwordLbl;
    TextField usernameTF;
    PasswordField passwordPF;
    Button loginBtn;
    Hyperlink goRegister;
    GridPane gp;
    User user;
    
    private void init() {
    	titleLbl = new Label("Login Form");
        usernameLbl = new Label("Username: ");
        usernameTF = new TextField();
        passwordLbl = new Label("Password : ");
        passwordPF = new PasswordField();
        
        loginBtn = new Button("LOGIN");
        success1 = new Alert(Alert.AlertType.INFORMATION, "Login berhasil! Welcome!");
        success2 = new Alert(Alert.AlertType.INFORMATION, "User role tidak terdeteksi");
        
        goRegister = new Hyperlink("Sudah punya akun? Register disini!");
        goRegister.setOnAction(e -> new RegisterViewPage(stage));
        
        loginBtn.setOnAction(e -> {
            String username = usernameTF.getText().trim();
            String pass = passwordPF.getText().trim();
            
            user = UserController.getLogin(username, pass);
            if (user == null) {
            	System.out.println("Ada kesalahan!");
            } else {
            	success1.showAndWait();
            	switch (user.getRole().toLowerCase()) {
                case "seller":
                    new SellerHomePage(stage, user);
                    break;
                case "buyer":
                    new BuyerHomePage(stage, user);
                    break;
                case "admin":
                    new AdminHomePage(stage, user);
                    break;
                default:
                    success2.show();
                    break;
            	}
            }
        });
        //
    }
    
    // note: hgap vgap atur spasi antar fieldny
    private void setLayout() {
    	gp = new GridPane();
    	gp.add(titleLbl, 0,0, 3, 1);
        gp.add(usernameLbl, 0, 1); 
        gp.add(usernameTF, 1, 1);    
        gp.add(passwordLbl, 0, 2); 
        gp.add(passwordPF, 1, 2);    
        gp.add(loginBtn, 1, 4);
        gp.add(goRegister, 1, 5);
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(20));
        gp.setAlignment(Pos.CENTER);
        usernameTF.setPrefWidth(200);
        passwordPF.setPrefWidth(200);
        loginBtn.setPrefWidth(200);
        this.getChildren().add(gp);
    }

    public LoginViewPage(Stage stage) {
        this.stage = stage;
        init();
        setLayout();
        Scene scene = new Scene(this, 350, 200);
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.show();
    }
}
