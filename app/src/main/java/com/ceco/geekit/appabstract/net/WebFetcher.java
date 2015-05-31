package com.ceco.geekit.appabstract.net;

import android.app.ActivityManager;
import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 24 May 2015
 */
public class WebFetcher {

    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    private WebFetcher() {
        super();
    }

    public static void init(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();

        // Use 1/8th of the available memory for this memory cache.
        int cacheSize = 1024 * 1024 * memClass / 8;

        imageLoader = new ImageLoader(requestQueue, new BitmapLruCache(cacheSize));
    }

    public static RequestQueue getRequestQueue() {
        if (requestQueue != null) {
            return requestQueue;
        } else {
            throw new IllegalStateException("Volley RequestQueue not initialized!");
        }
    }
    /**
     * Returns instance of imageLoader initialized with {@see FakeImageCache} which effectively means
     * that no memory caching is used. This is useful for images that you know that will be show
     * only once.
     *
     * @return
     */
    public static ImageLoader getImageLoader() {
        if (imageLoader != null) {
            return imageLoader;
        } else {
            throw new IllegalStateException("Volley ImageLoader not initialized!");
        }
    }

    public static class LoadImage {

        private ImageLoader imageLoader = getImageLoader();

        private String imageUrl;

        private int defaultImageResId;

        private int errorImageResId;

        private ImageView imageView;

        private ImageLoader.ImageListener imageListener;

        private LoadImage() {
            super();
        }

        public static LoadImage newInstance() {
            return new LoadImage();
        }

        public LoadImage withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public LoadImage withDefaultImage(int defaultImageResId) {
            this.defaultImageResId = defaultImageResId;
            return this;
        }

        public LoadImage withErrorImage(int errorImageResId) {
            this.errorImageResId = errorImageResId;
            return this;
        }

        public LoadImage withImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public LoadImage configure() {
            imageListener = ImageLoader
                    .getImageListener(imageView, defaultImageResId, errorImageResId);

            return this;
        }

        public LoadImage load() {
            imageLoader.get(imageUrl, imageListener);
            return this;
        }

    }

    public static abstract class JsonRequest<T> {

        private String url;

        private Class<T> clazz;

        private Gson customGson;

        public JsonRequest withUrl(String url) {
            this.url = url;
            return this;
        }

        public JsonRequest withResponseEntity(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        public JsonRequest withCustomGsonConfig(Gson gson) {
            this.customGson = gson;
            return this;
        }

        public JsonRequest execute() {

            GsonRequest<T> request;
            if (customGson != null) {
                request= new GsonRequest<>(
                        url,
                        clazz,
                        createSuccessListener(),
                        createErrorListener(),
                        customGson);
            } else {
                request = new GsonRequest<>(
                        url,
                        clazz,
                        createSuccessListener(),
                        createErrorListener());
            }
            getRequestQueue().add(request);

            return this;
        }



        public abstract Response.Listener<T> createSuccessListener();

        public abstract Response.ErrorListener createErrorListener();
    }
}
