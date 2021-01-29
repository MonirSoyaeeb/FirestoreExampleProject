package com.example.firestoreexampleproject;

import com.google.firebase.firestore.Exclude;

public class Note {


    private String documentID;
    private String title;
    private String description;

    public Note(String title, String description){
        this.title = title;
        this.description = description;
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

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }
}
