package ru.ezhov.ujatools;


import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Тестируем распаковку файла
 * <p>
 * @author ezhov_da
 */
@Ignore
public class UnzipFileTest
{

    public UnzipFileTest()
    {
    }

    @Test
    public void testSomeMethod()
    {
        File directory = new File("E:\\_test_app_service");
        File file = new File("E:\\_test_app_service\\output.zip");
        UnzipFile unzipFile = new UnzipFile();
        try
        {
            unzipFile.unzip(directory, file);
        } catch (Exception ex)
        {
            Logger.getLogger(UnzipFileTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
