package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookAccessoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBookAccessories;
    private BookAccessoriesAdapter bookAccessoriesAdapter;
    private List<BookAccessories> bookAccessoriesList;
    private List<BookAccessories> allBookAccessoriesList; // Store all items for resetting
    private ImageButton bookmarkButton;
    private ImageButton booklightButton;
    private ImageButton othersButton;
    private ImageButton backButton;
    private Button viewCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_accessories);

        // Initialize views
        recyclerViewBookAccessories = findViewById(R.id.bookaccesoriesRecyclerView);
        bookmarkButton = findViewById(R.id.bookmarkButton);
        booklightButton = findViewById(R.id.booklightButton);
        othersButton = findViewById(R.id.othersButton);
        backButton = findViewById(R.id.backButton);
        viewCartButton = findViewById(R.id.viewCartButton);

        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookAccessoriesActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Set up RecyclerView
        recyclerViewBookAccessories.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the lists and adapter
        bookAccessoriesList = new ArrayList<>();
        allBookAccessoriesList = new ArrayList<>();
        bookAccessoriesAdapter = new BookAccessoriesAdapter(bookAccessoriesList, bookAccessories -> {
            Intent intent = new Intent(BookAccessoriesActivity.this, BookAccessoriesDetailActivity.class);
            intent.putExtra("bookAccessories", bookAccessories);
            startActivity(intent);
        });
        recyclerViewBookAccessories.setAdapter(bookAccessoriesAdapter);

        // Load sample data
        fetchBookAccessories();

        // Initially filter to show only "Bookmark" items
        filterByCategory("Bookmark");

        // Set up button click listeners
        backButton.setOnClickListener(v -> finish());

        bookmarkButton.setOnClickListener(v -> filterByCategory("Bookmark"));
        booklightButton.setOnClickListener(v -> filterByCategory("Booklight"));
        othersButton.setOnClickListener(v -> filterByCategory("Others"));
    }

    private void fetchBookAccessories() {
        // Sample data for demonstration purposes
        addBookAccessoriesToList();
        bookAccessoriesAdapter.notifyDataSetChanged();
    }

    private void addBookAccessoriesToList() {
        // Add items to both lists
        bookAccessoriesList.add(new BookAccessories("Just One More Chapter", 50.0, R.drawable.bookmark1, "Bookmark"));
        bookAccessoriesList.add(new BookAccessories("Maple Leaf", 150.0, R.drawable.maple_leaf, "Bookmark"));
        bookAccessoriesList.add(new BookAccessories("Lotus", 200.0, R.drawable.lotus, "Bookmark"));
        bookAccessoriesList.add(new BookAccessories("Metal Feather", 250.0, R.drawable.metal_feather, "Bookmark"));
        bookAccessoriesList.add(new BookAccessories("Rose", 180.0, R.drawable.rose, "Bookmark"));
        bookAccessoriesList.add(new BookAccessories("You alone is enough", 50.0, R.drawable.you_alone_is_enough, "Bookmark"));
        bookAccessoriesList.add(new BookAccessories("Lost between the pages", 50.0, R.drawable.lost_between_the_pages, "Bookmark"));

        bookAccessoriesList.add(new BookAccessories("DSJ Book Reading Light", 150.0, R.drawable.booklight1, "Booklight"));
        bookAccessoriesList.add(new BookAccessories("CPENSUS USB Rechargeable Book Reading Light", 850.0, R.drawable.cpensus, "Booklight"));
        bookAccessoriesList.add(new BookAccessories("NEXT GEEK Plastic Book Reading LED Light", 800.0, R.drawable.next, "Booklight"));
        bookAccessoriesList.add(new BookAccessories("Mishrit Led Night Reading Panel", 1000.0, R.drawable.mishrit, "Booklight"));
        bookAccessoriesList.add(new BookAccessories("Glocusent Lightweight Rechargeable Light", 950.0, R.drawable.glu, "Booklight"));
        bookAccessoriesList.add(new BookAccessories("NYRWANA_Study_Lamp", 700.0, R.drawable.ny, "Booklight"));



        bookAccessoriesList.add(new BookAccessories("Book theme coffee mug", 150.0, R.drawable.others1, "Others"));
        bookAccessoriesList.add(new BookAccessories("Book Nerd: Booksleeves", 200.0, R.drawable.book_sleeves, "Others"));
        bookAccessoriesList.add(new BookAccessories("White Hand Bookends", 350.0, R.drawable.bookends, "Others"));
        bookAccessoriesList.add(new BookAccessories("Wooden Page Holder", 250.0, R.drawable.page_holder, "Others"));
        bookAccessoriesList.add(new BookAccessories("Wooden Reading Rest", 500.0, R.drawable.wooden_reading_rest, "Others"));
        bookAccessoriesList.add(new BookAccessories("Book theme glass", 100.0, R.drawable.glass, "Others"));


        // Keep a copy of all items
        allBookAccessoriesList.addAll(bookAccessoriesList);
    }

    private void filterByCategory(String category) {
        List<BookAccessories> filteredList = new ArrayList<>();
        if (category.equals("All")) {
            // Reset to show all items
            filteredList.addAll(allBookAccessoriesList);
        } else {
            for (BookAccessories item : allBookAccessoriesList) {
                if (item.getCategory().equalsIgnoreCase(category)) {
                    filteredList.add(item);
                }
            }
        }
        bookAccessoriesAdapter.updateList(filteredList);
    }
}
