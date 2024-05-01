package com.example.th2.models;

import java.io.Serializable;

public class Music implements Serializable {
    private int id;
    private String name;
    private String singer;
    private String album;
    private String type;
    private boolean isLike;

    public Music() {
    }

    public Music(int id, String name, String singer, String album, String type, boolean isLike) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.type = type;
        this.isLike = isLike;
    }

    public Music(String name, String singer, String album, String type, boolean isLike) {
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.type = type;
        this.isLike = isLike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
