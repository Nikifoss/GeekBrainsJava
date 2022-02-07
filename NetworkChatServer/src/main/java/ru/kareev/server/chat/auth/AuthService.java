package ru.kareev.server.chat.auth;

import sql.jdbc.AuthServiceSQL;

import java.util.Set;

public class AuthService {

    public String getUserNameByLoginAndPassword(String login, String password){
        return AuthServiceSQL.getUsernameFromSQL(login, password);
    }

}
