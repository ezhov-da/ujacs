package ru.ezhov.server.frame;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import ru.ezhov.server.ServerHolder;

public class ActionStartServer extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(ActionStartServer.class.getName());

    {
        putValue(NAME, "Запустить сервер");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        ServerHolder.getInstance().startServer();
    }
}
