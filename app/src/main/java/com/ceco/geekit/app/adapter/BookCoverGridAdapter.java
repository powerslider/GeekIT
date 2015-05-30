package com.ceco.geekit.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceco.geekit.R;
import com.ceco.geekit.app.model.BookGridItem;
import com.ceco.geekit.appabstract.net.WebFetcher;

import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 24 May 2015
 */
public class BookCoverGridAdapter extends BaseAdapter {

    public final WebFetcher.LoadImage imageLoader = WebFetcher.LoadImage.newInstance()
            .withDefaultImage(R.drawable.no_image)
            .withErrorImage(R.drawable.error_image);

    private Context context;

    private List<BookGridItem> bookGridItems;

    public BookCoverGridAdapter(List<BookGridItem> bookDetailses, Context context) {
        this.context = context;
        this.bookGridItems = bookDetailses;
    }

    @Override
    public int getCount() {
        return bookGridItems.size();
    }

    @Override
    public Object getItem(int position) {
        return bookGridItems.get(position);
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

        BookGridItem bookGridItem = (BookGridItem) getItem(position);
        bookGridItemHolder.bookTitleTextView.setText(bookGridItem.getTitle());

        imageLoader
                .withImageView(bookGridItemHolder.bookCoverImageView)
                .withImageUrl(bookGridItem.getBookCoverImageUrl())
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
