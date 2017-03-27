package trainedge.kaleidoscope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SignUpPage extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText password;
    private RadioButton male;
    private RadioButton female;
    private EditText date;
    private Button reset;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        name=(EditText)findViewById(R.id.etName);
        surname =(EditText)findViewById(R.id.etSurname);
        email =(EditText)findViewById(R.id.etEmail);
        password =(EditText)findViewById(R.id.etPassword);
        male =(RadioButton)findViewById(R.id.rbMale);
        female =(RadioButton)findViewById(R.id.rbFemale);
        date =(EditText)findViewById(R.id.etDOB);
        reset =(Button)findViewById(R.id.btnReset);
        submit =(Button)findViewById(R.id.btnSubmit);

        reset.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    public void Reset()
    {

    }

    public void Submit(){

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnReset:
                Reset();
                break;
            case R.id.btnSubmit:
                Submit();
                break;

        }

    }
}
