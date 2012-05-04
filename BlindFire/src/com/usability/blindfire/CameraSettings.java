package com.usability.blindfire;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.PreferenceActivity;

public class CameraSettings extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setPreferenceScreen(createPreferenceHierarchy());
	}

	private PreferenceScreen createPreferenceHierarchy() {
		// Root
		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(
				this);

		PreferenceCategory options = new PreferenceCategory(this);
		options.setTitle("Options");
		root.addPreference(options);

		CheckBoxPreference flashPref = new CheckBoxPreference(this);
		flashPref.setKey("toggle_preference");
		flashPref.setTitle("Flash");
		flashPref.setSummary("toggle controlling if flash is on or not");
		options.addPreference(flashPref);

		CheckBoxPreference delayPref = new CheckBoxPreference(this);
		delayPref.setKey("toggle_preference");
		delayPref.setTitle("Delay");
		delayPref
				.setSummary("toggle controlling if a 10 second delay is on or not");
		options.addPreference(delayPref);

		PreferenceCategory focus = new PreferenceCategory(this);
		focus.setTitle("Focus");
		root.addPreference(focus);

		final CheckBoxPreference ulPref = new CheckBoxPreference(this);
		ulPref.setKey("toggle_preference");
		ulPref.setTitle("Upper Left");
		focus.addPreference(ulPref);

		final CheckBoxPreference urPref = new CheckBoxPreference(this);
		urPref.setKey("toggle_preference");
		urPref.setTitle("Upper Right");
		focus.addPreference(urPref);

		final CheckBoxPreference cPref = new CheckBoxPreference(this);
		cPref.setKey("toggle_preference");
		cPref.setTitle("Center");
		focus.addPreference(cPref);

		final CheckBoxPreference llPref = new CheckBoxPreference(this);
		llPref.setKey("toggle_preference");
		llPref.setTitle("Lower Left");
		focus.addPreference(llPref);

		final CheckBoxPreference lrPref = new CheckBoxPreference(this);
		lrPref.setKey("toggle_preference");
		lrPref.setTitle("Lower Right");
		focus.addPreference(lrPref);

		cPref.setChecked(true);
		llPref.setChecked(false);
		lrPref.setChecked(false);
		ulPref.setChecked(false);
		urPref.setChecked(false);

		ulPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				if (arg1.equals(true)) {
					ulPref.setChecked((Boolean) arg1);
					urPref.setChecked(false);
					llPref.setChecked(false);
					lrPref.setChecked(false);
					cPref.setChecked(false);
				}
				return false;
			}
		});

		lrPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				if (arg1.equals(true)) {
					lrPref.setChecked((Boolean) arg1);
					urPref.setChecked(false);
					llPref.setChecked(false);
					ulPref.setChecked(false);
					cPref.setChecked(false);
				}
				return false;
			}
		});

		urPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				if (arg1.equals(true)) {
					urPref.setChecked((Boolean) arg1);
					lrPref.setChecked(false);
					llPref.setChecked(false);
					ulPref.setChecked(false);
					cPref.setChecked(false);
				}
				return false;
			}
		});

		cPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				if (arg1.equals(true)) {
					cPref.setChecked((Boolean) arg1);
					lrPref.setChecked(false);
					llPref.setChecked(false);
					ulPref.setChecked(false);
					urPref.setChecked(false);
				}
				return false;
			}
		});

		llPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {

				if (arg1.equals(true)) {
					llPref.setChecked((Boolean) arg1);
					lrPref.setChecked(false);
					urPref.setChecked(false);
					ulPref.setChecked(false);
					cPref.setChecked(false);
				}
				return false;
			}
		});

		return root;

	}
}
