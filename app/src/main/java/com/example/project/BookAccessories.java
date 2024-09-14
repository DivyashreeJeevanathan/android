package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

public class BookAccessories implements Parcelable {
    private String name;
    private double price;
    private int imageResourceId;
    private String category;

    public BookAccessories(String name, double price, int imageResourceId, String category) {
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.category = category;
    }

    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getImageResourceId() { return imageResourceId; }
    public String getCategory() { return category; }

    // Parcelable implementation
    protected BookAccessories(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        imageResourceId = in.readInt();
        category = in.readString();
    }

    public static final Creator<BookAccessories> CREATOR = new Creator<BookAccessories>() {
        @Override
        public BookAccessories createFromParcel(Parcel in) {
            return new BookAccessories(in);
        }

        @Override
        public BookAccessories[] newArray(int size) {
            return new BookAccessories[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(imageResourceId);
        dest.writeString(category);
    }
}
