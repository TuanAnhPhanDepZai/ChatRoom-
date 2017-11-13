package chatroom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phant
 */
public class ServerThread extends Thread {

    public Socket socket;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    public ServerFrame serverFrame;
    public ConnectToDataBase connectToDataBase; // connect To Database to get Data;
    public boolean boo = true;
   

    public ServerThread(Socket socket, ServerFrame serverFrame, ConnectToDataBase connectToDataBase) {
        this.connectToDataBase = connectToDataBase;
        connectToDataBase.connnect();
        this.socket = socket;
        this.serverFrame = serverFrame;

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String username = null;
        while (true) {
            try {
                Message message = (Message) ois.readObject();
                username = message.getUsername();
                if (message.getOption() == 1) {
                    boolean check = connectToDataBase.checkUsername(message);  // check username vs password dang nhap
                    if (check == true) {
                        oos.writeObject(message);
                        // gui ve cho cac client ve client moi dang nhap vao ha thong
                        serverFrame.clientName.add(username);
                        sendClientName();
                        serverFrame.getTextShow().append(username + ":is online \n");
                    } else {
                        Message message1 = new Message(message.getUsername(), message.getPassword(), 2);
                        oos.writeObject(message1);
                    }
                } else if (message.getOption() == 5) {
                    // getServerFrame().getTextShow().append(username+":"+message.getPassword()+"\n");
                    Message m = new Message(message.getUsername(), message.getPassword(), 5);
                    serverFrame.sendMessageToAllClient(m);
                } else if (message.getOption() == 3) // thong bao dang ki tai khoan nguoi dung 
                {
                    boolean boo = connectToDataBase.singUpUser(message);
                    if (boo == true) // dang ki thanh cong
                    {
                        oos.writeObject(message);
                    } else {  // dang ki khong thanh cong 
                        oos.writeObject(new Message("", "", 4));  // option 4 gui di de xac nhan dang ki khong thanh cong
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public ServerFrame getServerFrame() {
        return serverFrame;
    }

    public void setServerFrame(ServerFrame serverFrame) {
        this.serverFrame = serverFrame;
    }

    public void sendMessageToClient(Message message) {
        try {
            oos.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showMessage(Message message) {
        System.out.println(message.getUsername() + " " + message.getPassword() + " " + message.getOption());
    }

    public boolean checkUser(Message message) {
        return connectToDataBase.checkUsername(message);
    }

    public boolean singUpUser(Message message) {
        return connectToDataBase.singUpUser(message);
    }

    public static void main(String[] args) {
        Message m = new Message("tuananh", "27121997", 1);
        ServerThread s = new ServerThread(null, new ServerFrame(new ConnectToDataBase()), new ConnectToDataBase());
        System.out.println(s.checkUser(m));
    }
    
    public void sendClientName(){
        for(int i = 0 ; i< serverFrame.clientName.size();i++){
            Message m = new Message(serverFrame.clientName.get(i),"", 6);
            serverFrame.sendMessageToAllClient(m);
        }
    }
}
