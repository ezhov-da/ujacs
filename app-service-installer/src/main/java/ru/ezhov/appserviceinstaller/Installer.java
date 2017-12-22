package ru.ezhov.appserviceinstaller;

import java.awt.Desktop;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ru.ezhov.ujatools.HttpFileLoader;
import ru.ezhov.ujatools.PropertyHttpHolder;
import ru.ezhov.ujatools.UnzipFile;

/**
 * Класс, отвечающий за полный цикл установки "Сервиса приложений"
 * <p>
 * @author ezhov_da
 */
public class Installer
{
    private static final Logger LOG = Logger.getLogger(Installer.class.getName());

    private File directoryForInstall;

    private PropertyHttpHolder propertyHttpHolder;
    private File folderToFile;
    private String pathToAppServiceFolder;
    private String httpInstallAppServiceLoader;
    private String httpFileIconAppService;
    private String httpFileVBScript;
    private String nameZipArchive;
    private String nameArchive;
    private HttpFileLoader httpFileLoader;

    public Installer(File directoryForInstall)
    {
        this.directoryForInstall = directoryForInstall;
    }

    /**
     * Установка сервиса приложения
     */
    public void install()
    {
        propertyHttpHolder = PropertyHttpHolder.getInstance();
        initVarible();
        if (!folderToFile.canExecute())
        {
            installService();
        } else
        {
            JOptionPane.showMessageDialog(
                    null,
                    String.format(propertyHttpHolder.getProperty("error.app.already.install.body"), folderToFile.getAbsolutePath()),
                    propertyHttpHolder.getProperty("error.app.already.install.title"),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Инициализируем переменные
     */
    private void initVarible()
    {
        httpInstallAppServiceLoader = propertyHttpHolder.getProperty("http.install.app.service.loader");
        pathToAppServiceFolder = directoryForInstall.getAbsolutePath() + File.separator + propertyHttpHolder.getProperty("name.folder.app.service");
        folderToFile = new File(pathToAppServiceFolder);
    }

    /**
     * Устанавливаем сервис
     */
    private void installService()
    {
        folderToFile.mkdirs();
        nameZipArchive = propertyHttpHolder.getProperty("name.output.archive.app.service.install");
        nameArchive = pathToAppServiceFolder + File.separator + nameZipArchive;
        httpFileLoader = new HttpFileLoader(httpInstallAppServiceLoader, nameArchive);
        if (loadArchive())
        {
            if (unzipFile())
            {
                installApp();
            }
        }
    }

    /**
     * загружаем архив
     * <p>
     * @return
     */
    private boolean loadArchive()
    {
        try
        {
            httpFileLoader.load();
            return true;
        } catch (IOException ex)
        {
            Logger.getLogger(AppServiceInstaller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    null,
                    String.format(propertyHttpHolder.getProperty("error.load.install.file.servuce.app.body") + ": \n" + ex.getMessage(), httpInstallAppServiceLoader),
                    propertyHttpHolder.getProperty("error.load.install.file.servuce.app.title"),
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    /**
     * Распаковываем файл
     * <p>
     * @return
     */
    private boolean unzipFile()
    {
        UnzipFile unzipFile = new UnzipFile();
        File fileArchive = new File(nameArchive);
        try
        {
            unzipFile.unzip(new File(pathToAppServiceFolder), fileArchive);
            fileArchive.delete();
            return true;
        } catch (Exception ex)
        {
            Logger.getLogger(AppServiceInstaller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    null,
                    String.format(propertyHttpHolder.getProperty("error.unzip.file.body") + ": \n" + ex.getMessage(), httpInstallAppServiceLoader),
                    propertyHttpHolder.getProperty("error.unzip.file.title"),
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    private String nameIcon;
    private String fullPathToIcon;
    private String nameScript;
    private String fullPathToScript;
    private String nameApplication;

    /**
     * Устанавливаем
     */
    private void installApp()
    {
        httpFileIconAppService = propertyHttpHolder.getProperty("http.file.icon.app.service");
        nameIcon = propertyHttpHolder.getProperty("name.icon.app.service");
        fullPathToIcon = pathToAppServiceFolder + File.separator + nameIcon;
        httpFileLoader = new HttpFileLoader(httpFileIconAppService, fullPathToIcon);
        nameApplication = propertyHttpHolder.getProperty("name.app.service");
        if (!loadIcon())
        {
            return;
        }
        httpFileVBScript = propertyHttpHolder.getProperty("http.file.vbscript");
        nameScript = propertyHttpHolder.getProperty("name.file.vbscript");
        fullPathToScript = pathToAppServiceFolder + File.separator + nameScript;
        httpFileLoader = new HttpFileLoader(httpFileVBScript, fullPathToScript);
        if (loadScript())
        {
            if (installScript())
            {
                int runNow = JOptionPane.showConfirmDialog(
                        null,
                        String.format(propertyHttpHolder.getProperty("good.install.body"), nameApplication),
                        propertyHttpHolder.getProperty("good.install.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (runNow == JOptionPane.YES_OPTION)
                {
                    RunApp.run(nameApplication);
                }
                System.exit(0);
            }
        }
    }

    /**
     * Грузим иконку
     * <p>
     * @return
     */
    private boolean loadIcon()
    {
        try
        {
            httpFileLoader.load();
            return true;
        } catch (IOException ex)
        {
            Logger.getLogger(AppServiceInstaller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    null,
                    String.format(propertyHttpHolder.getProperty("error.load.icon.body") + ": \n" + ex.getMessage(), httpFileIconAppService),
                    propertyHttpHolder.getProperty("error.load.icon.title"),
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    /**
     * Грузим скрипт
     * <p>
     * @return
     */
    private boolean loadScript()
    {
        try
        {
            httpFileLoader.load();
            return true;
        } catch (IOException ex)
        {
            Logger.getLogger(AppServiceInstaller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    null,
                    String.format(propertyHttpHolder.getProperty("error.load.script.body") + ": \n" + ex.getMessage(), httpFileVBScript),
                    propertyHttpHolder.getProperty("error.load.script.title"),
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    private File fileScript;
    private Scanner scanner;
    private String pathToFileRunAppService;
    private String textForScript;

    /**
     * Инициализируем скрипт
     * <p>
     * @return
     */
    private boolean installScript()
    {
        try
        {
            fileScript = new File(fullPathToScript);
            scanner = new Scanner(fileScript, propertyHttpHolder.getProperty("charset.for.read.and.replace.vbscript.constants"));
            StringBuilder stringBuilder = new StringBuilder();
            pathToFileRunAppService =
                    pathToAppServiceFolder + File.separator + propertyHttpHolder.getProperty("file.run.service");
            while (scanner.hasNextLine())
            {
                String s = scanner.nextLine();
                s = replaceScript(s);
                stringBuilder.append(s);
                stringBuilder.append("\n");
            }
            scanner.close();
            textForScript = stringBuilder.toString();
            if (writeFile())
            {
                runScript();
            }
            return true;
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(AppServiceInstaller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    null,
                    String.format(propertyHttpHolder.getProperty("error.not.found.script.body") + ": \n" + ex.getMessage(), fullPathToScript),
                    propertyHttpHolder.getProperty("error.not.found.script.title"),
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    private String replaceScript(String s)
    {
        s = s.replace(":NAME_APPLICATION", nameApplication);
        s = s.replace(":PATH_TO_ICO", fullPathToIcon);
        s = s.replace(":PATH_TO_APP_RUN", pathToFileRunAppService);
        s = s.replace(":PATH_TO_APP_DIR", pathToAppServiceFolder);
        return s;
    }

    /**
     * Пишем файл со скриптом
     * <p>
     * @return
     */
    private boolean writeFile()
    {
        Writer out = null;
        try
        {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileScript), propertyHttpHolder.getProperty("charset.for.write.vbscript.create.link")));
            out.write(textForScript);
            out.flush();
            return true;
        } catch (IOException ex)
        {
            Logger.getLogger(AppServiceInstaller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    null,
                    String.format(propertyHttpHolder.getProperty("error.create.script.body") + ": \n" + ex.getMessage(), fullPathToScript),
                    propertyHttpHolder.getProperty("error.create.script.title"),
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        } finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                } catch (IOException ex)
                {
                    Logger.getLogger(AppServiceInstaller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Запускаем создание ярлыка
     * <p>
     * @return
     */
    private boolean runScript()
    {
        try
        {
            Desktop.getDesktop().open(fileScript);
            return true;
        } catch (IOException ex)
        {
            Logger.getLogger(AppServiceInstaller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(
                    null,
                    String.format(propertyHttpHolder.getProperty("error.run.script.body") + ": \n" + ex.getMessage(), fullPathToScript),
                    propertyHttpHolder.getProperty("error.run.script.title"),
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }
}
