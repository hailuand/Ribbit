package com.ahailu.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class RibbitApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "o3zA7zFLnxTsTy1fZG95cbL2wEycdjBTgpwEvW2x", "ynFpdJ3fThmbAdv6Jq731TfIPXDCW93ZeXM6YxHZ");
		
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();
		}

}
