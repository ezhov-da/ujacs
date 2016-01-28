package ru.ezhov.ujac;

import java.io.*;
import java.util.zip.*;

/**
 * Класс извлекает файлы из архива в нашу папку
 * <p>
 * @author ezhov_da
 */
public class ExtractZIP
{
    /**
     * Метод извлекает файлы из архивов
     * @param nameArchive
     * @param nameFolder
     */
    public void extractZIP(String nameArchive, String nameFolder) throws IOException
    {
        File fileArcvhive = new File(nameArchive);
        File pathToApplication = new File(nameFolder);
        FileInputStream fileInputStream = null;
        ZipInputStream zipInputStream = null;
        try
        {
            fileInputStream = new FileInputStream(fileArcvhive);
            zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry entry;
            byte[] buff = new byte[20 * 1024];
            while ((entry = zipInputStream.getNextEntry()) != null)
            {
                File newFile = new File(pathToApplication.getAbsoluteFile() + "/" + entry.getName());
                //Проверяем, если это директория  , тогда создадим папку и будем вносить туда =)
                if (entry.isDirectory())
                {
                    createDirectory(newFile);
                    continue;
                }
                FileOutputStream fos = new FileOutputStream(newFile);
                int l;
                while ((l = zipInputStream.read(buff)) > 0)
                {
                    fos.write(buff, 0, l);
                }
            }
            zipInputStream.close();
            fileArcvhive.delete();
        } finally
        {
            if (fileInputStream != null)
            {
                fileInputStream.close();
            }
            if (zipInputStream != null)
            {
                zipInputStream.close();
            }
        }
    }

    private void createDirectory(File file)
    {
        file.mkdirs();
    }
}
