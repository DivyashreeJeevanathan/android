package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private OnBookClickListener onBookClickListener;

    public BookAdapter(List<Book> bookList, OnBookClickListener onBookClickListener) {
        this.bookList = bookList;
        this.onBookClickListener = onBookClickListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void updateList(List<Book> newBookList) {
        bookList = newBookList;
        notifyDataSetChanged();
    }

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView bookName;
        private TextView bookAuthor;
        private TextView bookPrice;
        private ImageView bookImage;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookName);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookPrice = itemView.findViewById(R.id.bookPrice);
            bookImage = itemView.findViewById(R.id.bookImage);

            itemView.setOnClickListener(v -> {
                if (onBookClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onBookClickListener.onBookClick(bookList.get(position));
                    }
                }
            });
        }

        public void bind(Book book) {
            bookName.setText(book.getName());
            bookAuthor.setText(book.getAuthor());
            bookPrice.setText(String.format("₹%.2f", book.getPrice()));
            bookImage.setImageResource(book.getImageResourceId());
        }
    }
}
