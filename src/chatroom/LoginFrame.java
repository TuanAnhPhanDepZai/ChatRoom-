package chatroom;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.java.swing.plaf.windows.resources.windows;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFrame extends JFrame {

    private final JLabel labelUersname = new JLabel("     Uers name ");
    private final JLabel labelPassword = new JLabel("     Pass word");
    private final JTextField tfuser = new JTextField(20);
    private JPasswordField ps = new JPasswordField(20);
    private JButton btnSingIn = new JButton("Sing In");
    private JButton btnCancel = new JButton("Cancel");
    public JButton btnSingUp = new JButton("SingUp");
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    public Thread threadClient;
    public Socket socket;
    public ClientFrame clientFrame;
    public ClientThread clientThread;
    public String name;

    // public boolean IsPressSignIn = false ;
    public LoginFrame(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        this.setTitle("Đăng nhập");
        this.setLayout(new BorderLayout());
        this.add(createPanelInput(), BorderLayout.PAGE_START);
        this.add(createPanelButton(), BorderLayout.PAGE_END);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setEvent();
        pack();
    }

    private JPanel createPanelInput() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(labelUersname);
        panel.add(tfuser);
        panel.add(labelPassword);
        panel.add(ps);
        panel.setBorder(new EmptyBorder(5, 10, 5, 10));
        return panel;
    }

    private JPanel createPanelButton() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 5, 5));
        panel.add(btnSingIn);
        panel.add(btnSingUp);
        panel.add(btnCancel);
        panel.setBorder(new EmptyBorder(5, 40, 5, 40));
        return panel;
    }

    private JButton createButtonSIngIn() {

        JButton button = new JButton("Sing In");

        return button;
    }

    private JButton createButtonCancel() {
        JButton button = new JButton("Cancel");
        return button;
    }

    public JButton getBtnSingIn() {
        return btnSingIn;
    }

    public void setBtnSingIn(JButton btnSingIn) {

        this.btnSingIn = btnSingIn;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(JButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public String getUername() {
        return tfuser.getText();
    }

    public String getPassword() {
        return ps.getText().toString().trim();
    }

    public void setEvent() {
        btnSingIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket = new Socket("localhost", 2017);
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    Message m = new Message(tfuser.getText(), ps.getText(), 1);
                    oos.writeObject(m);
                    try {
                        Message m1 = (Message) ois.readObject();
                        if (m1.getOption() == 2) {
                            JOptionPane.showMessageDialog(null, "Ten dang nhap hoac tai khoan khong dung");
                        } else {  // dang nhap thanh cong
                            name = m1.getUsername();
                            setVisible(false);
                            clientFrame.setName(name);
                            clientFrame.setOos(oos);
                            clientFrame.setVisible(true);
                            clientThread = new ClientThread(ois, clientFrame);
                        }
                    } catch (ClassNotFoundException ex) {
                        System.out.println("khong nhan duoc thong tin phan hoi tu server");
                    }
                } catch (IOException ex) {
                    System.out.println("khong the ket noi den server ");
                }
            }
        });

        btnSingUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket = new Socket("localhost", 2017);
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    Message m = new Message(tfuser.getText(), ps.getText(), 3); // 3 la option de dung cho viec dang ki thong tin nguoi dung
                    oos.writeObject(m);
                    try {
                        Message m1 = (Message) ois.readObject();
                        if (m1.getOption() == 3) {
                            JOptionPane.showMessageDialog(null, "dang ki tai khoan thanh cong");
                        } else if (m1.getOption() == 4) {
                            JOptionPane.showMessageDialog(null, "dang ki tai khoan khong thanh cong ");
                        }
                    } catch (ClassNotFoundException ex) {
                        System.out.println(" khong nhan duoc phan hoi tu server");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame(new ClientFrame(null, null));
    }
}
