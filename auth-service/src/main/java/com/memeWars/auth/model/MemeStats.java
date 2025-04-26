package com.memeWars.auth.model;

public class MemeStats {
    private int createdMemes;
    private int upvotes;
    private int downvotes;

    // Getters and setters
    public int getCreatedMemes() {
        return createdMemes;
    }

    public void setCreatedMemes(int createdMemes) {
        this.createdMemes = createdMemes;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    // Constructor, toString, etc.
}
