package com.gabiq.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class PhotosActivity extends Activity {
    public static final String TAG = PhotosActivity.class.getName();
    
    private static final String CLIENT_ID = "4ff912bb27cc492c8b35785bdc59f661";
    private static String POPULAR_URL = "https://api.instagram.com/v1/media/popular?client_id="
            + CLIENT_ID;
    
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    private SwipeRefreshLayout swipeContainer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photos);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            } 
        });
        
        setupPopularPhotosList();
        fetchPopularPhotos();
    }

    private void setupPopularPhotosList() {
        photos = new ArrayList<InstagramPhoto>();
        aPhotos = new InstagramPhotosAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhotos);
    }
    
    private void fetchPopularPhotos() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(POPULAR_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                    JSONObject response) {
                swipeContainer.setRefreshing(false);

                aPhotos.clear();
                try {
                    JSONArray photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        try {
                            JSONObject photoJSON = photosJSON.getJSONObject(i);
                            if (photoJSON.getString("type").equals("image")) {
                                InstagramPhoto photo = new InstagramPhoto();
                                photo.parseJSON(photoJSON);
                                photos.add(photo);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(PhotosActivity.this, R.string.photo_parsing_error, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "error parsing photo e:"+e.toString());
                        }
                    }
                    
                } catch (JSONException e) {
                    Toast.makeText(PhotosActivity.this, R.string.photo_parsing_error, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                aPhotos.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                    String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                swipeContainer.setRefreshing(false);
                Log.e("ERROR", "error downloading json");
                Toast.makeText(PhotosActivity.this, R.string.download_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.photos, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
