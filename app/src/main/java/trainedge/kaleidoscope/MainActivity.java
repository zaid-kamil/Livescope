package trainedge.kaleidoscope;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import trainedge.kaleidoscope.adapters.VideoAdapter;
import trainedge.kaleidoscope.models.VideoModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    public static final String BROADCASTS = "broadcasts";
    private FloatingActionButton fbVideo;
    private StorageReference mStorageRef;
    private CardView cvUploadCard;
    private TextView tvProgress;
    private TextView tvSizeInBytes;
    private TextView tvRemainingSize;
    private ProgressBar pbProgress;
    private TextView tvBroadcast;
    private RecyclerView rvBroadcastList;
    private List<VideoModel> videoList;
    private VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fbVideo = (FloatingActionButton) findViewById(R.id.fbVideo);
        fbVideo.setOnClickListener(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        cvUploadCard = (CardView) findViewById(R.id.tvUploadCard);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        tvSizeInBytes = (TextView) findViewById(R.id.tvSizeInBytes);
        tvRemainingSize = (TextView) findViewById(R.id.tvRemainingSize);
        pbProgress = (ProgressBar) findViewById(R.id.pbProgress);
        tvBroadcast = (TextView) findViewById(R.id.tvBroadcast);
        rvBroadcastList = (RecyclerView) findViewById(R.id.rvBroadcastList);

        cvUploadCard.setVisibility(View.GONE);

        setUpRecyclerView();
        startService(new Intent(this,MyService.class));
    }

    private void setUpRecyclerView() {
        rvBroadcastList.setLayoutManager(new LinearLayoutManager(this));
        videoList = new ArrayList<>();
        adapter = new VideoAdapter(this, videoList);
        rvBroadcastList.setAdapter(adapter);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference broadcastsRef = FirebaseDatabase.getInstance().getReference(BROADCASTS).child(uid);
        /*final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("loading");*/
        //dialog.show();
        broadcastsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int pos = 0;
                videoList.clear();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        videoList.add(new VideoModel(snapshot));
                        adapter.notifyItemInserted(pos);
                        pos++;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            Intent i = new Intent(MainActivity.this, Settings.class);
            startActivity(i);
        }
        if (id == R.id.users) {
            Intent i = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fbVideo:
                dispatchTakeVideoIntent();
                break;
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            uploadToDatabase(videoUri);
        }
    }

    private void uploadToDatabase(Uri videoUri) {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        StorageReference riversRef = mStorageRef.child(uid).child("upload");
        cvUploadCard.setVisibility(View.VISIBLE);
        riversRef.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        @SuppressWarnings("VisibleForTests")
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        new AsyncTask<String, Void, Void>() {

                            @Override
                            protected Void doInBackground(String... params) {
                                String url = params[0];
                                String uid = params[1];
                                String user = params[2];
                                DatabaseReference broadcastsRef = FirebaseDatabase.getInstance().getReference("broadcasts").child(uid);
                                HashMap<String, Object> broadData = new HashMap<String, Object>();
                                broadData.put(VideoModel.UPLOADER, user);
                                broadData.put(VideoModel.VIDEO_URL, url);
                                broadData.put(VideoModel.VIEWS, 1);
                                broadData.put(VideoModel.BROADCAST_STATUS, true);
                                broadData.put(VideoModel.NAME, "user" + System.currentTimeMillis());
                                broadcastsRef.push().setValue(broadData);
                                return null;
                            }
                        }.execute(downloadUrl.toString(), uid, name == null ? email : name);

                        cvUploadCard.setVisibility(View.GONE);
                        Snackbar.make(fbVideo, "Uploaded", Snackbar.LENGTH_INDEFINITE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...

                        cvUploadCard.setVisibility(View.GONE);
                        Snackbar.make(fbVideo, "Sorry your video could not be uploaded " + exception.getMessage(), Snackbar.LENGTH_INDEFINITE);
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests")
                        long totalByteCount = taskSnapshot.getTotalByteCount();
                        @SuppressWarnings("VisibleForTests")
                        long bytesTransferred = taskSnapshot.getBytesTransferred();
                        tvSizeInBytes.setText("total size : " + totalByteCount);
                        tvRemainingSize.setText("uploaded : " + bytesTransferred);
                    }
                })
                .addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
    }
}



