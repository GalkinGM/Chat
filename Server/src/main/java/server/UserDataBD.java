//package server;
//
//import java.sql.*;
//
//public class UserDataBD {
//    private static Connection connection;
//    private static Statement stmt;
//
//
//    public static void main(String[] args) {
//        try{
//            connect();
//
//            String sender = "eeee1";
//            String receiver = "gegg1";
//            String msg = "cecc1";
//
//            stmt.executeUpdate("INSERT INTO MessageDB (NickNameSender, NickNameRecipient, Msg) VALUES ('" + sender + "', '" + receiver +"', '" + msg + "');");
//        } catch (Exception e) {
//         e.printStackTrace();
//        }finally {
//            disconnect();
//        }
//
//
//    }
//
//    private static void connect() throws ClassNotFoundException, SQLException {
//        Class.forName("org.sqlite.JDBC");
//        connection = DriverManager.getConnection("jdbc:sqlite:ClientDB.db");
//        stmt = connection.createStatement();
//    }
//
//    private static void disconnect() {
//        try {
//            stmt.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        try {
//            connection.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//
//}
