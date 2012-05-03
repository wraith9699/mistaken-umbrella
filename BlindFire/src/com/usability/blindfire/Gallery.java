package com.usability.blindfire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Arrays;

public class Gallery extends Activity {

	Integer[] img = { R.drawable.sample_0, R.drawable.sample_1,
			R.drawable.sample_2, R.drawable.sample_3, R.drawable.sample_4,
			R.drawable.sample_5, R.drawable.sample_6, R.drawable.sample_7 };
	ArrayList<Integer> availableImages = new ArrayList<Integer>(
			Arrays.asList(img));

	String[] name = { "Sample 0", "Sample 1", "Sample 2", "Sample 3",
			"Sample 4", "Sample 5", "Sample 6", "Sample 7" };
	ArrayList<String> availableNames = new ArrayList<String>(
			Arrays.asList(name));

	int currImage = 0;

	final TextToSpeech tts = new TextToSpeech(this.getApplicationContext(),
			null);

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

		ImageButton delete = (ImageButton) findViewById(R.id.trash);
		delete.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				deleteImage();
				return true;
			}

		});

		ImageButton soraka = (ImageButton) findViewById(R.id.play);
		soraka.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tts.speak("There is no recorded message.",
						TextToSpeech.QUEUE_FLUSH, null);
			}

		});

		changeImage(0);
	}

	private void prevImage() {

		if (currImage - 1 < 0) {
			currImage = availableImages.size() - 1;
		} else {
			currImage -= 1;
		}
		changeImage(currImage);
	}

	private void nextImage() {
		if (currImage + 1 > availableImages.size() - 1) {
			currImage = 0;
		} else {
			currImage += 1;
		}
		changeImage(currImage);
	}

	private void changeImage(int imageId) {
		ImageView imageDisplay = (ImageView) findViewById(R.id.pic);
		EditText title = (EditText) findViewById(R.id.title);

		imageDisplay.setImageResource(availableImages.get(imageId));
		title.setText(availableNames.get(imageId));
	}

	private void deleteImage() {
		availableImages.remove(currImage);
		availableNames.remove(currImage);
		if (currImage > availableImages.size() - 1) {
			currImage = availableImages.size() - 1;
		}
		changeImage(currImage);
	}
}