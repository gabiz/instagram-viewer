package com.gabiq.instagramviewer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class InstagramPhoto {
    public class InstagramComment {
        public String username;
        public String caption;
        
        public void parseJSON(JSONObject commentJSON) throws JSONException {
            username = commentJSON.getJSONObject("from").getString("username");
            caption = commentJSON.getString("text");
        }
    }
    
    
    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    
    public String profilePictureUrl;
    public String createdTime;
    public int commentCount;
    public List<InstagramComment> comments;
    
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
        
        // Parse comments
        JSONObject commentsObj = photoJSON.getJSONObject("comments");
        commentCount = commentsObj.getInt("count");
        JSONArray commentsJSON = commentsObj.getJSONArray("data");
        
        comments = new ArrayList<InstagramComment>();
        int jsonCommentCount = commentsJSON.length();
        for (int i=0; i<jsonCommentCount; i++) {
            Log.d("foo", "comment #"+i);
            JSONObject commentJSON = commentsJSON.getJSONObject(i);
            InstagramComment comment = new InstagramComment();
            comment.parseJSON(commentJSON);
            comments.add(comment);
        }
    }
}
