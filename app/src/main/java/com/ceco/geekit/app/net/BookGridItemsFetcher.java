package com.ceco.geekit.app.net;

import android.content.Context;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ceco.geekit.app.adapter.BookCoverGridAdapter;
import com.ceco.geekit.app.model.BookGridItem;
import com.ceco.geekit.appabstract.net.WebFetcher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 30 May 2015
 */
public class BookGridItemsFetcher extends WebFetcher.JsonRequest<BookGridItem[]>  {

    private final Gson bookListCustomGson = new GsonBuilder()
            .registerTypeAdapter(BookGridItem[].class, new BookArrayJsonDeserializer())
            .create();

    private Context context;

    private GridView gridView;

    private BookCoverGridAdapter gridAdapter;

    public static BookGridItemsFetcher newInstance() {
        return new BookGridItemsFetcher();
    }

    private void init() {
        withResponseEntity(BookGridItem[].class);
        withCustomGsonConfig(bookListCustomGson);
    }

    public BookGridItemsFetcher withContext(Context context) {
        init();
        this.context = context;
        return this;
    }

    public BookGridItemsFetcher withTargetGridView(GridView gridView) {
        this.gridView = gridView;
        return this;
    }

    @Override
    public Response.Listener<BookGridItem[]> createSuccessListener() {
        return new Response.Listener<BookGridItem[]>() {
            @Override
            public void onResponse(BookGridItem[] bookGridItems) {
                List<BookGridItem> bookList = Arrays.asList(bookGridItems);
                gridAdapter = new BookCoverGridAdapter(bookList, context);
                gridView.setAdapter(gridAdapter);
            }
        };
    }

    @Override
    public Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError != null)
                    Log.e("BooksGridActivity", volleyError.getMessage());
            }
        };
    }
}
