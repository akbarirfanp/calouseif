package views;

import controllers.UserController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

import models.User;

public class RegisterViewPage extends GridPane {
    Stage stage;
    User user;
    Alert successAlert, failedAlert;
    RadioButton sellerRadioBtn, buyerRadioBtn, adminRadioBtn;
    ToggleGroup roleTG;
    TextField usernameTF, phoneNumberTF, addressTF;
    PasswordField passwordPF;
    Label usernameLbl, passLbl, phoneLbl, roleLbl, addressLbl, titleLbl;
    Hyperlink goLogin;
    Button registBtn;
    GridPane gp;
    
    private void init() {
    	sellerRadioBtn = new RadioButton("Seller");
        buyerRadioBtn = new RadioButton("Buyer");
        adminRadioBtn = new RadioButton("Admin");
    	roleTG = new ToggleGroup();
        sellerRadioBtn.setToggleGroup(roleTG);
        buyerRadioBtn.setToggleGroup(roleTG);
        adminRadioBtn.setToggleGroup(roleTG);
        
    	titleLbl = new Label("Register Form");
    	usernameLbl = new Label("Username : ");
        passLbl = new Label("Password : ");
        phoneLbl = new Label("Phone Number : ");
        addressLbl = new Label("Address : ");
        roleLbl = new Label("Role : ");
        usernameTF = new TextField();
        passwordPF = new PasswordField();
        addressTF = new TextField();
        phoneNumberTF = new TextField();
        registBtn = new Button("Submit");
        successAlert = new Alert(Alert.AlertType.INFORMATION, "Anda berhasil melakukan register!");
        failedAlert = new Alert(Alert.AlertType.ERROR, "Anda gagal melakukan register!");
        
        goLogin = new Hyperlink("Silahkan login jika sudah punya akun!");
        goLogin.setOnAction(e -> new LoginViewPage(stage));
        
        registBtn.setOnAction(e -> {
            String username = usernameTF.getText();
            String password = passwordPF.getText();
            String phone_number = phoneNumberTF.getText();
            String address = addressTF.getText();
            String roles = "";
            if(buyerRadioBtn.isSelected()) { roles = "buyer"; } 
            else if(adminRadioBtn.isSelected()) { roles = "admin"; }
            else if (sellerRadioBtn.isSelected()) { roles = "seller"; }
            	
            user = UserController.getRegister(username, password, phone_number, address, roles);
            if (user == null) {
            	failedAlert.showAndWait();
            } else {
                successAlert.showAndWait();
                new LoginViewPage(stage);
            }
        });
        //
    }
    
    private void setLayout() {
    	gp = new GridPane();
        gp.add(usernameLbl, 0, 0); 
        gp.add(usernameTF, 1, 0);   
        gp.add(passLbl, 0, 1); 
        gp.add(passwordPF, 1, 1);   
        gp.add(phoneLbl, 0, 2); 
        gp.add(phoneNumberTF, 1, 2);  
        gp.add(addressLbl, 0, 3); 
        gp.add(addressTF, 1, 3);        
        gp.add(roleLbl, 0, 4);
        gp.add(sellerRadioBtn, 1, 4);  
        gp.add(buyerRadioBtn, 1, 5); 
        gp.add(adminRadioBtn, 1, 6); 
        gp.add(registBtn, 1, 7);  
        gp.add(goLogin,1, 8);
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(30));
        
        registBtn.setPrefWidth(195);
        this.getChildren().add(gp);
    }

    public RegisterViewPage(Stage stage) {
        this.stage = stage;
        init();
        setLayout();
        
        Scene scene = new Scene(this, 400, 330);  
        stage.setScene(scene);
        stage.setTitle("Register Page");
        stage.show();
    }
}
