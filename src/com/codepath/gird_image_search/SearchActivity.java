package com.codepath.gird_image_search;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
	Button btnSetting;
	GridView gvResults;

	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
		
	private void initViews() {
		etInput = (EditText) findViewById(R.id.etInput);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSetting = (Button) findViewById(R.id.btnSetting);
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
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.i("INFO", "page:"+page);
				Log.i("INFO", "totalItemsCount:"+totalItemsCount);
				customLoadMoreDataFromApi(page, totalItemsCount); 
			}

			private void customLoadMoreDataFromApi(int page, int totalItemsCount) {
				String keyword = etInput.getText().toString();
				String requestUrl = getUrl(keyword);
				totalItemsCount +=1;
				requestUrl += "&start=" + totalItemsCount;
				Log.i("INFO", "requestUrl:"+requestUrl);
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(requestUrl, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						JSONArray result = null;
						try {
							result = response.getJSONObject("responseData").getJSONArray("results");
//							imageResults.clear();
							aImageResults.addAll(ImageResult.fromJASONArray(result));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
			
			
		});
	}

	public void onClickSearch(View v) {
		String keyword = etInput.getText().toString();
		Toast.makeText(this, URL+keyword, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		
		client.get(getUrl(keyword), new JsonHttpResponseHandler(){
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
	

	private String getUrl(String keyword) {
		String url = SearchActivity.URL + keyword;
		
		// get more parameter from setting
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		String imageSize = pref.getString("imageSize", ""); 
		String colorFilter = pref.getString("colorFilter", "");
		String imaegType = pref.getString("imaegType", "");
		String siteFilter = pref.getString("siteFilter", "");
		
		if (imageSize != "") {
			url += "&imgsz=" + imageSize;
		}
		
		if (colorFilter != "") {
			url += "&imgcolor=" + colorFilter;
		}
		
		if (imaegType != "") {
			url += "&imgtype=" + imaegType;
		}
		
		if (siteFilter != "") {
			url += "&as_sitesearch=" + siteFilter;
		}
		
		return url;
	}

	public void onClickSetting(View v) {
		Toast.makeText(this, "Loading Setting Page...", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(SearchActivity.this, SettingActivity.class);
		startActivity(intent);
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
