package com.ceco.geekit.app.net;

import com.ceco.geekit.app.model.BookSearchResultsItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 25 May 2015
 */
public class BookArrayJsonDeserializer implements JsonDeserializer<BookSearchResultsItem[]> {

    private JsonArray booksArray;

    @Override
    public BookSearchResultsItem[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        this.booksArray = (JsonArray) json.getAsJsonObject().get("Books");
        BookSearchResultsItem[] bookSearchResultsItems = new BookSearchResultsItem[booksArray.size()];

        for (int i = 0; i < booksArray.size(); i++) {
            bookSearchResultsItems[i] = new BookSearchResultsItem(
                    getValueFor("ID", i),
                    getValueFor("Title", i),
                    getValueFor("Image", i));
        }

        return bookSearchResultsItems;
    }

    private String getValueFor(String key, int i) {
        return booksArray.get(i)
                .getAsJsonObject()
                .get(key)
                .getAsString();
    }
}
