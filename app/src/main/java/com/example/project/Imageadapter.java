package com.example.project;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;

public class Imageadapter extends PagerAdapter {
    private Context mContext;
    private int[] mImageIds = new int[]{R.drawable.n, R.drawable.remin, R.drawable.log};//images to be displayed

    Imageadapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(mImageIds[position]);
        container.addView(imageView, 0);//e.g. image 1 will be 0 etc
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) { //used for when image transitions to next, essentially 'destroying it'
        container.removeView((ImageView) object);
    }
}