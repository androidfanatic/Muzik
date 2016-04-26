package androidfanatic.muzik.api;

import java.util.List;

import androidfanatic.muzik.models.Song;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SongApiImpl implements SongApi {


    private static final String BASEURL = "http://192.168.43.225:8080/";

    private static final SongApi SERVICE =
            new Retrofit.
                    Builder()
                    .baseUrl(BASEURL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(SongApi.class);

    @Override public Observable<List<Song>> getSongs() {
        return SERVICE
                .getSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .retry(5);
    }
}
