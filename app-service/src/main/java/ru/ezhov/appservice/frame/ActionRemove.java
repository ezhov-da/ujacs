package ru.ezhov.appservice.frame;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import ru.ezhov.appservice.managers.actionmanagers.AppRemoveManager;
import ru.ezhov.ujatools.exception.DeleteFileException;
import ru.ezhov.appservice.RunApp;

/**
 *
 * @author ezhov_da
 */
public class ActionRemove extends ActionApp
{
    {
        putValue(NAME, "Удалить");
    }
    private static final Logger LOG = Logger.getLogger(ActionRemove.class.getName());

    public ActionRemove(RunApp runApp)
    {
        super(runApp);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (runApp.isRunning())
        {
            int q =
                    JOptionPane.showConfirmDialog(
                            null,
                            "Приложение сейчас запущено, закрыть?",
                            "Приложение запущено",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
            if (q != JOptionPane.YES_OPTION)
            {
                return;
            }
        }
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                AppRemoveManager appRemoveManager = new AppRemoveManager(runApp);
                try
                {
                    appRemoveManager.removeApp();
                    BasicPanel.getInstance().load();
                } catch (IOException ex)
                {
                    Logger.getLogger(ActionRemove.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DeleteFileException ex)
                {
                    Logger.getLogger(ActionRemove.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
