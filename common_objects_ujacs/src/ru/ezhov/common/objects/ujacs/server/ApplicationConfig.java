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

    public String getFullPathToArchiveApp() {
        return pathToArchiveOnServer + version + extFile;
    }

    @Override
    public String toString() {
        return String.format("version: %s\n"
                + "extFile: %s\n"
                + "name: %s\n"
                + "nameArchive: %s\n"
                + "nameFolderApp: %s\n"
                + "fileForRunApp: %s\n"
                + "lnkNameOnDesktop: %s\n"
                + "lnkNameFromArchive: %s\n"
                + "shortCutCreate: %s\n"
                + "pathToArchiveOnServer: %s\n",
                version,
                extFile,
                name,
                nameArchive,
                nameFolderApp,
                fileForRunApp,
                lnkNameOnDesktop,
                lnkNameFromArchive,
                shortCutCreate,
                pathToArchiveOnServer);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApplicationConfig other = (ApplicationConfig) obj;
        return !((this.name == null) ? (other.name != null) : !this.name.equals(other.name));
    }

}
