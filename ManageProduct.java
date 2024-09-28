package main;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.control.ComboBox;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ManageProduct extends Page implements EventHandler<Event>{

	Label ProductList,ProductName,ProductBrand,ProductPrice,name,Addstock,DeleteProduct;
	TextField PNameTxt,PPriceTxt;
	ComboBox<String> Brand;
	Spinner<Integer> Stock;
	Button Addproduct,AddStock,Delete;
	GridPane formPane,TablePane,formPane2;
	FlowPane CenterPane, TitlePane;
	TableView<Racket> Tableproduct;
	ObservableList<Racket> Tableproductlist;
	FileChooser fileChooser;
	Menu PageMenu;
	MenuItem ManageProduct,ViewHistory,Logout;
	MenuBar menubar;
	BorderPane bp;
	
	
	
	static int ProductID = 1;
	ManageProduct selected = null;
	static String tempid = null;
	private Scene scene;
	private Stage primaryStage;
	private Statement st;
	static Connect connect = Connect.getInstance();
	
	public ManageProduct() {
		super();
	}
	
	public void init() {
		
		ProductList = new Label("Product List");
		ProductList.setFont(new Font(20));
		
		ProductName = new Label("Product Name");	
		PNameTxt = new TextField();
		
		ProductBrand = new Label("Product Brand"); 
		Brand = new ComboBox<>();
		
		ProductPrice = new Label("Product Price");
		PPriceTxt = new TextField();
		Addproduct = new Button("Add Product");
		
		name = new Label("Name               : ");
		name.setFont(new Font(15));
		
		Addstock = new Label("Add Stock");
		Stock = new Spinner<>();
		AddStock = new Button("Add Stock");
		
		DeleteProduct = new Label("Delete Product");
		Delete = new Button("Delete");
;		
		formPane = new GridPane();
		formPane2 = new GridPane();
		TablePane = new GridPane();
		CenterPane = new FlowPane();
		TitlePane = new FlowPane();
		bp = new BorderPane();
		Brand = new ComboBox<>();
		fileChooser = new FileChooser();
		PageMenu = new Menu("Admin");
		ManageProduct = new MenuItem("Manage Product");
		ViewHistory = new MenuItem("View History");
		Logout = new MenuItem("Logout");
		menubar = new MenuBar();
		
		menubar.getMenus().addAll(PageMenu);
		PageMenu.getItems().addAll(ManageProduct,ViewHistory,Logout);
			
		bp.setTop(menubar);
		
		 SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 70);
			Stock.setValueFactory(factory);
		
		setProduct();
;;	}
	

	private void setProduct() {
		Tableproductlist = FXCollections.observableArrayList();
		
		Tableproduct = new TableView<>(Tableproductlist);
		Tableproduct.setItems(Tableproductlist);
		
		TableColumn<Racket,String> Name = new
				TableColumn<>("Name");
		Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		Name.setPrefWidth(100);
		
		TableColumn<Racket,String> Brand = new
				TableColumn<>("Brand");
		Brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
		Brand.setPrefWidth(100);
		
		TableColumn<Racket,Integer> Stock = new
				TableColumn<>("Stock");
		Stock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		Stock.setPrefWidth(100);
		
		TableColumn<Racket,Integer> Price = new
				TableColumn<>("Price");
		Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
		Price.setPrefWidth(100);
		
		Tableproduct.getColumns().addAll(Name,Brand,Stock,Price);
		
		Tableproduct.setPrefWidth(400);
		
		Tableproduct.setOnMouseClicked(tableMouseEvent());
		
		 Tableproduct.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		        if (newSelection != null) {
		            Racket selectedRacket = newSelection;
		            name.setText("Name      : " + selectedRacket.getName());
		            tempid = selectedRacket.getId();
		        } else {
		        }
		    });
		
	}
	
	

	public void setLayout() {
		
		AddStock.setPrefWidth(150);
		Delete.setPrefWidth(60);
		
		Brand.getItems().add("Yonex");
		Brand.getItems().add("Li-Ning");
		Brand.getItems().add("Victor");
		Brand.getSelectionModel().select(0);
		
		TablePane.add(ProductList, 1, 1);
		TablePane.add(Tableproduct, 1, 2);
		
		ProductList.setPadding(new Insets(0,0,0,150));
		
		formPane.add(ProductName, 2, 2);
		formPane.add(PNameTxt, 2, 3);
		formPane.add(ProductBrand, 2, 4);
		formPane.add(Brand, 2, 5);
		formPane.add(ProductPrice, 2, 6);
		formPane.add(PPriceTxt, 2, 7);
		formPane.add(Addproduct, 2, 8);
		
		formPane2.add(name, 0,0);
		formPane2.add(Addstock, 0, 2);
		formPane2.add(Stock, 0, 3);
		formPane2.add(AddStock, 0 , 4);
		formPane2.add(DeleteProduct, 1, 2);
		formPane2.add(Delete, 1, 4);
		
		name.setPadding(new Insets(0,0,0,150));
		Addstock.setPadding(new Insets(0,0,0,50));
		DeleteProduct.setPadding(new Insets(0,0,0,-9));
		
		formPane.setHgap(1);
		formPane.setVgap(20);


		formPane2.setVgap(10);
		
		
		formPane2.setPadding(new Insets(30));
		
		formPane.setPadding(new Insets(15));
		
		TablePane.setVgap(10);
		TablePane.setHgap(5);

		
		CenterPane.setAlignment(Pos.CENTER);
		CenterPane.getChildren().add(TablePane);
		CenterPane.getChildren().add(formPane);
		CenterPane.getChildren().add(formPane2);
		
		formPane2.setAlignment(Pos.BOTTOM_CENTER);

		bp.setBottom(formPane2);
		
		
		CenterPane.setHgap(5);

		bp.setCenter(CenterPane);

	}
	
	public void setAction() {
	
		
	}
	
	public ManageProduct(Stage primaryStage) throws SQLException {
		this.primaryStage = primaryStage;
		
		initComponents();
		getData();
		
		ViewHistory.setOnAction(e -> {
			new MyHistory(primaryStage);
		});
		
		Logout.setOnAction(e -> {
		System.exit(0);
		
		});
		
		Addproduct.setOnAction(e -> {
		    try {
		    	String userID = generateRandomID();
		        String query ="insert into msproduct(ProductID,ProductName,ProductMerk,ProductPrice,ProductStock) VALUES(?,?,?,?,?)";
		        PreparedStatement ps = connect.prepareStatement(query);
		        ps.setString(1, userID);
		        ps.setString(2, PNameTxt.getText());
		        ps.setString(3, Brand.getSelectionModel().getSelectedItem());
		        ps.setInt(4, Integer.valueOf(PPriceTxt.getText()));
		        ps.setInt(5, Stock.getValue());
		        ps.executeUpdate();
		        
		        Racket Product = new Racket(
		            PNameTxt.getText(),
		            Brand.getSelectionModel().getSelectedItem(),
		            Stock.getValue(),
		            Integer.valueOf(PPriceTxt.getText()),
		            String.valueOf(ProductID)
		        );
		        Tableproductlist.add(Product);
		    } catch (SQLException e1) {
		        e1.printStackTrace();
		    }
		});

		
		Delete.setOnAction(e -> {
            try {
                String query = "DELETE FROM msproduct WHERE ProductID = ?";
                PreparedStatement ps = connect.prepareStatement(query);
                ps.setString(1, tempid);
                ps.executeUpdate();
                Tableproductlist.removeIf(racket -> racket.getId().equals(tempid)); 
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
		
		AddStock.setOnAction(e -> {
		    Racket selectedProduct = Tableproduct.getSelectionModel().getSelectedItem();

		    if (selectedProduct != null) {
		        try {
		            int additionalStock = Stock.getValue(); 

		            int currentStock = selectedProduct.getStock(); 
		            int newStock = currentStock + additionalStock;

		            String query = "UPDATE msproduct SET ProductStock = ? WHERE ProductID = ?";
		            PreparedStatement ps = connect.prepareStatement(query);
		            ps.setInt(1, newStock);
		            ps.setString(2, selectedProduct.getId());
		            ps.executeUpdate();

		            selectedProduct.setStock(newStock);

		            Tableproduct.refresh();
		            
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    } else {
		    	Alert alert = new Alert(AlertType.ERROR," Please Select Product First !");
		    }
		});
		
	}
	
	private void initComponents() {
		 scene = new Scene(bp,900,650);
		 primaryStage.setScene(scene);
		 primaryStage.setTitle("Manage Product");
		 primaryStage.show();
		
	}
	
	private void getData() {
        try {
            String query = "SELECT * FROM msproduct";
            connect.rs = connect.exeQuery(query);
            while (connect.rs.next()) {
                String productId = connect.rs.getString("ProductID");
                String productName = connect.rs.getString("ProductName");
                String productMerk = connect.rs.getString("ProductMerk");
                int productStock = connect.rs.getInt("ProductStock");
                int productPrice = connect.rs.getInt("ProductPrice");
                Tableproductlist.add(new Racket(productName, productMerk, productStock, productPrice, productId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
	 private String generateRandomID() {
	        int randomInt = new Random().nextInt(900) + 100;
	        return "PD" + randomInt;
	    }
	
	
	@Override
	public void handle(Event e) {
		
	}
	


	private EventHandler<Event> tableMouseEvent() {
	    return new EventHandler<Event>() {
	        @Override
	        public void handle(Event event) {
	        	TableSelectionModel<Racket> selectionModel = Tableproduct.getSelectionModel();
	            selectionModel.setSelectionMode(SelectionMode.SINGLE);
	            
	            Racket selectedRacket = selectionModel.getSelectedItem();
	            if (selectedRacket != null) {
	                name.setText("Name      : " + selectedRacket.getName());
	                tempid = selectedRacket.getId();
	            } else {
	                
	            }
	        }
	    };
	}

	

}
