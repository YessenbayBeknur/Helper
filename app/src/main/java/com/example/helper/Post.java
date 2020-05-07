package com.example.helper;

import java.util.ArrayList;

public class Post {
    private String date;
    private String text;
    private String title;
    private String type;
    private int color;
    private String id;
    private String who;
    private boolean solved;
    private ArrayList<String> likes;

    public Post (String title,String text,String date,String type,int color,String id,String who,boolean solved,ArrayList<String> likes){
        this.date = date;
        this.text = text;
        this.title = title;
        this.type = type;
        this.color = color;
        this.id = id;
        this.who = who;
        this.solved =solved;
        this.likes = likes;
    }

    public Post(){

    }

    public boolean isSolved() {
        return solved;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public String getWho() {
        return who;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }


    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public int getColor() {
        return color;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setColor(int color) { this.color = color; }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setType(String type) { this.type = type; }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }


}
