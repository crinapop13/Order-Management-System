package ro.tuc.tp.BLL;

import ro.tuc.tp.DAO.OrderDAO;
import ro.tuc.tp.Model.Client;
import ro.tuc.tp.Model.Orders;
import ro.tuc.tp.Model.Product;
import javax.swing.*;
import java.io.FileWriter;
import java.util.List;
import java.util.Random;

/**
 * Clasa OrdertBLL
 * @author Pop Crina-Maria
 */


public class OrderBLL {
    private OrderDAO order = new OrderDAO();
    private static FileWriter file;

    public int insertOrder(Orders o){
        if(order.insert(o) == 1) {
            return 0;
        }
        return 1;
    }

    public List<Orders> findAllOrders(){
        return order.findAll();
    }

    public void generateBill(Client c, Product p , Orders o) {
        try {
            file = new FileWriter("bill.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Random rand = new Random();
        int nr = rand.nextInt(1000);
        try {
            file.write("Invoice no. " + nr);
            file.write("\r\nDate " + o.getDate() + "\r\n");
            file.write("\r\nBill to: " + c.getName());
            file.write("\r\nAddress: " + c.getAddress());
            file.write("\r\nPhone: " + c.getPhoneNumber());
            file.write("\r\nDescription: ");
            file.write("\r\nName: " + p.getName() + " Quantity: " + o.getQuantity() + " Price: " + p.getPrice() + " Total: " + o.getTotal() + " lei");
            file.flush();
            file.close();
        } catch(Exception e){}

    }
    public JTable getTable(List<Orders> orders) {
        return order.getTable(orders);
    }
}
