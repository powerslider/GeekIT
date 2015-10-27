package com.ceco.geekit.app.net;

import android.content.Context;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ceco.geekit.app.adapter.BookSearchResultsAdapter;
import com.ceco.geekit.app.exception.GeekItException;
import com.ceco.geekit.app.model.BookSearchResultsItem;
import com.ceco.geekit.appabstract.net.WebFetcher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 30 May 2015
 */
public class BookSearchResultsItemsFetcher extends WebFetcher.JsonRequest<BookSearchResultsItem[]>  {

    private final Gson bookListCustomGson = new GsonBuilder()
            .registerTypeAdapter(BookSearchResultsItem[].class, new BookArrayJsonDeserializer())
            .create();

    private Context context;

    private AbsListView targetView;

    private BookSearchResultsAdapter searchResultsAdapter;

    public void setViewOffline(ArrayList<BookSearchResultsItem> bookList) {
        this.bookList = bookList;
        setAdapter();
    }

    public List<BookSearchResultsItem> bookList;

    private BookSearchResultsItemsFetcher() {
        super();
    }

    public static BookSearchResultsItemsFetcher newInstance() {
        return new BookSearchResultsItemsFetcher();
    }

    private void init() {
        withResponseEntity(BookSearchResultsItem[].class);
        withCustomGsonConfig(bookListCustomGson);
    }

    public BookSearchResultsItemsFetcher withContext(Context context) {
        init();
        this.context = context;
        return this;
    }

    public BookSearchResultsItemsFetcher withTargetView(AbsListView targetView) {
        this.targetView = targetView;
        return this;
    }

    public void fetchResults(String url) {
        if (targetView == null) {
            throw new GeekItException("Please set a view to display search results!");
        }

        withUrl(url).execute();
    }

    @Override
    public Response.Listener<BookSearchResultsItem[]> createSuccessListener() {
        return new Response.Listener<BookSearchResultsItem[]>() {
            @Override
            public void onResponse(BookSearchResultsItem[] bookSearchResultsItems) {
                bookList = Arrays.asList(bookSearchResultsItems);
                setAdapter();
            }
        };
    }

    private void setAdapter() {
        searchResultsAdapter = new BookSearchResultsAdapter(bookList, context);
        targetView.setAdapter(searchResultsAdapter);
    }

    @Override
    public Response.ErrorListener createErrorListener() {
        if (targetView instanceof GridView) {
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if(volleyError != null)
                        Log.e("BooksGridFragment", volleyError.getMessage());
                }
            };
        } else {
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if(volleyError != null)
                        Log.e("BooksListFragment", volleyError.getMessage());
                }
            };
        }
    }
}
