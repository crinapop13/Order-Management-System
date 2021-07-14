package ro.tuc.tp.BLL;

import ro.tuc.tp.BLL.Validators.AddressValidator;
import ro.tuc.tp.BLL.Validators.NameValidator;
import ro.tuc.tp.BLL.Validators.Validator;
import ro.tuc.tp.DAO.ClientDAO;
import ro.tuc.tp.Model.Client;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa ClientBLL
 * @author Pop Crina-Maria
 */

public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO client;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new AddressValidator());
        validators.add(new NameValidator());

        client = new ClientDAO();
    }

    public Client findClientById(int id) {
        Client c = client.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return c;
    }

    public int insertClient(Client c){
        try{
            for(Validator<Client> valid: validators) {
                valid.validate(c);
            }
        } catch(IllegalArgumentException e) {
             JOptionPane.showMessageDialog(new JFrame("JOptionPane"),e.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE);
             return 0;
        }
        return client.insert(c);
    }

    public int deleteClient(int id){
        if(client.delete(id) == 1) {
            return 0;
        }
        return 1;
    }

    public int updateClient(Client c){
        if(client.update(c) == 1) {
            return 0;
        }
        return 1;
    }

    public List<Client> findAllClients(){
        return client.findAll();
    }
    public JTable getTable(List<Client> clienti){
        return client.getTable(clienti);
    }
}
