package ru.ezhov.ujatools.exception;

import java.util.logging.Logger;

/**
 *
 * @author ezhov_da
 */
public class FileNotFoundException extends Exception
{
    private static final Logger LOG = Logger.getLogger(FileNotFoundException.class.getName());

    public FileNotFoundException()
    {
    }

    public FileNotFoundException(String message)
    {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public FileNotFoundException(Throwable cause)
    {
        super(cause);
    }


}
