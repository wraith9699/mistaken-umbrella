package com.usability.blindfire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.EditText;

public class Gallery extends Activity {

	int[] availableImages = { R.drawable.sample_0, R.drawable.sample_1,
			R.drawable.sample_2, R.drawable.sample_2, R.drawable.sample_3,
			R.drawable.sample_4, R.drawable.sample_5, R.drawable.sample_6,
			R.drawable.sample_7 };
	String[] availableNames = { "Sample 0", "Sample 1", "Sample 2", "Sample 3",
			"Sample 4", "Sample 5", "Sample 6", "Sample 7" };
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
				startActivity(new Intent("com.usability.blindfire.CAMPRE"));
			}

		});
		changeImage(0);
	}

	private void prevImage() {

		if (currImage - 1 < 0) {
			currImage = availableImages.length - 1;
		} else {
			currImage -= 1;
		}
		changeImage(currImage);
	}

	private void nextImage() {
		if (currImage + 1 > availableImages.length - 1) {
			currImage = 0;
		} else {
			currImage += 1;
		}
		changeImage(currImage);
	}

	private void changeImage(int imageId) {
		ImageView imageDisplay = (ImageView) findViewById(R.id.pic);
		EditText title = (EditText) findViewById(R.id.title);

		imageDisplay.setImageResource(availableImages[imageId]);
		title.setText(availableNames[imageId]);
	}
}