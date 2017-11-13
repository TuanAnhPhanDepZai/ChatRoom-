/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiNhanModel;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Message implements Serializable {

    public ArrayList<String> list = new ArrayList<>();

    public Message(ArrayList arrayList) {
        this.list = arrayList;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
    
    public void show(){
        System.out.println(list);
    }
}
