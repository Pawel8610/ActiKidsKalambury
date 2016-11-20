package com.fgamesteam.free1.actikids;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by pawel on 2016-06-15.
 */
public class PokazObrazAsyncTask extends AsyncTask<Void, Void, Void> {
    private final WeakReference<ImageView> imageViewReferenceProtect;
    private final WeakReference<ImageView> imageViewReferenceImage;

    public PokazObrazAsyncTask(ImageView imageViewProtect, ImageView imageViewImage) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReferenceProtect = new WeakReference<ImageView>(imageViewProtect);
        imageViewReferenceImage = new WeakReference<ImageView>(imageViewImage);
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        final ImageView imageViewProtect = imageViewReferenceProtect.get();
        final ImageView imageViewImage = imageViewReferenceImage.get();

        if (imageViewProtect.getVisibility() == View.INVISIBLE) {
            imageViewProtect.setVisibility(View.VISIBLE);
            imageViewImage.setVisibility(View.INVISIBLE);
        } else {
            imageViewImage.setVisibility(View.VISIBLE);
            imageViewProtect.setVisibility(View.INVISIBLE);
        }



    }
}
