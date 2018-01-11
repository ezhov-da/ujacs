package ru.ezhov.updater;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.ezhov.ujatools.AbsolutePath;
import ru.ezhov.ujatools.FilesUtil;
import ru.ezhov.ujatools.UnzipFile;

/**
 *
 * @author ezhov_da
 */
public class Unzip
{
    private static final Logger LOG = Logger.getLogger(Unzip.class.getName());

    public void unzipArchive(String pathToArchive) throws IOException, Exception
    {
        UnzipFile unzipFile = new UnzipFile();
        String folder = AbsolutePath.getAbsolutePath(Unzip.class);
        LOG.log(Level.INFO, "Распаковка сервиса приложений в {0} из {1}", new Object[]
        {
            folder, pathToArchive
        });
        unzipFile.unzip(new File(folder), new File(pathToArchive));
        FilesUtil filesUtil = new FilesUtil();
        filesUtil.delete(new File(pathToArchive));
    }
}
