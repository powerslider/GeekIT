package com.ceco.geekit.app.net;

import android.content.Context;
import android.util.Log;
import android.widget.ExpandableListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ceco.geekit.app.adapter.BookDetailsExpandableListAdapter;
import com.ceco.geekit.app.model.BookDetails;
import com.ceco.geekit.appabstract.net.WebFetcher;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 30 May 2015
 */
public class BookDetailsFetcher extends WebFetcher.JsonRequest<BookDetails> {

    private Context context;

    private ExpandableListView expListView;

    private BookDetailsExpandableListAdapter bookDetailsExpListAdapter;

    public static BookDetailsFetcher newInstance() {
        return new BookDetailsFetcher();
    }

    private void init() {
        withResponseEntity(BookDetails.class);
    }

    public BookDetailsFetcher withContext(Context context) {
        init();
        this.context = context;
        return this;
    }

    public BookDetailsFetcher withTargetView(ExpandableListView gridView) {
        this.expListView = gridView;
        return this;
    }

    @Override
    public Response.Listener<BookDetails> createSuccessListener() {
        return new Response.Listener<BookDetails>() {
            @Override
            public void onResponse(BookDetails bookDetails) {
                populateBookDetailsData(bookDetails);
            }
        };
    }

    @Override
    public Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null)
                    Log.e("BookDetailsActivity", volleyError.getMessage());
            }
        };
    }

    private void populateBookDetailsData(BookDetails bookDetails) {
        List<String> bookDetailsHeaders = new ArrayList<>();
        bookDetailsHeaders.add("Title");
        bookDetailsHeaders.add("Subtitle");
        bookDetailsHeaders.add("Description");
        bookDetailsHeaders.add("ISBN");
        bookDetailsHeaders.add("Author");
        bookDetailsHeaders.add("Publisher");
        bookDetailsHeaders.add("Year");
        bookDetailsHeaders.add("Pages");

        List<AbstractMap.SimpleEntry<String, String>> bookDetailsPairs =
                new ArrayList<>();
        bookDetailsPairs.add(new AbstractMap.SimpleEntry<>(bookDetailsHeaders.get(0), bookDetails.getTitle()));
        bookDetailsPairs.add(new AbstractMap.SimpleEntry<>(bookDetailsHeaders.get(1), bookDetails.getSubTitle()));
        bookDetailsPairs.add(new AbstractMap.SimpleEntry<>(bookDetailsHeaders.get(2), bookDetails.getDescription()));
        bookDetailsPairs.add(new AbstractMap.SimpleEntry<>(bookDetailsHeaders.get(3), bookDetails.getIsbn()));
        bookDetailsPairs.add(new AbstractMap.SimpleEntry<>(bookDetailsHeaders.get(4), bookDetails.getAuthor()));
        bookDetailsPairs.add(new AbstractMap.SimpleEntry<>(bookDetailsHeaders.get(5), bookDetails.getPublisher()));
        bookDetailsPairs.add(new AbstractMap.SimpleEntry<>(bookDetailsHeaders.get(6), bookDetails.getYear()));
        bookDetailsPairs.add(new AbstractMap.SimpleEntry<>(bookDetailsHeaders.get(7), bookDetails.getNumberOfPages()));

        bookDetailsExpListAdapter = new BookDetailsExpandableListAdapter(context, bookDetailsHeaders,bookDetailsPairs);
        expListView.setAdapter(bookDetailsExpListAdapter);
    }
}
