package ru.ezhov.ujatools.exception;

/**
 *
 * @author ezhov_da
 */
public class FileAppVersionNotFoundException extends Exception
{

    public FileAppVersionNotFoundException()
    {
    }

    public FileAppVersionNotFoundException(String message)
    {
        super(message);
    }

    public FileAppVersionNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public FileAppVersionNotFoundException(Throwable cause)
    {
        super(cause);
    }

}
