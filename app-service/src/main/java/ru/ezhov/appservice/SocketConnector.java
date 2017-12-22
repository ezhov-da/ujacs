package ru.ezhov.appservice;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ezhov_da
 */
public class SocketConnector
{
    private static final Logger LOG = Logger.getLogger(SocketConnector.class.getName());

    public void connect() throws IOException
    {
        Socket socket = new Socket("localhost", 60000);
        LOG.log(Level.INFO, "Сокет запущен");
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        try
        {
            LOG.log(Level.INFO, "Пробуем считать данные из сокета");
            long l = inputStream.readLong();
            LOG.log(Level.INFO, "WOW, вот что мы получили! {0}", String.valueOf(l));
        } catch (Exception e)
        {
            LOG.log(Level.INFO, "Выход из программы так как сокет закрылся");
            System.exit(1);
        }
    }


}
