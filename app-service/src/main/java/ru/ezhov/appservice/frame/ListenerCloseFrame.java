package ru.ezhov.appservice.frame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import ru.ezhov.appservice.tray.AppTray;

/**
 * Перед закрытием приложения проверяем запущенные и реагируем
 * <p>
 * @author ezhov_da
 */
public class ListenerCloseFrame implements WindowListener
{
    private static final Logger LOG = Logger.getLogger(ListenerCloseFrame.class.getName());

    @Override
    public void windowOpened(WindowEvent e)
    {
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        executeColse();
    }


    @Override
    public void windowClosed(WindowEvent e)
    {
        executeColse();
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }

    private void executeColse()
    {
        if (AppTray.getInstance().isInstallAppTray())
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    BasicFrame.getInstance().setVisible(false);
                    WindowMinimizeToTray.getInstance().setVisible(true);
                }
            });
        } else
        {
            new CloseAppActionListener().actionPerformed(null);
        }
    }
}
