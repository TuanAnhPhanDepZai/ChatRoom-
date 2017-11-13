/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiNhanModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList ;

public class Client {
     
     public ArrayList<String> list = new ArrayList<>();
     public Socket socket ;
     public ObjectOutputStream oos ;
     public ObjectInputStream ois ;
     
     public void connect(){
         try {
             socket = new Socket("localhost",1997);
             oos = new ObjectOutputStream(socket.getOutputStream());
             ois = new ObjectInputStream(socket.getInputStream());
             System.out.println("da co stream den server");
             
             try {
                 String s =(String) ois.readObject();
                 if (s!= null){
                 System.out.println("da nhan duoc doi tuong ");
                 }
                  System.out.println(s);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
             }
             
         } catch (IOException ex) {
             Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     public static void main(String[] args) {
        Client c  = new Client();
        c.connect();
    }
}
