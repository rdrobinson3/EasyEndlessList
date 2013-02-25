package com.keyconsultant.easyendlesslist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A footer that has two states. The first state is a loading view with a 
 * progress spinner. The second state will display a supplied message centered in the 
 * text view. 
 * 
 * @author Trey Robinson
 *
 */
public class ListViewFooter extends LinearLayout {

	/**
	 * Text view that holds the complete message
	 */
	private TextView mLoadCompleteMessage;
	
	
	/**
	 * The default visible loading view 
	 */
	private View mLoadingView;
	
	
	/**
	 * The hidden complete view 
	 */
	private View mLoadCompleteView;

	public ListViewFooter(Context context) {
		super(context);
		initViews(context);
	}
	
	public ListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
	}
	
	public ListViewFooter(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initViews(context);
	}
	
	/**
	 * Load the layouts
	 * @param context
	 * 			The context
	 */
	private void initViews(Context context){
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.list_footer, this, true);
		
		mLoadingView = (View)findViewById(R.id.loading_view);
		mLoadCompleteView = (View)findViewById(R.id.load_complete_view);
		mLoadCompleteMessage = (TextView)findViewById(R.id.tvLoadComplete);
	}
	
	/**
	 * @param message
	 * 		Message to display on the loading footer
	 */
	public void setLoadCompleteMessage(String message){
		mLoadCompleteMessage.setText(message);
	}
	
	/**
	 *  Show the loading view
	 */
	public void showLoadingView(){
		mLoadingView.setVisibility(View.VISIBLE);
		mLoadCompleteView.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * Hide the loading view
	 */
	public void hideLoadingView(){
		mLoadingView.setVisibility(View.INVISIBLE);
		mLoadCompleteView.setVisibility(View.VISIBLE);
	}
}
