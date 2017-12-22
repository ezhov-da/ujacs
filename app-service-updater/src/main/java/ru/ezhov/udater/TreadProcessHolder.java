package ru.ezhov.udater;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Поток, который ждет завершение приложения и если
 * приложение завершается для обновления, запускает его еще раз
 * <p>
 * @author ezhov_da
 */
public class TreadProcessHolder extends Thread
{
    private static final Logger LOG = Logger.getLogger(TreadProcessHolder.class.getName());
    private static final int USER_CLOSE_APP = -1;
    private static final int UPDATER_CLOSE_APP = 1;
    private Process process;

    public TreadProcessHolder(Process process)
    {
        this.process = process;
    }

    @Override
    public void run()
    {
        try
        {
            int i = process.waitFor();
            LOG.log(Level.INFO, "Закрытие приложения со статусом: {0}", i);
            if (i == UPDATER_CLOSE_APP)
            {
                runAgaine();
            } else
            {
                LOG.log(Level.INFO, "Окончательное закрытие приложения");
                System.exit(-1000);
            }
        } catch (InterruptedException ex)
        {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-100);
        }
    }

    private void runAgaine()
    {
        LOG.log(Level.INFO, "Повторный запуск приложения");
        Thread thread = new Thread(new SwingWorkerApp());
        thread.setDaemon(true);
        thread.start();
    }
}
