package com.usability.blindfire;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.EditText;
import java.io.File;
import java.io.IOException;
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

	MediaPlayer beep;
	MediaRecorder recorder;
	MediaPlayer messagePlayer;
	boolean recording = false;

	EditText title;

	TextToSpeech tts;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("this", "Something");
		setContentView(R.layout.gallery);

		tts = new TextToSpeech(this.getApplicationContext(), null);
		beep = MediaPlayer.create(getApplicationContext(), R.raw.beep);
		messagePlayer = new MediaPlayer();

		title = (EditText) findViewById(R.id.title);
		title.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					titleChange();
					return true;
				}
				return false;
			}

		});

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
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteImage();
			}
		});

		ImageButton soraka = (ImageButton) findViewById(R.id.play);
		soraka.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (messagePlayer.isPlaying()) {
					stopMessage();
				} else {
					if ((new File(getAbsolutePath())).exists()) {
						playMessage();
					} else {
						speak("There is no recorded message.");
					}
				}
			}
		});

		ImageButton record = (ImageButton) findViewById(R.id.record);
		record.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (recording) {
					recording = false;
					saveMessage();
				} else {
					recording = true;
					recordMessage();
				}
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
			currImage = 0;
		}
		changeImage(currImage);
	}

	private void speak(String message) {
		tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
	}

	private void recordMessage() {
		speak("Record your message after the beep.");
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getAbsolutePath());
		try {
			recorder.prepare();
		} catch (IOException e) {
			Log.e("RecordMessage", "prepare() failed");
		}
		beep.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		recorder.start();
	}

	private void saveMessage() {
		recorder.stop();
		recorder.release();
		recorder = null;
		speak("Message recorded.");
	}

	private void playMessage() {
		try {
			messagePlayer.reset();
			messagePlayer.setDataSource(getAbsolutePath());
			messagePlayer.prepare();
			messagePlayer.start();
		} catch (IOException e) {
			Log.e("Playing message", "prepare() failed");
		}
	}

	private void stopMessage() {
		messagePlayer.stop();
	}

	private String getAbsolutePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/" + availableNames.get(currImage).replace(" ", "_")
				+ ".3gp";
	}
	
	private void titleChange() {
		File oldFile = new File(getAbsolutePath());
		availableNames.set(currImage, title.getText().toString());
		oldFile.renameTo(new File(getAbsolutePath()));
	}
}