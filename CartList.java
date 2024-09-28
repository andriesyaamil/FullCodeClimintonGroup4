package main;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class CartList extends Page implements EventHandler<ActionEvent> {

	Label YourCart,ProductName,ProductBrand,ProductPrize,Quantity,TotalPrize;
	Button Checkout,DeleteProduct;
	GridPane formPane,TablePane,ButtonPane;
	FlowPane CenterPane, TitlePane;
	TableView<DataCart> table;
	ObservableList<DataCart> tableData;
	FileChooser fileChooser;
	Menu PageMenu;
	MenuItem Home,Cart,History,Logout;
	MenuBar menubar;
	BorderPane bp;
	 Connect connect = Connect.getInstance();
	  private ChangeListener<String> listenerToRemove; 
	
	private Scene scene;
	private Stage primaryStage;
	private String tempId = null;
	
	
	public CartList() {
		super();
	}
	
	public void init() {
		 YourCart = new Label("Your Cart List");
		 YourCart.setFont(new Font(20));
		 ProductName = new Label("Name              : ");
		 ProductBrand = new Label("Brand              : ");
		 ProductPrize = new Label("Price                :");
		 TotalPrize = new Label("Total Price       : ");
		 formPane = new GridPane();
		 TablePane = new GridPane();
		 ButtonPane = new GridPane();
		 CenterPane = new FlowPane();
		 TitlePane = new FlowPane();
		 Checkout = new Button("Checkout");
		 DeleteProduct = new Button("Delete Product");
		 fileChooser = new FileChooser();
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
		 
		 Quantity = new Label();
		 Quantity.setFont(new Font(14));
		 
		 
		 setTable();
	}
	
	private void setTable() {
		tableData = FXCollections.observableArrayList();
		
		table = new TableView<>(tableData);
		TableColumn<DataCart,String> Name = new
				TableColumn<>("Name");
		Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		Name.setPrefWidth(70);
		
		TableColumn<DataCart,String> Brand = new
				TableColumn<>("Brand");
		Brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
		Brand.setPrefWidth(70);
		
		TableColumn<DataCart,Integer> Price = new
				TableColumn<>("Price");
		Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
		Price.setPrefWidth(70);
		
		TableColumn<DataCart,Integer> Quantity = new
				TableColumn<>("Quantity");
		Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		Quantity.setPrefWidth(70);
		
		TableColumn<DataCart,Integer> Total = new
				TableColumn<>("Total");
		Total.setCellValueFactory(new PropertyValueFactory<>("Total"));
		Total.setPrefWidth(70);
		
		
		table.getColumns().addAll(Name,Brand,Price,Quantity,Total);
		
		table.setPrefHeight(200);
		
		table.setOnMouseClicked(tableMouseEvent());
	}
	
	public void setLayout() {
		
		TablePane.add(YourCart, 1, 1);
		TablePane.add(table, 1, 2);
		
		ButtonPane.add(Checkout, 1, 2);
		ButtonPane.add(DeleteProduct, 1, 3);
		
		Checkout.setPrefWidth(400);
		DeleteProduct.setPrefWidth(400);
	
		ButtonPane.setVgap(15);
		
		formPane.add(ProductName, 2, 2);
		formPane.add(ProductBrand, 2,3);
		formPane.add(ProductPrize, 2, 4);
		formPane.add(TotalPrize, 2, 5);
		
		formPane.setHgap(1);
		formPane.setVgap(15);

		formPane.setPadding(new Insets(15));
		
		TablePane.setVgap(5);

		CenterPane.setAlignment(Pos.CENTER);
		CenterPane.getChildren().add(TablePane);
		CenterPane.getChildren().add(formPane);
		CenterPane.getChildren().add(ButtonPane);
		
		CenterPane.setVgap(0);
		
		bp.setCenter(CenterPane);

		
	}
	
	private EventHandler<Event> tableMouseEvent() {
	    return event -> {
	        TableSelectionModel<DataCart> tableSelectionModel = table.getSelectionModel();
	        tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
	        DataCart dataCart = tableSelectionModel.getSelectedItem();

	        if (dataCart != null) {
	            ProductName.setText("Name : " + dataCart.getName());
	            ProductBrand.setText("Brand : " + dataCart.getBrand());
	            ProductPrize.setText("Price  : " + dataCart.getPrice());

	            Quantity.setText(Integer.toString(dataCart.getQuantity()));
	            TotalPrize.setText("Total Price  : " + dataCart.getTotal());

	            tempId = dataCart.getId();
	        } else {
	            ProductName.setText("Name : ");
	            ProductBrand.setText("Brand : ");
	            ProductPrize.setText("Price  : ");
	            Quantity.setText("");
	            TotalPrize.setText("");
	            tempId = null;
	        }
	    };
	}



	
	public void setAction() {
	
		
	}


	public CartList(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		initComponents();
		getData();
		
		Home.setOnAction(e ->{
			try {
				new Home(primaryStage);
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		History.setOnAction(e -> {
			new History(primaryStage);
			
		});
		
		DeleteProduct.setOnAction(e -> {
			if(table.getSelectionModel().getSelectedItem()== null) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setContentText("Please Select Product To Delete");
				alert.show();
			}else if(table.getSelectionModel().getSelectedItem() !=null) {
				int index = 0;
				for(int i = 0; i< tableData.size(); i++) {
					if(tableData.get(i).getName().equals(table.getSelectionModel().getSelectedItem().getName())) {
						index = i;
						break;
					}
				}
				tableData.remove(index);
				int totalprice = 0;
				for(DataCart racket : tableData) {
					totalprice += racket.getPrice()*racket.getQuantity();
				}
				TotalPrize.setText(("Total Price :" + totalprice));
			}
		});
		
		
		
		Checkout.setOnAction(e -> {
		    if (table.getSelectionModel().getSelectedItem()== null) {
		        Alert alert = new Alert(Alert.AlertType.WARNING);
		        alert.setContentText("Please insert to your cart");
		        alert.show();
		    } else {
		        new TransactionPOPUP(primaryStage, tableData);
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
		
	}
	
		private int calculateTotalPrice() {
		    int totalprice = 0;
		    for (DataCart racket : tableData) {
		        totalprice += racket.getPrice() * racket.getQuantity();
		    }
		    return totalprice;
		}

	private void initComponents() {
		 scene = new Scene(bp,800,600);
		 primaryStage.setScene(scene);
		 primaryStage.setTitle("Cart");
		 primaryStage.show();
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		 if (table.getSelectionModel().getSelectedItem() != null) {
		        DataCart selectedItem = table.getSelectionModel().getSelectedItem();
		        ProductName.setText("Name : " + selectedItem.getName());
		        ProductBrand.setText("Brand : " + selectedItem.getBrand());
		        ProductPrize.setText("Price : " + selectedItem.getPrice());
		    }
	};
	
	private void getData() {
		  try (PreparedStatement preparedStatement = connect.prepareStatement(
                  "SELECT msproduct.*, carttable.quantity FROM msproduct JOIN carttable ON msproduct.ProductID = carttable.ProductID;");
          ResultSet rs = preparedStatement.executeQuery()) {

         while (rs.next()) {
             String productName = rs.getString("ProductName");
             String productMerk = rs.getString("ProductMerk");
             int productPrice = rs.getInt("ProductPrice");
             int quantity = rs.getInt("Quantity");
             tableData.add(new DataCart(productName, productMerk, productPrice, quantity, tempId));
         }

     } catch (SQLException e) {
         e.printStackTrace();
     }
	}



	  
	}
