package ru.ezhov.updater;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Класс, который отвечает за один процесс
 * <p>
 * @author ezhov_da
 */
public class ProcessRegister
{
    private static final Logger LOG = Logger.getLogger(ProcessRegister.class.getName());
    private static ProcessRegister processRegister;
    private Process process;

    public static synchronized ProcessRegister getInstance()
    {
        if (processRegister == null)
        {
            processRegister = new ProcessRegister();
        }
        return processRegister;
    }

    public synchronized void registerProcess(Process process)
    {
        this.process = process;
    }

    public synchronized void destroyProcess()
    {
        try
        {
            LOG.log(Level.INFO, "Закрываем сокет");
            ServerSocketUpdateApp.getInstance().closeSocket();
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(ProcessRegister.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex)
        {
            LOG.log(Level.WARNING, "Ошибка закрытия сокета");
        }
    }
}
