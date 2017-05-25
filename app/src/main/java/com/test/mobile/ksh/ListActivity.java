package com.test.mobile.ksh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by KSH on 2016-11-14.
 */

public class ListActivity extends Activity implements AdapterView.OnItemClickListener{
    private static final String TAG = ListActivity.class.getSimpleName();
    private static String mUrl = "http://demo2587971.mockable.io/images";

    private ProgressDialog mProgressDialog;
    private ListView mListView;
    private ArrayList<Item> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.listview);
        mItems = new ArrayList<>();
        new GetData().execute();

        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent detailViewIntent = new Intent(ListActivity.this, ItemDetailActivity.class);
        detailViewIntent.putExtra(Constant.KEY_ITEM, mItems.get(position));
        startActivity(detailViewIntent);
    }

    private class GetData extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(ListActivity.this);
            mProgressDialog.setMessage(getString(R.string.please_wait));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }

            // Sort list by date of photos
            Collections.sort(mItems);
            ItemListAdapter adapter = new ItemListAdapter(ListActivity.this, mItems);
            mListView.setAdapter(adapter);
            //Adapter
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler handler = new HttpHandler();
            String jsonStr = handler.makeServiceCall(mUrl);

            if(jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray photoItems = jsonObj.getJSONArray(Constant.TAG_PHOTO);

                    for(int i=0; i<photoItems.length(); i++){
                        JSONObject photoItem = photoItems.getJSONObject(i);
                        Item item = new Item();

                        String dateOfPic = photoItem.getString(Constant.TAG_DATE);
                        String title = photoItem.getString(Constant.TAG_TITLE);
                        item.setDateOfPic(dateOfPic);
                        item.setTitle(title);

                        if(photoItem.has(Constant.TAG_WIDTH)){
                            item.setWidth(Integer.parseInt(photoItem.getString(Constant.TAG_WIDTH)));
                        }
                        if(photoItem.has(Constant.TAG_HEIGHT)){
                            item.setHeight(Integer.parseInt(photoItem.getString(Constant.TAG_HEIGHT)));
                        }
                        if(photoItem.has(Constant.TAG_URL)){
                            item.setUrl(photoItem.getString(Constant.TAG_URL));
                        }

                        mItems.add(item);
                    }
                }catch (final JSONException e){
                    if(Debug.DEBUG){
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.parsing_error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else {
                if(Debug.DEBUG){
                    Log.e(TAG, "Couldn't get json from server.");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_LONG).show();
                    }
                });

            }

            return null;
        }
    }
}
