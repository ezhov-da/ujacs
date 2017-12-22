package ru.ezhov.ujatools.exception;

/**
 *
 * @author ezhov_da
 */
public class DeleteFileException extends Exception
{

    public DeleteFileException()
    {
    }

    public DeleteFileException(String message)
    {
        super(message);
    }

    public DeleteFileException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DeleteFileException(Throwable cause)
    {
        super(cause);
    }

}
