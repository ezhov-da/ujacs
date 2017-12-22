package ru.ezhov.ujatools;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Получаем путь на папку выше
 * <p>
 * @author ezhov_da
 */
public class ParentPath
{
    private static final Logger LOG = Logger.getLogger(ParentPath.class.getName());

    public static synchronized String getPatentPath() throws IOException
    {
        File file;
        String userDir = System.getProperty("user.dir");
        file = new File(userDir + "/..");
        return file.getCanonicalPath();
    }

    public static synchronized String getPatentPathWithEnd() throws IOException
    {
        return ParentPath.getPatentPath() + File.separator;
    }

    public static synchronized String getPatentPathWithEnd(String endPath)
            throws IOException
    {
        return getPatentPathWithEnd() + endPath;
    }
}
