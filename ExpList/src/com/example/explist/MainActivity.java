package com.example.explist;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.Adapter;

public class MainActivity extends FragmentActivity implements TabListener {
	// tab adapter and page viewer
	myAdapter myAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		JSONObjectclient oclient = new JSONObjectclient(this, ol);
		String ourl = "http://54.218.117.137/scoutservices/jsonobjectcommand.php?user=scoutreader&pass=readscout";
		oclient.execute(ourl);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
	getJSONObjectListener ol = new getJSONObjectListener() {

		@Override
		public void onRemoteCallComplete(JSONObject jsonObjectFromNet)throws JSONException 
		{	
			myAdapter = new myAdapter(getSupportFragmentManager(),jsonObjectFromNet);

			// Set up action bar.
			final ActionBar actionBar = getActionBar();

			// Specify that the Home/Up button should not be enabled
			actionBar.setHomeButtonEnabled(false);

			// Specified that we will be displaying tabs in the action bar.
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			// Set up the ViewPager with my custom adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(myAdapter);
			// set up a listener for when the user swipes between tabs
			mViewPager
					.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
						@Override
						public void onPageSelected(int position) {

							// when the user swipes, select the corresponding tab

							actionBar.setSelectedNavigationItem(position);
						}
					});

			// For each team member, add a tab with their name to the action bar.
			for (int i = 0; i < myAdapter.getCount(); i++) {
				// Create a tab with text corresponding to the page and get title info from the adapter.
				// include a tab listener for when this tab is selected.
				actionBar.addTab(actionBar.newTab()
						.setText(myAdapter.getPageTitle(i)).setTabListener(MainActivity.this));
			}
			/*JSONArray mJsonArray1 = new JSONArray();
			JSONArray mJsonArray2 = new JSONArray();
			
            for (int i = 0; i < jsonObjectFromNet.length(); i++) 
            {    
            	mJsonArray1 = jsonObjectFromNet.getJSONArray("Day1");
            	mJsonArray1 = jsonObjectFromNet.getJSONArray("Day2");
            }*/
            
        }
	};
}