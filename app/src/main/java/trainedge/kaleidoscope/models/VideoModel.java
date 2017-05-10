package trainedge.kaleidoscope.models;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Shreya on 09-05-2017.
 */

public class VideoModel {

    public static final String UPLOADER = "uploader";
    public static final String VIDEO_URL = "video_url";
    public static final String VIEWS = "views";
    public static final String BROADCAST_STATUS = "broadcast_status";
    public static final String NAME = "name";

    private final String uploader;
    private final String videoUrl;
    private final Integer views;
    private final Boolean broadcast_status;
    private final String name;
    private String key;

    public VideoModel(DataSnapshot snapshot) {

        key = snapshot.getKey();
        uploader = snapshot.child(UPLOADER).getValue(String.class);
        videoUrl = snapshot.child(VIDEO_URL).getValue(String.class);
        views = snapshot.child(VIEWS).getValue(Integer.class);
        name = snapshot.child(NAME).getValue(String.class);
        broadcast_status = snapshot.child(BROADCAST_STATUS).getValue(Boolean.class);
    }

    public String getName() {
        return name;
    }

    public Integer getViews() {
        return views;
    }

    public Boolean getBroadcast_status() {
        return broadcast_status;
    }

    public String getUploader() {
        return uploader;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getKey() {
        return key;
    }

}
