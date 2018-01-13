package ru.ezhov.server.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.*;

import ru.ezhov.server.common.ServerHolder;
import ru.ezhov.server.common.TitleHolder;

public class BasicFrame extends JFrame {
    private static final Logger LOG = Logger.getLogger(BasicFrame.class.getName());
    private JLabel label;
    private JButton button;
    private boolean startServer = false;
    private final String START_SERVER_TEXT = "Start server";
    private final String STOP_SERVER_TEXT = "Stop server";

    public BasicFrame() {
        String title = TitleHolder.title();
        label = new JLabel(title);
        button = new JButton(START_SERVER_TEXT);
        button.addActionListener(getButtonActionListener());
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(button, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle(title);
        setIconImage(new ImageIcon(BasicFrame.class.getResource("/server-icon.png")).getImage());
        pack();
        LocatedComponent.locatedInTheCorner(this);
    }

    private ActionListener getButtonActionListener() {
        return e -> {
            Thread thread = new Thread(() -> {
                startServer = !startServer;
                String text = startServer ? STOP_SERVER_TEXT : START_SERVER_TEXT;
                button.setText(text);
                if (startServer) {
                    ServerHolder.getInstance().startServer(true);
                } else {
                    ServerHolder.getInstance().stopServer();
                }
            });
            thread.setDaemon(true);
            thread.start();
        };
    }
}
