package main;

import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Home extends Page implements EventHandler<Event> {

	Label ProductList,ProductName,ProductBrand,ProductStock,ProductPrize,TotalPrize;
	Spinner<Integer> Prize;
	Button addcart;
	GridPane formPane,TablePane;
	FlowPane CenterPane, TitlePane;
	TableView<Racket> table;
	ObservableList<Racket> tableData;
	FileChooser fileChooser;
	Menu PageMenu;
	MenuItem Home,Cart,History,Logout;
	MenuBar menubar;
	BorderPane bp;
	private Connect connect = Connect.getInstance();
	
	private Scene scene;
	private Stage primaryStage;
	private String tempid = null;

	
	public Home() {
		super();
		
	}
	public void init() {
	 ProductList = new Label("Product List");
	 ProductList.setFont(new Font(20));
	 ProductName = new Label("Product Name : ");
	 ProductBrand = new Label("Product Brand : ");
	 ProductStock = new Label("Product Stock");
	 ProductPrize = new Label("Price                :");
	 TotalPrize = new Label("Total Price       : ");
	 formPane = new GridPane();
	 TablePane = new GridPane();
	 CenterPane = new FlowPane();
	 TitlePane = new FlowPane();
	 Prize = new Spinner<>();
	 addcart = new Button("Add to Cart");
	 PageMenu = new Menu("Page");
	 Home = new MenuItem("Home");
	 Cart = new MenuItem("Cart");
	 History = new MenuItem("History");
	 Logout = new MenuItem("Logout");
	 menubar = new MenuBar();
	 bp = new BorderPane();
		
	 menubar.getMenus().addAll(PageMenu);
	 PageMenu.getItems().addAll(Home,Cart,History,Logout);
		
	 bp.setTop(menubar);
	 
	 fileChooser = new FileChooser();
	 SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 70);
		Prize.setValueFactory(factory);
		
		
	 
	 setTable();
	}
	
	private void setTable() {
		tableData = FXCollections.observableArrayList();

		table = new TableView<>(tableData);
		TableColumn<Racket,String> Name = new
				TableColumn<>("Name");
		Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		Name.setPrefWidth(70);
		
		TableColumn<Racket,String> Brand = new
				TableColumn<>("Brand");
		Brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
		Brand.setPrefWidth(70);
		
		TableColumn<Racket,Integer> Stock = new
				TableColumn<>("Stock");
		Stock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		Stock.setPrefWidth(70);
		
		TableColumn<Racket,Integer> Price = new
				TableColumn<>("Price");
		Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
		Price.setPrefWidth(70);
		
		table.getColumns().addAll(Name,Brand,Stock,Price);
		
		table.setPrefHeight(300);
		
		table.setOnMouseClicked(tableMouseEvent());
	}

	public void setLayout() {
		
		
	TablePane.add(ProductList, 1, 1);
	TablePane.add(table, 1, 2);
	
	formPane.add(ProductName, 2, 2);
	formPane.add(ProductBrand, 2,3);
	formPane.add(ProductPrize, 2, 4);
	formPane.add(Prize, 2, 5);
	formPane.add(TotalPrize, 2, 6);
	formPane.add(addcart, 2, 7);
	
	formPane.setHgap(1);
	formPane.setVgap(20);

	formPane.setPadding(new Insets(15));
	
	TablePane.setVgap(5);
	
	CenterPane.setAlignment(Pos.CENTER);
	CenterPane.getChildren().add(TablePane);
	CenterPane.getChildren().add(formPane);

	bp.setCenter(CenterPane);

	
	}
	
	public void setAction() {
		
		 addcart.setOnAction(e -> addToCart());

	
}

	public Home(Stage primaryStage) throws Exception{
		this.primaryStage = primaryStage;
		
		initcomponents();
		getData();
		
		Cart.setOnAction(e -> {
			new CartList(primaryStage);
		});
		
		History.setOnAction(e -> {
			new History(primaryStage);
		});
		
		Logout.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION,"Need Confirmation",
			ButtonType.YES, ButtonType.NO);
			alert.setHeaderText("Are You Sure Want To Logout");
			Optional<ButtonType> opt = alert.showAndWait();
			if(opt.get().equals(ButtonType.YES)) {
						try {
							new Login().start(primaryStage);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
		});

	}
	
	private void initcomponents() {
		scene = new Scene(bp,700,600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Home");
		primaryStage.show();
	}
	
	
	@Override
	public void handle(Event event) {
	
		
	}
	
	private EventHandler<Event> tableMouseEvent(){
		return new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				TableSelectionModel<Racket> tableSelectionModel = table.getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				Racket racket = tableSelectionModel.getSelectedItem();
				
				ProductName.setText("Product Name : " + racket.getName());
				ProductBrand.setText("Product Brand : " + racket.getBrand());
				ProductStock.setText("Product Stock : " + racket.getStock());
				ProductPrize.setText("Price             : " + racket.getPrice());

				Prize.valueProperty().addListener((obs, oldValue, newValue) -> {
				    int totalItems = Prize.getValue(); 
				    int totalPrice = racket.getPrice() * totalItems; 
				    
				    TotalPrize.setText("Total Price  : " + totalPrice);
				});
				tempid = racket.getId();		
				}
			
		};
		
	}
	
	
	private void getData() {
		
		try {
		String query = "SELECT * FROM msproduct";
		connect.rs = connect.exeQuery(query);
			while(connect.rs.next()) {
		String ProductID = connect.rs.getString("ProductID");
		String ProductName = connect.rs.getString("ProductName");
		String ProductMerk = connect.rs.getString("ProductMerk");
		int ProductStock = connect.rs.getInt("ProductStock");
		int ProductPrice = connect.rs.getInt("ProductPrice");
		tableData.add(new Racket(ProductName, ProductMerk, ProductStock, ProductPrice, ProductID));
	}
		}catch (SQLException e) {
			e.printStackTrace();
			}
	}
	
	 private void addToCart() {
	        Racket selectedRacket = table.getSelectionModel().getSelectedItem();
	        if (selectedRacket != null) {
	            int quantity = Prize.getValue();
	            String brand = ProductBrand.getText();
	            String price = ProductPrize.getText();
	            int totalPrice = selectedRacket.getPrice() * quantity;
	            
	            System.out.printf(selectedRacket.getName() +
	            		"," + brand + "," + price +
	                    ", Quantity: " + quantity +
	                    ", Total Price: " + totalPrice);
	            
	            new CartList(primaryStage);
	        } else {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setContentText("Please Choose 1 Item");
	            alert.show();
	        }
	    }

}
