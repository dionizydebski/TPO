package src.zad1.GUI;

import src.zad1.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdminGUI extends JFrame {
    private Admin admin;
    private JTextArea messageArea;
    private JTextField topicField;
    private JTextField messageField;

    public AdminGUI() {
        super("Admin");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        try {
            admin = new Admin();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        messageArea = new JTextArea(10, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        panel.add(scrollPane);


        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Temat:"));
        topicField = new JTextField(10);
        inputPanel.add(topicField);

        inputPanel.add(new JLabel("Wiadomość:"));
        messageField = new JTextField(20);
        inputPanel.add(messageField);

        JButton addButton = new JButton("Dodaj");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String topic = topicField.getText();
                try {
                    admin.addTopic(topic);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                }
            }
        });
        inputPanel.add(addButton);

        JButton removeButton = new JButton("Usuń");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String topic = topicField.getText();
                try {
                    admin.removeTopic(topic);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                }
            }
        });
        inputPanel.add(removeButton);

        JButton updateButton = new JButton("Wyślij");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String topic = topicField.getText();
                String news = messageField.getText();
                try {
                    admin.updateTopic(topic,news);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                }
            }
        });
        inputPanel.add(updateButton);

        panel.add(inputPanel);
        add(panel);
        setVisible(true);

        try {
            admin.receiveMessages(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayMessage(String message) {
        messageArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdminGUI();
            }
        });
    }
}
