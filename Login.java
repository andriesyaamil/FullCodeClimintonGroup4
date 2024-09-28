package main;

import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Login extends Application implements EventHandler<ActionEvent>{

	Label EmailLbl, PasswordLbl;
	TextField EmailTxt;
	PasswordField PassTxt;
	Button LoginBtn;
	GridPane formPane;
	FlowPane CenterPane, TitlePane;
	Menu PageMenu;
	MenuItem Login,Register;
	MenuBar menubar;
	BorderPane bp;
	private Scene scene;
	private Stage primaryStage;
	
	public void init() {
		EmailLbl = new Label ("Email");
		EmailTxt = new TextField();
		PasswordLbl = new Label ("Password");
		PassTxt = new PasswordField();
		LoginBtn = new Button("Login");
		formPane = new GridPane();
		CenterPane = new FlowPane();
		TitlePane = new FlowPane();
		PageMenu = new Menu("Page");
		Login = new MenuItem("Login");
		Register = new MenuItem("Register");
		menubar = new MenuBar();
		bp = new BorderPane();

		
		menubar.getMenus().addAll(PageMenu);
		PageMenu.getItems().addAll(Login,Register);
		
		bp.setTop(menubar);
		bp.setCenter(formPane);
	}
	
	public void setLayout() {
		
		EmailLbl.setPrefWidth(200);
		EmailTxt.setPrefWidth(100);
		LoginBtn.setPrefWidth(100);
		
		formPane.add(EmailLbl, 0, 1);
		formPane.add(EmailTxt, 0, 2);
		formPane.add(PasswordLbl, 0, 3);
		formPane.add(PassTxt, 0, 4);
		formPane.add(LoginBtn, 0, 5);
		
		formPane.setAlignment(Pos.CENTER);
		CenterPane.setAlignment(Pos.CENTER);
		CenterPane.getChildren().add(formPane);
		
		formPane.setVgap(10);
		formPane.setHgap(15);
		
		formPane.setPadding(new Insets(15));
		
		bp.setCenter(CenterPane);
		
		BorderPane.setAlignment(CenterPane,Pos.CENTER);
		
		bp.setPadding(new Insets(0,0,50,0));
		
		
	}
	
	public void setAction() {
		
		LoginBtn.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		try {
		if(event.getSource().equals(LoginBtn)){
			handleLogin();
		}
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.WARNING,e.getMessage(),ButtonType.OK);
			alert.show();
		}
		
	}
	

	
	private void handleLogin() throws Exception {
		
        String email = EmailTxt.getText();
        String password = PassTxt.getText();

        if (email.isEmpty() || password.isEmpty()) {
            throw new Exception("Email or Password must be filled!");
        }
        
        Role role = validateCredentials(email, password);

        if (role != null) {
            if (role.getType().equals("user")) {
                new Home(primaryStage);
            } else if (role.getType().equals("admin")) {
                new ManageProduct(primaryStage);
            }
        } else {
            throw new Exception("Wrong Email or Password");
        }
    }
	
	private Role validateCredentials(String email, String password) {
        if (email.equals("boodi@gmail.com") && password.equals("user1234")) {
            return new Role("user");
        } else if (email.equals("admin@gmail.com") && password.equals("admin1234")) {
            return new Role("admin");
        } else {
            return null;
        }
    }
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		init();
		setLayout();
		setAction();
		
		Register.setOnAction(e ->{
			new Register(primaryStage);
		});
		
		scene = new Scene(bp, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();
		
		primaryStage.setResizable(false);
		
		primaryStage.setOnCloseRequest(e -> {
		Alert alert = new Alert(AlertType.CONFIRMATION,"Are You Want To Close?",
		ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> opt = alert.showAndWait();
		if(opt.get().equals(ButtonType.NO)) {
			e.consume();
		}
		});
	}
	
	public static void main (String[]args) {
		launch(args);
	}

}
