package trainedge.kaleidoscope.models;

import com.google.firebase.database.DataSnapshot;

import static trainedge.kaleidoscope.SignUpPage.DATE_OF_BIRTH;
import static trainedge.kaleidoscope.SignUpPage.EMAIL;
import static trainedge.kaleidoscope.SignUpPage.FIRST_NAME;
import static trainedge.kaleidoscope.SignUpPage.LAST_NAME;

/**
 * Created by xaidi on 12-05-2017.
 */

public class UserModel {


    private final String key;
    private final String name;
    private final String surname;
    private final String dob;
    private final String email;

    public UserModel(DataSnapshot snapshot) {

        key = snapshot.getKey();
        name = snapshot.child(FIRST_NAME).getValue(String.class);
        surname = snapshot.child(LAST_NAME).getValue(String.class);
        dob = snapshot.child(DATE_OF_BIRTH).getValue(String.class);
        email = snapshot.child(EMAIL).getValue(String.class);

    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }
}
