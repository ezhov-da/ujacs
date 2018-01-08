package ru.ezhov.server;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

@Ignore
public class UnpackTest {
    @Test
    public void test() {
        File file = new File("/unpack");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.isDirectory());
    }
}