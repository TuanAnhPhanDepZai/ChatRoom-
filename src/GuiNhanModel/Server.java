/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiNhanModel;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import chatroom.ConnectToDataBase;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Server {
    
    public ServerSocket serverSocket;
    public Socket socketListen;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    public ConnectToDataBase connectToDataBase;
    public Message m;
    public ArrayList<String> list = new ArrayList<>();
    public String s [] = {"tuan anh","dang"} ;
    public void serve() {
        
        try {
            serverSocket = new ServerSocket(1997);
            System.out.println("da mo cong ket noi");
            socketListen = serverSocket.accept();
            System.out.println("da co client ket noi");
            oos = new ObjectOutputStream(socketListen.getOutputStream());
            ois = new ObjectInputStream(socketListen.getInputStream());
            System.out.println("da co stream");
            Message message;
            message = new Message(setArrayList());
            oos.writeObject("tuan anh phan");
            System.out.println("da gui doi tuong cho client");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Server() {
        connectToDataBase = new ConnectToDataBase();
        connectToDataBase.connnect();
    }
    
    public ArrayList<String> setArrayList() {
        for (int i = 0; i < 5; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }
    
    public static void main(String[] args) {
        Server s = new Server();
        s.serve();
    }
}
