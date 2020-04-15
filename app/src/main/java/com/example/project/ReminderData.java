package com.example.project;
//declaring variables and methods
public class ReminderData {
    private int RId;
    private String RContent;
    private int RImportant;

    public ReminderData(int id, String content, int important) {
        RId = id;
        RImportant = important;
        RContent = content;
    }
    public int getId() {
        return RId;
    }
    public void setId(int id) {
        RId = id;
    }
    public int getImportant() {
        return RImportant;
    }
    public void setImportant(int important) {
        RImportant = important;
    }
    public String getContent() {
        return RContent;
    }
    public void setContent(String content) {
        RContent = content;
    }
}