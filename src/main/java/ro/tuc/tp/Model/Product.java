package ro.tuc.tp.Model;

/**
 * Clasa Product reprezinta tablelul Product din baza de date
 * @author Pop Crina-Maria
 */

public class Product {
    private int idProduct;
    private String name;
    private double price;
    private int quantity;

    /**
     * Constructorul fara parametrii - instantiaza un produs cu valori implicite
     */

    public Product(){

    }

    /**
     * Constructorul cu parametrii - instantiaza un produs cu valorile primite ca si parametrii
     * @param idProd
     * id-ul produsului
     * @param name
     * numele produsului
     * @param price
     * pretul produsului
     * @param quantity
     * cantitatea din stoc
     */

    public Product(int idProd, String name, double price, int quantity) {
        this.idProduct = idProd;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getIdProduct(){
        return idProduct;
    }

    public void setIdProduct(int id){
        this.idProduct = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name= name;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double pret){
        this.price = pret;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int q){
        this.quantity = q;
    }

    public String toString(){
        return "Product [id = " + idProduct + "name = " + name + ", price = " + price
                + ", quantity = " + quantity + "]";
    }
}
