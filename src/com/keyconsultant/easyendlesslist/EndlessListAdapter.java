package com.keyconsultant.easyendlesslist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A straight forward endless list adapter.  Simple and easy to implement.
 * 
 * @author Trey Robinson
 *
 */
public class EndlessListAdapter extends ArrayAdapter<String> {

	/**
	 *  The data that drives the adapter
	 */
	private List<String> data;
	
	
	/**
	 *	Whether or not we are currently loading data. A simple way to avoid queuing another network request before the current one is completed. 
	 *	If you want to run multiple requests at a time the implementation is slightly more complicated and is not supported in this demo app.  
	 */
	private boolean isLoading;
	
	
	/**
	 * Flag telling us our last network call returned 0 results and we do not need to execute any new requests
	 */
	private boolean moreDataToLoad;
	
	/**
	 * @param context
	 * 			The context
	 * @param textViewResourceId
	 * 			Resource for the rows of the listview
	 * @param newData
	 * 			Initial dataset. 
	 */
	public EndlessListAdapter(Context context, int textViewResourceId, List<String> newData) {
		super(context, textViewResourceId, newData);
		
		this.data = new ArrayList<String>();
		this.data.addAll(newData);
		
		moreDataToLoad = true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		ViewHolder viewHolder;
		
		//check to see if we need to load more data
		if(shouldLoadMoreData(data, position) )
			loadMoreData();
		
		if(v == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_row, null);
			
			viewHolder = new ViewHolder();
			viewHolder.tvTestString = (TextView)v.findViewById(R.id.tvTestString);
			v.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}
		
		String currentString = data.get(position);
		if(currentString != null){
			viewHolder.tvTestString.setText(currentString);
		}
		
		return v;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}
	
	/**
	 * Add the data to the current listview
	 * @param newData
	 * 			Data to be added to the listview
	 */
	public void add(List<String> newData)
	{
		isLoading = false;
		if(newData.isEmpty()){
			moreDataToLoad = false;
		} else{
			this.data.addAll(newData);
			notifyDataSetChanged();
		}
	}
	
	/**
	 * This function is designed to be tweaked. For the demo it just checks to see if you are halfway through the current list and executes
	 * a new request. 
	 * @param list
	 * 			Current list of data
	 * @param position
	 * 			Current view position
	 * @return
	 */
	private boolean shouldLoadMoreData(List<String> list, int position){
		boolean scrollRangeReached = (position > list.size()/2);
		return (scrollRangeReached && !isLoading && moreDataToLoad);
	}
	
	/**
	 * Call the fake backend and tell it we need some more data 
	 */
	private void loadMoreData(){
		isLoading = true;
		DemoListManager.getInstance().getTestData(data.size());
	}
	
	/**
	 * Viewholder for the listview row
	 * 
	 * @author Trey Robinson
	 *
	 */
	static class ViewHolder{
		TextView tvTestString;
	}
	
}
