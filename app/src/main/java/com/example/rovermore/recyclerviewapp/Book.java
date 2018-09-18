package com.example.rovermore.recyclerviewapp;

import java.util.ArrayList;

public class Book {

    private String tit;
    private ArrayList<String> author;
    private String year;
    private String imageUrl;

    public Book(ArrayList<String> authors, String title, String yy, String imageUrl) {


        this.author = authors;
        this.tit = title;
        this.year = yy;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl (){

        return imageUrl;
    }

    public String getNameBook (){

        return tit;
    }

    public ArrayList<String> getAuthors(){

        return author;
    }

    public String getYear(){

        return year;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
