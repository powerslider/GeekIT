package com.ceco.geekit.app.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ceco.geekit.R;
import com.ceco.geekit.app.fragment.BookDetailsFragment;
import com.ceco.geekit.app.fragment.BooksGridFragment;
import com.ceco.geekit.app.fragment.BooksListFragment;
import com.ceco.geekit.appabstract.fragment.FragmentUtil;
import com.ceco.geekit.appabstract.fragment.OnDataPass;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 30 May 2015
 */
public class BookDetailsActivity extends AppCompatActivity implements OnDataPass {

    String currentBookCoverId;
    String currentBookCoverUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Bundle bundle = getIntent().getExtras();
        String clickedBookId = bundle.getString(BooksGridFragment.BOOK_COVER_ID);
        String clickedBookCoverUrl = bundle.getString(BooksGridFragment.BOOK_COVER_IMAGE_URL);
        String currentBookSearchUrl = bundle.getString(BooksGridFragment.CURRENT_BOOK_SEARCH_URL);

        final FragmentManager fm = getFragmentManager();

        BookDetailsFragment detailsFragment = BookDetailsFragment
                .newInstance(clickedBookId, clickedBookCoverUrl);

        boolean isDualPane = getResources().getBoolean(R.bool.dual_pane);
        if (isDualPane) {
            BooksListFragment booksListFragment = BooksListFragment
                    .newInstance(currentBookSearchUrl);
            FragmentUtil.replaceFragment(R.id.book_list_horizontal_placeholder,
                    booksListFragment, fm);
            FragmentUtil.replaceFragment(R.id.book_details_horizontal_placeholder,
                    detailsFragment, fm);
        }

        if (savedInstanceState != null) {
            currentBookCoverId = savedInstanceState.getString(BooksListFragment.CURRENT_BOOK_COVER_ID);
            currentBookCoverUrl = savedInstanceState.getString(BooksListFragment.CURRENT_BOOK_COVER_URL);
            if (currentBookCoverId != null && currentBookCoverUrl != null) {
                BookDetailsFragment currentDetailsFragment = BookDetailsFragment
                        .newInstance(currentBookCoverId, currentBookCoverUrl);
                FragmentUtil.replaceFragment(R.id.book_details_vertical_placeholder,
                        currentDetailsFragment, fm);
            }
        } else {
            FragmentUtil.replaceFragment(R.id.book_details_vertical_placeholder,
                    detailsFragment, fm);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BooksListFragment.CURRENT_BOOK_COVER_ID, currentBookCoverId);
        outState.putString(BooksListFragment.CURRENT_BOOK_COVER_URL, currentBookCoverUrl);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDataPass(Bundle data) {
        currentBookCoverId = data.getString(BooksListFragment.CURRENT_BOOK_COVER_ID);
        currentBookCoverUrl = data.getString(BooksListFragment.CURRENT_BOOK_COVER_URL);
    }
}
