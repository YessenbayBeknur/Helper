package com.example.helper;

public class Comment {
    private String username,content,date;
    public Comment(){

    }

    public Comment(String username,String content,String date){
        this.username = username;
        this.content = content;
        this.date = date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }
}
