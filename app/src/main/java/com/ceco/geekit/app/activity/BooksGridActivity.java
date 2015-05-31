package com.ceco.geekit.app.activity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;

import com.ceco.geekit.R;
import com.ceco.geekit.app.net.BookGridItemsFetcher;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 24 May 2015
 */
public class BooksGridActivity extends AppCompatActivity {

    public static final String BOOK_COVER_ID = "BOOK_COVER_ID";

    public static final String BOOK_COVER_IMAGE_URL = "BOOK_COVER_IMAGE_URL";

    public static final String IT_EBOOKS_API_BASE_URL = "http://it-ebooks-api.info/v1/search/";


    private int currentPage = 1;

    private String bookSearchUrl;

    private GridView gridView;

    private Button nextPageButton;

    private BookGridItemsFetcher bookGridItemsFetcher = BookGridItemsFetcher.newInstance()
            .withContext(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_grid);

        gridView = (GridView) findViewById(R.id.book_covers_grid_view);

        nextPageButton = (Button) findViewById(R.id.next_page_button);

        bookGridItemsFetcher = bookGridItemsFetcher.withTargetView(gridView);

        bookSearchUrl = getIntent().getStringExtra(BookSearchActivity.SEARCH_URL);

        bookGridItemsFetcher
                .withUrl(bookSearchUrl)
                .execute();

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                bookGridItemsFetcher
                        .withUrl(bookSearchUrl + "/page/" + currentPage)
                        .execute();
            }
        });

        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String clickedItemId = bookGridItemsFetcher.bookList.get(position).getId();
                final String clickedItemBookCoverImageUrl = bookGridItemsFetcher.bookList.get(position).getBookCoverImageUrl();
                Intent intent = new Intent(BooksGridActivity.this, BookDetailsActivity.class);
                intent.putExtra(BOOK_COVER_ID, clickedItemId);
                intent.putExtra(BOOK_COVER_IMAGE_URL, clickedItemBookCoverImageUrl);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(true);
//
//        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                doSearch(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newQuery) {
//                doSearch(newQuery);
//                return true;
//            }
//        };
//
//        searchView.setOnQueryTextListener(textChangeListener);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public void onNewIntent(Intent intent) {
//        setIntent(intent);
//        handleIntent(intent);
//    }
//
//    private void handleIntent(Intent intent) {
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            doSearch(query);
//        }
//    }

    private void doSearch(String query) {
        bookGridItemsFetcher
                .withUrl(IT_EBOOKS_API_BASE_URL + query)
                .execute();
    }
}
