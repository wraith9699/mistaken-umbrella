package com.usability.blindfire;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Gallery extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("this","Something");
        setContentView(R.layout.gallery);
    }
}