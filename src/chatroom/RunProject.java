/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom;

/**
 *
 * @author phant
 */
public class RunProject {

    public static void main(String[] args) {
        ClientFrame clientFrame = new ClientFrame(null,null);
        LoginFrame loginFrame = new LoginFrame(clientFrame);
    }

}
