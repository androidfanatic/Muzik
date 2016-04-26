package androidfanatic.muzik.main;

import java.util.List;

import androidfanatic.muzik.api.SongApiImpl;
import androidfanatic.muzik.base.BasePresenter;
import androidfanatic.muzik.models.Song;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainView> {

    private final SongApiImpl songApi;

    public MainPresenter() {
        songApi = new SongApiImpl();
    }

    public void loadSongs() {
        Subscription subscription = songApi.getSongs().subscribe(new Action1<List<Song>>() {
            @Override public void call(List<Song> songs) {
                getView().setSongs(songs);
            }
        }, new Action1<Throwable>() {
            @Override public void call(Throwable throwable) {
                Timber.d("API phati.");
                throwable.printStackTrace();
                getView().onLoadError();
            }
        });
        addSubscription(subscription);
    }
}

