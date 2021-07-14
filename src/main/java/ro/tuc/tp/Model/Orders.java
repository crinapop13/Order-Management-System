package ro.tuc.tp.Model;

import java.util.Date;

/**
 * Clasa Orders reprezinta tablelul Orders din baza de date
 * @author Pop Crina-Maria
 */

public class Orders {
    private int idOrder;
    private int idClient;
    private int idProduct;
    private int quantity;
    private double total;
    private Date date;

    /**
     * Constructorul fara parametrii - instantiaza o comanda cu valori implicite
     */

    public Orders(){

    }

    /**
     * Constructorul cu parametrii - instantiaza o comanda cu valorile primite ca si parametrii
     * @param idOrder
     * id-ul comenzii
     * @param idClient
     * id-ul clientului care face comanda
     * @param idProd
     * id-ul produsului comandat
     * @param quantity
     * cantitatea dorita
     * @param total
     * suma totala
     * @param date
     * data in care s-a facut comanda
     */
    public Orders(int idOrder, int idClient, int idProd, int quantity, double total, Date date) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idProduct = idProd;
        this.quantity = quantity;
        this.total = total;
        date = date;
    }

    public int getIdOrder(){
        return idOrder;
    }

    public void setIdOrder(int id){
        this.idOrder = id;
    }

    public int getIdClient(){
        return idClient;
    }

    public void setIdClient(int id){
        this.idClient = id;
    }

    public int getIdProduct(){
        return idProduct;
    }

    public void setIdProduct(int id){
        this.idProduct = id;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int q){
        this.quantity = q;
    }

    public double getTotal(){
        return total;
    }

    public void setTotal(double tot){
        this.total = tot;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date d) {
        this.date = d;
    }
    public String toString(){
        return "Order [idOrder = " + idOrder + "idClient = " + idClient + ", idProduct = " + idProduct
                + ", quantity = " + quantity + ", total = " + total + "]";
    }
}
