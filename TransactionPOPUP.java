package main;

import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class TransactionPOPUP extends Page {

	Label ListLabel,NameProduct,NameProduct1,NameProduct2,Courier,TotalPrice,Title;
	ComboBox<String> Kurir;
	CheckBox asuransi;
	Button checkout;
	Menu PageMenu;
	MenuBar menubar;
	FlowPane CenterPane, TitlePane;
	GridPane formPane;
	BorderPane bp;
	 private ObservableList<DataCart> tableData;

	private Connect connect = Connect.getInstance();
	
	
	private Scene scene;
	private Stage primaryStage;
	
	public TransactionPOPUP() {
		super();
	}
	
	
	public void init() {
		
		Font labelFont = new Font("Arial", 25);
		
		formPane = new GridPane();
		CenterPane = new FlowPane();
		TitlePane = new FlowPane();
		menubar = new MenuBar();
		PageMenu = new Menu("Transaction Card");
		
		Title = new Label("Transaction Card");
		Title.setFont(labelFont);
		
		ListLabel = new Label("List");
		ListLabel.setFont(new Font(20));
		
		NameProduct = new Label("Astrox 99 : ");
		NameProduct.setFont(new Font(15));
		
		NameProduct1 = new Label("Thruster HMR : ");
		NameProduct1.setFont(new Font(15));
		
		NameProduct2 = new Label("Tectonic 7 : ");
		NameProduct2.setFont(new Font(15));
		
		Courier = new Label("Courier");
		Courier.setFont(new Font(20));
		
		Kurir = new ComboBox<>();
		Kurir.setPrefWidth(230);
		Kurir.setPrefHeight(30);
		
		asuransi = new CheckBox("Use Insurance");
		asuransi.setFont(new Font(15));
		
		TotalPrice = new Label("Total Price :");
		TotalPrice.setFont(new Font(15));
		
		checkout = new Button("Checkout");
		
		bp = new BorderPane();
		
			
	}
	
	
	
	public void setLayout() {
		
		Title.setAlignment(Pos.CENTER);
		Title.setPrefWidth(800); 
		
		Kurir.getItems().add("J&E");
		Kurir.getItems().add("JET");
		Kurir.getItems().add("Nanji Express");
		Kurir.getItems().add("Gejok");
		Kurir.getSelectionModel().select(0);
		
		bp.setStyle("-fx-background-color: #ADD8E6;");
		
		menubar.setStyle("-fx-background-color: black; -fx-font-weight: bold; -fx-text-fill: white; -fx-alignment: center;");
	
		
		checkout.setPrefWidth(230);
		checkout.setPrefHeight(30);
		
		formPane.add(ListLabel, 1, 1);
		formPane.add(NameProduct, 1, 3);
		formPane.add(NameProduct1, 1, 4);
		formPane.add(NameProduct2, 1, 5);
		formPane.add(Courier, 1, 7);
		formPane.add(Kurir, 1, 8);
		formPane.add(asuransi, 1, 9);
		formPane.add(TotalPrice, 1, 10);
		formPane.add(checkout, 1, 11);
		
		
		
		ListLabel.setPadding(new Insets(0,0,0,90));
		Courier.setPadding(new Insets(0,0,0,85));
		asuransi.setPadding(new Insets(0,0,0,50));
		TotalPrice.setPadding(new Insets(0,0,0,30));
		Title.setPadding(new Insets(60,0,0,0));
		
		formPane.setHgap(0);
		formPane.setVgap(15);

		formPane.setPadding(new Insets(25));
		
		CenterPane.setAlignment(Pos.CENTER);
		CenterPane.getChildren().add(formPane);
		
		CenterPane.setVgap(10);
		
		bp.setCenter(CenterPane);
		
		bp.setTop(Title);
	}
	 
	public void setAction() {
		
		checkout.setOnMouseClicked(e -> {
				Alert alert = new Alert(AlertType.CONFIRMATION,"Need Confirmation",
				ButtonType.YES, ButtonType.NO);
				alert.setHeaderText("Are You Sure Want To Checkout All The Item");
				Optional<ButtonType> opt = alert.showAndWait();
				if(opt.get().equals(ButtonType.YES)) {
							try {
								new History(primaryStage);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
			});
	}

		
	public TransactionPOPUP(Stage primaryStage, ObservableList<DataCart> tableData) {
			 
	this.primaryStage = primaryStage;
	 this.tableData = tableData;
				
	initComponents();
	
	if (!tableData.isEmpty()) {
        DataCart firstItem = tableData.get(0);
        DataCart firstItem1 = tableData.get(1);
        DataCart firstItem2 = tableData.get(2);
        NameProduct.setText(firstItem.getName() + " : " + firstItem.getPrice());
        NameProduct1.setText(firstItem1.getName() + " : " + firstItem1.getPrice());
        NameProduct2.setText(firstItem2.getName() + " : " + firstItem2.getPrice());
    }

    int totalPrice = calculateTotalPrice();
    TotalPrice.setText("Total Price : " + totalPrice);
	}
	
	 private int calculateTotalPrice() {
	        int totalprice = 0;
	        for (DataCart item : tableData) {
	            totalprice += item.getPrice() * item.getQuantity();
	        }
	        return totalprice;
	    }
	
	
	private void initComponents() {
		 scene = new Scene(bp,800,600);
		 primaryStage.setTitle("");
		 primaryStage.setScene(scene);
		 primaryStage.show();
		
	}
}
