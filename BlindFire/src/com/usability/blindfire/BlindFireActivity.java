package com.usability.blindfire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BlindFireActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       finally{
    	   startActivity(new Intent("com.usability.blindfire.CAMPRE"));
       }
    }
}