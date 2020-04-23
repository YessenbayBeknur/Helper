package com.example.helper;

public class Post {
    private String date;
    private String text;
    private String title;
    private String type;
    private int color;

    public Post (String title,String text,String date,String type,int color){
        this.date = date;
        this.text = text;
        this.title = title;
        this.type = type;
        this.color = color;
    }

    public Post(){

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

    public int getColor() {
        return color;
    }

    public void setColor(int color) { this.color = color; }

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
