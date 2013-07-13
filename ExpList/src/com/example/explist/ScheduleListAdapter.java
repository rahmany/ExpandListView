package com.example.explist;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ScheduleListAdapter extends BaseExpandableListAdapter {
			private final LayoutInflater inflater;
			// our ultimate arraylist that contains arraylist of days and their
			// events
			private ArrayList<Group> fullList;
			private Context mContext;
			// set the constructor up
			public ScheduleListAdapter(FragmentActivity fragmentActivity, ArrayList<Group> fullList) {
				this.fullList = fullList;
				
				this.mContext=fragmentActivity;
				
				// the layoutinflater for our child/group views
				inflater = LayoutInflater.from(fragmentActivity);
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
