package com.example.explist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.explist.myAdapter.scheduleFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class JSONObjectClient extends AsyncTask<String, Void, JSONObject> {
	ProgressDialog progressDialog;
	JSONObjectListener JSONObjectListener;
	Context curContext;

	public JSONObjectClient(Context context, JSONObjectListener listener) {
		this.JSONObjectListener = listener;
		curContext = context;
	}

	public JSONObjectClient(scheduleFragment scheduleFragment,
			JSONObjectListener listener) {
		// TODO Auto-generated constructor stub
		this.JSONObjectListener = listener;
	}

	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	public static JSONObject connect(String url) {
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(url);

		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);

				JSONObject mJsonObject = new JSONObject(result);

				// Closing the input stream will trigger connection release
				instream.close();
				//myAdapter.scheduleFragment.publishProgress();
				return mJsonObject;
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void onPreExecute() {
		progressDialog = new ProgressDialog(curContext);
		progressDialog.setMessage("Loading..Please wait..");
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();

	}

	@Override
	protected JSONObject doInBackground(String... urls) {
		return connect(urls[0]);
	}

	@Override
	protected void onPostExecute(JSONObject json) {
		try {
			JSONObjectListener.onRemoteCallComplete(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		progressDialog.dismiss();
	}
	
}