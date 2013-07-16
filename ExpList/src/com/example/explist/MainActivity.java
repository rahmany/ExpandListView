package com.example.explist;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.explist.myAdapter.scheduleFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity {
	FrameLayout container;
	private static final boolean VERBOSE = true;
	private static final String TAG = "ActivityStatus:";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_list);
		
		if(savedInstanceState == null){
			JSONObjectClient oclient = new JSONObjectClient(this, ol);
			String ourl = "http://54.218.117.137/scoutservices/jsonobjectcommand.php?user=scoutreader&pass=readscout";
			oclient.execute(ourl);
		}
		
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
	JSONObjectListener ol = new JSONObjectListener() {

		@Override
		public void onRemoteCallComplete(JSONObject jsonObjectFromNet)throws JSONException 
		{	
			//Master data list Fragment  
				Fragment list = new scheduleFragment(jsonObjectFromNet);  
		           
		        FragmentManager fragmentManager = getSupportFragmentManager();  
		        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();  
		        
		        fragmentTransaction.add(R.id.list_container, list);  
		           
		        fragmentTransaction.commit();  
			
            
        }
	};

}