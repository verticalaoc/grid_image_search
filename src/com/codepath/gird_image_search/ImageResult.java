package com.codepath.gird_image_search;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable{
	private static final long serialVersionUID = -5574069889810170789L;
	public String title;
	public String url;
	public String tbUrl;
	public ImageResult(JSONObject imageResultJASON) {
		try {
			this.url = imageResultJASON.getString("url");
			this.title = imageResultJASON.getString("title");
			this.tbUrl = imageResultJASON.getString("tbUrl");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<ImageResult> fromJASONArray(JSONArray imageArray) {
		ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
		
		for(int i = 0; i<imageArray.length(); i++) {
			try {
				imageResults.add(new ImageResult(imageArray.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return imageResults;
	}
}
