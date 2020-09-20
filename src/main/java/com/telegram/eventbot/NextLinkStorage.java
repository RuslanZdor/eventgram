package com.telegram.eventbot;

import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class NextLinkStorage {

    private static Map<Integer, String> storage = new WeakHashMap<>();

    public static int saveURL(String url) {
        int hashcode = url.hashCode();
        storage.put(hashcode, url);
        return hashcode;
    }

    public static Optional<String> getURL(int hashcode) {
        String url = storage.get(hashcode);
        if (url != null) {
            return Optional.of(url);
        }
        return Optional.empty();
    }

}
