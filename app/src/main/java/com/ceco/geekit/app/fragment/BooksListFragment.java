package com.ceco.geekit.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ceco.geekit.R;
import com.ceco.geekit.app.exception.GeekItException;
import com.ceco.geekit.app.model.BookSearchResultsItem;
import com.ceco.geekit.app.net.BookSearchResultsItemsFetcher;
import com.ceco.geekit.appabstract.fragment.FragmentUtil;
import com.ceco.geekit.appabstract.fragment.OnDataPass;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 10 Oct 2015
 */
public class BooksListFragment extends Fragment {

    private static final String BOOK_SEARCH_URL = "BOOK_SEARCH_URL";

    public static final String CURRENT_BOOK_SEARCH_URL = "CURRENT_BOOK_SEARCH_URL";

    public static final String CURRENT_BOOK_COVER_ID = "CURRENT_BOOK_COVER_ID";

    public static final String CURRENT_BOOK_COVER_URL = "CURRENT_BOOK_COVER_URL";

    public static final String CURRENT_BOOK_SEARCH_RESULTS = "CURRENT_BOOK_SEARCH_RESULTS";

    private static final Pattern DIGITS_PATTERN = Pattern.compile("/page/(\\d+)$");

    private OnDataPass dataPasser;

    private int currentPage = 1;

    private List<BookSearchResultsItem> currentBookSearchResults;

    private String currentBookSearchUrl;

    private ListView listView;

    private Button nextPageButton;

    private BookSearchResultsItemsFetcher bookSearchResultsItemsFetcher = BookSearchResultsItemsFetcher.newInstance();


    public static BooksListFragment newInstance(String bookSearchUrl) {
        Bundle args = new Bundle();
        args.putString(BOOK_SEARCH_URL, bookSearchUrl);
        BooksListFragment booksListFragment = new BooksListFragment();
        booksListFragment.setArguments(args);

        return booksListFragment;
    }
    public static BooksListFragment newInstance(String bookSearchUrl, List<BookSearchResultsItem> bookSearchResults) {
        Bundle args = new Bundle();
        args.putString(BOOK_SEARCH_URL, bookSearchUrl);
        args.putParcelableArrayList(CURRENT_BOOK_SEARCH_RESULTS,
                new ArrayList<>(bookSearchResults));
        BooksListFragment booksListFragment = new BooksListFragment();
        booksListFragment.setArguments(args);

        return booksListFragment;
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books_list, container, false);

        listView = (ListView) view.findViewById(R.id.book_covers_list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        nextPageButton = (Button) view.findViewById(R.id.next_page_list_button);

        bookSearchResultsItemsFetcher = bookSearchResultsItemsFetcher
                .withContext(getActivity().getBaseContext())
                .withTargetView(listView);

        final ArrayList<BookSearchResultsItem> bookSearchResults = getArguments()
                .getParcelableArrayList(CURRENT_BOOK_SEARCH_RESULTS);
        final String bookSearchUrl = getArguments().getString(BOOK_SEARCH_URL);
        if (bookSearchResults != null) {
            bookSearchResultsItemsFetcher.setViewOffline(bookSearchResults);
        } else {
            if (bookSearchUrl != null) {
                bookSearchResultsItemsFetcher
                        .fetchResults(bookSearchUrl);
                currentBookSearchUrl = bookSearchUrl;
            } else {
                throw new GeekItException("Book Search Url is null");
            }
        }

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentBookSearchUrl == null) {
                    currentBookSearchUrl = bookSearchUrl;
                }

                if (currentBookSearchUrl != null) {

                    final Matcher matcher = DIGITS_PATTERN.matcher(currentBookSearchUrl);
                    if (matcher.find()) {
                        currentPage = Integer.parseInt(matcher.group(1));
                        currentPage++;

                        String cleanedBookSearchUrl = currentBookSearchUrl.replaceAll("(\\d+)$", "");
                        currentBookSearchUrl = cleanedBookSearchUrl + currentPage;
                    } else {
                        currentPage++;
                        currentBookSearchUrl += "/page/" + currentPage;
                    }
                    bookSearchResultsItemsFetcher
                            .fetchResults(currentBookSearchUrl);
                } else {
                    throw new GeekItException("Book Search Url for next page is null");
                }
            }
        });

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentBookSearchResults = bookSearchResultsItemsFetcher.bookList;
                final String clickedItemId = currentBookSearchResults.get(position).getId();
                final String clickedItemBookCoverImageUrl = currentBookSearchResults.get(position).getBookCoverImageUrl();

                BookDetailsFragment detailsFragment = BookDetailsFragment
                        .newInstance(clickedItemId, clickedItemBookCoverImageUrl);
                FragmentUtil.replaceFragment(R.id.book_details_horizontal_placeholder,
                        detailsFragment, getFragmentManager());

                Bundle args = new Bundle();
                args.putString(CURRENT_BOOK_COVER_ID, clickedItemId);
                args.putString(CURRENT_BOOK_COVER_URL, clickedItemBookCoverImageUrl);
                args.putString(CURRENT_BOOK_SEARCH_URL, currentBookSearchUrl);
                args.putParcelableArrayList(CURRENT_BOOK_SEARCH_RESULTS,
                        new ArrayList<>(currentBookSearchResults));
                dataPasser.onDataPass(args);
            }
        });

        return view;
    }
}
