package trainedge.kaleidoscope;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import trainedge.kaleidoscope.adapters.CommentAdapter;
import trainedge.kaleidoscope.models.CommentModel;

import static trainedge.kaleidoscope.adapters.VideoAdapter.EXTRA_KEY;
import static trainedge.kaleidoscope.adapters.VideoAdapter.EXTRA_NAME;
import static trainedge.kaleidoscope.adapters.VideoAdapter.EXTRA_URL;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String COMMENTS = "comments";
    private static final int REQUEST_CODE_VIEW = 232;
    private JCVideoPlayerStandard playerStandard;
    private TextView tvVideoName;
    private RecyclerView rvComments;
    private DatabaseReference commentsRef;
    private FloatingActionButton fabComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);

        tvVideoName = (TextView) findViewById(R.id.tvVideoName);
        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        fabComment = (FloatingActionButton) findViewById(R.id.fabComment);
        fabComment.setOnClickListener(this);
        getVideoPathFromFirebase();
        setUpCommentList();
    }

    private void setUpCommentList() {
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        final List<CommentModel> list= new ArrayList<>();
        String key = getIntent().getStringExtra(EXTRA_KEY);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        commentsRef = FirebaseDatabase.getInstance().getReference("broadcasts").child(uid).child(key).child(COMMENTS);
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    list.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        list.add(new CommentModel(snapshot));
                        rvComments.setAdapter(new CommentAdapter(PlayerActivity.this,list));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getVideoPathFromFirebase() {
        String videoUrl = getIntent().getStringExtra(EXTRA_URL);
        String name = getIntent().getStringExtra(EXTRA_NAME);
        tvVideoName.setText(name);

        playerStandard.setUp(videoUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "Broadcast");
        playerStandard.thumbImageView.setImageResource(R.drawable.logo);

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabComment:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.view_editor, null);
                dialogBuilder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.etComment);

                dialogBuilder.setTitle("Enter Comment");
                dialogBuilder.setMessage("Give a comment on this video");
                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String comment = edt.getText().toString();
                        if(comment.isEmpty()){
                            edt.setError("Please add some text");
                            return;
                        }
                        else{
                            HashMap<String,Object> data= new HashMap<String, Object>();
                            data.put("comment",comment);
                            String displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                            if (displayName==null){
                                displayName = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            }
                            data.put("by", displayName);
                            commentsRef.push().setValue(data);
                        }
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();

                break;
        }
    }
}
