package com.usability.blindfire;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Gallery extends Activity {

	String[] availableImages = null;
	int currImage;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("this", "Something");
		setContentView(R.layout.gallery);

		currImage = 0;
		availableImages = fileList();
		
		ImageButton prev = (ImageButton) findViewById(R.id.prev_image);
		prev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				prevImage();
			}

		});

		ImageButton next = (ImageButton) findViewById(R.id.next_image);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nextImage();
			}

		});

		ImageButton changeView = (ImageButton) findViewById(R.id.go_to_camera);
		changeView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(
						"com.usability.blindfire.MAIN"));
			}

		});
	}

	public void prevImage() {
		if (currImage - 1 < 0) {
			currImage = availableImages.length - 1;
		} else {
			currImage -= 1;
		}
		loadImage();
	}

	public void nextImage() {		
		if (currImage + 1 > availableImages.length - 1) {
			currImage = 0;
		} else {
			currImage += 1;
		}
		loadImage();
	}
	
	public void loadImage() {	
		File pic = new File(availableImages[currImage]);
		if(pic.exists()){
		    Bitmap myBitmap = BitmapFactory.decodeFile(pic.getAbsolutePath());

		    ImageView imageDisplay = (ImageView) findViewById(R.id.pic);
		    imageDisplay.setImageBitmap(myBitmap);
		}
	}
}