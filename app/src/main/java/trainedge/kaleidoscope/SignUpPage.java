package trainedge.kaleidoscope;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpPage extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etSurname;
    private EditText etEmail;
    private EditText etPassword;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private EditText etDate;
    private Button btnReset;
    private Button btnSubmit;
    private TextView tvGenderError;
    private RadioGroup rgRadioSex;
    private FirebaseDatabase db;
    private DatabaseReference signUpRef;
    private ImageView ivBack;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent i = new Intent(SignUpPage.this, MainActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    // User is signed out
                }
                // ...
            }
        };

        setContentView(R.layout.activity_sign_up_page);
        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvGenderError = (TextView) findViewById(R.id.tvGender);
        rbMale = (RadioButton) findViewById(R.id.rbMale);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);
        etDate = (EditText) findViewById(R.id.etDOB);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        db = FirebaseDatabase.getInstance();
        signUpRef = db.getReference("Users");
        ivBack.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReset:
                Reset();
                break;
            case R.id.btnSubmit:
                Submit();
                break;
            case R.id.ivBack:
                Back();
                break;
        }
    }

    public void Submit() {
        final String name = etName.getText().toString();
        final String surname = etSurname.getText().toString();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String date = etDate.getText().toString();
        if (name.isEmpty()) {
            etName.setError("Please enter your First Name!!");
            return;
        }
        if (surname.isEmpty()) {
            etSurname.setError("Please enter your Last Name");
            return;
        }
        if (email.isEmpty()) {
            etEmail.setError("Please enter a valid email address!!");
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Please enter your password!!");
            return;
        } else if (date.isEmpty()) {
            etDate.setError("Please enter your Date of Birth!!");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpPage.this, "invalid details "+task.getException()
                                    , Toast.LENGTH_SHORT).show();
                        }else{
                            HashMap<String, String> map = new HashMap<>();
                            map.put("FirstName", name);
                            map.put("LastName", surname);
                            map.put("Email", email);
                            map.put("DateOfBirth", date);
                            signUpRef.push().setValue(map, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    etName.setText("");
                                    etSurname.setText("");
                                    etEmail.setText("");
                                    etPassword.setText("");
                                    etDate.setText("");
                                    rbMale.setChecked(false);
                                    rbFemale.setChecked(false);
                                }
                            });
                        }

                        // ...
                    }
                });

        //firebase upload

    }

    public void Reset() {
        etName.setText("");
        etSurname.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etDate.setText("");
        rbMale.setChecked(false);
        rbFemale.setChecked(false);

    }

    public void Back() {
        Intent i = new Intent(SignUpPage.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
