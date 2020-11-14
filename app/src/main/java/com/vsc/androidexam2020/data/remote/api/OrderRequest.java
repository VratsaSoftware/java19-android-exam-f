package com.vsc.androidexam2020.data.remote.api;

public class OrderRequest {

    private String userId;
    private String filename;
    private long filesize;

    public OrderRequest(String userId) {
        this(userId, null, 0);
    }

    public OrderRequest(String filename, long filesize) {
        this(null, filename, filesize);
    }

    public OrderRequest(String userId, String filename, long filesize) {
        this.userId = userId;
        this.filename = filename;
        this.filesize = filesize;
    }

    public String getUserId() {
        return userId;
    }

    public String getFilename() {
        return filename;
    }

    public long getFilesize() {
        return filesize;
    }
}
