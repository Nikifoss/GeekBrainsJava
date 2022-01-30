package ru.kareev.client.model;

import ru.kareev.clientserver.Command;

public interface ReadCommandListener {

    void processReceivedCommand(Command command);

}
