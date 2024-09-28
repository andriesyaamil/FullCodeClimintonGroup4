package main;

public class TransactionDetail {
	
	String TransactionID,namaproduct;
	int Price = 0;
	int Quantity =0;
	public String getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}
	public String getNamaproduct() {
		return namaproduct;
	}
	public void setNamaproduct(String namaproduct) {
		this.namaproduct = namaproduct;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	
    public int getTotal() {
        return Price * Quantity;
    }
    
    public int getPrize() {
            return Price; 
    }
    
    
	public TransactionDetail(String transactionID, String namaproduct, int price, int quantity) {
		super();
		TransactionID = transactionID;
		this.namaproduct = namaproduct;
		Price = price;
		Quantity = quantity;
	}
	

	
}
