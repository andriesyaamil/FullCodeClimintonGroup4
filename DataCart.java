package main;

public class DataCart {
    private String name;
    private String brand;
    private int price;
    private int quantity;
    private String id;

    public DataCart(String name, String brand, int price, int quantity, String id) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return price * quantity;
    }
    
    public int getPrize() {
            return price; 
    }
    

    
}
