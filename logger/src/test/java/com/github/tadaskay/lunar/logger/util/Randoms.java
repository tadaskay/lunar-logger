package com.github.tadaskay.lunar.logger.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public abstract class Randoms {

    public static URL url() {
        try {
            return new URL("http://test.local/" + string());
        } catch (MalformedURLException ignored) {
            return null;
        }
    }

    public static String string() {
        return UUID.randomUUID().toString();
    }

}
