package com.gvoscar.apps.postsapp.libs.base.glide;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class GlideImageLoader implements ImageLoader {
    private RequestManager requestManager;

    public GlideImageLoader(Context context) {
        if (context != null) {
            this.requestManager = Glide.with(context);
        }
    }

    @Override
    public void load(ImageView imgAvatar, String url) {
        try {
            this.requestManager.load(url).into(imgAvatar);
        } catch (Exception e) {
            Log.d(GlideImageLoader.class.getSimpleName(), e.getMessage(), e);
        }
    }
}
