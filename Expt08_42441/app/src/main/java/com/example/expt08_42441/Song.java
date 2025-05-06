package com.example.expt08_42441;

public class Song {
    private String title;
    private String artist;
    private int resourceId;
    private int coverResourceId;

    public Song(String title, String artist, int resourceId, int coverResourceId) {
        this.title = title;
        this.artist = artist;
        this.resourceId = resourceId;
        this.coverResourceId = coverResourceId;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getResourceId() { return resourceId; }
    public int getCoverResourceId() { return coverResourceId; }
}