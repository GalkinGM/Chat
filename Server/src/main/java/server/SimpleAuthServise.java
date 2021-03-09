package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class SimpleAuthServise implements AuthService {
    private static Connection connection;
    private static Statement stmt;


    private class UserData {
        String login;
        String password;
        String nickname;

        public UserData(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }

    private List<UserData> users;

    public SimpleAuthServise() {
        users = new ArrayList<>();
//        users.add(new UserData("qwe", "qwe", "qwe"));
//        users.add(new UserData("asd", "asd", "asd"));


    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) throws SQLException, ClassNotFoundException {
         connect();
        ResultSet rs =stmt.executeQuery("SELECT Login, Password, NickName FROM AccountDB;");
        while (rs.next()) {
            if(rs.getString("Login").equals(login) && rs.getString("Password").equals(password)){

                String a = rs.getString("NickName");
                rs.close();
                return a ;
            }
        }
        rs.close();
        return null;
    }

    @Override
    public boolean registration  (String login, String password, String nickname) throws SQLException {
        try{ connect();
        ResultSet rs =stmt.executeQuery("SELECT Login, NickName FROM AccountDB;");
        while (rs.next()){
            if(rs.getString("Login").equals(login) || rs.getString("NickName").equals(nickname)){
                rs.close();
                return false;
            }
        }

            stmt.executeUpdate("INSERT INTO AccountDB (Login, Password, NickName) VALUES ('" + login + "', '" + password +"', '" + nickname + "');");
        System.out.println("Клиент добавлен");
        stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            disconnect();
        }


        return true;
    }

    public boolean chengNickName (String login, String password, String nickname, String newNickName) throws SQLException {
        try{ connect();
            ResultSet rs =stmt.executeQuery("SELECT Login, NickName, Password FROM AccountDB;");
            while (rs.next()){
                if(rs.getString("Login").equals(login) && !rs.getString("NickName").equals(newNickName) && rs.getString("Password").equals(password)){

                    stmt.executeUpdate("UPDATE AccountDB SET NickName = '" + newNickName +"' WHERE NickName == '"+nickname+"';");
                    System.out.println("NickName изменен");
                    stmt.close();
                    return true;

                }
            }

            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            disconnect();
        }


        return false;
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
