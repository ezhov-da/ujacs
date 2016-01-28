package ru.ezhov.ujacsappupdater;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.awt.Desktop;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;
import javax.swing.JOptionPane;
import ru.ezhov.common.objects.ujacs.Commands;
import ru.ezhov.common.objects.ujacs.InformationClass;

/**
 *
 * @author ezhov_da
 */
public class UpdateRunner extends Thread {

    private static final Logger LOG = Logger.getLogger(UpdateRunner.class.getName());
    private final String nameApp;
    private final String version;
    private final String host;
    private final int port;
    private Socket socket;

    public UpdateRunner(String nameApp, String version, String host, int port) {
        this.nameApp = nameApp;
        this.version = version;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            checkVersion();
        } catch (IOException ex) {
            Logger.getLogger(UpdateRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkVersion() throws IOException {
        createSocket();
        boolean notEqualsVersion = isVersionNotEquals(createInformationClassToXml());
        if (!notEqualsVersion) {
            runUpdateApp();
        } else {
            startCheckerVersion();
        }
    }

    private void createSocket() throws IOException, UnknownHostException {
        socket = new Socket(host, port);
    }

    private String createInformationClassToXml() {
        InformationClass informationClass = createInformationClass();
        XStream xStream = new XStream(new DomDriver());
        Annotations.configureAliases(xStream, InformationClass.class);
        String stringXml = xStream.toXML(informationClass);
        return stringXml;
    }

    private InformationClass createInformationClass() {
        InformationClass informationClass = new InformationClass(nameApp, Commands.CHECK_UPDATE);
        informationClass.setDate(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()));
        informationClass.setUserName(System.getProperty("user.name"));
        informationClass.setVersion(version);
        return informationClass;
    }

    private boolean isVersionNotEquals(String infoClassXml) throws IOException {
        sendInfoClass(infoClassXml);
        DataInputStream serializer = new DataInputStream(socket.getInputStream());
        boolean change = serializer.readBoolean();
        return !change;
    }

    private void sendInfoClass(String infoClassXml) throws IOException {
        DataOutputStream serializer = new DataOutputStream(socket.getOutputStream());
        serializer.writeUTF(infoClassXml);
        serializer.flush();
    }

    private void runUpdateApp() throws IOException {
        LOG.log(Level.INFO, "запускается обновление приложения");
        File pathToLoad = new File("");
        String parentPathToLoad = pathToLoad.getAbsoluteFile().getParent();
        System.out.println(pathToLoad.getAbsolutePath());
        File pathToParent = new File(parentPathToLoad + File.separator + "UJAC.jar");
        Desktop.getDesktop().open(pathToParent);
        System.exit(0);
    }

    private void startCheckerVersion() throws IOException {
        LOG.log(Level.INFO, "запущена проверка версий");
        boolean idOK = false;
        while (true) {
            createSocket();
            boolean notEqualsVersion = isVersionNotEquals(createInformationClassToXml());
            if (notEqualsVersion && !idOK) {
                StringBuilder strB = new StringBuilder();
                strB.append("ВАЖНО! Приложение необходимо обновить!\n");
                strB.append("Не гарантируется корректная работа необновленного приложения.\n");
                strB.append("При первой же возможности необходимо выполнить шаги ниже: \n\n");
                strB.append("Для обновления приложения необходимо:\n");
                strB.append("1. Закрыть приложение.\n");
                strB.append("2. Запустить заново с рабочего стола.\n");
                JOptionPane.showMessageDialog(null, strB.toString(), "Обновление", JOptionPane.INFORMATION_MESSAGE);
                idOK = true;
            }
            try {
                Thread.sleep(300000L);
            } catch (InterruptedException ex) {
                LOG.log(Level.INFO, "ошибка на ожидании потока ", ex);
            }
        }
    }
}
