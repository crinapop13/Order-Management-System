package ro.tuc.tp.BLL;

import ro.tuc.tp.BLL.Validators.PriceValidator;
import ro.tuc.tp.BLL.Validators.QuantityValidator;
import ro.tuc.tp.BLL.Validators.Validator;
import ro.tuc.tp.DAO.ProductDAO;
import ro.tuc.tp.Model.Product;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa ProductBLL
 * @author Pop Crina-Maria
 */


public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO prod;

    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new QuantityValidator());
        validators.add(new PriceValidator());

        prod = new ProductDAO();
    }
    public Product findProductById(int id) {
        Product p = prod.findById(id);
        if (p == null) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return p;
    }

    public List<Product> findAllProducts(){
        return prod.findAll();
    }

    public int insertProduct(Product p){
        try{
            for(Validator<Product> valid: validators) {
                valid.validate(p);
            }
        } catch(IllegalArgumentException e) {
            JOptionPane.showMessageDialog(new JFrame("JOptionPane"),e.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE);
            return 0;
        }
        return prod.insert(p);
    }

    public int deleteProduct(int id){

        if(prod.delete(id) == 1){
            return 0;
        }
        return 1;
    }

    public int updateProduct(Product p){

        if(prod.update(p) == 1) {
            return 0;
        }
        return 1;
    }

    public void updateQuantity(int id, int q) {
        prod.updateQuantity(id,q);
    }

    public JTable getTable(List<Product> products) {
        return prod.getTable(products);
    }
}
