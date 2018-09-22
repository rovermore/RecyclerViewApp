package com.example.rovermore.recyclerviewapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.MyViewHolder> {

    private List<Book> bookList;
    private Context context;

    public BookRecyclerAdapter(Context context, List<Book> bookList){
        this.bookList=bookList;
        this.context=context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView authors, title;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            authors = itemView.findViewById(R.id.book_author);
            title = itemView.findViewById(R.id.book_name);
            imageView = itemView.findViewById(R.id.book_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book currentBook = bookList.get(position);
        String author = currentBook.getAuthors();
        holder.authors.setText(author);
        holder.title.setText(currentBook.getNameBook());
        //to extract the actual context and pass it to Glide library
        //Context context = holder.authors.getContext();
        Glide.with(context)
                .load(currentBook.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


}
