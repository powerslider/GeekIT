package com.ceco.geekit.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 27 Oct 2015
 */
public class ItEbooksUtils {

    public static final String BOOK_COVER_ID = "BOOK_COVER_ID";
    public static final String BOOK_SEARCH_URL = "BOOK_SEARCH_URL";
    public static final String BOOK_COVER_IMAGE_URL = "BOOK_COVER_IMAGE_URL";
    public static final String CURRENT_BOOK_COVER_ID = "CURRENT_BOOK_COVER_ID";
    public static final String CURRENT_BOOK_SEARCH_URL = "CURRENT_BOOK_SEARCH_URL";
    public static final String CURRENT_BOOK_COVER_URL = "CURRENT_BOOK_COVER_URL";
    public static final String CURRENT_BOOK_SEARCH_RESULTS = "CURRENT_BOOK_SEARCH_RESULTS";

    private static final Pattern NEXT_PAGE_URL_PATTERN = Pattern.compile("/page/(\\d+)$");


    public static String constructNextPageBookSearchUrl(String currentBookSearchUrl, int currentPage) {
        final Matcher matcher = NEXT_PAGE_URL_PATTERN.matcher(currentBookSearchUrl);
        if (matcher.find()) {
            currentPage = Integer.parseInt(matcher.group(1));
            currentPage++;

            String cleanedBookSearchUrl = currentBookSearchUrl.replaceAll("(\\d+)$", "");
            return cleanedBookSearchUrl + currentPage;
        } else {
            currentPage++;
            return currentBookSearchUrl + "/page/" + currentPage;
        }
    }
}
