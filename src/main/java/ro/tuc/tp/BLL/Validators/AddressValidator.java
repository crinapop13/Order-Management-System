package ro.tuc.tp.BLL.Validators;

import ro.tuc.tp.Model.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clasa AddressValidator valideaza adresa unui client
 * @author Pop Crina-Maria
 */

public class AddressValidator implements Validator<Client>{
    private static final String ADDRESS_PATTERN = "^([a-zA-z.0-9/\\\\''(),\\s]{2,255})$";

    public void validate(Client c){
        Pattern pattern = Pattern.compile(ADDRESS_PATTERN);
        Matcher m = pattern.matcher(c.getAddress());
        if(!m.matches()) {
            throw new IllegalArgumentException("Invalid address");
        }
    }
}
