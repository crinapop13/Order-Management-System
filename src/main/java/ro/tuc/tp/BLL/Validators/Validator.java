package ro.tuc.tp.BLL.Validators;

/**
 * Interfata Validator defineste antetul metodei validate
 * @param <T> - reprezinta tipul pentru care se definesc validatori (Client, Product, Orders)
 * @author Pop Crina-Maria
 */

public interface Validator<T> {
    public void validate(T t);
}
