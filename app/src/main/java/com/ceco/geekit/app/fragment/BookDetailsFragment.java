package com.ceco.geekit.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.ceco.geekit.R;
import com.ceco.geekit.app.exception.GeekItException;
import com.ceco.geekit.app.model.BookDetails;
import com.ceco.geekit.app.net.BookDetailsFetcher;
import com.ceco.geekit.app.util.ItEbooksRestApiUrls;
import com.ceco.geekit.appabstract.fragment.OnDataPass;
import com.ceco.geekit.appabstract.net.WebFetcher;

import static com.ceco.geekit.app.util.ItEbooksUtils.BOOK_COVER_ID;
import static com.ceco.geekit.app.util.ItEbooksUtils.BOOK_COVER_IMAGE_URL;
import static com.ceco.geekit.app.util.ItEbooksUtils.BOOK_DETAILS_TAG;
import static com.ceco.geekit.app.util.ItEbooksUtils.CURRENT_BOOK_DETAILS;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 11 Oct 2015
 */
public class BookDetailsFragment extends Fragment {

    private BookDetailsFetcher bookDetailsFetcher = BookDetailsFetcher.newInstance();

    public final WebFetcher.LoadImage imageLoader = WebFetcher.LoadImage.newInstance()
            .withDefaultImage(R.drawable.no_image)
            .withErrorImage(R.drawable.error_image);

    private ImageView bookCoverView;

    private Button downloadButton;

    private ExpandableListView bookDetailsExpListView;

    private OnDataPass dataPasser;


    public static BookDetailsFragment newInstance(String clickedBookId, String clickedBookCoverUrl) {
        BookDetailsFragment detailsFragment = new BookDetailsFragment();

        Bundle args = new Bundle();
        args.putString(BOOK_COVER_ID, clickedBookId);
        args.putString(BOOK_COVER_IMAGE_URL, clickedBookCoverUrl);

        detailsFragment.setArguments(args);

        return detailsFragment;
    }

    public static BookDetailsFragment newInstance(BookDetails bookDetails) {
        BookDetailsFragment detailsFragment = new BookDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(CURRENT_BOOK_DETAILS, bookDetails);

        detailsFragment.setArguments(args);

        return detailsFragment;
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books_details, container, false);

        downloadButton = (Button) view.findViewById(R.id.download_button);

        bookCoverView = (ImageView) view.findViewById(R.id.book_cover_details);

        bookDetailsExpListView = (ExpandableListView) view.findViewById(R.id.book_details_exp_list);

        String clickedBookId = getArguments().getString(BOOK_COVER_ID);
        String clickedBookCoverUrl = getArguments().getString(BOOK_COVER_IMAGE_URL);

        /**
         * Load book cover thumbnail for the book details view
         */
        imageLoader
                .withImageView(bookCoverView)
                .withImageUrl(clickedBookCoverUrl)
                .configure()
                .load();


        bookDetailsFetcher
                .withContext(getActivity().getBaseContext())
                .withTargetView(bookDetailsExpListView);


        final BookDetails bookDetails = getArguments()
                .getParcelable(CURRENT_BOOK_DETAILS);
        if (bookDetails != null) {
            bookDetailsFetcher.setViewOffline(bookDetails);
            Log.i(BOOK_DETAILS_TAG, ">>>>>>> Fetch offline");
        } else {
            if (clickedBookId != null) {
                bookDetailsFetcher
                        .fetchResults(ItEbooksRestApiUrls.BOOK_DETAILS + clickedBookId);
                Log.i(BOOK_DETAILS_TAG, ">>>>>>> Fetch online");
            } else {
                throw new GeekItException("Book Details ID is null");
            }
        }

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetails bookDetails = bookDetailsFetcher.getBookDetails();
                Bundle args = new Bundle();
                args.putParcelable(CURRENT_BOOK_DETAILS, bookDetails);
                dataPasser.onDataPass(args);
            }
        });

        return view;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            //Restore the fragment's state here
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }
}

