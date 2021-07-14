package ro.tuc.tp.BLL.Validators;

import ro.tuc.tp.Model.Product;

/**
 * Clasa PriceValidator valideaza pretul unui produs
 * @author Pop Crina-Maria
 */

public class PriceValidator implements Validator<Product>{
    public void validate(Product p) {
        if (p.getPrice() < 0) {
            throw new IllegalArgumentException("Invalid price");
        }
    }
}
