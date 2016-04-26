package androidfanatic.muzik.main;

import java.util.List;

import androidfanatic.muzik.base.BaseView;
import androidfanatic.muzik.models.Song;

public interface MainView extends BaseView {
    void setSongs(List<Song> music);

    void onLoadError();
}
