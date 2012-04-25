/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.usability.blindfire;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// ----------------------------------------------------------------------

public class CameraPreview extends Activity {
	private Preview mPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Change so the layouts are built in XML

		// Forces the view into fullscreen mode
		// Note this should be removed once the layout is translated into XML
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// This is built from the bottom and going up in layers
		// Main layout that holds the button layer ON TOP OF the preview layer
		FrameLayout layout = new FrameLayout(this);

		// Create our Preview view and set it as the content of our activity.
		mPreview = new Preview(this);

		// Initial first layer
		layout.addView(mPreview);

		ViewFinderView vfv = new ViewFinderView(this);
		vfv.setId(0x7f080003);

		// the top layer, holding all the buttons
		RelativeLayout buttons = new RelativeLayout(this);

		//
		ImageView galleryImage = new ImageView(this);
		galleryImage.setImageResource(R.drawable.to_gallery_horizontal);
		LayoutParams galleryParam = new LayoutParams(130, 160);
		galleryParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		galleryImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent("com.usability.blindfire.GALLERY"));
			}

		});

		ImageView settingsImage = new ImageView(this);
		settingsImage.setImageResource(R.drawable.setting_horizontal);
		LayoutParams settingsParam = new LayoutParams(130, 160);
		settingsParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		settingsParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

		settingsImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent("com.usability.blindfire.CAMSET"));
				Log.d("gallary", "start");
			}
		});

		settingsImage.setPadding(-4, 0, 0, 0);

		ImageView takePictureImage = new ImageView(this);
		takePictureImage
				.setImageResource(R.drawable.take_picture_horizontal_vertical);
		LayoutParams takePictureParam = new LayoutParams(177, 370);
		takePictureParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		takePictureParam.addRule(RelativeLayout.CENTER_VERTICAL);

		final MediaPlayer mp = MediaPlayer.create(this, R.raw.click);

		takePictureImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mp.start();
			}

		});

		final ImageView flashImage = new ImageView(this);
		flashImage.setImageResource(R.drawable.flash_horizontal);
		LayoutParams flashParam = new LayoutParams(130, 160);
		flashParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		flashParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		final AtomicInteger i = new AtomicInteger(1);

		flashImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (i.get() == 1) {
					i.decrementAndGet();
					flashImage.setImageResource(R.drawable.no_flash_horizontal);
				} else {
					i.incrementAndGet();
					flashImage.setImageResource(R.drawable.flash_horizontal);
				}

			}

		});

		buttons.addView(settingsImage, settingsParam);
		buttons.addView(galleryImage, galleryParam);
		buttons.addView(flashImage, flashParam);
		buttons.addView(takePictureImage, takePictureParam);
		LayoutParams buttonParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		layout.setPadding(0, 0, -2, 0);
		layout.addView(buttons, buttonParams);

		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// mPreview.startFaceDetection();
			}

		});

		setContentView(layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.camera_settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		startActivity(new Intent("com.usability.blindfire.CAMSET"));
		return true;
	}

}

// ----------------------------------------------------------------------

class Preview extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder mHolder;
	Camera mCamera;

	public void startFaceDetection() {
		
		//Log.d("this: ");
		mCamera.startFaceDetection();
	}

	Preview(Context context) {
		super(context);

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		mCamera = Camera.open();
		try {

			mCamera.setPreviewDisplay(holder);

			mCamera.setFaceDetectionListener(new FaceDetectionListener() {

				@Override
				public void onFaceDetection(Face[] faces, Camera camera) {
					Log.d("facedetection", "Faces Found: " + faces.length);
					ViewFinderView view = ((ViewFinderView) (((Activity) getContext())
							.findViewById(0x7f080001)));
					view.setFaces(Arrays.asList(faces));
				}

			});
			
			

		} catch (IOException exception) {
			Log.d("This: ", exception.toString());
			mCamera.release();
			mCamera = null;
			// TODO: add more exception handling logic here
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.05;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;

		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters parameters = mCamera.getParameters();

		List<Size> sizes = parameters.getSupportedPreviewSizes();
		Size optimalSize = getOptimalPreviewSize(sizes, w, h);
		parameters.setPreviewSize(optimalSize.width, optimalSize.height);

		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}

}
