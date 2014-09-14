package com.gabiq.instagramviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    
    public String profilePictureUrl;
    public String createdTime;
    
    public void parseJSON(JSONObject photoJSON) throws JSONException {
        username = photoJSON.getJSONObject("user").getString("username");
        
        if (photoJSON.getJSONObject("caption") != null) {
            caption = photoJSON.getJSONObject("caption").getString("text");
        } else {
            caption = "";
        }
        imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
        imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
        likesCount = photoJSON.getJSONObject("likes").getInt("count");

        profilePictureUrl = photoJSON.getJSONObject("user").getString("profile_picture");
        createdTime = photoJSON.getString("created_time");
        
        JSONObject comments = photoJSON.getJSONObject("comments");
        int commentCount = comments.getInt("count");
        JSONArray commentData = comments.getJSONArray("data");
        
        Log.d("InstagramPhoto", "comments"+commentCount);
    }
}
