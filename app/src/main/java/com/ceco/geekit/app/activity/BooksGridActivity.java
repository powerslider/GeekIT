package com.ceco.geekit.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.ceco.geekit.R;
import com.ceco.geekit.app.net.BookGridItemsFetcher;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 24 May 2015
 */
public class BooksGridActivity extends AppCompatActivity {

    private GridView gridView;

    private BookGridItemsFetcher bookGridItemsFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_grid);

        gridView = (GridView) findViewById(R.id.book_covers_grid_view);

        bookGridItemsFetcher = (BookGridItemsFetcher) BookGridItemsFetcher.newInstance()
                .withContext(this)
                .withTargetGridView(gridView)
                .withUrl("http://it-ebooks-api.info/v1/search/haskell")
                .execute();
    }
}
