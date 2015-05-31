package com.ceco.geekit.app.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 25 May 2015
 */
public class BookGridItem {

    @SerializedName("ID")
    private String id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Image")
    private String bookCoverImageUrl;

    public BookGridItem(String id, String title, String bookCoverImageUrl) {
        this.id = id;
        this.title = title;
        this.bookCoverImageUrl = bookCoverImageUrl;
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

    public void setBookCoverImageUrl(String bookCoverImageUrl) {
        this.bookCoverImageUrl = bookCoverImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
