package trainedge.kaleidoscope;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private TextView name;
    private ImageView image;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        name = (TextView) findViewById(R.id.name);
        image = (ImageView) findViewById(R.id.image);
        pref = getSharedPreferences("SplashScreen",MODE_PRIVATE);

        Boolean val=pref.getBoolean("hasVisited",false);
        if(val)
        {
            Intent i=new Intent(SplashScreen.this,LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        image.animate()
                .scaleX(1.5f)
                .scaleY(1.5f)
                .setDuration(5000)
                .setInterpolator(new FastOutSlowInInterpolator());
        name.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(5000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        SharedPreferences.Editor editor=pref.edit();
                        editor.putBoolean("hasVisited",true);
                        editor.apply();
                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(i);
                        finish();


                    }
                });
    }
}
