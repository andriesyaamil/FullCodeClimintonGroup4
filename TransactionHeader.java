package main;

import java.sql.Date; 

public class TransactionHeader {

	String TransactionID;
	String Email;
	Date Date;
	
	public String getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	
	public TransactionHeader(String transactionID, String email, Date date) {
		super();
		TransactionID = transactionID;
		Email = email;
		Date = date;
	}



	
	
	
}
