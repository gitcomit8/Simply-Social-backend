package com.mirza.simplysocial.model;

public class PostRequest {
    private String mediaFile;
    private String username;

    public PostRequest() {
    }

    public String getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(String mediaFile) {
        this.mediaFile = mediaFile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}