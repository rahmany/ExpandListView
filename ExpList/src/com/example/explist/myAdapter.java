package com.example.explist;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class myAdapter extends FragmentPagerAdapter {
	final int NUM_TAB = 1; // number of tabs
	// I could not figure out a way to access the string array in values so I
	// set up an array
	// for tab titles
	private String title = "Schedule";
	protected JSONObject jsonObject;
	public myAdapter(FragmentManager fm, JSONObject jsonObject) {
		super(fm);
		
		this.jsonObject = jsonObject;
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given section.

		Fragment fragment = new scheduleFragment(jsonObject);

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
		protected JSONObject jsonObject;
		@SuppressLint("ValidFragment")
		public scheduleFragment(JSONObject jsonObject) {
			this.jsonObject = jsonObject;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// we inflate the layout containing the explist
			View v = inflater.inflate(R.layout.list_activity, null);
			// find the expandable list
			ExpandableListView ExlistView = (ExpandableListView) v
					.findViewById(R.id.list);
			// create an array list of eventItem so that we can put as many
			// shows/events occurring
			ArrayList<EventItem> eventItems = new ArrayList<EventItem>();
			/*JSONArray mJsonArray1 = new JSONArray();
			JSONArray mJsonArray2 = new JSONArray();
			JSONObject mJsonObject = new JSONObject();
			*/
			String name = null;
			String time = null;
			//loops through the object that contains days, with a loop that loops through the events on that day and gets name and time
            for (int i = 1; i < jsonObject.length() + 1; i++) 
            {
            	try {
		        	for (int j = 0; j < jsonObject.getJSONArray("Day" + (i)).length(); j++) 
		        	{
						name = jsonObject.getJSONArray("Day" + (i)).getJSONObject(j).getString("SubEventName");
		            	System.out.println("name test " + i + ":" + name);
		            	time = jsonObject.getJSONArray("Day" + (i)).getJSONObject(j).getString("SubEventStartTime");
		            	System.out.println("Time test " + i + ":" + time);
		            	EventItem tempList = new EventItem(name, time);
						eventItems.add(tempList);
		        	}
            	} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            }
            

			// populate the day/time of the list with static data that I created
			// in string value folder
			/*for (int i = 0; i < mJsonArray1.length(); i++) {
				try {
					mJsonObject = mJsonArray1.getJSONObject(i);
					name = mJsonObject.getString("SubEventName");
					time = mJsonObject.getString("SubEventStartTime");
					System.out.print(name + " " + time);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// create a temp list to hold the info and then add to our
				// ultimate list that has all the events of the expand list
				EventItem tempList = new EventItem(name, time);
				eventItems.add(tempList);
			}
			*/
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

			// set the adapter for our list
			// once the data is all set, we need to pass it to our adapter
			ExlistView.setAdapter(new ScheduleListAdapter(groupItem));
			// no group indicator - meaning, no little arrows to indicate
			// whether the list expanded or collapsed
			ExlistView.setGroupIndicator(null);
			// listeners for header and items
			ExlistView.setOnChildClickListener(myListItemClicked);
			ExlistView.setOnGroupClickListener(myListGroupClicked);
			return v; // return our view
		}
		
		
		public View onProgressUpdate(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			return container;
			
		}
		private OnChildClickListener myListItemClicked = new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					 final int groupPosition,  final int childPosition, long id) {
				 
				

							
							// get the group header
							  Group eventDay = groupItem.get(groupPosition);
							// get the child info
							  EventItem eventInfo = eventDay.getEventList().get(childPosition);
							// display it or do something with it
							
							  Toast.makeText(
									  scheduleFragment.this.getActivity(),
										"Clicked " + eventDay.getEventDay() + "/"
												+ eventInfo.getItemName()+"/"+eventInfo.getItemTime(), Toast.LENGTH_LONG)
										.show();
		

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
				Toast.makeText(scheduleFragment.this.getActivity(), "group name " + eventDay.getEventDay(),
						Toast.LENGTH_LONG).show();

				return false;
			}

		};

		public class ScheduleListAdapter extends BaseExpandableListAdapter {
			private final LayoutInflater inflater;
			// our ultimate arraylist that contains arraylist of days and their
			// events
			private ArrayList<Group> fullList;

			// set the constructor up
			public ScheduleListAdapter(ArrayList<Group> fullList) {
				this.fullList = fullList;
				// the layoutinflater for our child/group views
				inflater = LayoutInflater.from(getActivity());
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// get the data associated with a child from our ultimate
				// arraylist
				ArrayList<EventItem> eventList = fullList.get(groupPosition)
						.getEventList();
				return eventList.get(childPosition);
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {

				return childPosition;
			}

			@Override
			public View getChildView(int groupPosition,
					final int childPosition, boolean isLastChild, View view,
					ViewGroup parent) {
				// get the view that displays the child within a group - takes
				// in the position of the child and group

				EventItem itemlist = (EventItem) getChild(groupPosition,
						childPosition);
				if (view == null) {

					view = inflater.inflate(R.layout.activity_child, null);

				}

				TextView name = (TextView) view
						.findViewById(R.id.childeventname);
				TextView time = (TextView) view
						.findViewById(R.id.childeventtime);

				name.setText(itemlist.getItemName());
				time.setText(itemlist.getItemTime());

				return view;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				// get the number of events in each day
				ArrayList<EventItem> eventList = fullList.get(groupPosition)
						.getEventList();
				return eventList.size();
			}

			@Override
			public Object getGroup(int groupPosition) {
				// get the data associated with each event day. it returns the
				// events for each day
				return fullList.get(groupPosition);
			}

			@Override
			public int getGroupCount() {
				return fullList.size();
			}

			@Override
			public long getGroupId(int groupPosition) {

				return groupPosition;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View view, ViewGroup parent) {
				Group days = (Group) getGroup(groupPosition);
				if (view == null) {

					view = inflater.inflate(R.layout.activity_group, null);
				}
				// set the name of each event day
				TextView groupheader = (TextView) view
						.findViewById(R.id.groupheader);
				groupheader.setText(days.getEventDay());

				return view;

			}

			@Override
			public boolean hasStableIds() {

				return true;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				// it allows us to use action listeners
				return true;
			}

		}

		public static void publishProgress() {
			// TODO Auto-generated method stub
			System.out.print("fuck this");
		}

	}

}
