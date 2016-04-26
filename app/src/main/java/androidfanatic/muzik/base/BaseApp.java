package androidfanatic.muzik.base;

import android.app.Application;
import android.content.Context;

import androidfanatic.muzik.BuildConfig;
import androidfanatic.muzik.R;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseApp extends Application {

    public static Context context;

    public static Context getContext() {
        return context;
    }

    @Override public void onCreate() {
        super.onCreate();
        context = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("PiratesWriters.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

    }

}
