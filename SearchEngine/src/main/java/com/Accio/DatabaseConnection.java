package com.Accio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static Connection connection = null;
    public static Connection getConnection(){
        if(connection!=null){
            return connection;
        }
        String db = "searchenginejava";
        String user = "root";
        String pwd = "Aparna@09";
        return getConnection(db, user, pwd);
    }
    private static Connection getConnection(String db, String user, String pwd){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            //using this link(below) we are going to
            //establish connection with mysql jdbc
            connection = DriverManager.getConnection("jdbc:mysql://localhost/"+db+"?user="+user+"&password="+pwd);
        }
        catch(ClassNotFoundException | SQLException classNotFoundException)
        {
            classNotFoundException.printStackTrace();
        }
//        catch (Exception exception){
//            exception.printStackTrace();
//        }
        return connection;
    }
}

