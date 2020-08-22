package com.devculi.sway.utils;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;

public class ImageUtils {

    public static String encodeToBase64(String url) {
        try (InputStream inputStream = new URL(url).openStream()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return new String(Base64.encodeBase64(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decodeToImage(String slug, String imageValue) {
        try {
            //This will decode the String which is encoded by using Base64 class
            byte[] imageByte = Base64.decodeBase64(imageValue);
            String photoUri = "photos/" + slug + ".png";
            String rootPath = System.getProperty("user.dir") + "/";
            new FileOutputStream(rootPath + photoUri).write(imageByte);

            return photoUri;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String data = encodeToBase64("https://icdn.dantri.com.vn/thumb_w/640/2017/1-1510967806416.jpg");
        byte[] imageByte = Base64.decodeBase64(data.getBytes());
        String photoUri = "photos/" + "1" + ".png";
        String rootPath = System.getProperty("user.dir") + "/";
        try {
            new FileOutputStream(rootPath + photoUri).write(imageByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(data);
//        decodeToImage("1",data);
//        System.out.println(decodeToImage("1", buffer.toString()));
    }
}
