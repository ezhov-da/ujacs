package ru.ezhov.server.frame;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import javax.swing.AbstractAction;

import static javax.swing.Action.NAME;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import ru.ezhov.server.ServerHolder;

public class ActionStopServer extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(ActionStartServer.class.getName());

    {
        putValue(NAME, "Остановить сервер");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        ServerHolder.getInstance().stopServer();
    }

}
