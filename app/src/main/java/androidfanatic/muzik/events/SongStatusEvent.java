package androidfanatic.muzik.events;

public class SongStatusEvent {
    public enum STATUS{
        PLAYING,
        PAUSED,
        STOPPED
    };

    public STATUS status;

    public SongStatusEvent(STATUS status){
        this.status = status;
    }
}
