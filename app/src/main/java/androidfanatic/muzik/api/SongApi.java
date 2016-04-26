package androidfanatic.muzik.api;

import java.util.List;

import androidfanatic.muzik.models.Song;
import retrofit2.http.GET;
import rx.Observable;

public interface SongApi {

    @GET("/songs.json") Observable<List<Song>> getSongs();
}

