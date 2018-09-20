package com.example.rovermore.recyclerviewapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader {

    String userQuery;

    List<Book> books = new ArrayList<>();

    public BookLoader(Context context, String str) {
        super(context);

        this.userQuery = str;
    }

    @Override
    public List<Book> loadInBackground() {

        String stringUrl = QueryUtils.createUrlWithQuery(userQuery);

        URL url = QueryUtils.createURL(stringUrl);

        String jSonResponse = "";
        try {
            jSonResponse = QueryUtils.makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            books = QueryUtils.extractBooks(jSonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return books;
    }
}
