package main;


public class Racket {

	String Name,Brand;
	int Stock = 0;
	int Price = 0;
	String id ;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		Brand = brand;
	}
	public int getStock() {
		return Stock;
	}
	public void setStock(int stock) {
		Stock = stock;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Racket(String name, String brand, int stock, int price, String id) {
		super();
		Name = name;
		Brand = brand;
		Stock = stock;
		Price = price;
		this.id = id;
	}


	

}
