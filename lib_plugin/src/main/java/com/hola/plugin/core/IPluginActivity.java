package com.hola.plugin.core;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public interface IPluginActivity {
    void initActivity(Activity activity);

    void onCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
