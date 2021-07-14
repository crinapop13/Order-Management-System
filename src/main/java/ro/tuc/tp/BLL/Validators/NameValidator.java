package ro.tuc.tp.BLL.Validators;

import ro.tuc.tp.Model.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clasa NameValidator valideaza numele unui client
 * @author Pop Crina-Maria
 */

public class NameValidator implements Validator<Client>{
    private static final String NAME_PATTERN = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
    public void validate(Client c){
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher m = pattern.matcher(c.getName());
        if(!m.matches()) {
            throw new IllegalArgumentException("Invalid name");
        }
    }
}

