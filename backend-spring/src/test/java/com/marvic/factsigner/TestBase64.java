package com.marvic.factsigner;

import software.amazon.awssdk.utils.IoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

public class TestBase64 {

    public static void main(String[] args) {
        File f = new File("/Users/marvic/Home/ssl/OPENJSOFTSOFTWARECONSULTING.pfx");
        try (InputStream in = new FileInputStream(f)) {
            byte[] sourceBytes = IoUtils.toByteArray(in);
            String encodedString = Base64.getEncoder().encodeToString(sourceBytes);
            System.out.println("---");
            System.out.println(encodedString);
            System.out.println("---");
        } catch(Exception e) {
            e.printStackTrace();
        }

    }



}
