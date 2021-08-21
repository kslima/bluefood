package com.kslima.bluefood.util;

import java.util.Arrays;

public enum FileType {

    PNG("image/png", "png"),
    JPG("image/jpeg", "jpg");

    String mimeType;
    String extension;

    FileType(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    public String getExtension() {
        return this.extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public boolean sameOf(String mimeType) {
        return this.mimeType.equalsIgnoreCase(mimeType);
    }

    public static FileType of(String mimeType) {
        return Arrays.stream(values())
                .filter(f -> f.sameOf(mimeType))
                .findFirst().orElse(null);
    }
}
