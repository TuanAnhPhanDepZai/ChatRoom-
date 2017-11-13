package chatroom;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ServerFrame extends JFrame {

    private JLabel label = new JLabel("server");
    private JButton btnSend = new JButton("SEND");
    public JTextArea textShow;
    private JTextField textMessage = new JTextField();
    public ServerSocket serverSocket;
//    public ObjectOutputStream objectOutputStream;
//    public ObjectInputStream objectInputStream;
    public Socket socket;
    private JPanel panel;
    public final int MAX_CLIENT = 10;
    public List<ServerThread> serverArray = new ArrayList<>();
    public ServerThread serverThread;
    public ConnectToDataBase connectToDataBase;
    public ArrayList<String> clientName = new ArrayList<>();

    public ServerFrame(ConnectToDataBase connectToDataBase) {
        this.connectToDataBase = connectToDataBase;
        this.setTitle("Server");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500, 400);
        this.setLocation(300, 100);
        this.setResizable(false);
        addItem();
    }

    public void openServer() {

        try {
            serverSocket = new ServerSocket(2017);
            System.out.println("may chu san sang");
            while (true) {
                socket = serverSocket.accept();
                serverThread = new ServerThread(socket, this, this.connectToDataBase);
                serverArray.add(serverThread);

            }
        } catch (IOException ex) {
            System.out.println("khong the mo duoc server");
        }

    }

    public void addItem() {

        setLayout(null);

        btnSend.setSize(90, 50);
        btnSend.setLocation(400, 320);
        add(btnSend);

        textMessage.setSize(380, 50);
        textMessage.setLocation(10, 320);
        add(textMessage);

        textShow = new JTextArea();
        textShow.setLineWrap(true);
        textShow.setWrapStyleWord(true);
        textShow.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane scroll = new JScrollPane(textShow);
        scroll.setLocation(10, 30);
        scroll.setSize(400, 280);
        scroll.setBorder(new LineBorder(Color.black));
        add(scroll);
//     
//        textShow.setSize(380, 280);
//        textShow.setBorder(new LineBorder(Color.black));
//        textShow.setLocation(10,30);
//        add(textShow);
    }

    public static void main(String[] args) {
        ServerFrame s = new ServerFrame(new ConnectToDataBase());
        s.openServer();
    }

    public JTextArea getTextShow() {
        return textShow;
    }

    public void sendMessageToAllClient(Message message) {
        for (int i = 0; i < serverArray.size(); i++) {
            serverThread = serverArray.get(i);
            if (!(serverThread == null)) {
                serverThread.sendMessageToClient(message);
            }
        }
    }

}
