package com.ceco.geekit.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.ceco.geekit.R;
import com.ceco.geekit.app.net.BookDetailsFetcher;
import com.ceco.geekit.app.util.ItEbooksRestApiUrls;
import com.ceco.geekit.appabstract.net.WebFetcher;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 11 Oct 2015
 */
public class BookDetailsFragment extends Fragment {

    public static final String BOOK_COVER_ID = "BOOK_COVER_ID";

    public static final String BOOK_COVER_IMAGE_URL = "BOOK_COVER_IMAGE_URL";
//    public static final String CURRENT_BOOK_COVER_ID = "CURRENT_BOOK_COVER_ID";
//    public static final String CURRENT_BOOK_COVER_URL = "CURRENT_BOOK_COVER_URL";
//
////    private String clickedBookCoverUrl;
//
//    private OnDataPass dataPasser;

    private BookDetailsFetcher bookDetailsFetcher = BookDetailsFetcher.newInstance();

    public final WebFetcher.LoadImage imageLoader = WebFetcher.LoadImage.newInstance()
            .withDefaultImage(R.drawable.no_image)
            .withErrorImage(R.drawable.error_image);

    private ImageView bookCoverView;

    private Button downloadButton;

    private ExpandableListView bookDetailsExpListView;

    public static BookDetailsFragment newInstance(String clickedBookId, String clickedBookCoverUrl) {
        BookDetailsFragment detailsFragment = new BookDetailsFragment();

        Bundle args = new Bundle();
        args.putString(BOOK_COVER_ID, clickedBookId);
        args.putString(BOOK_COVER_IMAGE_URL, clickedBookCoverUrl);

        detailsFragment.setArguments(args);

        return detailsFragment;
    }

//    @Override
//    public void onAttach(Activity a) {
//        super.onAttach(a);
//        dataPasser = (OnDataPass) a;
//    }

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
                .withTargetView(bookDetailsExpListView)
                .withUrl(ItEbooksRestApiUrls.BOOK_DETAILS + clickedBookId)
                .execute();

//        Bundle args = new Bundle();
//        args.putString(CURRENT_BOOK_COVER_ID, clickedBookId);
//        args.putString(CURRENT_BOOK_COVER_URL, clickedBookCoverUrl);
//        dataPasser.onDataPass(args);

        return view;
    }

    //    private String clickedBookId;
    //
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

