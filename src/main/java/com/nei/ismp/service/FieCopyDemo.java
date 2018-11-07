package com.nei.ismp.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FieCopyDemo {
    public static void main(String[] args) throws IOException {
        Files.copy(new File("D:\\testDir\\55-6tb\\b").toPath(), new File("D:\\testDir\\55-9tb\\b").toPath());
        System.out.println("=========");
    }
}
