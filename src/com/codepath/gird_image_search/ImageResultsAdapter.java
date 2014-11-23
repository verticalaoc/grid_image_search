package com.codepath.gird_image_search;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultsAdapter(Context context, List<ImageResult> images) {
		super(context, android.R.layout.simple_list_item_1, images);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		return super.getView(position, convertView, parent);
		
		ImageResult imageResult = getItem(position);
		
		if(convertView==null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.display_image, parent, false);
		}
		
		ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
		
		ivImage.setImageResource(0);
		tvTitle.setText(Html.fromHtml(imageResult.title));
		Picasso.with(getContext()).load(imageResult.tbUrl).into(ivImage);
		return convertView;
	}
}
