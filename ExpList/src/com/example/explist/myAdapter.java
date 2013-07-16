package com.example.explist;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class myAdapter extends FragmentPagerAdapter {
	final int NUM_TAB = 1; // number of tabs
	// I could not figure out a way to access the string array in values so I
	// set up an array
	// for tab titles
	private String title = "Schedule";
	private static final boolean VERBOSE = true;
	private static final String TAG = "ActivityStatus:";
	public myAdapter(FragmentManager fm) {
		super(fm);

	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given section.

		Fragment fragment = new scheduleFragment(null);

		return fragment;
	}

	@Override
	public int getCount() {
		// returns the number of tabs
		return NUM_TAB;
	}

	@Override
	public CharSequence getPageTitle(int position) {

		return title;

	}

	@SuppressLint("ValidFragment")
	public static class scheduleFragment extends Fragment {
		ArrayList<Group> groupItem = new ArrayList<Group>();
		JSONObject jsonObject;
		@SuppressLint("ValidFragment")
		public scheduleFragment(JSONObject jsonObject) {
			this.jsonObject = jsonObject;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// we inflate the layout containing the explist
			View v = inflater.inflate(R.layout.list_activity, null);
			// find the expandable list

			return v; // return our view
		}

		private OnChildClickListener myListItemClicked = new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					final int groupPosition, final int childPosition, long id) {

				// get the group header
				Group eventDay = groupItem.get(groupPosition);
				// get the child info
				EventItem eventInfo = eventDay.getEventList()
						.get(childPosition);
				// display it or do something with it

				Toast.makeText(
						scheduleFragment.this.getActivity(),
						"Clicked " + eventDay.getEventDay() + "/"
								+ eventInfo.getItemName() + "/"
								+ eventInfo.getItemTime(), Toast.LENGTH_LONG)
						.show();
				
				//create a Fragment  
				FragmentDetail detailFragment = new FragmentDetail();  
                
                Bundle mBundle = new Bundle();  
                mBundle.putString("name", eventInfo.getItemName()); 
                mBundle.putString("time", eventInfo.getItemTime()); 
                detailFragment.setArguments(mBundle);  
                   
                 FragmentManager fragmentManager = getActivity().getSupportFragmentManager();  
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();  
                   
                //check if the device is landscape or portrait 
               //Configuration configuration = getActivity().getResources().getConfiguration();  
               // int ori = configuration.orientation;  
                 fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(((ViewGroup)(getView().getParent())).getId(), detailFragment);  
               
                //if(ori == configuration.ORIENTATION_PORTRAIT){  
                 //   fragmentTransaction.addToBackStack(null);  
               // }  
                   
                fragmentTransaction.commit();  
				
				
				
				

				return false;
			}

		};

		// our group listener
		private OnGroupClickListener myListGroupClicked = new OnGroupClickListener() {

			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				// get the group header
				Group eventDay = groupItem.get(groupPosition);
				// display it or do something with it
				Toast.makeText(scheduleFragment.this.getActivity(),
						"group name " + eventDay.getEventDay(),
						Toast.LENGTH_LONG).show();

				return false;
			}

		};

		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			setRetainInstance(true);
			ExpandableListView ExlistView = (ExpandableListView) getActivity()
					.findViewById(R.id.list);
			// create an array list of eventItem so that we can put as many
			// shows/events occurring
			//ArrayList<EventItem> eventItems = new ArrayList<EventItem>();

			String name = null;
			String time = null;

			// populate the day/time of the list with static data that I created
			// in string value folder
			for (int i = 1; i < jsonObject.length() + 1; i++) 
            {
            	// create an array list of eventItem so that we can put as many
    			// shows/events occurring
            	ArrayList<EventItem> eventItems = new ArrayList<EventItem>();
            	try 
            	{
		        	for (int j = 0; j < jsonObject.getJSONArray("Day" + (i)).length(); j++) 
		        	{
						name = jsonObject.getJSONArray("Day" + (i)).getJSONObject(j).getString("SubEventName");
		            	//System.out.println("name test " + i + ":" + name);
		            	time = jsonObject.getJSONArray("Day" + (i)).getJSONObject(j).getString("SubEventStartTime");
		            	//System.out.println("Time test " + i + ":" + time);
		            	EventItem tempList = (EventItem) new EventItem(name, time);
						eventItems.add(tempList);
		        	}
            	} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	// create a group class (one per each day of the event) which
				// will contain all of the events(days of the festivals)
				// we need to create a group class for each event date
				Group groupTitle = new Group();
				groupTitle.setEventDay("Day " + (i));
				// populate the events for each day from our eventItems
				// arraylist above
				// since the data is static, each day will contain the same
				// stuff
				groupTitle.setEventList(eventItems);
				// add the days and events to our ultimate list which will pass
				// it to our adapter
				groupItem.add(groupTitle);
            }
			/*
			for (int i = 0; i < getResources().getStringArray(R.array.names).length; i++) {

				name = (getResources().getStringArray(R.array.names)[i]);
				time = (getResources().getStringArray(R.array.time)[i]);
				// create a temp list to hold the info and then add to our
				// ultimate list that has all the events of the expand list
				EventItem tempList = new EventItem(name, time);
				eventItems.add(tempList);
			}
			for (int j = 0; j < getResources().getStringArray(R.array.days).length; j++) {
				// create a group class (one per each day of the event) which
				// will contain all of the events(days of the festivals)
				// we need to create a group class for each event date
				Group groupTitle = new Group();
				// populate the day title from our static info
				// since I setup three days, we'll create three headers which
				// will expand (saturday,sunday,monday)
				groupTitle.setEventDay((getResources().getStringArray(
						R.array.days)[j]));
				// populate the events for each day from our eventItems
				// arraylist above
				// since the data is static, each day will contain the same
				// stuff
				groupTitle.setEventList(eventItems);
				// add the days and events to our ultimate list which will pass
				// it to our adapter
				groupItem.add(groupTitle);
			}
			*/
			// set the adapter for our list
			// once the data is all set, we need to pass it to our adapter
			ExlistView.setAdapter(new ScheduleListAdapter(getActivity(),
					groupItem));
			// no group indicator - meaning, no little arrows to indicate
			// whether the list expanded or collapsed
			ExlistView.setGroupIndicator(null);
			// listeners for header and items
			ExlistView.setOnChildClickListener(myListItemClicked);
			ExlistView.setOnGroupClickListener(myListGroupClicked);
		}
		@Override
	    public void onResume() {
	        super.onResume();
	        if (VERBOSE) Log.v(TAG, "+ ON RESUME +");
	    }

	    @Override
	    public void onPause() {
	        super.onPause();
	        if (VERBOSE) Log.v(TAG, "- ON PAUSE -");
	    }
	    @Override
	    public void onStart() {
	        super.onStart();
	        if (VERBOSE) Log.v(TAG, "++ ON START ++");
	    }
	    public void onReStart() {
	        super.onStart();
	        if (VERBOSE) Log.v(TAG, "++ ON RESTART ++");
	    }
	}
	

}
