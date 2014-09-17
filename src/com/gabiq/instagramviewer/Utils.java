package com.gabiq.instagramviewer;

public class Utils {
    public static String getTimeStringFromInterval(long interval) {
        String timeStamp = "";
        if (interval < 0) {
            timeStamp = "now";
        } else if (interval > 60*60*24*30) {
            timeStamp = interval / (60*60*24*30) + "M";
        } else if (interval > 60*60*24*7) {
            timeStamp = interval / (60*60*24*7) + "w";
        } else if (interval > 60*60*24) {
            timeStamp = interval / (60*60*24) + "d";
        } else if (interval > 60*60) {
            timeStamp = interval / (60*60) + "h";
        } else if (interval > 60) {
            timeStamp = interval / 60 + "m";
        } else {
            timeStamp = interval + "s";
        }
        return timeStamp;
    }
}
