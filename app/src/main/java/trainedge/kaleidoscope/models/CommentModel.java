package trainedge.kaleidoscope.models;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Shreya on 09-05-2017.
 */

public class CommentModel {

    public static final String BY = "by";
    public static final String COMMENT = "comment";
    private final String key;
    private final String comment;
    private final String by;

    public CommentModel(DataSnapshot snapshot) {

        key = snapshot.getKey();
        by = snapshot.child(BY).getValue(String.class);
        comment = snapshot.child(COMMENT).getValue(String.class);

    }

    public String getKey() {
        return key;
    }

    public String getComment() {
        return comment;
    }

    public String getBy() {
        return by;
    }
}
