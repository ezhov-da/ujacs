package ru.ezhov.appservice.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Окошко, которое показывается, когда пользователь закрывает приложение (а на самом деле мы его сворачиваем)
 * <p>
 * @author ezhov_da
 */
public class WindowMinimizeToTray extends JWindow
{
    private static final Logger LOG = Logger.getLogger(WindowMinimizeToTray.class.getName());
    private static WindowMinimizeToTray windowMinimizeToTray;
    private JLabel label;


    private WindowMinimizeToTray()
    {
        JPanel panel = (JPanel) getContentPane();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label = new JLabel();
        panel.add(label, BorderLayout.CENTER);
    }

    public static WindowMinimizeToTray getInstance()
    {
        if (windowMinimizeToTray == null)
        {
            windowMinimizeToTray = new WindowMinimizeToTray();
        }
        return windowMinimizeToTray;
    }

    @Override
    public void setVisible(boolean b)
    {
        if (b)
        {
            label.setText(PropertyHttpHolder.getInstance().getProperty(PropertiesConst.TEXT_MINIMIZE_APP_SERVICE));
            Point point = MouseInfo.getPointerInfo().getLocation();
            setLocation(point);
            pack();
            setAlwaysOnTop(true);
            TimerTask timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            WindowMinimizeToTray.this.setVisible(false);
                        }
                    });
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 1500);
        }
        super.setVisible(b);
    }
}
