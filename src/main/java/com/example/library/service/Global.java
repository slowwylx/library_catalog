package com.example.library.service;

import com.example.library.DBConnection.DBconnection;
import com.example.library.abonents.User;
import com.example.library.literature.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Global {
    public static boolean confirmDel = false;
    public static boolean add = false;
    public static Book selectRow;
    public static User selectUser;
    public static boolean added = false;
    public static boolean giveOut;


    public static int check(String phn2){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int isExist = 0;
        String quer2 = "SELECT * FROM users WHERE userPhone =?";
        try {
            connection = DBconnection.getDbConnection();
            preparedStatement = connection.prepareStatement(quer2);
            preparedStatement.setString(1,phn2);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.isBeforeFirst()){
                isExist++;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return isExist;
    }

}
