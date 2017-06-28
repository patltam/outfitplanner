package com.example.patrick.outfitplanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * Created by Patrick on 12/26/2016.
 */

public class ImageAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Clothes> mThumbIds;
    int param;

    // Constructor
    public ImageAdapter(Context c, ArrayList<Clothes> ids, int parameter) {
        this.mContext = c;
        this.mThumbIds = ids;
        this.param = parameter;
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(85,85));
            //imageView.setLayoutParams(new GridView.LayoutParams(400,400));
            imageView.setLayoutParams(new GridView.LayoutParams(this.param,this.param));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(18, 18, 18, 18);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        String imagePath = mThumbIds.get(position).getImage();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(mContext.openFileInput(imagePath));
            imageView.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (mThumbIds.get(position).getAllowed()) {
            imageView.setBackgroundColor(0x3300FF00);
        }
        else {
            imageView.setBackgroundColor(0x33FF0000);
        }
        return imageView;
    }
}
