package ro.tuc.tp.Presentation.View;

import ro.tuc.tp.BLL.OrderBLL;
import ro.tuc.tp.Presentation.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrderFrame extends JFrame{
    OrderBLL o = new OrderBLL();
    private JPanel content = new JPanel();
    private JPanel flow = new JPanel();
    private JPanel pane = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    public JTextField jos = new JTextField(10);
    private JTable table = o.getTable(o.findAllOrders());
    private JButton insert = new JButton("INSERT");
    private JButton afis = new JButton("Afisare");
    JLabel labelOrderID = new JLabel("ID Comanda:");
    JTextField textOrderID = new JTextField(20);
    JLabel labelClientID = new JLabel("ID Client:");
    JTextField textClientID = new JTextField(20);
    JLabel labelProductID = new JLabel("ID Produs:");
    JTextField textProducID = new JTextField(20);
    JLabel labelQuantity = new JLabel("Quantity:");
    JTextField textQuantity = new JTextField(20);

    public OrderFrame(String name) {
        super(name);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        flow.add(insert);
        flow.add(afis);
        flow.add(jos);

        c.gridx = 0;
        c.gridy = 0;
        pane.add(labelOrderID,c);

        c.gridx = 1;
        c.gridy = 0;
        pane.add(textOrderID,c);

        c.gridx = 0;
        c.gridy = 1;
        pane.add(labelClientID,c);

        c.gridx = 1;
        c.gridy = 1;
        pane.add(textClientID,c);

        c.gridx = 0;
        c.gridy = 2;
        pane.add(labelProductID,c);

        c.gridx = 1;
        c.gridy = 2;
        pane.add(textProducID,c);

        c.gridx = 0;
        c.gridy = 3;
        pane.add(labelQuantity,c);

        c.gridx = 1;
        c.gridy = 3;
        pane.add(textQuantity,c);


        content.add(pane);
        content.add(flow);
        JScrollPane scrollPane = new JScrollPane(table);
        content.add(scrollPane);
        jos.setEditable(false);

        this.add(content);
    }

    public int getOrderID() {
        return Integer.parseInt(textOrderID.getText());
    }

    public int getClientID(){
        return Integer.parseInt(textClientID.getText());
    }

    public int getProductID(){
        return Integer.parseInt(textProducID.getText());
    }

    public int getQuantity(){
        return Integer.parseInt(textQuantity.getText());
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
        table = o.getTable(o.findAllOrders());
        JScrollPane scrollPane = new JScrollPane(table);
        content.add(scrollPane);
        this.validate();
        this.repaint();
    }
    public void InsertAddListener(ActionListener a) {
        insert.addActionListener(a);
    }

    public void AfisareAddListener(ActionListener a) {
        afis.addActionListener(a);
    }
}
