package trainedge.kaleidoscope.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import trainedge.kaleidoscope.R;
import trainedge.kaleidoscope.adapters.holders.CommentHolder;
import trainedge.kaleidoscope.models.CommentModel;



public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {
    Context context;
    List<CommentModel> list;

    public CommentAdapter(Context context, List<CommentModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.simple_comment_item, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        CommentModel model = list.get(position);
        //load data from a single model obj
        final String key = model.getKey();

        //bind data to holder
        holder.tvBName.setText(model.getBy());
        holder.tvUploader.setText(model.getComment());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}