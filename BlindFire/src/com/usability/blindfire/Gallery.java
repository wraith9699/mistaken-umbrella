package com.usability.blindfire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Gallery extends Activity {

	int[] availableImages = { R.drawable.sample_0, R.drawable.sample_1,
			R.drawable.sample_2, R.drawable.sample_2, R.drawable.sample_3,
			R.drawable.sample_4, R.drawable.sample_5, R.drawable.sample_6,
			R.drawable.sample_7 };
	int currImage = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("this", "Something");
		setContentView(R.layout.gallery);

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
						"com.usability.blindfire.CAMERAPREVIEW"));
			}

		});
	}

	public void prevImage() {
		ImageView imageDisplay = (ImageView) findViewById(R.id.pic);
		if (currImage - 1 < 0) {
			currImage = availableImages.length - 1;
		} else {
			currImage -= 1;
		}
		imageDisplay.setImageResource(availableImages[currImage]);
	}

	public void nextImage() {
		ImageView imageDisplay = (ImageView) findViewById(R.id.pic);
		if (currImage + 1 > availableImages.length - 1) {
			currImage = 0;
		} else {
			currImage += 1;
		}
		imageDisplay.setImageResource(availableImages[currImage]);
	}
}