package com.keyconsultant.easyendlesslist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.AsyncTask;

/**
 * This manager class also manages listeners for loading data. This is purely put together to fake a simple back-end network interaction.
 * It is not representative of how you would do this in a production environment.  
 * 
 * @author Trey Robinson
 *
 */
public class DemoListManager {

	private static DemoListManager mInstance;
	
	private static final int PAGE_SIZE = 15;
	private static final int THREAD_SLEEP_TIME = 1000;
	
	private List<ITestDataLoadListener> listeners;
	private List<String> testList;
	
	/**
	 * @return
	 * 		Singleton instance of the list manager. 
	 */
	public static DemoListManager getInstance(){
		if(mInstance == null){
			mInstance = new DemoListManager();
		}
		
		return mInstance;
	}
	
	/**
	 * Default constructor
	 */
	private DemoListManager(){
		listeners = new ArrayList<DemoListManager.ITestDataLoadListener>();
		
		//take our array and make it a list so it is easier to play with
		testList = new ArrayList<String>();
		Collections.addAll(testList, testData);
	}
	
	/**
	 * @param startingFrom
	 * 		Pass in our current index. That way we know where to start. Most services that support pagination will take some
	 *  	form of integer as an argument telling it how many records to skip before returning data. 
	 */
	public void getTestData(int startingFrom){
		new FakeNetworkRequest(startingFrom).execute();
	}
	
	
	//listener manager stuff
	
	/**
	 * @param listener
	 * 		Listener to add 
	 */
	public void registerListener(ITestDataLoadListener listener){
		listeners.add(listener);
	}
	
	/**
	 * @param listener
	 * 		Listener to remove
	 */
	public void unregisterListener(ITestDataLoadListener listener){
		listeners.remove(listener);
	}
	
	/**
	 * Let everyone know we got our data!
	 * @param data
	 * 		Data sent back to listeners
	 */
	private void notifyListenersSuccess(List<String> data){
		for(ITestDataLoadListener listener:listeners)
			listener.testDataLoadSuccessful(data);
	}
	
	/**
	 * Something went wrong. 
	 */
	private void notifyListenersError(){
		for(ITestDataLoadListener listener:listeners)
			listener.testDataLoadFailure();
	}
	
	/**
	 * Simple interface for our listeners
	 * 
	 * @author Trey Robinson
	 *
	 */
	public interface ITestDataLoadListener{
		public void testDataLoadSuccessful(List<String> data);
		public void testDataLoadFailure();
	}
	
	/**
	 * Our fake network request. A simple Async task with a sleep.
	 * @author Trey Robinson
	 *
	 */
	private class FakeNetworkRequest extends AsyncTask<String, Void, String> {

		private int startingAt;

		public FakeNetworkRequest(int startingAt){
			this.startingAt = startingAt;
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				Thread.sleep(THREAD_SLEEP_TIME);
			} catch (InterruptedException e) {
				notifyListenersError();
				e.printStackTrace();
			}

			return "Executed";
		}

		@Override
		protected void onPostExecute(String result) {

			if(startingAt > testList.size()){
				//pretend like the service did not return any data because we reached the end
				notifyListenersSuccess(new ArrayList<String>());
			} else{
				//still have data so lets send it back
				int endIndex = (startingAt + PAGE_SIZE > testList.size())?testList.size():startingAt+PAGE_SIZE;
				
				//create a new list otherwise we will get issues with concurrent modifications. Again.. not 
				// an issue you will have in a normal environment
				List<String> list = new ArrayList<String>();
				list.addAll(testList.subList(startingAt, endIndex));
				notifyListenersSuccess(list);
			}
		    
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

	/**
	 * Test data. A bunch of random names.
	 */
	private String[] testData = new String[]{"Thaddeus Harper"
			,"Lionel Ortiz"
			,"Geoffrey Hall"
			,"Keegan Fitzgerald"
			,"Xander Mcmahon"
			,"Lester Randall"
			,"Walker Parks"
			,"Lucius Wolf"
			,"Carter Fletcher"
			,"Thaddeus Howe"
			,"Kevin Finch"
			,"Walker England"
			,"Vincent Wise"
			,"Ronan Mann"
			,"Brett Carroll"
			,"Jameson Austin"
			,"Joseph Cantu"
			,"Neville Wallace"
			,"Jeremy Finley"
			,"Colin Kaufman"
			,"Theodore Slater"
			,"Craig Short"
			,"Kane Suarez"
			,"Rooney Potts"
			,"Carson Perkins"
			,"Cedric Richards"
			,"Abdul Callahan"
			,"Alfonso Watkins"
			,"Boris Solomon"
			,"Jared Donovan"
			,"Abraham Alvarez"
			,"Deacon Lynch"
			,"Caesar Wood"
			,"Isaac Nolan"
			,"Dean Patterson"
			,"Caldwell Chavez"
			,"Kasimir Haynes"
			,"Adam Burch"
			,"Devin Shepard"
			,"Sebastian Hurst"
			,"Kareem Barker"
			,"Colt Marquez"
			,"Camden Elliott"
			,"Jarrod Figueroa"
			,"Brendan Armstrong"
			,"John Caldwell"
			,"Alexander Russell"
			,"Melvin Coleman"
			,"Rafael Chapman"
			,"Slade Benson"
			,"Stuart Avery"
			,"Colt Holmes"
			,"Hamilton Dixon"
			,"Kamal Patton"
			,"Axel Hebert"
			,"Carl Mooney"
			,"Hayden Hays"
			,"Chase Medina"
			,"Marsden Patton"
			,"Thomas Rollins"
			,"Norman Holden"
			,"Tyrone Reyes"
			,"Conan Mcknight"
			,"Wade Austin"
			,"Phillip Salinas"
			,"Barclay Morris"
			,"Russell Fry"
			,"Axel Dunn"
			,"Kareem Montoya"
			,"Wylie Morales"
			,"Allistair Cardenas"
			,"Demetrius Jordan"
			,"Josiah Johns"
			,"Beau Crawford"
			,"Chaim Wyatt"
			,"Aaron Valentine"
			,"Hamilton Vaughn"
			,"Matthew Myers"
			,"Jesse Michael"
			,"Melvin Flowers"
			,"Harper Richards"
			,"Vernon Emerson"
			,"Baker Atkins"
			,"Jesse Moreno"
			,"Malachi Kent"
			,"Denton Pace"
			,"Logan Malone"
			,"Carson Cobb"
			,"Alfonso Estes"
			,"Gavin Daugherty"
			,"Darius Harrison"
			,"Brody Orr"
			,"Eaton Sanford"
			,"Ezekiel Sherman"
			,"Hasad Baird"
			,"Brock Stephens"
			,"Ralph Blanchard"
			,"Bruce Stone"
			,"Benedict Nguyen"};
	
}
