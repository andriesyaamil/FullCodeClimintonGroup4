package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Register extends Page implements EventHandler<ActionEvent> {
	
	Label EmailLbl, PasswordLbl, ConfirmPassLbl, AgeLbl, GenderLbl, NationalityLbl;
	TextField EmailTxt;
	PasswordField PassTxt, ConfirmPassTxt;
	Button RegisBtn;
	GridPane formPane;
	FlowPane CenterPane, TitlePane;
	RadioButton male;
	RadioButton female;
	ToggleGroup tg;
	ComboBox<String> Negara;
	Spinner<Integer> Usia;
	Menu PageMenu;
	MenuItem Login,Register;
	MenuBar menubar;
	BorderPane bp;
	
	private Scene scene;
	private Stage primaryStage;
	private Connect connect = Connect.getInstance();
	private String tempid;
	private static final String INSERT_USER_QUERY = "INSERT INTO msuser (UserID, UserEmail, UserPassword, UserAge, UserGender, UserNationality, UserRole) VALUES (?, ?, ?, ?, ?, ?, 'User')";




	public Register() {
		super();
	}
	
	@Override
	public void init() {
		EmailLbl = new Label ("Email");
		EmailTxt = new TextField();
		PasswordLbl = new Label ("Password");
		PassTxt = new PasswordField();
		ConfirmPassLbl = new Label ("Confirm Password");
		ConfirmPassTxt = new PasswordField();
		AgeLbl = new Label ("Age");
		GenderLbl = new Label ("Gender");
		NationalityLbl = new Label ("Nationality");
		formPane = new GridPane();
		CenterPane = new FlowPane();
		TitlePane = new FlowPane();
		male = new RadioButton("Male");
		female = new RadioButton("Female");
		tg = new ToggleGroup();
		Negara = new ComboBox<>();
		Usia = new Spinner<>();
		RegisBtn = new Button ("Register");	
		PageMenu = new Menu("Page");
		Login = new MenuItem("Login");
		Register = new MenuItem("Register");
		menubar = new MenuBar();
		bp = new BorderPane();
		
		menubar.getMenus().addAll(PageMenu);
		PageMenu.getItems().addAll(Login,Register);
		
		bp.setTop(menubar);
		
	}
	
	
	public void setLayout() {
		
		RegisBtn.setPrefWidth(100);
		male.setToggleGroup(tg);
		female.setToggleGroup(tg);
		
		Negara.getItems().add("Indonesia");
		Negara.getItems().add("China");
		Negara.getItems().add("Arab");
		Negara.getItems().add("Australia");
		Negara.getSelectionModel().select(0);
		
		SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 70);
		Usia.setValueFactory(factory);
				
		
		
		formPane.add(EmailLbl, 0, 1);
		formPane.add(EmailTxt, 0, 2);
		formPane.add(PasswordLbl, 0, 3);
		formPane.add(PassTxt, 0, 4);
		formPane.add(ConfirmPassLbl, 0, 5);
		formPane.add(ConfirmPassTxt, 0, 6);
		formPane.add(AgeLbl, 0, 7);
		formPane.add(Usia, 0, 8);
		formPane.add(GenderLbl, 1,1 );
		formPane.add(male,1,2);
		formPane.add(female, 1, 3);
		formPane.add(NationalityLbl, 1, 4);
		formPane.add(Negara, 1, 5);
		formPane.add(RegisBtn, 1, 6);
		formPane.setVgap(5);
		formPane.setHgap(5);
		formPane.setPadding(new Insets(15));
		
		CenterPane.setAlignment(Pos.CENTER);
		CenterPane.getChildren().add(formPane);
		formPane.setHgap(30);
		

		bp.setCenter(CenterPane);
		
		BorderPane.setAlignment(CenterPane,Pos.CENTER);
		
		setPadding(new Insets(0,0,50,0));
		
		
	}
	
	
	public void setAction() {
        RegisBtn.setOnAction(this);
    }
	

	@Override
	public void handle(ActionEvent event) {
	    String email = EmailTxt.getText();
	    String password = PassTxt.getText();
	    String confirmPassword = ConfirmPassTxt.getText();
	    int age = Usia.getValue();
	    String gender = male.isSelected() ? "Male" : "Female";
	    String nationality = Negara.getValue();

	    if (!email.endsWith("@gmail.com")) {
	        showAlert("Invalid Email", "Email must end with '@gmail.com'");
	        return;
	    }

	    if (password.length() < 6) {
	        showAlert("Invalid Password", "Password must contain at least 6 characters");
	        return;
	    }

	    if (!password.equals(confirmPassword)) {
	        showAlert("Wrong Password", "Confirm Password must match Password");
	        return;
	    }

	    if (age <= 0) {
	        showAlert("Invalid Age", "Age must be greater than 0");
	        return;
	    }

	    if (tg.getSelectedToggle() == null) {
	        showAlert("Gender Not Selected", "Please select a gender");
	        return;
	    }

	    if (nationality == null || nationality.isEmpty()) {
	        showAlert("Nationality Not Selected", "Please select a nationality");
	        return;
	    }
	    tempid = generateUserID(email);
	    saveToDatabase(email, password, age, gender, nationality);

	    redirectToLogin();
	}

	private String generateUserID(String email) {
	  
	    return email.substring(0, 3) + System.currentTimeMillis();
	}
	
	private void redirectToLogin() {
	    try {
	        primaryStage.close();
	        new Login().start(new Stage());

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void showAlert(String title, String content) {
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(content);
	    alert.showAndWait();
	}

	private void saveToDatabase(String email, String password, int age, String gender, String nationality) {
	    try {
	        Connection connection = connect.getConnection();

	        if (emailExists(email)) {
	            showAlert("Registration Error", "Email already registered");
	            return;
	        }
	        String userID = generateRandomID();

	        String Query = "INSERT INTO msuser (UserID, UserEmail, UserPassword, UserAge, UserGender, UserNationality, UserRole) VALUES (?, ?, ?, ?, ?, ?, ?)";

	        String Role = "User";

	        PreparedStatement preparedStatement = connection.prepareStatement(Query);
	        preparedStatement.setString(1, userID);
	        preparedStatement.setString(2, email);
	        preparedStatement.setString(3, password);
	        preparedStatement.setInt(4, age);
	        preparedStatement.setString(5, gender);
	        preparedStatement.setString(6, nationality);
	        preparedStatement.setString(7, Role);
	        preparedStatement.executeUpdate();

	        showAlert("Registration Successful", "User registered successfully!");

	    } catch (SQLException e) {
	        e.printStackTrace();
	        showAlert("Registration Error", "An error occurred while registering user.");
	    }
	}
	
	private boolean emailExists(String email) {
	    try {
	        Connection connection = connect.getConnection();
	        String Query = "SELECT COUNT(*) FROM msuser WHERE UserEmail = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(Query);
	        preparedStatement.setString(1, email);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        resultSet.next();
	        int count = resultSet.getInt(1);
	        return count > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	    private String generateRandomID() {
	        int randomInt = new Random().nextInt(900) + 100;
	        return "UA" + randomInt;
	    }

	public Register(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		Login.setOnAction(e ->{
			try {
				new Login().start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		});

		scene = new Scene(bp,600,400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Register");
		primaryStage.show();
	}

}
