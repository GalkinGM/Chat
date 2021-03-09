package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MessageDB {
    private static Connection connection;
    private static Statement stmt;

    public static void MessageDBs (ClientHandler sender, String receiver, String msg) {

        try{
            connect();
            System.out.println("!!!!!!!!!!!!!!!");
            String senders = sender.getNickname();
//            String receivers = "sss";
//            String msgs = "sss";

            stmt.executeUpdate("INSERT INTO MessageDB (NickNameSender, NickNameRecipient, Msg) VALUES ('" + senders + "', '" + receiver +"', '" + msg + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            disconnect();
        }


    }


    private static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:ClientDB.db");
        stmt = connection.createStatement();
    }

    private static void disconnect() {
        try {
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}





