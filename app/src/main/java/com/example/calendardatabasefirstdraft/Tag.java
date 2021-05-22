package com.example.calendardatabasefirstdraft;

public class Tag {
    int tagID;
    String tagName;

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(int tagID, String tagName) {
        this.tagID = tagID;
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return tagName;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
