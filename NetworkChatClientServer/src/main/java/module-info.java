module NetworkChatClientServer {
    requires java.sql;

    exports ru.kareev.clientserver;
    opens ru.kareev.clientserver to NetworkChatClient;
    exports ru.kareev.clientserver.commands;
    opens ru.kareev.clientserver.commands to NetworkChatClient;
    exports ru.kareev.clientserver.sqlService;
}