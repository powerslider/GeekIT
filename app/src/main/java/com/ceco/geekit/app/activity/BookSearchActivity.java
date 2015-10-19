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
import com.ceco.geekit.app.fragment.BooksListFragment;
import com.ceco.geekit.app.util.ItEbooksRestApiUrls;
import com.ceco.geekit.appabstract.fragment.FragmentUtil;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 31 May 2015
 */
public class BookSearchActivity extends AppCompatActivity {

    private static final String BOOK_SEARCH_URL = "BOOK_SEARCH_URL";
    public static final String ORIENTATION_CHANGED = "ORIENTATION_CHANGED";
    public static final String CURRENT_FRAGMENT = "CURRENT_FRAGMENT";

    private String bookSearchUrl;

    private Fragment currentFragment;

    private boolean orientationChanged;

    private int orientationChangeCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);
        if(savedInstanceState != null) {
            currentFragment = getFragmentManager().getFragment(savedInstanceState, CURRENT_FRAGMENT);
            orientationChanged = savedInstanceState.getBoolean(ORIENTATION_CHANGED);
//            bookSearchUrl = savedInstanceState.getString(BOOK_SEARCH_URL);
        }
        handleIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //orientationChangeCount++;
        getFragmentManager().putFragment(outState, CURRENT_FRAGMENT, currentFragment);
        outState.putString(BOOK_SEARCH_URL, bookSearchUrl);
       // if (orientationChangeCount == 2) {
            outState.putBoolean(ORIENTATION_CHANGED,true);
         //   orientationChangeCount = 0;
        //}
//        outState.putString(BOOK_SEARCH_URL, bookSearchUrl);
        super.onSaveInstanceState(outState);
    }


    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }

        if (orientationChanged) {
            restoreAfterRotation();
        }
    }

    private void doSearch(String query) {
        bookSearchUrl = ItEbooksRestApiUrls.SEARCH + query;

        boolean isDualPane = getResources().getBoolean(R.bool.dual_pane);
        final FragmentManager fm = getFragmentManager();
        Bundle args = new Bundle();
        args.putString(BOOK_SEARCH_URL, bookSearchUrl);
        if (isDualPane) {
            currentFragment = BooksListFragment.newInstance(bookSearchUrl);
            FragmentUtil.replaceFragment(R.id.book_list_horizontal_placeholder,
                    currentFragment, fm);
        } else {
            currentFragment = BooksGridFragment.newInstance(bookSearchUrl);
            FragmentUtil.replaceFragment(R.id.book_grid_placeholder,
                    currentFragment, fm);
        }
    }

    private void restoreAfterRotation() {
        boolean isDualPane = getResources().getBoolean(R.bool.dual_pane);
        final FragmentManager fm = getFragmentManager();
        if (isDualPane && currentFragment instanceof BooksListFragment) {
            FragmentUtil.replaceFragment(R.id.book_list_horizontal_placeholder,
                    currentFragment, fm);
        } else {
            FragmentUtil.replaceFragment(R.id.book_grid_placeholder,
                    currentFragment, fm);
        }
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
