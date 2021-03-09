package server;

import java.sql.SQLException;

public interface AuthService {
    String getNicknameByLoginAndPassword(String login, String password) throws ClassNotFoundException, SQLException;
    boolean registration(String login, String password, String nickname) throws SQLException;
    boolean chengNickName(String login, String password, String nickname, String newNickName) throws SQLException;
}
