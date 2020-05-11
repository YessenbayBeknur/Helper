package com.example.helper;



import java.util.ArrayList;
import java.util.Comparator;

public class Post {
    private String date;
    private String text;
    private String title;
    private String type;
    private String color;
    private String id;
    private String who;
    private boolean solved;
    private ArrayList<String> likes;
    private ArrayList<String> photos;

    public Post (String title,String text,String date,String type,String color,String id,String who,boolean solved,ArrayList<String> likes,ArrayList<String> photos){
        this.date = date;
        this.text = text;
        this.title = title;
        this.type = type;
        this.color = color;
        this.id = id;
        this.who = who;
        this.solved =solved;
        this.likes = likes;
        this.photos = photos;
    }

    public Post(){

    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public ArrayList<String> getPhotos() {
        return photos;
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

    public String getColor() {
        return color;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setColor(String color) { this.color = color; }

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

    public static Comparator<Post> PostLikeComparator = new Comparator<Post>() {

        public int compare(Post p1, Post p2) {
            int s1 = p1.likes.size();
            int s2 = p2.likes.size();


            return s2 - s1;


        }};

    public static Comparator<Post> PostSolveComparator = new Comparator<Post>() {

        public int compare(Post p1, Post p2) {
            int s1 = 0;
            int s2 = 0;
            if(p1.solved){
                s1 = 1;
            }
            if (p2.solved){
                s2 = 1;
            }

            return s2 - s1;

        }};


}
