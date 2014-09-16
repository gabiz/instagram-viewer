package com.gabiq.instagramviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    
    public String profilePictureUrl;
    public String createdTime;
    public int commentCount;
    public JSONArray comments;
    
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
        
        JSONObject commentsObj = photoJSON.getJSONObject("comments");
        commentCount = commentsObj.getInt("count");
        comments = commentsObj.getJSONArray("data");
    }
}
