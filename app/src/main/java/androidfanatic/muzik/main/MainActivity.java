package androidfanatic.muzik.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import androidfanatic.muzik.R;
import androidfanatic.muzik.base.BaseActivity;
import androidfanatic.muzik.events.SongStatusEvent;
import androidfanatic.muzik.events.TogglePlayEvent;
import androidfanatic.muzik.models.Song;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.songs_list) RecyclerView songsList;
    @Bind(R.id.swiperefresh) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.btn_play) ImageButton playBtn;
    private SongsAdapter songsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        songsAdapter = new SongsAdapter();
        songsList.setAdapter(songsAdapter);
        songsList.setLayoutManager(new LinearLayoutManager(this));
        songsList.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout.setOnRefreshListener(this);
        setSupportActionBar(toolbar);

    }

    @Override protected void onStart() {
        super.onStart();
        getPresenter().loadSongs();
    }

    @NonNull @Override public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override public void setSongs(List<Song> songs) {


        //SongUtil.downloadAndPlay(songs.get(0));

        stopRefreshing();
        Timber.d("HERE");

        for (Song song : songs) {
            Timber.d("Gotxx:" + song.getName());
        }
        songsAdapter.setSongs(songs);
        songsAdapter.notifyDataSetChanged();
    }

    @Override public void onLoadError() {
        stopRefreshing();
        showToast("Unable to load songs.");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                startRefreshing();
                onRefresh();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onRefresh() {
        getPresenter().loadSongs();
    }

    public void stopRefreshing() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void startRefreshing() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @OnClick(R.id.btn_play) public void togglePlay(View view) {
        EventBus.getDefault().post(new TogglePlayEvent());
        Timber.d("Sent toggle play event!");
    }

    @Subscribe
    public void songEvent(SongStatusEvent event) {
        switch (event.status) {
            case PLAYING:
                playBtn.setImageResource(R.drawable.ic_pause_white_36dp);
                break;
            case STOPPED:
                playBtn.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                break;
            case PAUSED:
                playBtn.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                break;
        }
    }

}