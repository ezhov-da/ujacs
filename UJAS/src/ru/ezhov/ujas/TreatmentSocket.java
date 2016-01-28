package ru.ezhov.ujas;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.net.Socket;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import ru.ezhov.common.objects.ujacs.InformationClass;
import ru.ezhov.common.objects.ujacs.server.ApplicationConfig;
import ru.ezhov.common.objects.ujacs.server.CommonConfig;

/**
 * Класс обрабатывает подключения
 * <p>
 * @author ezhov_da
 */
public class TreatmentSocket extends Thread {
    
    private static final Logger LOG = Logger.getLogger(TreatmentSocket.class.getName());
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private InformationClass informationClass;
    private CommonConfig commonConfig;
    
    public TreatmentSocket(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }
    
    @Override
    public void run() {
        LOG.log(Level.INFO, "соединились: {0}", socket.getInetAddress().getHostAddress());
        try {
            startConnect();
        } catch (FileNotFoundException ex) {
            LOG.log(Level.WARNING, "не удалось загрузить файл настроек", ex);
        } catch (IOException ex) {
            LOG.log(Level.WARNING, "ошибка чтения присланного объекта", ex);
        }
        
    }
    
    private void startConnect() throws FileNotFoundException, IOException {
        createInfoObjectFromSocket();
        loadCommonConfig();
        
        try {
            viewApp();
        } catch (BadLocationException ex) {
            LOG.log(Level.SEVERE, "не удалось прочитать файл для отправки", ex);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                //пока не волнует корректность закрытия подключения
            }
        }
    }
    
    private void createInfoObjectFromSocket() throws IOException {
        LOG.info("создаем присланный объект");
        String xmlObject = getXmlObject();
        XStream xStream = new XStream(new DomDriver());
        Annotations.configureAliases(xStream, InformationClass.class);
        LOG.info("создаем присланный объект из xml");
        informationClass = (InformationClass) xStream.fromXML(xmlObject);
        LOG.log(
                Level.INFO,
                "подключился:\nдата: {0}\nпользователь: {1}\nназвание приложения: {2}",
                new Object[]{
                    informationClass.getDate(), informationClass.getUserName(), informationClass.getNameApplication()
                }
        );
    }
    
    private String getXmlObject() throws IOException {
        Object object = dataInputStream.readUTF();
        LOG.log(Level.INFO, "считали xml:\n{0}", object.toString());
        return object.toString();
    }

    /**
     * загружаем общие настройки сервера
     *
     * @throws FileNotFoundException
     */
    private void loadCommonConfig() throws FileNotFoundException {
        LOG.info("загружается общий конфигурационный файл");
        commonConfig = LoadProperties.getCommonConfig();
        LOG.info("загружен общий конфигурационный файл");
    }

    /**
     * просмотр наличия указанного приложения
     *
     * @throws IOException
     * @throws FileNotFoundException
     * @throws BadLocationException
     */
    private void viewApp() throws IOException, FileNotFoundException, BadLocationException {
        List<ApplicationConfig> applicationConfigs = commonConfig.getApplicationConfigs();
        ApplicationConfig applicationConfigForCheck = new ApplicationConfig();
        applicationConfigForCheck.setName(informationClass.getNameApplication());
        if (applicationConfigs.contains(applicationConfigForCheck)) {
            for (ApplicationConfig applicationConfig : applicationConfigs) {
                if (applicationConfig.getName().equals(informationClass.getNameApplication())) {
                    finderApp(applicationConfig);
                }
            }
        } else {
            sendUnsupportedApp();
        }
    }
    
    private void sendUnsupportedApp() throws IOException {
        dataOutputStream.writeUTF("False");
        dataOutputStream.flush();
    }
    
    private void finderApp(ApplicationConfig applicationConfig) throws IOException, FileNotFoundException, BadLocationException {
        switch (informationClass.getCommands()) {
            case CHECK_UPDATE:
                LOG.log(Level.INFO, "проверка версии");
                commandCheckVersion(applicationConfig);
                break;
            case NEW_LOAD:
                LOG.log(Level.INFO, "загрузка файла");
                commandLoadFile(applicationConfig);
                break;
            default:
                throw new IllegalArgumentException("неподдерживаемая команда");
        }
    }
    
    private void commandCheckVersion(ApplicationConfig applicationConfig) throws IOException {
        LOG.log(Level.INFO, "команда на проверку версии:\nназвание приложения: {0}\nтекущая версия приложения: {0},", new Object[]{
            applicationConfig.getName(), applicationConfig.getVersion()
        });
        if (isNotMatchesVersion(applicationConfig)) {
            LOG.log(Level.INFO, "версия не совпадает");
            dataOutputStream.writeBoolean(false);
        } else {
            LOG.log(Level.INFO, "версия совпадает");
            dataOutputStream.writeBoolean(true);
        }
        dataOutputStream.flush();
    }
    
    private boolean isNotMatchesVersion(ApplicationConfig applicationConfig) {
        return !applicationConfig.getVersion().equals(informationClass.getVersion());
    }
    
    private void commandLoadFile(ApplicationConfig applicationConfig) throws IOException, FileNotFoundException, BadLocationException {
        sendAppConfig(applicationConfig);
        sendFile(applicationConfig);
    }
    
    private void sendAppConfig(ApplicationConfig applicationConfig) throws IOException {
        String xmlStr = createXmlAppConfig(applicationConfig);
        dataOutputStream.writeUTF(xmlStr);
        dataOutputStream.flush();
    }
    
    private String createXmlAppConfig(ApplicationConfig applicationConfig) {
        XStream xStream = new XStream(new DomDriver());
        Annotations.configureAliases(xStream, ApplicationConfig.class);
        return xStream.toXML(applicationConfig);
    }
    
    private void sendFile(ApplicationConfig applicationConfig) throws FileNotFoundException, IOException, BadLocationException {
        FileInputStream fileExporReader = null;
        try {
            File exportFile = new File(applicationConfig.getFullPathToArchiveApp());
            fileExporReader = new FileInputStream(exportFile);
            //отправялем размер файла
            dataOutputStream.writeLong(exportFile.length());
            //Вот так мы отправляем файл
            byte[] buffer = new byte[5 * 1024];
            int count;
            while ((count = fileExporReader.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, count);
            }
            dataOutputStream.flush();
            LOG.log(
                    Level.INFO,
                    "файл [{0}] приложения [{1}] передан пользователю {2}",
                    new Object[]{
                        applicationConfig.getFullPathToArchiveApp(), informationClass.getNameApplication(), informationClass.getUserName()
                    }
            );
        } finally {
            if (fileExporReader != null) {
                fileExporReader.close();
            }
        }
    }
}
