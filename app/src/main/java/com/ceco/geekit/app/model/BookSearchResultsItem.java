package com.ceco.geekit.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 25 May 2015
 */
public class BookSearchResultsItem implements Parcelable {

    @SerializedName("ID")
    private String id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Image")
    private String bookCoverImageUrl;

    public BookSearchResultsItem(String id, String title, String bookCoverImageUrl) {
        this.id = id;
        this.title = title;
        this.bookCoverImageUrl = bookCoverImageUrl;
    }

    public BookSearchResultsItem(Parcel source) {
        id = source.readString();
        title = source.readString();
        bookCoverImageUrl = source.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookCoverImageUrl() {
        return bookCoverImageUrl;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(bookCoverImageUrl);
    }

    public static final Parcelable.Creator<BookSearchResultsItem> CREATOR = new Parcelable.Creator<BookSearchResultsItem>() {
        @Override
        public BookSearchResultsItem createFromParcel(Parcel source) {
            return new BookSearchResultsItem(source);
        }

        @Override
        public BookSearchResultsItem[] newArray(int size) {
            return new BookSearchResultsItem[size];
        }
    };
}
