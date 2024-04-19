package src.zad1;

import src.zad1.ProxyServerModules.ProxyServer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Ui extends JFrame {
    public Ui() {
        super("Translator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 360);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel inputPanel = new JPanel(new FlowLayout());

        JTextField textField = new JTextField(25);

        JButton button = new JButton("Translate");

        button.addActionListener(_ -> {
            String clientInput = textField.getText();

            try {
                Socket socketOut = new Socket(ProxyServer.getHost(),ProxyServer.handlerPort);

                PrintWriter printWriter = new PrintWriter(socketOut.getOutputStream(),true);
                printWriter.println(clientInput);

                socketOut.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }


            try {
                ServerSocket socketIn = new ServerSocket(Integer.parseInt(clientInput.split(",")[2]));
                Socket languageSocket = socketIn.accept();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(languageSocket.getInputStream()));
                String response = bufferedReader.readLine();

                JOptionPane.showMessageDialog(null, response);
                socketIn.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        });

        inputPanel.add(new JLabel("Client input: "));
        inputPanel.add(textField);

        add(inputPanel);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(button, gbc);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Ui::new);
    }
}
