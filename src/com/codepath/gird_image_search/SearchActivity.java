package com.codepath.gird_image_search;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	private static final String URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=";
	
	EditText etInput;
	Button btnSearch;
	GridView gvResults;

	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	
	private void initViews() {
		etInput = (EditText) findViewById(R.id.etInput);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(SearchActivity.this, FullImageActivity.class);
				
				intent.putExtra("result", imageResults.get(position));
				startActivity(intent);
			}
			
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initViews();
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultsAdapter(this, imageResults);
		gvResults.setAdapter(aImageResults);
	}

	public void onClickSearch(View v) {
		String keyword = etInput.getText().toString();
		Toast.makeText(this, URL+keyword, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(URL+keyword, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				JSONArray result = null;
				try {
					result = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					aImageResults.addAll(ImageResult.fromJASONArray(result));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
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
