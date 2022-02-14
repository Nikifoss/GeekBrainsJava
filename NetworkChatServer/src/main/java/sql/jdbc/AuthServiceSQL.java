package sql.jdbc;

import java.sql.*;

public class AuthServiceSQL {

    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\nikif\\IdeaProjects\\GeekBrainsJava\\Networkchat.db";

    //getUsernameFromSQL создает подключение к базе данных, принимает пару логин/пароль, после чего проводит
    // поиск по логину и сопоставляет его с паролем. возвращает Username пользователя или null, если пользователь не найден
    public static String getUsernameFromSQL(String login, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            return userSearchInBase(connection, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String userSearchInBase(Connection connection, String login, String password) {
        String loginSQL = ("'" + login + "'");
        try {
            if (searchLoginInBase(connection, loginSQL, login)) {
                int id = getUserId(connection, loginSQL);
                if (checkPasswordComplianceCheck(connection, id, password)) {
                    return getUserName(connection, id);
                }
            }
        } catch (SQLException e) {
            System.err.println("userSearchInBase - Такой пары логин/пароль не существует");
        }
        return null;
    }

    private static String getUserName(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM users WHERE id = " + id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getString("username");
    }

    private static boolean checkPasswordComplianceCheck(Connection connection, int id, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE id = " + id);
        ResultSet resultSet = preparedStatement.executeQuery();
        String passwordSQL = resultSet.getString("password");
        return password.equals(passwordSQL);
    }

    private static int getUserId(Connection connection, String login) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = " + login);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getInt("id");
    }

    private static boolean searchLoginInBase(Connection connection, String fLogin, String login) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = " + fLogin);
        ResultSet resultSet = preparedStatement.executeQuery();
        String resLogin = resultSet.getString("login");
        return resLogin.equals(login);
    }


}
