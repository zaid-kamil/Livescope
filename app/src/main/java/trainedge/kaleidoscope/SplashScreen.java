package trainedge.kaleidoscope;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    public static final int REQUEST_CODE = 2312;
    private TextView name;
    private ImageView image;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        name = (TextView) findViewById(R.id.name);
        image = (ImageView) findViewById(R.id.image);
        pref = getSharedPreferences("SplashScreen", MODE_PRIVATE);
        handlePermissions();

    }

    private void handlePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                return;
            } else {
                moveToNext();
            }
        } else {
            moveToNext();
        }
    }

    private void moveToNext() {
        Boolean val = pref.getBoolean("hasVisited", false);
        if (val) {
            Intent i = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_DENIED) {
                handlePermissions();
            } else {
                moveToNext();
            }
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
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("hasVisited", true);
                        editor.apply();
                        handlePermissions();


                    }
                });
    }
}
