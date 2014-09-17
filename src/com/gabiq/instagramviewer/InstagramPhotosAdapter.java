package com.gabiq.instagramviewer;

import java.util.List;

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
    public static final String TAG = InstagramPhotosAdapter.class.getName();

    
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
        super(context, R.layout.item_photo, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_photo, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.tvCaption = (TextView) convertView
                    .findViewById(R.id.tvCaption);
            viewHolder.imgPhoto = (ImageView) convertView
                    .findViewById(R.id.imgPhoto);
            viewHolder.imgHeaderUserProfile = (ImageView) convertView
                    .findViewById(R.id.imgHeaderUserProfile);
            viewHolder.tvHeaderUserName = (TextView) convertView
                    .findViewById(R.id.tvHeaderUserName);
            viewHolder.tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.tvCreationTime = (TextView) convertView.findViewById(R.id.tvCreationTime);
            viewHolder.tvCommentHeader = (TextView) convertView.findViewById(R.id.tvCommentHeader);
            viewHolder.tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
            viewHolder.tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tvHeaderUserName.setText(photo.username);
        
        long timeInterval = System.currentTimeMillis() / 1000 - Long.parseLong(photo.createdTime);
        viewHolder.tvCreationTime.setText(Utils.getTimeStringFromInterval(timeInterval));
        
        String likesText = getContext().getString(R.string.pre_likes_text)
                + " " + photo.likesCount + " like";
        if (photo.likesCount != 1) {
            likesText = likesText + "s";
        }
        viewHolder.tvLikes.setText(likesText);
        viewHolder.tvCaption.setText(Html.fromHtml("<font color=\"#517fa4\"><b>" + photo.username +
                "</b></font> " + photo.caption));

        viewHolder.imgHeaderUserProfile.setImageResource(0);
        viewHolder.imgPhoto.setImageResource(0);

        Picasso.with(getContext()).load(photo.profilePictureUrl).error(R.drawable.ic_launcher).transform(new RoundTransform()).into(viewHolder.imgHeaderUserProfile);
        Picasso.with(getContext()).load(photo.imageUrl).into(viewHolder.imgPhoto);
        viewHolder.imgPhoto.getLayoutParams().height = viewHolder.imgPhoto.getLayoutParams().width;

        if (photo.comments.size() > 0) {
            viewHolder.tvCommentHeader.setText("view all " + photo.commentCount + " comments");

            InstagramPhoto.InstagramComment comment1 = photo.comments.get(0);
            viewHolder.tvComment1.setText(Html.fromHtml("<font color=\"#517fa4\"><b>" + comment1.username + "</b></font> " + comment1.caption));
            viewHolder.tvComment1.setVisibility(View.VISIBLE);
            
            if (photo.comments.size() > 1) {
                InstagramPhoto.InstagramComment comment2 = photo.comments.get(1);
                viewHolder.tvComment2.setText(Html.fromHtml("<font color=\"#517fa4\"><b>" + comment2.username + "</b></font> " + comment2.caption));
                viewHolder.tvComment2.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvComment2.setVisibility(View.GONE);
            }
        } else {
            viewHolder.tvComment1.setVisibility(View.GONE);
        }
        
        return convertView;
    }
    
    private static class ViewHolder {
        ImageView imgHeaderUserProfile;
        TextView tvHeaderUserName;
        TextView tvCreationTime;
        
        ImageView imgPhoto;
        TextView tvLikes;
        TextView tvCaption;

        TextView tvCommentHeader;
        TextView tvComment1;
        TextView tvComment2;
    }
}
