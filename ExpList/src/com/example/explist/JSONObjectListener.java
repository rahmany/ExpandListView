package com.example.explist;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONObjectListener {
	public void onRemoteCallComplete(JSONObject jsonObjectFromNet) throws JSONException;
}