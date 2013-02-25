package com.keyconsultant.easyendlesslist;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

/**
 * Base list fragment class. Load the footer view. 
 * @author Trey Robinson
 *
 */
public class BaseListFragment extends ListFragment {
	
	private ListViewFooter mFooter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mFooter = new ListViewFooter(getActivity());
		getListView().addFooterView(mFooter);
	}
	
    /**
     * Hide the loading view and set the display message
     * @param message
     * 			Message to display in the footer
     */
    public void loadComplete(String message){
    	mFooter.hideLoadingView();
    	mFooter.setLoadCompleteMessage(message);
    }
    
    /**
     * Show the loading view 
     */
    public void  loadStarted() {
    	mFooter.showLoadingView();
    }
}
