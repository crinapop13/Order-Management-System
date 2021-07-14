package ro.tuc.tp.Presentation.View;

import ro.tuc.tp.BLL.ProductBLL;
import ro.tuc.tp.Presentation.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductFrame extends JFrame{
    ProductBLL p = new ProductBLL();
    private JPanel content = new JPanel();
    private JPanel flow = new JPanel();
    private JPanel pane = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    public JTextField jos = new JTextField(10);
    private JTable table = p.getTable(p.findAllProducts());
    private JButton insert = new JButton("INSERT");
    private JButton up = new JButton("UPDATE");
    private JButton del = new JButton("DELETE");
    private JButton afis = new JButton("Afisare");
    JLabel labelProductID = new JLabel("ID produs:");
    JTextField textProductID = new JTextField(20);
    JLabel labelProductName = new JLabel("Nume produs:");
    JTextField textProductName = new JTextField(20);
    JLabel labelPrice = new JLabel("Pret:");
    JTextField textPrice = new JTextField(20);
    JLabel labelQuantity = new JLabel("Quantity:");
    JTextField textQuantity = new JTextField(20);

    public ProductFrame(String name) {
        super(name);

        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        jos.setEditable(false);

        jos.setEditable(false);
        flow.add(insert);
        flow.add(up);
        flow.add(del);
        flow.add(afis);
        flow.add(jos);

        c.gridx = 0;
        c.gridy = 0;
        pane.add(labelProductID,c);

        c.gridx = 1;
        c.gridy = 0;
        pane.add(textProductID,c);

        c.gridx = 0;
        c.gridy = 1;
        pane.add(labelProductName,c);

        c.gridx = 1;
        c.gridy = 1;
        pane.add(textProductName,c);

        c.gridx = 0;
        c.gridy = 2;
        pane.add(labelPrice,c);

        c.gridx = 1;
        c.gridy = 2;
        pane.add(textPrice,c);

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
        this.add(content);

        this.add(content);
    }

    public int getId(){
        return Integer.parseInt(textProductID.getText());
    }
    public String getProductName(){
        return textProductName.getText();
    }

    public double getPrice(){
        return Double.parseDouble(textPrice.getText());
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
        table = p.getTable(p.findAllProducts());
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
