package com.codepath.gird_image_search;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends Activity {

	EditText etImageSize;
	EditText etColorFilter;
	EditText etImageType;
	EditText etSiteFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setupViews();
		initSetting();
	}

	private void initSetting() {
		// get more parameter from setting
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		String imageSize = pref.getString("imageSize", "");
		String colorFilter = pref.getString("colorFilter", "");
		String imaegType = pref.getString("imageType", "");
		String siteFilter = pref.getString("siteFilter", "");

		etImageSize.setText(imageSize);
		etColorFilter.setText(colorFilter);
		etImageType.setText(imaegType);
		etSiteFilter.setText(siteFilter);
		
	}

	private void setupViews() {
		etImageSize = (EditText) findViewById(R.id.etImageSize);
		etColorFilter = (EditText) findViewById(R.id.etColorFilter);
		etImageType = (EditText) findViewById(R.id.etImageType);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}
	
	public void onClickSave(View v) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = pref.edit();
		edit.putString("imageSize", etImageSize.getText().toString());
		edit.putString("colorFilter", etColorFilter.getText().toString());
		edit.putString("imageType", etImageType.getText().toString());
		edit.putString("siteFilter", etSiteFilter.getText().toString());
		edit.commit();
		Toast.makeText(this, "Setting savd.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
