package trainedge.kaleidoscope;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        etName=(EditText)findViewById(R.id.etName);
        etSurname =(EditText)findViewById(R.id.etSurname);
        etEmail =(EditText)findViewById(R.id.etEmail);
        etPassword =(EditText)findViewById(R.id.etPassword);
        tvGenderError =(TextView)findViewById(R.id.tvGender);
        rbMale =(RadioButton)findViewById(R.id.rbMale);
        rbFemale =(RadioButton)findViewById(R.id.rbFemale);
        etDate =(EditText)findViewById(R.id.etDOB);
        btnReset =(Button)findViewById(R.id.btnReset);
        btnSubmit =(Button)findViewById(R.id.btnSubmit);
        rgRadioSex =(RadioGroup)findViewById(R.id.radioSex);

        db = FirebaseDatabase.getInstance();
        signUpRef = db.getReference("Users");
        btnReset.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
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

        }
    }

    public void Submit() {
        String name=etName.getText().toString();
        String surname=etSurname.getText().toString();
        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();
        String date=etDate.getText().toString();
        String gender=rgRadioSex.toString();
        if(name.isEmpty()) {
            etName.setError("Please enter your First Name!!");
            return;
        }
        if (surname.isEmpty()){
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
        }
        else if(date.isEmpty()) {
            etDate.setError("Please enter your Date of Birth!!");
            return;
        }
        if(!rgRadioSex.isSelected()) {
            tvGenderError.setText("Please select your Gender!!");
            tvGenderError.setTextColor(Color.RED);
            return;
        }

        //firebase upload
        HashMap<String,String> map=new HashMap<>();
        map.put("FirstName",name);
        map.put("LastName",surname);
        map.put("Email",email);
        map.put("Password",password);
        map.put("Gender",gender);
        map.put("DateOfBirth",date);
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
                Intent i = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(SignUpPage.this, "Your Kaleidoscope Account has been created successfully ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Reset()
    {
        etName.setText("");
        etSurname.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etDate.setText("");
        rbMale.setChecked(false);
        rbFemale.setChecked(false);

    }
}
