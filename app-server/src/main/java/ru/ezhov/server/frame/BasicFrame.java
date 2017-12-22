package ru.ezhov.server.frame;

import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ru.ezhov.server.TitleHolder;

public class BasicFrame extends JFrame
{
    private static final Logger LOG = Logger.getLogger(BasicFrame.class.getName());
    private JButton buttonStart;
    private JButton buttonStop;

    public BasicFrame()
    {
        buttonStart = new JButton(new ActionStartServer());
        buttonStop = new JButton(new ActionStopServer());
        JPanel panel = new JPanel();
        panel.add(buttonStart);
        panel.add(buttonStop);
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle(TitleHolder.getTitle());
        pack();
        LocatedComponent.locatedInTheCorner(this);
    }
}
