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

public class BookListActivity extends AppCompatActivity {
    private RecyclerView bookRecyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private Button viewCartButton;
    private ImageButton bookmarkButton;
    private ImageButton booklightButton;
    private ImageButton othersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        // Initialize views
        bookRecyclerView = findViewById(R.id.bookRecyclerView);
        viewCartButton = findViewById(R.id.viewCartButton);
        bookmarkButton = findViewById(R.id.bookmarkButton);
        booklightButton = findViewById(R.id.booklightButton);
        othersButton = findViewById(R.id.othersButton);

        // Set up RecyclerView
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up click listener for back button
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Initialize the book list and adapter
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList, book -> {
            Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
            intent.putExtra("book", book); // Ensure Book implements Parcelable
            startActivity(intent);
        });
        bookRecyclerView.setAdapter(bookAdapter);

        // Fetch books data and filter to show only the default category
        fetchBooks();
        filterBooks("Fiction"); // Filter to show only Fiction books initially

        // Set up button click listeners
        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookListActivity.this, CartActivity.class);
            startActivity(intent);
        });

        bookmarkButton.setOnClickListener(v -> filterBooks("Fiction"));
        booklightButton.setOnClickListener(v -> filterBooks("Mystery"));
        othersButton.setOnClickListener(v -> filterBooks("Romance"));
    }

    private void fetchBooks() {
        // Add books to the list with appropriate categories
        bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 899.00, R.drawable.the_great_gatsby, "Fiction"));
        bookList.add(new Book("1984", "George Orwell", 749.00, R.drawable.nineteen_eighty_four, "Fiction"));
        bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", 699.00, R.drawable.to_kill_a_mockingbird, "Fiction"));
        bookList.add(new Book("The Catcher in the Rye", "J.D. Salinger", 849.00, R.drawable.the_catcher_in_the_rye, "Fiction"));
        bookList.add(new Book("The Alchemist", "Paulo Coelho", 999.00, R.drawable.the_alchemist, "Fiction"));
        bookList.add(new Book("Brave New World", "Aldous Huxley", 899.00, R.drawable.brave_new_world, "Fiction"));
        bookList.add(new Book("Animal Farm", "George Orwell", 679.00, R.drawable.animal_farm, "Fiction"));

        bookList.add(new Book("Gone Girl", "Gillian Flynn", 1099.00, R.drawable.gone_girl, "Mystery"));
        bookList.add(new Book("The Da Vinci Code", "Dan Brown", 999.00, R.drawable.the_da_vinci_code, "Mystery"));
        bookList.add(new Book("Sherlock Holmes", "Arthur Conan Doyle", 849.00, R.drawable.sherlock_holmes, "Mystery"));
        bookList.add(new Book("Big Little Lies", "Liane Moriarty", 1099.00, R.drawable.big_little_lies, "Mystery"));
        bookList.add(new Book("The Girl with the Dragon Tattoo", "Stieg Larsson", 1299.00, R.drawable.the_girl_with_the_dragon_tattoo, "Mystery"));
        bookList.add(new Book("In the Woods", "Tana French", 1099.00, R.drawable.in_the_woods, "Mystery"));
        bookList.add(new Book("The Silent Patient", "Alex Michaelides", 1499.00, R.drawable.the_silent_patient, "Mystery"));

        bookList.add(new Book("Pride and Prejudice", "Jane Austen", 599.00, R.drawable.pride_and_prejudice, "Romance"));
        bookList.add(new Book("The Notebook", "Nicholas Sparks", 699.00, R.drawable.the_notebook, "Romance"));
        bookList.add(new Book("Outlander", "Diana Gabaldon", 799.00, R.drawable.outlander, "Romance"));
        bookList.add(new Book("Me Before You", "Jojo Moyes", 849.00, R.drawable.me_before_you, "Romance"));
        bookList.add(new Book("The Rosie Project", "Graeme Simsion", 999.00, R.drawable.the_rosie_project, "Romance"));
        bookList.add(new Book("The Hating Game", "Sally Thorne", 1099.00, R.drawable.the_hating_game, "Romance"));
        bookList.add(new Book("Red, White & Royal Blue", "Casey McQuiston", 1299.00, R.drawable.red_white_royal_blue, "Romance"));

        bookAdapter.notifyDataSetChanged();
    }

    private void filterBooks(String category) {
        List<Book> filteredList = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(book);
            }
        }
        bookAdapter.updateList(filteredList);
    }
}
