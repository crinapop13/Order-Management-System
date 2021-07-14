package ro.tuc.tp.Presentation.View;

import ro.tuc.tp.BLL.ClientBLL;
import ro.tuc.tp.Presentation.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientFrame extends JFrame{
    ClientBLL client = new ClientBLL();
    private JPanel content = new JPanel();
    private JPanel flow = new JPanel();
    private JPanel pane = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    public JTextField jos = new JTextField(10);
    private JTable table = client.getTable(client.findAllClients());
    private JButton insert = new JButton("INSERT");
    private JButton up = new JButton("UPDATE");
    private JButton del = new JButton("DELETE");
    private JButton afis = new JButton("Afisare");
    JLabel labelClientID = new JLabel("ID:");
    JTextField textClientID = new JTextField(20);
    JLabel labelClientName = new JLabel("Name:");
    JTextField textClientName = new JTextField(20);
    JLabel labelClientAdress = new JLabel("Adress:");
    JTextField textClientAdress = new JTextField(20);
    JLabel labelPhone = new JLabel("Phone number:");
    JTextField textPhone = new JTextField(20);

    public ClientFrame(String name) {
        super(name);
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

        jos.setEditable(false);
        flow.add(insert);
        flow.add(up);
        flow.add(del);
        flow.add(afis);
        flow.add(jos);

        c.gridx = 0;
        c.gridy = 0;
        pane.add(labelClientID,c);

        c.gridx = 1;
        c.gridy = 0;
        pane.add(textClientID,c);

        c.gridx = 0;
        c.gridy = 1;
        pane.add(labelClientName,c);

        c.gridx = 1;
        c.gridy = 1;
        pane.add(textClientName,c);

        c.gridx = 0;
        c.gridy = 2;
        pane.add(labelClientAdress,c);

        c.gridx = 1;
        c.gridy = 2;
        pane.add(textClientAdress,c);

        c.gridx = 0;
        c.gridy = 3;
        pane.add(labelPhone,c);

        c.gridx = 1;
        c.gridy = 3;
        pane.add(textPhone,c);

        content.add(pane);
        content.add(flow);

        JScrollPane scrollPane = new JScrollPane(table);
        content.add(scrollPane);
        this.add(content);
    }

    public int getId(){
        return Integer.parseInt(textClientID.getText());
    }

    public String getName() {
        return textClientName.getText();
    }

    public String getAddress() {
        return textClientAdress.getText();
    }

    public String getPhone() {
        return textPhone.getText();
    }

    public void setText(String s){
        jos.setText(s);
    }

    public JTable getTable(){
        return table;
    }

    public void setTable(){
        int numberOfComponents = content.getComponents().length - 1;
        content.remove(content.getComponent(numberOfComponents));
        table = client.getTable(client.findAllClients());
        JScrollPane scrollPane = new JScrollPane(table);
        content.add(scrollPane);
        this.validate();
        this.repaint();
    }
    public void InsertAddListener(ActionListener a) {
        insert.addActionListener(a);
    }

    public void UpdateAddListener(ActionListener a) {
        up.addActionListener(a);
    }

    public void DeleteAddListener(ActionListener a) {
        del.addActionListener(a);
    }
    public void AfisareAddListener(ActionListener a) {
        afis.addActionListener(a);
    }
}
