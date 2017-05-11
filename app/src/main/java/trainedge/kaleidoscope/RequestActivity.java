package trainedge.kaleidoscope;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import trainedge.kaleidoscope.adapters.RequestAdapter;
import trainedge.kaleidoscope.adapters.UserAdapter;
import trainedge.kaleidoscope.models.UserModel;

import static trainedge.kaleidoscope.adapters.UserAdapter.FOLLOWERS;
import static trainedge.kaleidoscope.adapters.UserAdapter.STATUS;

public class RequestActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(RequestActivity.this, UsersActivity.class);
                    startActivity(intent);
                    finish();
                    return true;

                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }

    };
    private RecyclerView rvBroadcastList;
    private RequestAdapter adapter;
    private ArrayList<UserModel> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rvBroadcastList = (RecyclerView) findViewById(R.id.rvBroadcastList);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        rvBroadcastList.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        adapter = new RequestAdapter(this, userList);
        rvBroadcastList.setAdapter(adapter);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference broadcastsRef = FirebaseDatabase.getInstance().getReference(FOLLOWERS).child(uid);
        /*final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("loading");*/
        //dialog.show();
        broadcastsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int pos = 0;
                userList.clear();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (!snapshot.child(STATUS).getValue(Boolean.class)) {
                            userList.add(new UserModel(snapshot));
                            adapter.notifyItemInserted(pos);
                            pos++;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
