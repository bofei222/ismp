package com.nei.ismp.service;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;

public class Main {
    public static void main(String[] args) {
        File[] roots = File.listRoots();
        System.out.println(roots.length);
        for (File file : roots) {
            System.out.println("======" + file);
        }
        System.out.println("===========");
        for (final FileStore store : FileSystems.getDefault().getFileStores()) {
            System.out.println(store.toString() + "======== " + store.name() + "===" + store.type());

        }
    }
}
