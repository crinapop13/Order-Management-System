package ro.tuc.tp.DAO;

import ro.tuc.tp.Connection.ConnectionFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa AbstractDAO
 * @param <T> - reprezinta tipul generic pentru clasele din pachetul Model
 * @author Pop Crina-Maria
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructorul fara parametrii - creeaza o noua instanta a clasei AbstractDAO
     * setand valoarea variabilei instanta type ca fiind tipul dat ca parametru in antetul clasei
     */

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Metoda findAll executa o interogare asupra bazei de date
     * pentru a afla toate randurile unui tabel
     * @return o lista de obiecte de tipul T
     */
    public List<T> findAll() {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        try {
            con = ConnectionFactory.getConnection();
            statement = con.prepareStatement(sb.toString());
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }
        return null;
    }

    /**
     * Metoda createSelectQuery creeaza string-ul pentru interogarea de tip SELECT
     * @param field - specifica coloana dupa care se face cautarea
     * @return un String care contine interogarea
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Metoda createDeleteQuery creeaza string-ul pentru clauza DELETE
     * @param field - specifica coloana dupa care selectam randul pe care vrem sa il stergem
     * @return un String care contine interogarea
     */

    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Metoda createObjects obtine prin tehnica de reflectie o lista de obiecte dintr-un ResultSet
     * @param resultSet - reprezinta rezultatele obtinute in urma unei interogari
     * @return lista de obiecte de tipul T
     */

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for(Field field : type.getDeclaredFields()){
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName,type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Metoda findById gaseste intr-un tabelprintr-o interogare de tip SELECT o inregistrare in functie de id
     * @param id - cautarea in tabel se face dupa id
     * @return un obiect de tipul T daca acesta a fost gasit, sau null in caz contrar
     */

    public T findById(int id) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String idPK = type.getSimpleName();
        String query = createSelectQuery("id" + idPK);
        try {
            con = ConnectionFactory.getConnection();
            statement = con.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return null;
    }

    /**
     * Metoda insert insereaza un rand nou intr-un tabel
     * @param t obiectul pe care vrem sa il inseram
     * @return valoarea 1 daca s-a inserat obiectul cu succes, 0 in caz contrar
     */

    public int insert(T t) {
        Connection con = null;
        PreparedStatement statement = null;
        int result = -1;
        String insertString = ("INSERT INTO " + type.getSimpleName() + " VALUES (");
        int leng = type.getDeclaredFields().length;
        for(int i = 0; i < leng; i++ ) {
            if(i != leng - 1) {
                insertString += "?,";
            } else {
                insertString += "?)";
            }
        }
        try {
            con = ConnectionFactory.getConnection();
            statement = con.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            for(Field field : t.getClass().getDeclaredFields()) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(),type);
                Method method = propertyDescriptor.getReadMethod();
                statement.setObject(i,method.invoke(t));
                i++;
            }
            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return result;
    }

    /**
     * Metoda update modifica datele unei inregistrari din tabel pe care o gasim dupa id-ul obiectului dat ca parametru
     * @param t obiectul pentru care se modifica campurile
     * @return valoarea 1 daca s-a modificat obiectul cu succes, 0 in caz contrar
     */

    public int update(T t) {
        Connection con = null;
        PreparedStatement statement = null;
        int result = -1;
        String updateString = "UPDATE " + type.getSimpleName() + " SET ";

        for(Field field : t.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            if(fieldName.contains("id")) {
                continue;
            }
            updateString = updateString + fieldName + "=?,";
        }
        updateString = updateString.substring(0,updateString.length()-1);  //eliminam ultima virgula
        updateString += " WHERE id" + type.getSimpleName() + "=?";
        try {
            con = ConnectionFactory.getConnection();
            statement = con.prepareStatement(updateString);
            String fieldName = "";
            String idPK = "id" + type.getSimpleName();
            PropertyDescriptor propertyDescriptor = null;
            Method method = null;
            int i = 1;
            for(Field field : t.getClass().getDeclaredFields()) {
                fieldName = field.getName();
                if(fieldName.compareTo(idPK) == 0) {
                    continue;
                }
                propertyDescriptor = new PropertyDescriptor(fieldName,type);
                method = propertyDescriptor.getReadMethod();
                statement.setObject(i,method.invoke(t));
                i++;
            }
            propertyDescriptor = new PropertyDescriptor(idPK,type);
            method = propertyDescriptor.getReadMethod();
            statement.setObject(i,method.invoke(t));
            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }
        return result;
    }

    /**
     * Metoda delete sterge o anumita inregistrare dintr-un tabel in functie de id
     * @param id - specifica id-ul inregistrarii pe care vrem sa o stergem
     * @return valoarea 1 daca s-a modificat obiectul cu succes, 0 in caz contrar
     */
    public int delete(int id) {
        Connection con = null;
        PreparedStatement statement = null;
        int result = -1;
        String idQuery = "id" + type.getSimpleName();
        String deleteString = createDeleteQuery(idQuery);
        try {
            con = ConnectionFactory.getConnection();
            statement = con.prepareStatement(deleteString);
            statement.setInt(1, id);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }
        return result;
    }

    /**
     * Metoda getTable obtine cu ajutorul tehnicii de reflectie capul unui tabel, iar apoi
     * obtine datele acestui tabel din lista de obiecte data ca parametru
     * @param list - contine datele din tabelul de tip T
     * @return tabelul populat cu date
     */

    public JTable getTable(List<T> list){
        JTable table;
        String[] column = new String[type.getDeclaredFields().length];
        String[][] values = new String[100][type.getDeclaredFields().length];
        try {
            int cnt = 0;
            for(Field field: type.getDeclaredFields()) {
                column[cnt] = field.getName();
                cnt += 1;
            }
            for(int i = 0; i < list.size(); i++) {
                cnt = 0;
                for(Field field: type.getDeclaredFields()) {
                    field.setAccessible(true);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method m = propertyDescriptor.getReadMethod();
                    try {
                        values[i][cnt] = m.invoke(list.get(i)).toString();
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    cnt += 1;
                }
            }
        } catch (IntrospectionException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:getTable " + e.getMessage());
        }
        TableModel t = new DefaultTableModel(values,column);
        table = new JTable(t);
        table.setPreferredScrollableViewportSize(new Dimension(400,100));
        table.setRowSelectionAllowed(true);
        table.setFillsViewportHeight(true);
        return table;
    }
}