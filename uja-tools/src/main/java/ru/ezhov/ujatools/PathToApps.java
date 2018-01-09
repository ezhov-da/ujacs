package ru.ezhov.ujatools;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Класс, который просто возвращает путь к папке приложений
 * <p>
 * @author ezhov_da
 */
public class PathToApps
{
    private static final Logger LOG = Logger.getLogger(PathToApps.class.getName());

    public static synchronized String pathToApps() throws IOException
    {
        return ParentPath.getPatentPathWithEnd() + File.separator + PropertyHttpHolder.getInstance().getProperty(PropertiesConst.NAME_FOLDER_APPS);
    }

    public static synchronized String pathToApps(String endPath) throws IOException
    {
        return pathToApps() + File.separator + endPath;
    }

    public static synchronized String createFolderAndReturnPath() throws IOException
    {
        File file = new File(pathToApps());
        file.mkdirs();
        return file.getAbsolutePath();
    }

    public static synchronized String createFolderAndReturnPath(String endPath)
            throws IOException
    {
        return createFolderAndReturnPath() + File.separator + endPath;
    }

    public static synchronized String getFolderJava()
            throws IOException
    {
        return ParentPath.getPatentPathWithEnd()
                + PropertyHttpHolder.getInstance().getProperty(PropertiesConst.NAME_FOLDER_APP_SERVICE) + File.separator
                + PropertyHttpHolder.getInstance().getProperty(PropertiesConst.NAME_FOLDER_APP_SERVICE_REAL) + File.separator
                + PropertyHttpHolder.getInstance().getProperty(PropertiesConst.NAME_FOLDER_JAVA_REAL);
    }
}
