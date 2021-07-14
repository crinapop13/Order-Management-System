package ro.tuc.tp.DAO;

import ro.tuc.tp.Connection.ConnectionFactory;
import ro.tuc.tp.Model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Clasa ProductDAO mosteneste clasa AbstractDAO si specifica clasa care va inlocui parametrul T
 * @author Pop Crina-Maria
 */

public class ProductDAO extends AbstractDAO<Product>{
    public ProductDAO() {
        super();
    }

    /**
     * Metoda getPrice selecteaza din tabelul Product pretul unui produs
     * in functie de numele produsului dat ca parametru
     * @param name - numele produsului pentru care cautam pretul
     * @return price
     */

    public double getPrice(String name){
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT price FROM product WHERE name=?";
        double price = 0;
        try {
            con = ConnectionFactory.getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                price = resultSet.getDouble(1);
            }
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO: getPrice " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }
        return price;
    }

    /**
     * Metoda updateQuantity modifica stocul unui produs atunci cand se face o comanda
     * @param idProd - id-ul produsului pentru care modificam stocul
     * @param quantity - noul stoc
     * @return valoarea 1 daca s-a modificat cu succes stocul, 0 in caz contrar
     */

    public int updateQuantity(int idProd, int quantity){
        Connection con = null;
        PreparedStatement statement = null;
        int result = -1;
        String query = "UPDATE product SET quantity=? WHERE idProduct=?";
        try {
            con = ConnectionFactory.getConnection();
            statement = con.prepareStatement(query);
            statement.setInt(1, quantity);
            statement.setInt(2, idProd);
            result = statement.executeUpdate();
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO: updateQuantity " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }
        return result;
    }
}
