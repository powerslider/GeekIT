package com.ceco.geekit.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceco.geekit.R;
import com.ceco.geekit.app.model.BookSearchResultsItem;
import com.ceco.geekit.appabstract.net.WebFetcher;

import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 24 May 2015
 */
public class BookSearchResultsAdapter extends BaseAdapter {

    public final WebFetcher.LoadImage imageLoader = WebFetcher.LoadImage.newInstance()
            .withDefaultImage(R.drawable.no_image)
            .withErrorImage(R.drawable.error_image);

    private Context context;

    private List<BookSearchResultsItem> bookSearchResultsItems;

    public BookSearchResultsAdapter(List<BookSearchResultsItem> bookDetailses, Context context) {
        this.context = context;
        this.bookSearchResultsItems = bookDetailses;
    }

    @Override
    public int getCount() {
        return bookSearchResultsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return bookSearchResultsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookGridItemHolder bookGridItemHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_cover_grid_item, parent, false);
            bookGridItemHolder = BookGridItemHolder.newInstance();
            bookGridItemHolder.bookCoverImageView = (ImageView) convertView.findViewById(R.id.book_cover_image);
            bookGridItemHolder.bookTitleTextView = (TextView) convertView.findViewById(R.id.book_title);
            bookGridItemHolder.bookCoverImageView.setPadding(8, 8, 8, 8);
            bookGridItemHolder.bookCoverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            convertView.setTag(bookGridItemHolder);
        } else {
            bookGridItemHolder = (BookGridItemHolder) convertView.getTag();
        }

        BookSearchResultsItem bookSearchResultsItem = (BookSearchResultsItem) getItem(position);
        bookGridItemHolder.bookTitleTextView.setText(bookSearchResultsItem.getTitle());

        imageLoader
                .withImageView(bookGridItemHolder.bookCoverImageView)
                .withImageUrl(bookSearchResultsItem.getBookCoverImageUrl())
                .configure()
                .load();

        return convertView;
    }

    static class BookGridItemHolder {

        ImageView bookCoverImageView;
        TextView bookTitleTextView;

        static BookGridItemHolder newInstance() {
            return new BookGridItemHolder();
        }
    }
}
