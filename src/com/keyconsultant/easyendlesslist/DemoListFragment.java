package com.keyconsultant.easyendlesslist;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keyconsultant.easyendlesslist.DemoListManager.ITestDataLoadListener;

/**
 * Demo fragment for simulating endless list. This fragment DOES NOT save state 
 * 
 * @author Trey Robinson
 *
 */
public class DemoListFragment extends BaseListFragment implements ITestDataLoadListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_demolist, container,false);
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		DemoListManager.getInstance().registerListener(this);
		
		//load the test data
		if(getListView().getCount() == 0)
			DemoListManager.getInstance().getTestData(0);
	}

	@Override
	public void onPause() {
		super.onPause();
		DemoListManager.getInstance().unregisterListener(this);
	}

	@Override
	public void testDataLoadSuccessful(List<String> data) {
		if(getListAdapter() == null){
			EndlessListAdapter adapter = new EndlessListAdapter(this.getActivity(), R.layout.list_row, data);
			setListAdapter(adapter);
		} else {
			if(data.isEmpty())
				loadComplete(getString(R.string.finishedLoading) + " " + getListAdapter().getCount() + " Names");
			
			((EndlessListAdapter)getListAdapter()).add(data);
		}
	}

	@Override
	public void testDataLoadFailure() {
		Log.v(this.getTag(), "Well something went wrong.. that is not good...");
		
	}
}
