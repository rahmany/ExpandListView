package com.example.explist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentDetail extends Fragment {  
   View v;
    @Override 
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
    	 v = inflater.inflate(R.layout.detail_layout, null);
		// find the expandable list
    	
    	//get the data from the master list  
        
        
       
       

		return v; // return our view 
    }  
       
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
           
        //get the data from the master list  
        Bundle bundle = getArguments();  
        String mname = bundle.getString("name");  
        String mtime = bundle.getString("time");  
    
        
        
        TextView name = (TextView) getActivity().findViewById(R.id.name);  
        TextView time = (TextView) getActivity().findViewById(R.id.time); 
        
        name.setText(mname);
        time.setText(mtime);
       
    }  
   
   
}  