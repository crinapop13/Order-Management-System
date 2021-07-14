package ro.tuc.tp.BLL.Validators;

import ro.tuc.tp.Model.Product;

/**
 * Clasa QuantityValidator valideaza stocul unui produs
 * @author Pop Crina-Maria
 */

public class QuantityValidator implements Validator<Product>{

    public void validate(Product p) {
        if (p.getQuantity() < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }
    }
}
