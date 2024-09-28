package main;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class History extends Page{

	
	Label MyTransaction, TransactionDetail, TotalPrice;
	TableView<TransactionHeader> Transaction;
	TableView<TransactionDetail> Transactiondetail;
	GridPane formPane,TablePane;
	FlowPane CenterPane, TitlePane;
	ObservableList<TransactionHeader> tableDataTransaction;
	ObservableList<TransactionDetail> tableDataTransactionDetail;
	FileChooser fileChooser;
	Menu PageMenu;
	MenuItem Home,Cart,History,Logout;
	MenuBar menubar;
	BorderPane bp;
	private Connect connect = Connect.getInstance();
	
	private Scene scene;
	private Stage primaryStage;
	private String tempid = null;
	
	public History() {
		super();
	}
	
	
	public void init() {
		MyTransaction = new Label("My Transaction");
		MyTransaction.setFont(new Font(20));
		
		TransactionDetail = new Label("Transaction Detail");
		TransactionDetail.setFont(new Font(20));
		
		TotalPrice = new Label("Total Price : ");
		TotalPrice.setFont(new Font(15));
		
		fileChooser = new FileChooser();
		formPane = new GridPane();
		TablePane = new GridPane();
		CenterPane = new FlowPane();
		TitlePane = new FlowPane();
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
		
		
		setTransaction();
		setTransactionDetail();
		
	}
	
	private void setTransaction() {
	tableDataTransaction = FXCollections.observableArrayList();

	Transaction = new TableView<>(tableDataTransaction);
		TableColumn<TransactionHeader, String> id = new TableColumn<>("ID");
		id.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		id.setPrefWidth(100);

	    TableColumn<TransactionHeader, String> email = new TableColumn<>("Email");
	    email.setCellValueFactory(new PropertyValueFactory<>("Email"));
	    email.setPrefWidth(100);

	    TableColumn<TransactionHeader, Date> date = new TableColumn<>("Date");
	    date.setCellValueFactory(new PropertyValueFactory<>("Date")); 
	    date.setPrefWidth(100);

	    Transaction.getColumns().addAll(id, email, date);
	    Transaction.setPrefWidth(300);
		


		
	}
	
	private void setTransactionDetail() {
		
	tableDataTransactionDetail = FXCollections.observableArrayList();

		
		Transactiondetail = new TableView<>(tableDataTransactionDetail);
		TableColumn<TransactionDetail,String> Id = new
				TableColumn<>("ID");
		Id.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
		Id.setPrefWidth(80);
		
		TableColumn<TransactionDetail, String> Product = new 
				TableColumn<>("Product");
		Product.setCellValueFactory(new PropertyValueFactory<>("namaproduct"));
		Product.setPrefWidth(80);
		
		TableColumn<TransactionDetail, String> Price = new 
				TableColumn<>("Price");
		Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
		Price.setPrefWidth(80);
		
		TableColumn<TransactionDetail,Integer> Quantity = new
				TableColumn<>("Quantity");
		Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		Quantity.setPrefWidth(80);
		
		 TableColumn<TransactionDetail, Integer> TotalPrice = new TableColumn<>("Total Price");
	        TotalPrice.setCellValueFactory(cellData -> {
	            TransactionDetail cartItem = cellData.getValue();
	            int totalPrice = cartItem.getTotal();
	            return new SimpleObjectProperty<>(totalPrice);
	        });
		
		Transactiondetail.getColumns().addAll(Id,Product,Price,Quantity,TotalPrice);
		
		Transactiondetail.setPrefWidth(400);
		
	}
	
	

	public void setLayout() {
		
		formPane.add(MyTransaction, 1, 1);
		formPane.add(Transaction, 1, 2);
		
		formPane.setHgap(1);
		formPane.setVgap(20);

		formPane.setPadding(new Insets(5));
		
		
		TablePane.add(TransactionDetail, 2, 1);
		TablePane.add(Transactiondetail, 2, 2);
		TablePane.add(TotalPrice, 2, 3);
		

		TablePane.setVgap(22);
		TablePane.setHgap(5);

	
		
		CenterPane.setAlignment(Pos.CENTER);
		CenterPane.getChildren().add(formPane);
		CenterPane.getChildren().add(TablePane);
		
		CenterPane.setHgap(5);

		bp.setCenter(CenterPane);
	}
	
	public void setAction() {
	      Transaction.getSelectionModel().selectedItemProperty().addListener(
	              (ObservableValue<? extends TransactionHeader> observable, TransactionHeader oldValue, TransactionHeader newValue) -> {
	                  if (newValue != null) {
	                      updateTransactionDetail(newValue.getTransactionID());
	                  }
	              });
	}
	

	 private void updateTransactionDetail(String transactionID) {
	        tableDataTransactionDetail.clear();
	        int totalTransactionPrice = 0;
	        try {
	            String query = "SELECT td.TransactionID, td.Quantity, mp.ProductName, mp.ProductPrice " +
	                    "FROM transactiondetail td " +
	                    "JOIN msproduct mp ON td.ProductID = mp.ProductID " +
	                    "WHERE td.TransactionID = '" + transactionID + "';";
	            connect.rs = connect.exeQuery(query);
	            while (connect.rs.next()) {
	                String id = connect.rs.getString("TransactionID");
	                String productName = connect.rs.getString("ProductName");
	                int price = connect.rs.getInt("ProductPrice");
	                int quantity = connect.rs.getInt("Quantity");
	                int totalPrice = price * quantity;
	                tableDataTransactionDetail.add(new TransactionDetail(id, productName, price, quantity));
	                totalTransactionPrice += totalPrice;
	            }
	            TotalPrice.setText("Total Price  : " + totalTransactionPrice);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (connect.rs != null) {
	                    connect.rs.close();
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

	 private void getData() {
	        try {
	            String query = "SELECT * FROM transactionheader JOIN msuser ON transactionheader.UserID = msuser.UserID;";
	            connect.rs = connect.exeQuery(query);
	            while (connect.rs.next()) {
	                String transactionID = connect.rs.getString("TransactionID");
	                String email = connect.rs.getString("UserEmail");
	                Date date = connect.rs.getDate("TransactionDate");
	                tableDataTransaction.add(new TransactionHeader(transactionID, email, date));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (connect.rs != null) {
	                    connect.rs.close();
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	
	public History(Stage primaryStage) {
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
		
		Cart.setOnAction(e -> {
			new CartList(primaryStage);
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
	
	private void initComponents() {
		 scene = new Scene(bp,800,700);
		 primaryStage.setScene(scene);
		 primaryStage.setTitle("My History");
		 primaryStage.show();
		
	}

}
