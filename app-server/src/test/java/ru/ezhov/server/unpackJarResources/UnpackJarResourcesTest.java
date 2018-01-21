package ru.ezhov.server.unpackJarResources;

import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

@Ignore
public class UnpackJarResourcesTest {
    @Test
    public void createAndCopy() throws Exception {
//        Directory directory = new Directory("test", null);
//        directory.addFile(new File("testFile", directory.getName()));
//        directory.addDirectory(new Directory("test dir", directory.getName()));
//
//        StringWriter stringWriter = new StringWriter();
//        JAXB.marshal(directory, stringWriter);
//        String s = stringWriter.toString();
//        try {
//            stringWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(s);

        try (InputStream inputStream =
                     UnpackJarResources.class.getResourceAsStream("/server/unpack.xml")) {

            UnpackJarResources unpackJarResources =
                    new UnpackJarResources(inputStream, ".");

            unpackJarResources.createAndCopy();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
