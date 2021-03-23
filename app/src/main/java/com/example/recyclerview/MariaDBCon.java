package com.example.recyclerview;


import java.sql.*;

public class MariaDBCon{

    private static MariaDBCon database;

    public static synchronized MariaDBCon getInstance(){
        if(database == null){
            database = new MariaDBCon();
        }
        return database;
    }
    private MariaDBCon(){}
    public static void connect(){
        try {

            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://kaifuhome.de:3307/hlg", "teamhlg", "1FiTUaR2UV8c.X4#p0NW0ofZ0Qic1cI3");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("CREATE TABLE test_01 (a VARCHAR(255))");

            

            /*
            rs = stmt.executeQuery("SELECT * FROM jsonStorage");

            while(rs.next()){
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+rs.getString(3));
            }*/
            con.close();

        }catch(Exception e){

            System.out.println("\n\n\n\n-------------------------MARIADB\n+"+e+ "\n------------------------------\n\n\n\n");}

    }
    //private class DriverAction implements DriverManager{}
}
