package com.aceappsdev.calendar2013;

import android.app.Activity;
import android.os.Bundle;


public class Awaiting extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.awaiting);
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
