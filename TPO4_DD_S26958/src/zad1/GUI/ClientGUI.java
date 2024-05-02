package src.zad1.GUI;

import src.zad1.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientGUI extends JFrame {
    private Client client;
    private JTextArea messageArea;
    private JTextField topicField;

    public ClientGUI() {
        super("Client");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        messageArea = new JTextArea(10, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        panel.add(scrollPane);

        try {
            client = new Client();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Temat:"));
        topicField = new JTextField(10);
        inputPanel.add(topicField);

        JButton subscribeButton = new JButton("Subskrybuj");
        subscribeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String topic = topicField.getText();
                try {
                    client.subscribe(topic);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                }
            }
        });
        inputPanel.add(subscribeButton);

        JButton unsubscribeButton = new JButton("Rezygnuj");
        unsubscribeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String topic = topicField.getText();
                try {
                    client.unsubscribe(topic);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage());
                }
            }
        });
        inputPanel.add(unsubscribeButton);

        panel.add(inputPanel);
        add(panel);
        setVisible(true);

        try {
            client.receiveMessages(this);
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
                new ClientGUI();
            }
        });
    }
}
