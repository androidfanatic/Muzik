package androidfanatic.muzik.utils;

import android.widget.Toast;

import androidfanatic.muzik.base.BaseApp;

public class ToastUtil{
    public static void show(String msg) {
        Toast.makeText(BaseApp.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
