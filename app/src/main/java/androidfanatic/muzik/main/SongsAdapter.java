package androidfanatic.muzik.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidfanatic.muzik.R;
import androidfanatic.muzik.models.Song;
import androidfanatic.muzik.utils.SongUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongHolder> {

    private List<Song> songs = new ArrayList<>();
    private int selected = -1;

    @Override public SongsAdapter.SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_item, parent, false);
        return new SongHolder(view);
    }

    @Override public void onBindViewHolder(SongsAdapter.SongHolder holder, final int position) {
        final Song song = songs.get(position);

        Timber.d("Got : " + song.getName());

        holder.songName.setText(song.getName());
        holder.songItem.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                // DialogUtil.showDownloadDialog(view.getContext(), song);
                if (selected != position) {
                    notifyItemChanged(selected);
                    selected = position;
                    notifyItemChanged(position);
                }
            }
        });

        // Colors
        if (selected == position) {
            holder.songItemHolder.setBackgroundResource(R.color.list_background);
        } else {
            holder.songItemHolder.setBackgroundResource(android.R.color.transparent);
        }

        if(SongUtil.isDownloaded(song)){
            holder.songStatus.setImageResource(R.drawable.ic_play_arrow_pink_500_48dp);
        } else {
            holder.songStatus.setImageResource(R.drawable.ic_cloud_download_pink_500_48dp);
        }
    }

    @Override public int getItemCount() {
        return songs.size();
    }

    public void setSongs(List<Song> songs) {
        Timber.d("Even here!");
        this.songs = songs;
        selected = -1;
    }

    public class SongHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.song_name) TextView songName;
        @Bind(R.id.song_item) LinearLayout songItem;
        @Bind(R.id.song_item_holder) LinearLayout songItemHolder;
        @Bind(R.id.song_status) ImageView songStatus;

        public SongHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
