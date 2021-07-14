package ro.tuc.tp.Model;

/**
 * Clasa Client reprezinta tablelul Client din baza de date
 * @author Pop Crina-Maria
 */

public class Client {
    private int idClient;
    private String name;
    private String address;
    private String phoneNumber;

    /**
     * Constructorul fara parametrii - instantiaza un client cu valori implicite
     */

    public Client(){

    }

    /**
     * Constructorul cu parametrii - instantiaza un client cu valorile primite ca si parametrii
     * @param id
     * id-ul clientului
     * @param name
     * numele clientului
     * @param address
     * adresa clientului
     * @param number
     * numarul de telefon al clientului
     */

    public Client(int id, String name, String address, String number) {
        this.idClient = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = number;
    }

    public int getIdClient(){
        return idClient;
    }

    public void setIdClient(int id) {
        this.idClient = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String adr) {
        this.address = adr;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String number) {
        this.phoneNumber = number;
    }
    public String toString(){
        return "Client [id = " + idClient + "name = " + name
                + ", address = " + address + "]";
    }
}
