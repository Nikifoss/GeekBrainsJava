module NetworkChatClientServer {

    exports ru.kareev.clientserver;
    opens ru.kareev.clientserver to NetworkChatClient;
    exports ru.kareev.clientserver.commands;
    opens ru.kareev.clientserver.commands to NetworkChatClient;
}