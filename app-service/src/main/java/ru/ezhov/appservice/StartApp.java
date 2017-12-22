package ru.ezhov.appservice;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.ujatools.ParentPath;
import ru.ezhov.ujatools.xmlobjects.RunnerApp;
import ru.ezhov.ujatools.xmlobjects.RunnerAppHolder;

/**
 * Класс, который сохраняет список запущенных приложений
 * , и возвращает список приложений
 * <p>
 * @author ezhov_da
 */
public class StartApp
{
    private static final Logger LOG = Logger.getLogger(StartApp.class.getName());
    private static final String FILE_NAME = "list_runner_apps.xml";
    private RunnerAppHolder runnerAppHolder;
    private File fileSaveApps;
    private XStream xStream;

    public StartApp() throws IOException
    {
        runnerAppHolder = new RunnerAppHolder();
        fileSaveApps = new File(ParentPath.getPatentPathWithEnd(FILE_NAME));
        LOG.log(Level.INFO, "Файл для сохранения запущенных приложений: {0}", fileSaveApps.getAbsolutePath());
        xStream = new XStream(new DomDriver());
        xStream.alias("runnerAppHolder", RunnerAppHolder.class);
        xStream.alias("runnerApp", RunnerApp.class);
    }

    public void addRunnerApp(RunnerApp runnerApp)
    {
        LOG.log(Level.INFO, "Добавлено приложение: {0}", runnerApp.getNameAppSystem());
        runnerAppHolder.addRunnerApp(runnerApp);
    }

    public void save()
    {
        try
        {
            LOG.info("Сохранение объекта");
            xStream.toXML(runnerAppHolder, new FileOutputStream(fileSaveApps));
            LOG.info("Объект сохранен");
        } catch (IOException ex)
        {
            LOG.log(Level.SEVERE, "Не удалось сохранить файл запущенных приложений", ex);
        }
    }

    public void load() throws FileNotFoundException
    {
        if (fileSaveApps.exists())
        {
            LOG.info("Загрузка объекта");
            runnerAppHolder = (RunnerAppHolder) xStream.fromXML(new FileInputStream(fileSaveApps));
            LOG.info("Объект загружен");
        }
    }

    public List<RunnerApp> getListRunnerApp()
    {
        return runnerAppHolder.getRunnerApps();
    }
}
