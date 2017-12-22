package ru.ezhov.appservice.frame;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import ru.ezhov.appservice.IconLoader;
import ru.ezhov.appservice.RunApp;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 *
 * @author ezhov_da
 */
public class ActionStop extends ActionApp
{
    {
        putValue(NAME, "Остановить");
    }
    private static final Logger LOG = Logger.getLogger(ActionStop.class.getName());

    public ActionStop(RunApp runApp)
    {
        super(runApp);
        String prop = PropertyHttpHolder.getInstance().getProperty(PropertiesConst.ICON_STOP);
        try
        {
            Icon icon = IconLoader.getIcon(prop);
            putValue(SMALL_ICON, icon);
        } catch (IOException ex)
        {
            Logger.getLogger(ActionRun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                if (runApp.isRunning())
                {
                    try
                    {
                        runApp.destroyProcess();
                        JButton jButton = (JButton) e.getSource();
                        jButton.setAction(new ActionRun(runApp));
                        BasicPanel.getInstance().load();
                    } catch (InterruptedException ex)
                    {
                        Logger.getLogger(ActionStop.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }


}
