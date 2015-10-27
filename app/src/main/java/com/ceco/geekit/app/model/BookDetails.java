package com.ceco.geekit.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 25 May 2015
 */
public class BookDetails implements Parcelable {

    @SerializedName("ID")
    private String id;

    private String isbn;

    @SerializedName("Title")
    private String title;

    @SerializedName("SubTitle")
    private String subTitle;

    @SerializedName("Author")
    private String author;

    @SerializedName("Publisher")
    private String publisher;

    @SerializedName("Year")
    private String year;

    @SerializedName("Page")
    private String numberOfPages;

    @SerializedName("Description")
    private String description;

    @SerializedName("Image")
    private String bookCoverImageUrl;

    @SerializedName("Download")
    private String downloadUrl;

    public BookDetails(Parcel source) {
        id = source.readString();
        isbn = source.readString();
        title = source.readString();
        subTitle = source.readString();
        author = source.readString();
        publisher = source.readString();
        year = source.readString();
        numberOfPages = source.readString();
        description = source.readString();
        bookCoverImageUrl = source.readString();
        downloadUrl = source.readString();
    }

    public String getBookCoverImageUrl() {
        return bookCoverImageUrl;
    }

    public void setBookCoverImageUrl(String bookCoverImageUrl) {
        this.bookCoverImageUrl = bookCoverImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(isbn);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(author);
        dest.writeString(publisher);
        dest.writeString(year);
        dest.writeString(numberOfPages);
        dest.writeString(description);
        dest.writeString(bookCoverImageUrl);
        dest.writeString(downloadUrl);
    }

    public static final Parcelable.Creator<BookDetails> CREATOR = new Parcelable.Creator<BookDetails>() {

        public BookDetails createFromParcel(Parcel source) {
            return new BookDetails(source);
        }

        public BookDetails[] newArray(int size) {
            return new BookDetails[size];
        }
    };
}
