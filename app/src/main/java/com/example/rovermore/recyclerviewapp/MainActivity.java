package com.example.rovermore.recyclerviewapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public String userQuery = "El señor de los anillos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText searchBar = (EditText) findViewById(R.id.search_bar);

        Button searchButton = findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userQuery = String.valueOf(searchBar.getText());
                getLoaderManager().initLoader(0,null,MainActivity.this).forceLoad();
            }
        });
    }

    private void createUI(final List<Book> bookList){
        BookRecyclerAdapter adapter = null;

        adapter = new BookRecyclerAdapter(this, bookList);

        RecyclerView booksListView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                //StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                //GridLayoutManager(getApplicationContext(),2);
        booksListView.setLayoutManager(mLayoutManager);
        booksListView.setItemAnimator(new DefaultItemAnimator());

        //booksListView.setEmptyView(emptyStateView);

        booksListView.setAdapter(adapter);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        BookLoader booksLoader = new BookLoader(this, userQuery);

        return booksLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookList) {
        Log.v("onLoadFinished","update of the UI should start and create de interface");
        //Makes progerss bar disappear when the loader is loaded
        //progressBar.setVisibility(View.GONE);
        createUI(bookList);

        getLoaderManager().destroyLoader(0);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.v("onLoaderReset","Create a new arraylist");
        List<Book> listBook = new ArrayList<>();
        createUI(listBook);

    }
}
