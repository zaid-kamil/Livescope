package trainedge.kaleidoscope;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PlayerActivity extends AppCompatActivity {

    private JCVideoPlayerStandard playerStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.   layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        playerStandard.setUp(getVideoPathFromFirebase(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"Broadcast");
        playerStandard.thumbImageView.setImageResource(R.drawable.logo);
    }

    private String getVideoPathFromFirebase() {
        // TODO: 07-05-2017 get video details from intents
        // TODO: 07-05-2017 fetch from database
        return null;
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
}
