package trainedge.kaleidoscope;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Feedback extends AppCompatActivity implements View.OnClickListener {

    private EditText etFeedback;
    private EditText etEmail;
    private Button btnSubmit;
    private ImageView ivBcak;
    private FirebaseDatabase db;
    private DatabaseReference feedbackRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        etFeedback =(EditText)findViewById(R.id.etFeedback);
        etEmail =(EditText)findViewById(R.id.etEmail);
        btnSubmit =(Button)findViewById(R.id.btnSubmit);
        ivBcak =(ImageView)findViewById(R.id.ivBack);

        db = FirebaseDatabase.getInstance();
        feedbackRef = db.getReference("Feedback");

        btnSubmit.setOnClickListener(this);
        ivBcak.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                Back();
                break;
            case R.id.btnSubmit:
                Submit();
                break;

        }
    }

    public void Submit(){
        String feedback=etFeedback.getText().toString();
        String email=etEmail.getText().toString();
        if(feedback.isEmpty()) {
            etFeedback.setError("Please enter the feedback before submitting");
            return;
        }

        //firebase upload
        HashMap<String,String> map=new HashMap<>();
        map.put("Feedback",feedback);
        map.put("UserName",email);

        feedbackRef.push().setValue(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                etFeedback.setText("");
                etEmail.setText("");
                Intent i = new Intent(Feedback.this, Settings.class);
                startActivity(i);
                finish();
                Toast.makeText(Feedback.this, "Thank You!!" +
                        "Your feedback has been successfully sent ", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void Back(){
        Intent i=new Intent(Feedback.this,Settings.class);
        startActivity(i);
        finish();
    }
}
