package com.ceco.geekit.app.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import com.ceco.geekit.R;
import com.ceco.geekit.app.fragment.BooksGridFragment;
import com.ceco.geekit.app.util.ItEbooksRestApiUrls;
import com.ceco.geekit.appabstract.net.fragment.FragmentHelper;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 31 May 2015
 */
public class BookSearchActivity extends AppCompatActivity {

    public static final String SEARCH_URL = "SEARCH_URL";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_grid);
        handleIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String query) {
        String bookSearchUrl = ItEbooksRestApiUrls.SEARCH + query;
        BooksGridFragment booksGridFragment = new BooksGridFragment();
        booksGridFragment.setBookSearchUrl(bookSearchUrl);

        FragmentHelper.initFragment(booksGridFragment, R.id.book_grid_placeholder, getFragmentManager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        ComponentName cn = new ComponentName(this, BookSearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));
        searchView.setIconifiedByDefault(true);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final FragmentManager fm = getFragmentManager();
                Fragment currentActiveFragment = fm.findFragmentById(R.id.book_grid_placeholder);
                if (currentActiveFragment != null) {
                    FragmentHelper.removeFragment(currentActiveFragment, fm);
                }
                doSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
            //    doSearch(newQuery);
                return true;
            }
        };

        searchView.setOnQueryTextListener(textChangeListener);

        return super.onCreateOptionsMenu(menu);
    }
}
