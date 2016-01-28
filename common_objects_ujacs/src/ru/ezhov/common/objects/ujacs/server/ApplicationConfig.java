package ru.ezhov.common.objects.ujacs.server;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.logging.Logger;

/**
 *
 * @author ezhov_da
 */
@XStreamAlias("app")
public class ApplicationConfig {

    private static final Logger LOG = Logger.getLogger(ApplicationConfig.class.getName());
    private String version;
    private String extFile;
    private String name;
    private String nameArchive;
    private String nameFolderApp;
    private String fileForRunApp;
    private String lnkNameOnDesktop;
    private String lnkNameFromArchive;
    private String shortCutCreate;
    private String pathToArchiveOnServer;

    public String getPathToArchiveOnServer() {
        return pathToArchiveOnServer;
    }

    public void setPathToArchiveOnServer(String pathToArchiveOnServer) {
        this.pathToArchiveOnServer = pathToArchiveOnServer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getExtFile() {
        return extFile;
    }

    public void setExtFile(String extFile) {
        this.extFile = extFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameArchive() {
        return nameArchive;
    }

    public void setNameArchive(String nameArchive) {
        this.nameArchive = nameArchive;
    }

    public String getNameFolderApp() {
        return nameFolderApp;
    }

    public void setNameFolderApp(String nameFolderApp) {
        this.nameFolderApp = nameFolderApp;
    }

    public String getFileForRunApp() {
        return fileForRunApp;
    }

    public void setFileForRunApp(String fileForRunApp) {
        this.fileForRunApp = fileForRunApp;
    }

    public String getLnkNameOnDesktop() {
        return lnkNameOnDesktop;
    }

    public void setLnkNameOnDesktop(String lnkNameOnDesktop) {
        this.lnkNameOnDesktop = lnkNameOnDesktop;
    }

    public String getLnkNameFromArchive() {
        return lnkNameFromArchive;
    }

    public void setLnkNameFromArchive(String lnkNameFromArchive) {
        this.lnkNameFromArchive = lnkNameFromArchive;
    }

    public String getShortCutCreate() {
        return shortCutCreate;
    }

    public void setShortCutCreate(String shortCutCreate) {
        this.shortCutCreate = shortCutCreate;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getFullPathToArchiveApp() {
        return pathToArchiveOnServer + version + extFile;
    }
}
