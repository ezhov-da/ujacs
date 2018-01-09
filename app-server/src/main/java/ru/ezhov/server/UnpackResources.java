package ru.ezhov.server;

import ru.ezhov.server.unpackJarResources.UnpackJarResources;

import java.io.InputStream;

public class UnpackResources {
    public void unpack() throws Exception {
        try (InputStream inputStream =
                     UnpackJarResources.class.getResourceAsStream("/server/unpack.xml")) {

            UnpackJarResources unpackJarResources =
                    new UnpackJarResources(inputStream, ".");

            unpackJarResources.createAndCopy();
        }
    }
}
