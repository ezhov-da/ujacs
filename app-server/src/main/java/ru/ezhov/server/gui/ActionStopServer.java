package ru.ezhov.server.gui;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import javax.swing.AbstractAction;

import ru.ezhov.server.ServerHolder;

public class ActionStopServer extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(ActionStartServer.class.getName());

    {
        putValue(NAME, "Stop server");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        ServerHolder.getInstance().stopServer();
    }

}
