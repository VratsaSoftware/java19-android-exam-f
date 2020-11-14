package com.vsc.androidexam2020.data.remote.api;

public class OrderResponse {

    private String filename;
    private String filesize;
    private String prize;
    private long createdOn;
    private long completionTime;

    public String getFilename() {
        return filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public String getPrize() {
        return prize;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public long getCompletionTime() {
        return completionTime;
    }
}
