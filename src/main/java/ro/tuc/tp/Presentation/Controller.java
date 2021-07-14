package ro.tuc.tp.Presentation;

import ro.tuc.tp.BLL.ClientBLL;
import ro.tuc.tp.BLL.OrderBLL;
import ro.tuc.tp.BLL.ProductBLL;
import ro.tuc.tp.Model.Client;
import ro.tuc.tp.Model.Orders;
import ro.tuc.tp.Model.Product;
import ro.tuc.tp.Presentation.View.ClientFrame;
import ro.tuc.tp.Presentation.View.OrderFrame;
import ro.tuc.tp.Presentation.View.ProductFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Controller {
    private ClientFrame c = new ClientFrame("Client");
    private ProductFrame p = new ProductFrame("Product");
    private OrderFrame o = new OrderFrame("Orders");
    ClientBLL clientBLL = new ClientBLL();
    ProductBLL productBLL = new ProductBLL();
    OrderBLL orderBLL = new OrderBLL();

    public Controller() {
        this.c.InsertAddListener(new InsertClientListener());
        this.c.UpdateAddListener(new UpdateClientListener());
        this.c.DeleteAddListener(new DeleteClientListener());
        this.c.AfisareAddListener(new AfisareClientListener());
        this.p.InsertAddListener(new InsertProductListener());
        this.p.UpdateAddListener(new UpdateProductListener());
        this.p.DeleteAddListener(new DeleteProductListener());
        this.p.AfisareAddListener(new AfisareProdusListener());
        this.o.InsertAddListener(new InsertOrderListener());
        this.o.AfisareAddListener(new AfisareComandaListener());
    }

    public void init(){
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        o.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.setSize(700,300);
        c.setLocationByPlatform(true);
        c.setVisible(true);
        p.setSize(700,300);
        p.setLocationByPlatform(true);
        p.setVisible(true);
        o.setSize(700,300);
        o.setLocationByPlatform(true);
        o.setVisible(true);
    }

    class InsertClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = c.getName();
            String address = c.getAddress();
            String number = c.getPhone();
            int newId = c.getId();
            if(clientBLL.insertClient(new Client(newId,name,address,number)) == 1){
                c.setText("1 rows inserted");
            } else {
                c.setText("0 rows inserted");
            }
        }
    }

    class InsertProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = p.getProductName();
            double price = p.getPrice();
            int q = p.getQuantity();
            int newId = p.getId();
            if(productBLL.insertProduct(new Product(newId,name,price,q)) == 1) {
                p.setText("1 rows inserted");
            } else {
                p.setText("0 rows inserted");
            }
        }
    }

    class InsertOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int idOrder = o.getOrderID();
            int idClient = o.getClientID();
            int idProduct = o.getProductID();
            int quantity = o.getQuantity();
            Product product = productBLL.findProductById(idProduct);
            Client client = clientBLL.findClientById(idClient);
            if(product.getQuantity() < quantity) {
                JOptionPane.showMessageDialog(new JFrame("JOptionPane"),"Stoc insuficient!","Message",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            double total = product.getPrice() * quantity;
            Date date = new Date(System.currentTimeMillis());
            Orders order = new Orders(idOrder,idClient,idProduct,quantity,total,date);
            if(orderBLL.insertOrder(order) == 0) {
                o.setText("1 rows inserted");
            } else {
                o.setText("0 rows inserted");
            }
            productBLL.updateQuantity(idProduct,product.getQuantity() - quantity);
            orderBLL.generateBill(client,product,order);
        }
    }

    class UpdateClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = c.getId();
            String name = c.getName();
            String address = c.getAddress();
            String number = c.getPhone();
            if(clientBLL.updateClient(new Client(id,name,address,number)) == 0){
                c.setText("1 rows updated");
            } else {
                c.setText("0 rows updated");
            }
        }
    }

    class UpdateProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = p.getId();
            String name = p.getProductName();
            double price = p.getPrice();
            int quantity = p.getQuantity();
            if(productBLL.updateProduct(new Product(id,name,price,quantity)) == 0){
                p.setText("1 rows updated");
            } else {
                p.setText("0 rows updated");
            }
        }
    }

    class DeleteClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = c.getId();
            if(clientBLL.deleteClient(id) == 0){
                c.setText("1 rows deleted");
            } else {
                c.setText("0 rows deleted");
            }
        }
    }

    class DeleteProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = p.getId();
            if(productBLL.deleteProduct(id) == 0){
                p.setText("1 rows deleted");
            } else {
                p.setText("0 rows deleted");
            }
        }
    }

    class AfisareClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            c.setTable();
        }
    }

    class AfisareProdusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            p.setTable();
        }
    }

    class AfisareComandaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            o.setTable();
        }
    }
}
