package com.example.project.Notes_file;

public class Note {
    private String title;
    private String content;

    //this class is for retrieveing data from Firestore and is called on the main notes page
    public Note(){}
    public Note(String title,String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}