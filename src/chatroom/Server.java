/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    public DataOutputStream os;
    public DataInputStream is;
    public ArrayList<ServerThread> client = new ArrayList<>();
    public ServerThread clientThread;

    public void serve() {
        try {
            ServerSocket serverSocket = new ServerSocket(2000);
            System.out.println("da mo cong ket noi");

            Socket socket = serverSocket.accept();
            System.out.println("co client ket noi toi server");
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("da co stream du lieu ");
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.serve();
    }
}
