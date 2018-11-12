package com.nei.ismp.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import sun.misc.BASE64Encoder;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class FileChecksum {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        long start = System.currentTimeMillis();
//        test();
        System.out.println("===================" + (System.currentTimeMillis() - start));

        long start2 = System.currentTimeMillis();
//        test();
        System.out.println("++++++++" + (System.currentTimeMillis() - start2));

        long start3 = System.currentTimeMillis();
//        test2();
        System.out.println("+++++++++++++++" + (System.currentTimeMillis() - start3));

        long start4 = System.currentTimeMillis();
        test4();
        System.out.println("================" + (System.currentTimeMillis() - start4));

    }

    public static void test() throws NoSuchAlgorithmException, IOException{
        String fileName = "D:\\Virtual Machines\\CentOS-7-x86_64-DVD-1611.iso";
        byte[] buffer= new byte[8192];
        int count;
        MessageDigest digest = MessageDigest.getInstance("MD5");
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
        while ((count = bis.read(buffer)) > 0) {
            digest.update(buffer, 0, count);
        }
        bis.close();

        byte[] hash = digest.digest();
        System.out.println(new BASE64Encoder().encode(hash));
    }

    public static void test2() throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buffer= new byte[8192];

        try (InputStream is = Files.newInputStream(Paths.get("D:\\Virtual Machines\\CentOS-7-x86_64-DVD-1611.iso"));
             DigestInputStream dis = new DigestInputStream(is, md))
        {
            /* Read decorated stream (dis) to EOF as normal... */
            while (dis.read(buffer) != -1) ;

        }
        byte[] digest = md.digest();

        System.out.println(new BASE64Encoder().encode(digest));
    }

    public static void test3() throws IOException {
        FileInputStream fis = new FileInputStream(new File("D:\\Virtual Machines\\CentOS-7-x86_64-DVD-1611.iso"));
        String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
        fis.close();
        System.out.println(md5);
    }

    public static void test4() throws IOException {
        byte[] buffer= new byte[8192];
        HashCode hash = com.google.common.io.Files.asByteSource(new File("D:\\Virtual Machines\\CentOS-7-x86_64-DVD-1611.iso")).hash(Hashing.crc32());
        System.out.println(hash);
    }

}
