package trainedge.kaleidoscope.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import trainedge.kaleidoscope.PlayerActivity;
import trainedge.kaleidoscope.R;
import trainedge.kaleidoscope.adapters.holders.VideoHolder;
import trainedge.kaleidoscope.models.VideoModel;

/**
 * Created by Shreya on 09-05-2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {
    public static final String EXTRA_KEY = "trainedge.kaleidoscope.adapters.extra_key";
    public static final String EXTRA_URL="trainedge.kaleidoscope.adapters.extra_url";
    public static final String EXTRA_NAME="trainedge.kaleidoscope.adapters.extra_name";
    Context context;
    List<VideoModel> list;

    public VideoAdapter(Context context, List<VideoModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.simple_broadcast_view, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        VideoModel model = list.get(position);
        //load data from a single model obj
        String uploader = model.getUploader();
        final String videoUrl = model.getVideoUrl();
        final String name = model.getName();
        Boolean broadcast_status = model.getBroadcast_status();
        final String key = model.getKey();
        Integer views = model.getViews();

        //bind data to holder
        holder.tvBName.setText(name);
        holder.tvUploader.setText(uploader);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra(EXTRA_KEY, key);
                intent.putExtra(EXTRA_URL, videoUrl);
                intent.putExtra(EXTRA_NAME, name);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
