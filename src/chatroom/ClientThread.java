/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phant
 */
public class ClientThread extends Thread {

    public Socket socket;
    public ClientFrame clientFrame;
    public ObjectInputStream ois;
    public boolean run;

    public ClientThread(ObjectInputStream ois, ClientFrame clientFrame) {
        run = true;
        this.clientFrame = clientFrame;
        this.ois = ois;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    Message message = (Message) ois.readObject();
                    if (message.getOption() == 5) {
                        System.out.println("da nhan duoc tinh nhan tu server");
                        clientFrame.getTextShow().append(message.getUsername() + message.getPassword() + "\n");
                    } else if (message.getOption() == 6) { // hien thi thong tin khi co client moi vao
                        System.out.println("da nhan duoc ten cua client moi ket noi");
                        String username = message.getUsername();
                        if (clientFrame.clientName.contains(username)) {
                        }// neu trong list chua phan tu nay thi thoi khong add nua 
                        else {
                            clientFrame.clientName.add(username);
                            clientFrame.initDataClientName();
                        }

                    }
                } catch (ClassNotFoundException ex) {
                    System.out.println("khong the nhan tin nhan tu server");
                }
            }
        } catch (IOException ex) {
            System.out.println("khong the tao luong gui nhan du lieu tu server");
        }
    }
}
