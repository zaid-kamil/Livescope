package trainedge.kaleidoscope;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static trainedge.kaleidoscope.MainActivity.BROADCASTS;
import static trainedge.kaleidoscope.UsersActivity.USERS;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference broadcastsRef = FirebaseDatabase.getInstance().getReference(BROADCASTS);
        broadcastsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    long childrenCount = dataSnapshot.getChildrenCount();
                    if (childrenCount > 0) {
                        BroadcastNotification.notify(MyService.this, "You have " + childrenCount + " new videos");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return START_STICKY;
    }
}
