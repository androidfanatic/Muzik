package androidfanatic.muzik.utils;

import android.os.Environment;

import java.io.File;

import androidfanatic.muzik.models.Song;

public class SongUtil {

    private static final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath().concat(
            File.separator
    ).concat("Muzik").concat("/");

    // return status 0 or 1
    // 0 = not downloaded
    // 1 = downloaded
    public static boolean isDownloaded(Song song) {
        String url = song.getDl();
        String song_name = url.substring( url.lastIndexOf('/')+1, url.length());
        String song_path = path.concat(song_name);
        if(new File(song_path).exists()){
            return true;
        }
        return false;
    }
}
