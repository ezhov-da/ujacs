package ru.ezhov.udater;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.pathtofile.AbsolutePath;
import ru.ezhov.ujatools.LoadHttpProperties;
import ru.ezhov.ujatools.PropertiesConst;
import ru.ezhov.ujatools.PropertyHttpHolder;

/**
 * Загружаем настройки и запускаем приложение
 * <p>
 * @author ezhov_da
 */
public class LoaderAndRunner
{
    private static final Logger LOG = Logger.getLogger(LoaderAndRunner.class.getName());

    public void loadAndRun() throws IOException, Exception
    {
        initLoader();
        String path = createPathForRunFile();
        Runner runner = new Runner(path);
        runner.run();
    }

    private void initLoader() throws IOException
    {
        LoadHttpProperties loadHttpProperties = new LoadHttpProperties();
        loadHttpProperties.load();
    }

    private String createPathForRunFile() throws Exception
    {
        PropertyHttpHolder propertyHttpHolder = PropertyHttpHolder.getInstance();
        String path = AbsolutePath.getAbsolutePath(LoaderAndRunner.class);
        LOG.log(Level.INFO, "Путь к папке приложения: {0}", path);
        String pathToReal =
                path + File.separator
                + propertyHttpHolder.getProperty(PropertiesConst.NAME_FOLDER_APP_SERVICE_REAL);
        LOG.log(Level.INFO, "Реальный путь к приложению: {0}", pathToReal);
        String pathToJava = pathToReal + File.separator
                + propertyHttpHolder.getProperty(PropertiesConst.NAME_FOLDER_JAVA_REAL);
        LOG.log(Level.INFO, "Путь для запуска java: {0}", pathToJava);
        String constJava = propertyHttpHolder.getProperty(PropertiesConst.CONST_PATH_TO_JAVA);
        LOG.log(Level.INFO, "Константа замены java: {0}", constJava);
        String constPathToApp = propertyHttpHolder.getProperty(PropertiesConst.CONST_PATH_TO_APP);
        LOG.log(Level.INFO, "Константа замены пути: {0}", constPathToApp);
        String stringToRunAppService = propertyHttpHolder.getProperty(PropertiesConst.STRING_TO_RUN_APP_SERVICE);
        LOG.log(Level.INFO, "Строка запуска до замены: {0}", stringToRunAppService);
        String stringForRun = stringToRunAppService.replace(constJava, pathToJava).replace(constPathToApp, pathToReal);
        LOG.log(Level.INFO, "Строка запуска после замены: {0}", stringForRun);
        return stringForRun;
    }
}
