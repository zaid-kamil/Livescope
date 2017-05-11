package trainedge.kaleidoscope.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import trainedge.kaleidoscope.R;
import trainedge.kaleidoscope.RequestActivity;
import trainedge.kaleidoscope.adapters.holders.UserHolder;
import trainedge.kaleidoscope.models.UserModel;

/**
 * Created by xaidi on 12-05-2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<UserHolder> {
    public static final String FOLLOWERS = "followers";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    private final String displayName;
    private final String uid;
    private final DatabaseReference requestRef;
    Context context;
    List<UserModel> list;

    public RequestAdapter(Context context, List<UserModel> list) {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        requestRef = FirebaseDatabase.getInstance().getReference(FOLLOWERS);
        this.context = context;
        this.list = list;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.simple_comment_item, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserHolder holder, int position) {
        final UserModel model = list.get(position);
        //load data from a single model obj
        final String key = model.getKey();

        //bind data to holder
        holder.tvUsername.setText(model.getName());
        holder.fabRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> requestData=new HashMap<String, Object>();
                requestData.put(NAME,model.getName());
                requestData.put(STATUS,true);
                requestRef.child(uid).child(model.getKey()).setValue(requestData);
                Toast.makeText(context, "request accepted", Toast.LENGTH_SHORT).show();
                holder.fabRequest.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}