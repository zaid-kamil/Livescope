package trainedge.kaleidoscope;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private TextView tvAccount;
    private TextView tvAbout;
    private TextView tvFeedback;
    private TextView tvLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        tvAccount =(TextView)findViewById(R.id.tvAccount);
        tvAbout =(TextView)findViewById(R.id.tvAbout);
        tvFeedback =(TextView)findViewById(R.id.tvFeedback);
        tvLogOut =(TextView)findViewById(R.id.tvLogOut);

        tvAccount.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAccount:
                //accountSettings();
                break;
            case R.id.tvAbout:
                about();
                break;
            case R.id.tvFeedback:
                feedback();
                break;
            case R.id.tvLogOut:
                showAlert();
                break;
        }
    }
    private void about() {
        Intent i=new Intent(Settings.this,About.class);
        startActivity(i);
    }

    private void feedback() {
        Intent i=new Intent(Settings.this,Feedback.class);
        startActivity(i);
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_warning_black_24dp);
        builder.setTitle("Warning");
        builder.setMessage("   Sure to Log Out??");
        builder.setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    FirebaseAuth.getInstance().signOut();
                            try{
                                LoginManager.getInstance().logOut();
                            }
                            catch ( Exception Ignore){
                            }
                    Intent i = new Intent(Settings.this, LoginActivity.class);
                    startActivity(i);
                    Toast.makeText(Settings.this, "LogOut Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Settings.this, "LogOut Cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}
