package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAccessoriesAdapter extends RecyclerView.Adapter<BookAccessoriesAdapter.BookAccessoriesViewHolder> {
    private List<BookAccessories> bookAccessoriesList;
    private OnBookAccessoriesClickListener onBookAccessoriesClickListener;

    public BookAccessoriesAdapter(List<BookAccessories> bookAccessoriesList, OnBookAccessoriesClickListener onBookAccessoriesClickListener) {
        this.bookAccessoriesList = bookAccessoriesList;
        this.onBookAccessoriesClickListener = onBookAccessoriesClickListener;
    }

    @NonNull
    @Override
    public BookAccessoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_accessory, parent, false);
        return new BookAccessoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAccessoriesViewHolder holder, int position) {
        BookAccessories item = bookAccessoriesList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return bookAccessoriesList.size();
    }

    public void updateList(List<BookAccessories> newBookAccessoriesList) {
        bookAccessoriesList = newBookAccessoriesList;
        notifyDataSetChanged();
    }

    public interface OnBookAccessoriesClickListener {
        void onBookAccessoriesClick(BookAccessories bookAccessories);
    }

    public class BookAccessoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemPrice;
        private ImageView itemImage;

        public BookAccessoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(v -> {
                if (onBookAccessoriesClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onBookAccessoriesClickListener.onBookAccessoriesClick(bookAccessoriesList.get(position));
                    }
                }
            });
        }

        public void bind(BookAccessories item) {
            itemName.setText(item.getName());
            itemPrice.setText(String.format("₹%.2f", item.getPrice()));
            itemImage.setImageResource(item.getImageResourceId());
        }
    }
}
