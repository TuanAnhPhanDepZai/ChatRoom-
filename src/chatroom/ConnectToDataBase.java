package chatroom;

import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectToDataBase {

    public final String classname = "com.mysql.jdbc.Driver";
    public final String url = "jdbc:mysql://localhost:3306/dangnhap";
    public String user = "root";
    public String pass = "phantuananh123";
    public Connection connection;
    public String table = "dangnhap";
    
    public void connnect() {
        try {
            Class.forName(classname);

            try {
                connection = DriverManager.getConnection(url, user, pass);

            } catch (SQLException ex) {
                Logger.getLogger(ConnectToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isConnectToSingIn() {
        boolean Issucess = false;
        if (connection != null) {
            Issucess = true;
        } else {
            Issucess = false;
        }
        return Issucess;
    }

    public ResultSet getData() {
        ResultSet resultset = null;
        String sqlcommand = "select * from " + table;
        try {
            Statement st = (Statement) connection.createStatement();
            resultset = (ResultSet) st.executeQuery(sqlcommand);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultset;
    }

    public void ShowData(ResultSet resultset) {
        // su dung phuong thuc get String 
        try {
            System.out.println(" Show data base ");
            while (resultset.next()) {
                System.out.printf(" %-30s    %-20s   ", resultset.getString(1), resultset.getString(2));
                System.out.println();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ResultSet getDataId(String name) {
        ResultSet resultSet = null;
        String sqlcommand = "select * from " + table + " where username =  ? ";
        try {
            java.sql.PreparedStatement ps = connection.prepareStatement(sqlcommand);
            ps.setString(1, name);
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean checkUsername(Message message) {
        String username;
        String password;
        if (message.option == 1) {
            username = message.getUsername();
            password = message.getPassword();
            ResultSet rs = (ResultSet) getDataId(username);
            try {
                String s1 = "";
                String s2 = "";
                while (rs.next()) {
                    s1 = rs.getString(1);
                    s2 = rs.getString(2);
                }
                if (s1.equals("")) {
                    return false;
                } else if (s1.equals(username) && s2.equals(password)) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                System.out.println("khong the doc du lieu tu server");
            }
        }
        return false;
    }

    public boolean insertData(String s1, String s2) {
        int executeUpdate = 0;
        try {
            String command = " insert into " + table + " value( ?,?) ;";
            PreparedStatement ps = connection.prepareStatement(command);
            ps.setString(1, s1);
            ps.setString(2, s2);
            executeUpdate = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (executeUpdate > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean singUpUser(Message message) {
        boolean boo = false;
        String s1 = message.getUsername();
        String s2 = message.getPassword();
        boo = insertData(s1, s2);
        return boo;
    }

    public static void main(String[] args) {
//        ConnectToDataBase connectToDataBase  = new ConnectToDataBase();
////        connectToDataBase.connnect();
////        boolean boo  = connectToDataBase.insertData("tuan23","14121997");
////       if (boo == true ) System.out.println("thanh cong");
////       else {
////           System.out.println("khong thanh cong");
////       }
////    }
    }
}
