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
import com.ceco.geekit.app.net.BookGridItemsFetcher;


/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 10 Oct 2015
 */
public class BooksGridFragment extends Fragment {

    public static final String BOOK_COVER_ID = "BOOK_COVER_ID";

    public static final String BOOK_COVER_IMAGE_URL = "BOOK_COVER_IMAGE_URL";

    private View view;

    private int currentPage = 1;

    public void setBookSearchUrl(String bookSearchUrl) {
        this.bookSearchUrl = bookSearchUrl;
    }

    private String bookSearchUrl;

    private GridView gridView;

    private Button nextPageButton;

    private BookGridItemsFetcher bookGridItemsFetcher = BookGridItemsFetcher.newInstance();

    // True or False depending on if we are in horizontal or duel pane mode
    boolean isDualPane;

    // Currently selected item in the ListView
    int currentCheckPosition = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_books_grid, container, false);
        bookGridItemsFetcher = bookGridItemsFetcher.withContext(getActivity().getBaseContext());
        renderBooksGrid();
        return view;
    }

    public void renderBooksGrid() {
        gridView = (GridView) view.findViewById(R.id.book_covers_grid_view);

        nextPageButton = (Button) view.findViewById(R.id.next_page_button);

        bookGridItemsFetcher = bookGridItemsFetcher.withTargetView(gridView);

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
                Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
                intent.putExtra(BOOK_COVER_ID, clickedItemId);
                intent.putExtra(BOOK_COVER_IMAGE_URL, clickedItemBookCoverImageUrl);
                startActivity(intent);
            }
        });
    }


//    // Called every time the screen orientation changes or Android kills an Activity
//    // to conserve resources
//    // We save the last item selected in the list here and attach it to the key curChoice
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("currentChoice", currentCheckPosition);
//    }
//
//    // Shows the hero data
//    void showDetails(int index) {
//
//        // The most recently selected hero in the ListView is sent
//        currentCheckPosition = index;
//
//        // Check if we are in horizontal mode and if yes show the ListView and
//        // the hero data
//        if (isDualPane) {
//
//            // Make the currently selected item highlighted
//            getListView().setItemChecked(index, true);
//
//            // Create an object that represents the current FrameLayout that we will
//            // put the hero data in
//            DetailsFragment details = (DetailsFragment)
//                    getFragmentManager().findFragmentById(R.id.details);
//
//            // When a DetailsFragment is created by calling newInstance the index for the data
//            // it is supposed to show is passed to it. If that index hasn't been assigned we must
//            // assign it in the if block
//            if (details == null || details.getShownIndex() != index) {
//
//                // Make the details fragment and give it the currently selected hero index
//                details = DetailsFragment.newInstance(index);
//
//                // Start Fragment transactions
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//
//                // Replace any other Fragment with our new Details Fragment with the right data
//                ft.replace(R.id.book_details_placeholder, details);
//
//                // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.commit();
//            }
//
//        } else {
//            // Launch a new Activity to show our DetailsFragment
//            Intent intent = new Intent();
//
//            // Define the class Activity to call
//            intent.setClass(getActivity(), BookDetailsActivity.class);
//
//            // Pass along the currently selected index assigned to the keyword index
//            intent.putExtra("index", index);
//
//            // Call for the Activity to open
//            startActivity(intent);
//        }
//    }
}
