package com.github.gabrielmagal.video_player.controller.enums;

public enum MediaTypeEnum {
    VIDEO("video/mp4"),
    PHOTO("image/png"),
    MUSIC("audio/mp3");

    private final String contentType;

    MediaTypeEnum(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
