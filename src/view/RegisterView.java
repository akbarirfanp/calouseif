package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.User;
import controller.UserController;

public class RegisterView extends GridPane {
    Stage stage;
    TextField usernameTF;
    PasswordField passwordPF;
    TextField phoneNumberTF;
    TextField addressTF;
    
    RadioButton sellerRadioButton;
    RadioButton customerRadioButton;
    ToggleGroup roleToggleGroup;
    
    Button registerBtn;
    
    private void init() {
        usernameTF = new TextField();
        passwordPF = new PasswordField();
        phoneNumberTF = new TextField();
        addressTF = new TextField();
        registerBtn = new Button("Register");
        sellerRadioButton = new RadioButton("Seller");
        customerRadioButton = new RadioButton("Customer");
        
        roleToggleGroup = new ToggleGroup();
        sellerRadioButton.setToggleGroup(roleToggleGroup);
        customerRadioButton.setToggleGroup(roleToggleGroup);

        registerBtn.setOnAction(e -> {
            String username = usernameTF.getText();
            String password = passwordPF.getText();
            String phone_number = phoneNumberTF.getText();
            String address = addressTF.getText();

            // Get selected role
            String role = "";
            if (sellerRadioButton.isSelected()) {
                role = "seller";
            } else if (customerRadioButton.isSelected()) {
                role = "customer";
            }

            User user = UserController.register(username, password, phone_number, address, role);

            if (user != null) {
                System.out.println("Berhasil register!");
                usernameTF.clear();
                passwordPF.clear();
                phoneNumberTF.clear();
                addressTF.clear();
                sellerRadioButton.setSelected(false);
                customerRadioButton.setSelected(false);
                new LoginView(stage);
                
            } else {
          
                System.out.println("Gagal register.");
            }
            
            
            
        });

    }
    
    private void setLayout() {
        Label usernameLbl = new Label("Username:");
        Label passwordLbl = new Label("Password:");
        Label phoneNumberLbl = new Label("Phone Number:");
        Label addressLbl = new Label("Address:");
        Label roleLbl = new Label("Role:");

        this.add(usernameLbl, 0, 0); 
        this.add(usernameTF, 1, 0);   
        this.add(passwordLbl, 0, 1); 
        this.add(passwordPF, 1, 1);   
        this.add(phoneNumberLbl, 0, 2); 
        this.add(phoneNumberTF, 1, 2);  
        this.add(addressLbl, 0, 3); 
        this.add(addressTF, 1, 3);    
        
        this.add(roleLbl, 0, 4);
        this.add(sellerRadioButton, 1, 4);  
        this.add(customerRadioButton, 1, 5); 
        
        this.add(registerBtn, 1, 6);  
    }

    public RegisterView(Stage stage) {
        this.stage = stage;
        init();
        setLayout();
        Scene scene = new Scene(this, 400, 300);  
        stage.setScene(scene);
        stage.setTitle("Register Page - CaLouseIF");
        stage.show();
    }
}
