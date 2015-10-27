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

import static com.ceco.geekit.app.util.ItEbooksUtils.BOOK_COVER_ID;
import static com.ceco.geekit.app.util.ItEbooksUtils.BOOK_COVER_IMAGE_URL;

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

    public static BookDetailsFragment newInstance(String clickedBookId, String clickedBookCoverUrl) {
        BookDetailsFragment detailsFragment = new BookDetailsFragment();

        Bundle args = new Bundle();
        args.putString(BOOK_COVER_ID, clickedBookId);
        args.putString(BOOK_COVER_IMAGE_URL, clickedBookCoverUrl);

        detailsFragment.setArguments(args);

        return detailsFragment;
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
                .withTargetView(bookDetailsExpListView)
                .withUrl(ItEbooksRestApiUrls.BOOK_DETAILS + clickedBookId)
                .execute();

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

