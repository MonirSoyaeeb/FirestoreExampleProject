package com.example.firestoreexampleproject;

import com.google.firebase.firestore.Exclude;

public class Note {


    private String documentID;
    private String title;
    private String description;
    private int priority;

    public Note(String title, String description, int priority){
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    @Exclude
    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
    public Note(){
        // public no-arg constructor is needed
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
