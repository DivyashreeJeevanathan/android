package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String name;
    private String author;
    private double price;
    private int imageResourceId;
    private String category;

    public Book(String name, String author, double price, int imageResourceId, String category) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.category = category;
    }

    // Getters
    public String getName() { return name; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public int getImageResourceId() { return imageResourceId; }
    public String getCategory() { return category; }

    // Parcelable implementation
    protected Book(Parcel in) {
        name = in.readString();
        author = in.readString();
        price = in.readDouble();
        imageResourceId = in.readInt();
        category = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(author);
        dest.writeDouble(price);
        dest.writeInt(imageResourceId);
        dest.writeString(category);
    }
}
