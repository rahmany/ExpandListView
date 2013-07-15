package com.example.explist;



import com.example.explist.myAdapter.scheduleFragment;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity {
	FrameLayout container;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_list);
		
		if(savedInstanceState == null){
		//Master data list Fragment  
		Fragment list = new scheduleFragment();  
           
        FragmentManager fragmentManager = getSupportFragmentManager();  
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();  
        
        fragmentTransaction.add(R.id.list_container, list);  
           
        fragmentTransaction.commit();  
		}
		
		
	}
	 
	

}