package ru.ezhov.appservice.tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import ru.ezhov.appservice.IconLoader;
import ru.ezhov.appservice.frame.BasicFrame;
import ru.ezhov.appservice.frame.CloseAppActionListener;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Класс. который отвечает за трей приложения
 * <p>
 * @author ezhov_da
 */
public class AppTray
{
    private static final Logger LOG = Logger.getLogger(AppTray.class.getName());
    private static AppTray appTray;
    private boolean installAppTray;

    private AppTray()
    {
    }

    public static AppTray getInstance()
    {
        if (appTray == null)
        {
            appTray = new AppTray();
        }
        return appTray;
    }


    public void installTray()
    {
        if (SystemTray.isSupported())
        {
            SystemTray systemTray = SystemTray.getSystemTray();
            String prop = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.ICON_BASIC);
            try
            {
                Image image = IconLoader.getImage(prop);
                TrayIcon trayIcon = new TrayIcon(image);
                trayIcon.addMouseListener(new ShowFrameListener());
                PopupMenu popupMenu = new PopupMenu();
                MenuItem menuItem = new MenuItem("Закрыть");
                menuItem.addActionListener(new CloseAppActionListener());
                popupMenu.add(menuItem);
                trayIcon.setPopupMenu(popupMenu);
                systemTray.add(trayIcon);
                installAppTray = true;
            } catch (IOException ex)
            {
                LOG.log(Level.SEVERE, "Не удалось загрузить иконку трея", ex);
            } catch (AWTException ex)
            {
                LOG.log(Level.SEVERE, "Не удалось создать трей", ex);
            }
        }
    }

    public boolean isInstallAppTray()
    {
        return installAppTray;
    }

    //Показываем сервис приложений
    private class ShowFrameListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            if (e.getClickCount() == 1)
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        BasicFrame basicFrame = BasicFrame.getInstance();
                        basicFrame.setVisible(true);
                        basicFrame.toFront();
                    }
                });
            }
        }
    }

}
