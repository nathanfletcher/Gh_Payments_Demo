package com.ketecode.ghpaymentsdemo;

/**
 * Created by KobbyFletcher on 2/19/16.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.nhaarman.listviewanimations.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends ArrayAdapter<Product> {

    private final Context mContext;
    private final BitmapCache mMemoryCache;
    public ImageView imageView;
    public int imageResId;
    public List<Product> emojilist = new ArrayList<>();
    public Product myProduct = new Product();

    public GridViewAdapter(final Context context) {
        mContext = context;
        mMemoryCache = new BitmapCache();

        for (int i = 0; i < 10; i++) {
            //add(i);
        }
    }

    public GridViewAdapter(final Context context, List<Product> results) {

        mContext = context;
        mMemoryCache = new BitmapCache();
        for (int i = 0; i < results.size(); i++) {
            add(results.get(i));
        }


    }
    /*Nathan: YOu can also make a constructor that takes a list object too
    * just like a normal CustomArray Adapter. The quantity of objects in the
    * List<> you pass into the constructor will be the number of images which
    * would be displayed*/

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        /*LayoutInflater resultsInflater = LayoutInflater.from(getContext());
        View customView = resultsInflater.inflate(R.layout.custom_row, parent, false);*/
/*
        imageView = (ImageView) convertView;

        myProduct.theImage = new ImageView(mContext);
        myProduct.theImage = imageView;*/
/*

        if (imageView == null) {
            imageView = new ImageView(mContext);
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setMaxHeight(10);
        }
*/



/*
        if (imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
*/



/*        switch (getItem(position) % 5) {
            case 0:
                imageResId = R.drawable.img_nature1;
                break;
            case 1:
                imageResId = R.drawable.img_nature2;
                break;
            case 2:
                imageResId = R.drawable.img_nature3;
                break;
            case 3:
                imageResId = R.drawable.img_nature4;
                break;
            case 4:
                imageResId = R.drawable.image1;
                break;
            case 5:
                imageResId = R.drawable.image2;
                break;
            case 6:
                imageResId = R.drawable.image3;
                break;
            case 7:
                imageResId = R.drawable.image5;
                break;
            case 8:
                imageResId = R.drawable.image6;
                break;
            case 9:
                imageResId = R.drawable.image7;
                break;
            default:
                imageResId = R.drawable.img_nature5;
        }*/
/*

        switch (getItem(position)) {
            case 0:
                myProduct.setLocalResourceId(R.drawable.img_nature1);
                emojilist.add(myProduct);
                break;
            case 1:
                myProduct.setLocalResourceId(R.drawable.img_nature2);
                emojilist.add(myProduct);
                break;
            case 2:
                myProduct.setLocalResourceId(R.drawable.img_nature3);
                emojilist.add(myProduct);
                break;
            case 3:
                myProduct.setLocalResourceId(R.drawable.img_nature4);
                emojilist.add(myProduct);
                break;
            case 4:
                myProduct.setLocalResourceId(R.drawable.image1);
                emojilist.add(myProduct);
                break;
            case 5:
                myProduct.setLocalResourceId(R.drawable.image2);
                emojilist.add(myProduct);
                break;
            case 6:
                myProduct.setLocalResourceId(R.drawable.image3);
                emojilist.add(myProduct);
                break;
            case 7:
                myProduct.setLocalResourceId(R.drawable.image5);
                emojilist.add(myProduct);
                break;
            case 8:
                myProduct.setLocalResourceId(R.drawable.image6);
                emojilist.add(myProduct);
                break;
            case 9:
                myProduct.setLocalResourceId(R.drawable.image7);
                emojilist.add(myProduct);
                break;
            default:
                myProduct.setLocalResourceId(R.drawable.img_nature5);
                emojilist.add(myProduct);
        }
*/

/*        Bitmap bitmap = getBitmapFromMemCache(imageResId);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), imageResId);
            addBitmapToMemoryCache(imageResId, bitmap);
        }
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(this);

        return imageView;*/


        Product singleEmoji = getItem(position);
        //singleEmoji.setLocalResourceId()
        singleEmoji.setTheImage(new ImageView(mContext));
        //singleEmoji.theImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //singleEmoji.theImage.setScaleType(ImageView.);



        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
        GridView.LayoutParams lp = new GridView.LayoutParams(200,200);



        singleEmoji.theImage.setLayoutParams(lp);
        //singleEmoji.theImage.setLayoutParams();


        Bitmap bitmap = getBitmapFromMemCache(singleEmoji.getLocalResourceId());
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), singleEmoji.getLocalResourceId());
            addBitmapToMemoryCache(singleEmoji.getLocalResourceId(), bitmap);
        }

        singleEmoji.theImage.setImageBitmap(bitmap);
        //myProduct.theImage.setOnClickListener(this);

        return singleEmoji.theImage;
    }

    private void addBitmapToMemoryCache(final int key, final Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(final int key) {
        return mMemoryCache.get(key);
    }
/*
    @Override
    public void onClick(View v) {
        *//*Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image*//*");
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("android.resource://com.ketecode.imojitest/drawable/image1")); // Add image path - android.resource://com.ketecode.imojitest/drawable/image1
        startActivity(Intent.createChooser(share, "Share image using"));*//*

        //v.getResources().getResourceName(myProduct.getLocalResourceId())
        //+ myProduct.theImage.getResources().getResourceName(myProduct.getLocalResourceId())

        //+ imageView.getResources().getResourceName(imageView.getId())

        Toast.makeText(this.mContext, "Selected - ", Toast.LENGTH_SHORT).show();

        //emojilist.contains()
    }*/
}