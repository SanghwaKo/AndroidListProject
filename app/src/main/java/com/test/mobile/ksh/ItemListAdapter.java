package com.test.mobile.ksh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by KSH on 2016-11-14.
 */

public class ItemListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Item> mItems;

    public ItemListAdapter(Context context, ArrayList<Item> items){
        mContext = context;
        mItems = items;
    }

    public int getCount(){
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ItemView itemView;
        Item item = mItems.get(position);

        if(convertView == null){
            itemView = new ItemView(mContext);
        }else{
            itemView = (ItemView)convertView;
        }

        itemView.setTitle(item.getTitle());
        itemView.setDateOfPic(item.getDateOfPic());

        if(item.getIsHasPic() == 0){
            itemView.showViews();
            itemView.setPhoto(item.getWidth(), item.getHeight(), item.getUrl());
        }else{
            itemView.hideViews();
        }

        return itemView;
    }

    private class ItemView extends LinearLayout {
        private ImageView image;
        private TextView txtTitle, txtSize, txtUrl, txtDate;

        public ItemView(Context context){
            super(context);

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.item_view, this, true);

            image = (ImageView)findViewById(R.id.image);
            txtTitle = (TextView)findViewById(R.id.txt_title);
            txtSize = (TextView)findViewById(R.id.txt_size);
            txtUrl = (TextView)findViewById(R.id.txt_url);
            txtDate = (TextView)findViewById(R.id.txt_date);
        }

        public void setTitle(String title){
            txtTitle.setText(title);
        }

        public void setPhoto(int width, int height, String url){
          //  txtSize.setText(width + "*" + height); // Dont need to show
          //  txtUrl.setText(url); // Dont need to show url.
          //  Glide.with(mContext).load(url).override(width/3, height/3).into(image);
            Glide.with(mContext).load(url).into(image);
        }

        public void setDateOfPic(String date){
            txtDate.setText(date);
        }

        public void showViews(){
            image.setVisibility(View.VISIBLE);
            txtUrl.setVisibility(View.VISIBLE);
            txtSize.setVisibility(View.VISIBLE);
        }

        public void hideViews(){
            image.setVisibility(View.GONE);
            txtUrl.setVisibility(View.GONE);
            txtSize.setVisibility(View.GONE);
        }
    }

}
