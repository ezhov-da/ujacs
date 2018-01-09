package ru.ezhov.server.unpackJarResources;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Directory {
    @XmlAttribute(name = "name")
    private String name;

    @XmlElementWrapper(name = "dirs")
    @XmlElement(name = "dir")
    private List<Directory> directories = new ArrayList<>();

    @XmlElementWrapper(name = "files")
    @XmlElement(name = "file")
    private List<File> files = new ArrayList<>();

    public Directory() {
    }

    public Directory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addDirectory(Directory directory) {
        directories.add(directory);
    }

    public void addFile(File file) {
        files.add(file);
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public List<File> getFiles() {
        return files;
    }

}
