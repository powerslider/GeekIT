package com.ceco.geekit.app.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.ceco.geekit.R;
import com.ceco.geekit.app.activity.BookDetailsActivity;
import com.ceco.geekit.app.exception.GeekItException;
import com.ceco.geekit.app.net.BookSearchResultsItemsFetcher;


/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 10 Oct 2015
 */
public class BooksGridFragment extends Fragment {

    public static final String BOOK_COVER_ID = "BOOK_COVER_ID";

    public static final String BOOK_COVER_IMAGE_URL = "BOOK_COVER_IMAGE_URL";
    public static final String BOOK_SEARCH_URL = "BOOK_SEARCH_URL";
    public static final String CURRENT_BOOK_SEARCH_URL = "CURRENT_BOOK_SEARCH_URL";

    private int currentPage = 1;

    private String currentBookSearchUrl;

    private GridView gridView;

    private Button nextPageButton;

    private BookSearchResultsItemsFetcher bookSearchResultsItemsFetcher = BookSearchResultsItemsFetcher.newInstance();

    public static BooksGridFragment newInstance(String bookSearchUrl) {
        Bundle args = new Bundle();
        args.putString(BOOK_SEARCH_URL, bookSearchUrl);
        BooksGridFragment booksGridFragment = new BooksGridFragment();
        booksGridFragment.setArguments(args);

        return booksGridFragment;
    }

//    public BooksGridFragment withBookSearchUrl(String bookSearchUrl) {
//        this.bookSearchUrl = bookSearchUrl;
//        return this;
//    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books_grid, container, false);

        gridView = (GridView) view.findViewById(R.id.book_covers_grid_view);

        nextPageButton = (Button) view.findViewById(R.id.next_page_grid_button);

        bookSearchResultsItemsFetcher = bookSearchResultsItemsFetcher
                .withContext(getActivity().getBaseContext())
                .withTargetView(gridView);

        final String bookSearchUrl = getArguments().getString(BOOK_SEARCH_URL);
        currentBookSearchUrl = bookSearchUrl;
        if (bookSearchUrl != null) {
            bookSearchResultsItemsFetcher
                    .fetchResults(bookSearchUrl);
        } else {
            throw new GeekItException("Book Search Url is null");
        }

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                currentBookSearchUrl = bookSearchUrl + "/page/" + currentPage;
                bookSearchResultsItemsFetcher
                        .fetchResults(currentBookSearchUrl);
            }
        });

        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String clickedItemId = bookSearchResultsItemsFetcher
                        .bookList.get(position).getId();
                final String clickedItemBookCoverImageUrl = bookSearchResultsItemsFetcher
                        .bookList.get(position).getBookCoverImageUrl();
                Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
                intent.putExtra(BOOK_COVER_ID, clickedItemId);
                intent.putExtra(BOOK_COVER_IMAGE_URL, clickedItemBookCoverImageUrl);
                intent.putExtra(CURRENT_BOOK_SEARCH_URL, currentBookSearchUrl);
                startActivity(intent);
            }
        });

        return view;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            bookSearchUrl = savedInstanceState.getString(BOOK_SEARCH_URL);
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(BOOK_SEARCH_URL, bookSearchUrl);
//    }
}
