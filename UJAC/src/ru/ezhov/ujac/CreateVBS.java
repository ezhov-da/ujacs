package ru.ezhov.ujac;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RRNDeonisiusEZH
 */
public class CreateVBS
{
    /**
     *
     * @param pathToVBS        - путь к VBS
     * @param nameApplication  -название ярлыка
     * @param pathToICO        -полный путь к иконке
     * @param pathApplication  - путь к приложению полный
     * @param pathToParent     - рабочая папка
     * @param textForCreateVbs - текст для записи в vbs
     * <p>
     * @return полный путь к VBS
     * @throws java.io.IOException
     */
    public String create(String pathToVBS, String nameApplication, String pathToICO, String pathApplication, String pathToParent, String textForCreateVbs)
            throws IOException
    {
        FileWriter fileInputStream = null;
        try
        {
            File file = new File(pathToVBS + File.separator + "createShortCut.vbs");
            fileInputStream = new FileWriter(file);
            String text = String.format(textForCreateVbs, nameApplication, pathToICO, pathApplication, pathToParent);
            fileInputStream.write(text);
            fileInputStream.close();
            return file.getAbsolutePath();
        } catch (IOException ex)
        {
            Logger.getLogger(CreateVBS.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            if (fileInputStream != null)
            {
                fileInputStream.close();
            }
        }
        return null;
    }
}
