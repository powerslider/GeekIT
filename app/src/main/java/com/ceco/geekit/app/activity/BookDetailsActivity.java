package com.ceco.geekit.app.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ceco.geekit.R;
import com.ceco.geekit.app.fragment.BookDetailsFragment;
import com.ceco.geekit.app.fragment.BooksListFragment;
import com.ceco.geekit.app.model.BookDetails;
import com.ceco.geekit.app.model.BookSearchResultsItem;
import com.ceco.geekit.appabstract.fragment.FragmentUtil;
import com.ceco.geekit.appabstract.fragment.OnDataPass;

import java.util.ArrayList;

import static com.ceco.geekit.app.util.ItEbooksUtils.BOOK_COVER_ID;
import static com.ceco.geekit.app.util.ItEbooksUtils.BOOK_COVER_IMAGE_URL;
import static com.ceco.geekit.app.util.ItEbooksUtils.CURRENT_BOOK_COVER_ID;
import static com.ceco.geekit.app.util.ItEbooksUtils.CURRENT_BOOK_COVER_URL;
import static com.ceco.geekit.app.util.ItEbooksUtils.CURRENT_BOOK_DETAILS;
import static com.ceco.geekit.app.util.ItEbooksUtils.CURRENT_BOOK_SEARCH_RESULTS;
import static com.ceco.geekit.app.util.ItEbooksUtils.CURRENT_BOOK_SEARCH_URL;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 30 May 2015
 */
public class BookDetailsActivity extends AppCompatActivity implements OnDataPass {

    private String currentBookCoverId;
    private String currentBookCoverUrl;
    private ArrayList<BookSearchResultsItem> currentBookSearchResults;
    private BookDetails currentBookDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Bundle bundle = getIntent().getExtras();

        String clickedBookId = bundle.getString(BOOK_COVER_ID);
        currentBookCoverId = clickedBookId;

        String clickedBookCoverUrl = bundle.getString(BOOK_COVER_IMAGE_URL);
        currentBookCoverUrl = clickedBookCoverUrl;

        String currentBookSearchUrl = bundle.getString(CURRENT_BOOK_SEARCH_URL);

        currentBookSearchResults = bundle.getParcelableArrayList(CURRENT_BOOK_SEARCH_RESULTS);


        final FragmentManager fm = getFragmentManager();

        BookDetailsFragment detailsFragment = BookDetailsFragment
                .newInstance(clickedBookId, clickedBookCoverUrl);

        boolean isDualPane = getResources().getBoolean(R.bool.dual_pane);
        if (isDualPane) {
            renderLandscapeLayout(savedInstanceState, currentBookSearchUrl, detailsFragment, fm);
        } else {
            renderPortraitLayout(savedInstanceState, detailsFragment, fm);
        }
    }

    private void renderPortraitLayout(Bundle savedInstanceState,
                                      BookDetailsFragment detailsFragment,
                                      FragmentManager fm) {
        if (savedInstanceState != null) {
            renderUpdatedDetailsFragmentInPortrait(savedInstanceState, detailsFragment, fm);
        } else {
            FragmentUtil.replaceFragment(R.id.book_details_vertical_placeholder,
                    detailsFragment, fm);
        }
    }

    private void renderLandscapeLayout(Bundle savedInstanceState,
                                       String currentBookSearchUrl,
                                       BookDetailsFragment detailsFragment,
                                       FragmentManager fm) {
        BooksListFragment booksListFragment = BooksListFragment
                .newInstance(currentBookSearchUrl, currentBookSearchResults);
        FragmentUtil.replaceFragment(R.id.book_list_horizontal_placeholder,
                booksListFragment, fm);

        if (savedInstanceState != null) {
            renderUpdatedDetailsFragmentInLandscape(savedInstanceState, detailsFragment, fm);
        } else {
            FragmentUtil.replaceFragment(R.id.book_details_horizontal_placeholder,
                    detailsFragment, fm);
        }
    }

    private void renderUpdatedDetailsFragmentInPortrait(Bundle savedInstanceState,
                                                        BookDetailsFragment detailsFragment,
                                                        FragmentManager fm) {
        renderDetailsFragment(R.id.book_details_vertical_placeholder,
                savedInstanceState, detailsFragment, fm);
    }

    private void renderUpdatedDetailsFragmentInLandscape(Bundle savedInstanceState,
                                                         BookDetailsFragment detailsFragment,
                                                         FragmentManager fm) {
        renderDetailsFragment(R.id.book_details_horizontal_placeholder,
                savedInstanceState, detailsFragment, fm);
    }

    private void renderDetailsFragment(int fragmentPlaceholderId,
                                       Bundle savedInstanceState,
                                       BookDetailsFragment detailsFragment,
                                       FragmentManager fm) {
        BookDetailsFragment currentDetailsFragment;

        currentBookDetails = savedInstanceState.getParcelable(CURRENT_BOOK_DETAILS);
        if (currentBookDetails != null) {
            currentDetailsFragment = BookDetailsFragment
                    .newInstance(currentBookDetails);
            FragmentUtil.replaceFragment(fragmentPlaceholderId, currentDetailsFragment, fm);
            return;
        }

        currentBookCoverId = savedInstanceState.getString(CURRENT_BOOK_COVER_ID);
        currentBookCoverUrl = savedInstanceState.getString(CURRENT_BOOK_COVER_URL);
        if (currentBookCoverId != null && currentBookCoverUrl != null) {
            currentDetailsFragment = BookDetailsFragment
                    .newInstance(currentBookCoverId, currentBookCoverUrl);
            FragmentUtil.replaceFragment(fragmentPlaceholderId, currentDetailsFragment, fm);
        } else {
            FragmentUtil.replaceFragment(fragmentPlaceholderId, detailsFragment, fm);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(CURRENT_BOOK_COVER_ID, currentBookCoverId);
        outState.putString(CURRENT_BOOK_COVER_URL, currentBookCoverUrl);
        outState.putParcelableArrayList(CURRENT_BOOK_SEARCH_RESULTS, currentBookSearchResults);
        outState.putParcelable(CURRENT_BOOK_DETAILS, currentBookDetails);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDataPass(Bundle data) {
        currentBookCoverId = data.getString(CURRENT_BOOK_COVER_ID);
        currentBookCoverUrl = data.getString(CURRENT_BOOK_COVER_URL);
        currentBookSearchResults = data.getParcelableArrayList(CURRENT_BOOK_SEARCH_RESULTS);
        currentBookDetails = data.getParcelable(CURRENT_BOOK_DETAILS);
    }
}
