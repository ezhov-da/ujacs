package ru.ezhov.ujatools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

/**
 * Распаковка архивов
 * <p>
 * @author ezhov_da
 */
public class UnzipFile
{
    /**
     *
     * @param folderUnzip - папка для распаковки
     * @param uznip       - архив для распаковки
     * <p>
     * @throws IOException
     */
    public void unzip(File folderUnzip, File uznip) throws IOException
    {
        byte[] buf = new byte[65536];
        ZipArchiveInputStream zipArchiveInputStream = null;
        try
        {
            zipArchiveInputStream = new ZipArchiveInputStream(new FileInputStream(uznip), "cp866");
            ZipArchiveEntry zipArchiveEntry;
            while ((zipArchiveEntry = zipArchiveInputStream.getNextZipEntry()) != null)
            {
                int n;
                if (zipArchiveInputStream.canReadEntryData(zipArchiveEntry))
                {
                    File file = new File(folderUnzip.getAbsolutePath() + File.separator + zipArchiveEntry.getName());
                    if (zipArchiveEntry.isDirectory())
                    {
                        file.mkdirs();
                        continue;
                    }
                    File fileParent = file.getParentFile();
                    if (!fileParent.canExecute())
                    {
                        fileParent.mkdirs();
                    }
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    while ((n = zipArchiveInputStream.read(buf)) != -1)
                    {
                        bufferedOutputStream.write(buf, 0, n);
                    }
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
            }
        } finally
        {
            if (zipArchiveInputStream != null)
            {
                zipArchiveInputStream.close();
            }
        }
    }
}
