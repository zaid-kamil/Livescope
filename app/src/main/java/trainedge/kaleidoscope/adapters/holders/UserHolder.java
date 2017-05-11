package trainedge.kaleidoscope.adapters.holders;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import trainedge.kaleidoscope.R;

/**
 * Created by xaidi on 12-05-2017.
 */

public class UserHolder extends RecyclerView.ViewHolder {
    public View card;
    public TextView tvUsername;
    public FloatingActionButton fabRequest;

    public UserHolder(View itemView) {
        super(itemView);
        tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
        fabRequest = (FloatingActionButton) itemView.findViewById(R.id.fabRequest);
        card = itemView.findViewById(R.id.card);
    }
}
