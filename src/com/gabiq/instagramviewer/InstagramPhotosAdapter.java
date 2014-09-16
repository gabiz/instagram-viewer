package com.gabiq.instagramviewer;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
        super(context, R.layout.item_photo, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_photo, parent, false);
        }

//        TextView tvUserName = (TextView) convertView
//                .findViewById(R.id.tvUserName);
        TextView tvCaption = (TextView) convertView
                .findViewById(R.id.tvCaption);
        ImageView imgPhoto = (ImageView) convertView
                .findViewById(R.id.imgPhoto);
        ImageView imgHeaderUserProfile = (ImageView) convertView
                .findViewById(R.id.imgHeaderUserProfile);
        TextView tvHeaderUserName = (TextView) convertView
                .findViewById(R.id.tvHeaderUserName);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvCreationTime = (TextView) convertView.findViewById(R.id.tvCreationTime);
        TextView tvCommentHeader = (TextView) convertView.findViewById(R.id.tvCommentHeader);
        TextView tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
        TextView tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);

        tvHeaderUserName.setText(photo.username);

        long createdAt = Long.parseLong(photo.createdTime);
        long delta = System.currentTimeMillis() / 1000 - createdAt;
        String timeStamp = "";
        if (delta < 0) {
            timeStamp = "now";
        } else if (delta > 60*60*24*30) {
            timeStamp = delta / (60*60*24*30) + "M";
        } else if (delta > 60*60*24*7) {
            timeStamp = delta / (60*60*24*7) + "w";
        } else if (delta > 60*60*24) {
            timeStamp = delta / (60*60*24) + "d";
        } else if (delta > 60*60) {
            timeStamp = delta / (60*60) + "h";
        } else if (delta > 60) {
            timeStamp = delta / 60 + "m";
        } else {
            timeStamp = delta + "s";
        }
//        String timestamp = (String) DateUtils.getRelativeTimeSpanString(createdAt * 1000, System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_RELATIVE);
        tvCreationTime.setText(timeStamp);
        
        String likesText = getContext().getString(R.string.pre_likes_text)
                + " " + photo.likesCount + " like";
        if (photo.likesCount != 1) {
            likesText = likesText + "s";
        }
        tvLikes.setText(likesText);
        tvCaption.setText(Html.fromHtml("<font color=\"#517fa4\"><b>" + photo.username +
                "</b></font> " + photo.caption));

        imgHeaderUserProfile.setImageResource(0);
        imgPhoto.setImageResource(0);

        Picasso.with(getContext()).load(photo.profilePictureUrl).error(R.drawable.ic_launcher).transform(new RoundTransform()).into(imgHeaderUserProfile);
        Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
        imgPhoto.getLayoutParams().height = imgPhoto.getLayoutParams().width;

        if (photo.commentCount > 0) {
//            tvCommentHeader.setText(Html.fromHtml("<font color=\"#a4a4a4\">view all " + photo.commentCount + " comments</font>"));
            tvCommentHeader.setText("view all " + photo.commentCount + " comments");

            
            tvComment1.setVisibility(View.VISIBLE);
            try {
                JSONObject comment = (JSONObject) photo.comments.get(0);
                tvComment1.setText(Html.fromHtml("<font color=\"#517fa4\"><b>" + comment.getJSONObject("from").getString("username") + "</b></font> " + comment.getString("text")));
            } catch (Exception e) {
            }

            if (photo.commentCount > 1) {
                tvComment2.setVisibility(View.VISIBLE);
                try {
                    JSONObject comment = (JSONObject) photo.comments.get(1);
                    tvComment2.setText(Html.fromHtml("<font color=\"#517fa4\"><b>" + comment.getJSONObject("from").getString("username") + "</b></font> " + comment.getString("text")));
                } catch (Exception e) {
                }

            } else {
                tvComment2.setVisibility(View.GONE);
            }
        } else {
            tvComment1.setVisibility(View.GONE);
        }
        
        return convertView;
    }
}
