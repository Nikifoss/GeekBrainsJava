package ru.kareev.server.chat.auth;

import ru.kareev.clientserver.sqlService.AuthServiceSQL;

public class AuthService {

    public String getUserNameByLoginAndPassword(String login, String password){
        return AuthServiceSQL.getUsernameFromSQL(login, password);
    }

}
