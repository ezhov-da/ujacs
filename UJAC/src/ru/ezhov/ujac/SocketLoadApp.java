package ru.ezhov.ujac;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.io.xml.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ru.ezhov.common.objects.ujacs.*;
import ru.ezhov.common.objects.ujacs.client.ClientConfig;
import ru.ezhov.common.objects.ujacs.server.ApplicationConfig;

/**
 * Обрабатываем подключение
 *
 * @author ezhov_da
 */
public class SocketLoadApp extends Thread {

    private static final Logger LOG = Logger.getLogger(SocketLoadApp.class.getName());
    private ClientConfig clientConfig;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ApplicationConfig applicationConfig;

    @Override
    public void run() {
        startClient();
    }

    private void startClient() {
        try {
            LOG.info("загружаем файл настроек");
            loadClientConfig();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ошибка чтения файла настроек");
            System.exit(-10);
        }
        treatmentSocket();
    }

    private void treatmentSocket() {
        try {
            createSocket();
            createStream();
            InformationClass informationClass = createInfoClass();
            sendInfoObject(informationClass);
            readAppConfigFromSocket();
            loadFileFromSocket();
            removeAndCreateFolder();
            unZipArchive();
            createVbs();
            socket.close();
            LOG.info("загрузка файла завершена");
            JOptionPane.showMessageDialog(null, "Приложение обновлено!", "Обновление приложения...", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ошибка работы сокета");
        } catch (UnsupportedOperationException ex) {
            LOG.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                //
            }
        }
    }

    private void loadClientConfig() throws IOException {
        clientConfig = LoadProperties.INSTANCE.getProperties();
        LOG.log(Level.INFO, "получен файл с настройками приложения:\n{0}", clientConfig.toString());
    }

    private void createSocket() throws IOException {
        LOG.info("создали сокет");
        socket = new Socket(clientConfig.getHost(), clientConfig.getPort());
    }

    private void createStream() throws IOException {
        LOG.info("создали OutputStream и InputStream сокета");
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
    }

    private InformationClass createInfoClass() {
        InformationClass informationClass = new InformationClass(clientConfig.getNameApp(), Commands.NEW_LOAD);
        informationClass.setDate(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()));
        informationClass.setUserName(System.getProperty("user.name"));
        return informationClass;
    }

    private void sendInfoObject(InformationClass informationClass) throws IOException {
        LOG.info("отправляем информационный объект");
        String string = createXmlFromInfoObject(informationClass);
        dataOutputStream.writeUTF(string);
        dataOutputStream.flush();
    }

    private String createXmlFromInfoObject(InformationClass informationClass) {
        XStream xStream = new XStream(new DomDriver());
        Annotations.configureAliases(xStream, InformationClass.class);
        return xStream.toXML(informationClass);
    }

    private void readAppConfigFromSocket() throws IOException {
        LOG.info("читаем объект с настройками");
        //здесь может принимать false, если приложение не поддерживается сервером обновлений
        String xmlAppConfig = dataInputStream.readUTF();
        if (!"false".equals(xmlAppConfig)) {
            XStream xStream = new XStream(new DomDriver());
            Annotations.configureAliases(xStream, ApplicationConfig.class);
            applicationConfig = (ApplicationConfig) xStream.fromXML(xmlAppConfig);
            LOG.log(Level.INFO, "прочитан конфигурационный файл:\n{0}", applicationConfig.toString());
        } else {
            throw new UnsupportedOperationException("Неподдерживаемое приложение для обновления");
        }

    }

    private void loadFileFromSocket() throws IOException {
        LOG.info("загружаем файл");
        File file = new File(applicationConfig.getNameArchive());
        file.delete();
        long fileSize = dataInputStream.readLong(); //получаем размер файла

        if (fileSize == 0) {
            System.exit(0);
        }
        FileOutputStream fileWriter = null;
        try {
            fileWriter = new FileOutputStream(file);
            int count, total = 0;
            byte[] buffer = new byte[5 * 1024];
            while ((count = dataInputStream.read(buffer)) != -1) {
                fileWriter.write(buffer, 0, count);
                total += count;
                if (total == fileSize) {
                    break;
                }
                fileWriter.flush();
            }
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    private void removeAndCreateFolder() throws UnsupportedEncodingException, IOException {
        LOG.info("удалем и содаем папку");
        ClearAndCreateFolder clearAndCreateFolder = new ClearAndCreateFolder();
        clearAndCreateFolder.clear(applicationConfig.getNameFolderApp());
        clearAndCreateFolder.createFolder(applicationConfig.getNameFolderApp());
    }

    private void unZipArchive() throws IOException {
        LOG.info("распаковываем архив");
        new ExtractZIP().extractZIP(applicationConfig.getNameArchive(), applicationConfig.getNameFolderApp());
    }

    private void createVbs() throws IOException {
        LOG.info("создаем vbs");
        String basic = "cmd /c \"" + pathForRunVbs() + "\"";
        Runtime.getRuntime().exec(basic);
    }

    private String pathForRunVbs() throws IOException {
        File folderApp = new File(applicationConfig.getNameFolderApp());
        String absoluteFolderPathApp = folderApp.getAbsolutePath();
        String pathToICO;
        String pathApplication;
        pathToICO = absoluteFolderPathApp + File.separator + applicationConfig.getLnkNameFromArchive();
        pathApplication = absoluteFolderPathApp + File.separator + applicationConfig.getFileForRunApp();
        CreateVBS createVBS = new CreateVBS();
        String path = createVBS.create(
                absoluteFolderPathApp,
                applicationConfig.getLnkNameOnDesktop(),
                pathToICO,
                pathApplication,
                absoluteFolderPathApp,
                applicationConfig.getShortCutCreate()
        );
        return path;
    }

}
