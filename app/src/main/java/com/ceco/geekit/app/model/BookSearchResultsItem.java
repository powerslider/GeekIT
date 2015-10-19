package com.ceco.geekit.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 25 May 2015
 */
public class BookSearchResultsItem {

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
}
