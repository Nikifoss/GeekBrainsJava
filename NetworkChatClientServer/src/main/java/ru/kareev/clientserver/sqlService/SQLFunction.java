package ru.kareev.clientserver.sqlService;

import java.sql.*;
import java.util.ArrayList;

public class SQLFunction {

    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\nikif\\IdeaProjects\\GeekBrainsJava\\Networkchat.db";


    public static ArrayList<String> getListUsernameFromSQL() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            return onlineUserSearchInBase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<String> onlineUserSearchInBase(Connection connection) throws SQLException {
        ArrayList<String> onlineList = new ArrayList<>();
        Statement stmt = connection.createStatement();
        try (ResultSet rs = stmt.executeQuery("SELECT username FROM online;")) {
            while (rs.next()) {
                String username = rs.getString("username");
                onlineList.add(username);
            }
        }
        return onlineList;
    }

}
