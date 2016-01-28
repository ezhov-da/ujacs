package ru.ezhov.ujac;

import java.io.*;

/**
 * Чистит папку от старой программы
 * <p>
 * @author ezhov_da
 */
public class ClearAndCreateFolder
{

    public void clear(String nameFolder) throws UnsupportedEncodingException
    {
        File file = new File(nameFolder);
        delete(file);
    }

    private void delete(File file)
    {
        if (!file.exists())
        {
            return;
        }
        if (file.isDirectory())
        {
            for (File f : file.listFiles())
            {
                delete(f);
            }
            file.delete();
        } else
        {
            file.delete();
        }
    }

    public void createFolder(String nameFolder) throws IOException
    {
        File file = new File(nameFolder);
        file.mkdirs();
    }
}
