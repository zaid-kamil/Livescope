package trainedge.kaleidoscope;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        jcVideoPlayerStandard.setUp(getVideoPathFromFirebase(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"Broadcast");
        jcVideoPlayerStandard.thumbImageView.setImageResource(R.drawable.logo);
    }

    private String getVideoPathFromFirebase() {
        // TODO: 07-05-2017 get video details from intents
        // TODO: 07-05-2017 fetch from database
        return null;
    }

}
