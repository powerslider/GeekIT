package com.ceco.geekit.app.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.text.StringCharacterIterator;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 25 May 2015
 */
public class BookDetails {

    @SerializedName("ID")
    private String id;

    private String isbn;

//    @SerializedName("Title")
//    private String title;

    @SerializedName("SubTitle")
    private String subTitle;

    private String author;

    private String publisher;

    private Integer year;

    private Integer numberOfPages;

    @SerializedName("Description")
    private String description;

//    @SerializedName("Image")
//    private String bookCoverImageUrl;

//    public Bitmap getBookCoverImage() {
//        return bookCoverImage;
//    }
//
//    public void setBookCoverImage(Bitmap bookCoverImage) {
//        this.bookCoverImage = bookCoverImage;
//    }
//
//    private Bitmap bookCoverImage;

//    public String getBookCoverImageUrl() {
//        return bookCoverImageUrl;
//    }
//
//    public void setBookCoverImageUrl(String bookCoverImageUrl) {
//        this.bookCoverImageUrl = bookCoverImageUrl;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
