package ru.ezhov.server.unpackJarResources;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnpackJarResources {
    private static final Logger LOG = Logger.getLogger(UnpackJarResources.class.getName());

    private InputStream inputStreamFileXml;
    private String pathRootCreateFolders;

    public UnpackJarResources(
            InputStream inputStreamFileXml,
            String pathRootCreateFolders) {
        this.inputStreamFileXml = inputStreamFileXml;
        this.pathRootCreateFolders = pathRootCreateFolders;
    }

    public void createAndCopy() throws IOException {
        Directory directory =
                JAXB.unmarshal(inputStreamFileXml, Directory.class);
        recursiveProcess(directory, pathRootCreateFolders, "");
    }

    private void recursiveProcess(Directory directory, String pathTo, String pathFrom) throws IOException {
        String pathFromFull;
        if (pathFrom == null || pathFrom.isEmpty()) {
            pathFromFull = "/" + directory.getName();
        } else {
            pathFromFull = pathFrom + "/" + directory.getName();
        }

        String pathToFull = pathTo + "/" + directory.getName();

        createFolder(pathToFull);

        List<Directory> directories = directory.getDirectories();
        for (Directory dir : directories) {
            recursiveProcess(dir, pathToFull, pathFromFull);
        }

        List<File> files = directory.getFiles();
        for (File file : files) {
            copyFile(
                    pathFromFull + "/" + file.getName(),
                    pathToFull + "/" + file.getName()
            );
        }
    }

    private void createFolder(String pathCreateFolder) {
        java.io.File file = new java.io.File(pathCreateFolder);
        if (!file.exists()) {
            file.mkdirs();
            LOG.log(Level.CONFIG, "Создана папка [{0}]", pathCreateFolder);
        }
    }

    private void copyFile(String pathFromFile, String pathToFile) throws IOException {
        java.io.File file = new java.io.File(pathToFile);
        if (!file.exists()) {
            LOG.log(Level.CONFIG, "Копирование файла: [{0}] в [{1}]", new Object[]{pathFromFile, pathToFile});
            Files.copy(
                    getClass().getResourceAsStream(pathFromFile),
                    file.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
    }
}
