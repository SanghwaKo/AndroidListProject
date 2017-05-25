package com.test.mobile.ksh;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by KSH on 2016-11-14.
 */

public class ItemDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Item item = getIntent().getParcelableExtra(Constant.KEY_ITEM);
        // Show detail view of the selected-item.

        setContentView(R.layout.detail_view);

        ImageView image = (ImageView)findViewById(R.id.image);
        TextView txtTitle = (TextView)findViewById(R.id.txt_title);
        TextView txtDateSize = (TextView)findViewById(R.id.txt_date_size);
        TextView txtUrl = (TextView)findViewById(R.id.txt_url);

        txtTitle.setText(item.getTitle());

        if(item.getIsHasPic() == 0){
            txtUrl.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            txtUrl.setText(item.getUrl());
            txtDateSize.setText(item.getWidth() + "*" + item.getHeight() + " " + item.getDateOfPic());
            Glide.with(this).load(item.getUrl()).into(image);
            //Glide.with(this).load(item.getUrl()).override(item.getWidth(), item.getHeight()).into(image);
        }else{
            image.setVisibility(View.GONE);
            txtUrl.setVisibility(View.GONE);
            txtDateSize.setText(item.getDateOfPic());
        }

}
}
