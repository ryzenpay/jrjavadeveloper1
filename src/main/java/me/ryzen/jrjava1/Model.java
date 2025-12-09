package me.ryzen.jrjava1;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Model extends AbstractTableModel{
    private static final Logger logger = LogManager.getLogger("Model");
    private ArrayList<User> users;

    public Model(){
        this.users = new ArrayList<>();
    }

    public Model(ArrayList<User> users){
        this.users = users;
    }
    
    @Override
    public int getColumnCount(){
        return User.getColumnNames().length;
    }

    @Override
    public int getRowCount(){
        return this.users.size();
    }

    @Override
    public Object getValueAt(int row, int col){
        User user = this.users.get(row);
        if (user == null){
            logger.debug("null user at row " + row);
        }
        return user.getRowValue(col);
    }

    @Override
    public String getColumnName(int col) {
        return Arrays.asList(User.getColumnNames()).get(col);
    }
}
