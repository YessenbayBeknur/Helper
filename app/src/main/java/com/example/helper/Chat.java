package com.example.helper;

public class Chat {
    private String who,text_chat;

    public Chat(String who,String text_chat){
        this.who = who;
        this.text_chat = text_chat;
    }

    public Chat(){

    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setText_chat(String text_chat) {
        this.text_chat = text_chat;
    }

    public String getWho() {
        return who;
    }

    public String getText_chat() {
        return text_chat;
    }
}
