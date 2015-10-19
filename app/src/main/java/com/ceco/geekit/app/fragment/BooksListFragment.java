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
import com.ceco.geekit.app.net.BookSearchResultsItemsFetcher;
import com.ceco.geekit.appabstract.fragment.FragmentUtil;
import com.ceco.geekit.appabstract.fragment.OnDataPass;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 10 Oct 2015
 */
public class BooksListFragment extends Fragment {

    private static final String BOOK_SEARCH_URL = "BOOK_SEARCH_URL";

    public static final String CURRENT_BOOK_COVER_ID = "CURRENT_BOOK_COVER_ID";

    public static final String CURRENT_BOOK_COVER_URL = "CURRENT_BOOK_COVER_URL";

    private OnDataPass dataPasser;

    private int currentPage = 1;

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

        final String bookSearchUrl = getArguments().getString(BOOK_SEARCH_URL);
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
                bookSearchResultsItemsFetcher
                        .fetchResults(bookSearchUrl + "/page/" + currentPage);
            }
        });

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String clickedItemId = bookSearchResultsItemsFetcher
                        .bookList.get(position).getId();
                final String clickedItemBookCoverImageUrl = bookSearchResultsItemsFetcher
                        .bookList.get(position).getBookCoverImageUrl();

                BookDetailsFragment detailsFragment = BookDetailsFragment
                        .newInstance(clickedItemId, clickedItemBookCoverImageUrl);
                FragmentUtil.replaceFragment(R.id.book_details_horizontal_placeholder,
                        detailsFragment, getFragmentManager());

                Bundle args = new Bundle();
                args.putString(CURRENT_BOOK_COVER_ID, clickedItemId);
                args.putString(CURRENT_BOOK_COVER_URL, clickedItemBookCoverImageUrl);
                dataPasser.onDataPass(args);
            }
        });

        return view;
    }
}
