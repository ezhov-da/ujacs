package ru.ezhov.appservice;

import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ru.ezhov.appservice.frame.WindowNotifyUpdate;

/**
 *
 * @author ezhov_da
 */
public class WindowNotifyUpdateTest
{
    private static final Logger LOG = Logger.getLogger(WindowNotifyUpdateTest.class.getName());


    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Throwable ex)
                {
                    //
                }
                WindowNotifyUpdate windowNotifyUpdate = new WindowNotifyUpdate();
                windowNotifyUpdate.setVisible(true);
            }
        });
    }

}
