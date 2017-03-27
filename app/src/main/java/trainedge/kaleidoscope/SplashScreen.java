package trainedge.kaleidoscope;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private TextView name;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        name=(TextView)findViewById(R.id.name);
        image=(ImageView)findViewById(R.id.image);
        image.animate()
                .scaleX(1.5f)
                .scaleY(1.5f)
                .setDuration(1000)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent i= new Intent(SplashScreen.this,LoginActivity.class);
                        startActivity(i);

                    }
                });
        name.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(1000);
    }
}
