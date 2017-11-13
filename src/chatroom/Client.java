
package chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
public class Client {

    private Socket socket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    //private DataOutputStream os;
   // private DataInputStream is;

    public void connect() {
        try {
            socket = new Socket("localhost", 2000);
          //  os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
            System.out.println("da ker noi den server");
        } catch (IOException ex) {
            System.out.println("khong the ket noi toi server");
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }
}
