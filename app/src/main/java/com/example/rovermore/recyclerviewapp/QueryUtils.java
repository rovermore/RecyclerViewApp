package com.example.rovermore.recyclerviewapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private QueryUtils() {
    }

    public static final String LOG_TAG = MainActivity.class.getSimpleName();


    public static final String URL_BOOKS_REQUEST= "https://www.googleapis.com/books/v1/volumes?";

    //This method generates a url from the query that user inputs
    public static String createUrlWithQuery (String str){

        String query = str;

        query.replace(" ","+");

        StringBuilder url = new StringBuilder(URL_BOOKS_REQUEST);

        url.append("q=");

        url.append(query);

        url.append("&maxResults=20");

        String urlString = String.valueOf(url);

        return urlString;
    }

    public static URL createURL(String stringUrl){

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"Error with the URL ", e);
            return null;
        }

        return url;

    }

    public static String makeHttpRequest(URL url) throws IOException {

        String jSonResponse = "";

        if (url == null) {



            return jSonResponse;
        }


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = null;
            inputStream = null;

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {

                inputStream = urlConnection.getInputStream();
                jSonResponse = readFromStream(inputStream);

                Log.v("urlConnection","succesful Http connection");

            } else {

                String stringErrorConection = String.valueOf(urlConnection.getResponseCode());

                Log.e(LOG_TAG, "Error to set Http conection: " + stringErrorConection);

            }

        } catch (IOException e) {

            Log.e(LOG_TAG, "EIOException" + e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();

                Log.v("urlConnection", "disconnected");
            }

            if (inputStream != null) {

                inputStream.close();
                Log.v("inputStream", "closed");
            }
        }


        return jSonResponse;
    }


    public static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if(inputStream!=null){

            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while(line!=null){

                output.append(line);
                line = reader.readLine();
            }
        }

        String jSonOutput = String.valueOf(output);

        Log.v("readFromStream","jSon has been readed and added into a String");

        return jSonOutput;


    }




    public static List<Book> extractBooks (String jSonString) throws JSONException {

        List<Book> bookList = new ArrayList<>();


        JSONObject jsonBookList = new JSONObject(jSonString);

        JSONArray jsonArrayBookList = jsonBookList.getJSONArray("items");

        for(int i=0;i<jsonArrayBookList.length();i++){

            JSONObject item = jsonArrayBookList.getJSONObject(i);

            JSONObject volumeInfo = item.getJSONObject("volumeInfo");

            String title = volumeInfo.getString("title");

            String yearB = volumeInfo.optString("publishedDate");

            JSONObject imageLinkContainer = volumeInfo.optJSONObject("imageLinks");

            String imageLink = "http://books.google.com/books/content?id=DYmUGGwZ8_oC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api";

            if(imageLinkContainer!=null){
            imageLink = imageLinkContainer.optString("smallThumbnail");
            }

            JSONArray authorJsonList=volumeInfo.optJSONArray("authors");

            String author = "Unknown";

            if(authorJsonList!=null) {

                    author = authorJsonList.optString(0);

            }

            Book book = new Book(author,title,yearB,imageLink);

            bookList.add(book);

        }

        return bookList;
    }
}
