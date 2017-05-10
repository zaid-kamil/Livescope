package trainedge.kaleidoscope.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import trainedge.kaleidoscope.R;

/**
 * Created by Shreya on 09-05-2017.
 */

public class CommentHolder extends RecyclerView.ViewHolder {
    public View card;
    public TextView tvBName;
    public TextView tvUploader;

    public CommentHolder(View itemView) {
        super(itemView);
        tvBName = (TextView) itemView.findViewById(R.id.tvBName);
        tvUploader = (TextView) itemView.findViewById(R.id.tvUploader);
        card = itemView.findViewById(R.id.card);
    }
}

