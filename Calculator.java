import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Calculator implements ActionListener{

    BigDecimal inputAmount;
    BigDecimal resultAmount;
    String resultCurrency;

    JTextField inputAmountField;
    JComboBox<String> resultCurrencyField;
    JTextField resultAmountField;

    NodeList list;

    public Calculator(NodeList list){
        this.list = list;
        
        JFrame frame = new JFrame();
        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(2, 0, 5, 5));

        JPanel topRow = new JPanel();
        topRow.setLayout(new FlowLayout());
        panel.add(topRow);

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new FlowLayout());
        panel.add(bottomRow);

        JLabel label1 = new JLabel("Convert EUR to ", SwingConstants.RIGHT);
        resultCurrencyField = new JComboBox<String>();

        fillCurrencyOptions();
        
        JButton calculateButton = new JButton("Calculate");
        inputAmountField = new JTextField("100", 15);
        JLabel label2 = new JLabel(" EUR is ", SwingConstants.CENTER);
        resultAmountField = new JTextField("", 15);
        resultAmountField.setEditable(false);
        calculateButton.addActionListener(this);

        topRow.add(label1);
        topRow.add(resultCurrencyField);

        bottomRow.add(inputAmountField);
        bottomRow.add(label2);
        bottomRow.add(resultAmountField);
        bottomRow.add(calculateButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Currency Calculator");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        resultCurrency = (String) resultCurrencyField.getSelectedItem();
        try {
            String inputText = inputAmountField.getText();
            if (inputText.isEmpty()) {
                throw new NumberFormatException("EUR amount cannot be empty.");
            } else if (inputText.charAt(inputText.length() - 1) == 'e') {
                throw new NumberFormatException("e should be followed with a number.");   
            }
            inputAmount = new BigDecimal(inputAmountField.getText());
        } catch (NumberFormatException q) {
            Parser.popUpMessage(q.getMessage());
            return;
        }

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;
                String cur = e.getAttribute("currency");
                if (cur.equals(resultCurrency)) {
                    try {
                        String rateString = e.getAttribute("rate");
                        if (rateString.isEmpty()) {
                            throw new NumberFormatException("");
                        }
                        BigDecimal rate = new BigDecimal(rateString);
                        resultAmount = inputAmount.multiply(rate);
                    } catch (Exception q) {
                        Parser.popUpMessage("Content of file is invalid.");
                        return;
                    }
                }
            }
        }
        resultAmountField.setText(resultAmount.toString() + " " + resultCurrency);        
    }

    private void fillCurrencyOptions() {
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;
                String cur = e.getAttribute("currency");
                if (!cur.isEmpty()) {
                    resultCurrencyField.addItem(cur);
                }
            }
        }
    }
}