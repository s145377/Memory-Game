package com.example.memorygame;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;

public class FullscreenActivity extends Activity {
	/** Called when the activity is first created. */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ScrollView v = new ScrollView(this);
		AbsoluteLayout a = new AbsoluteLayout(this);
		v.addView(a);
		
		
		for(int i = 0; i < 20; i++) {
			ImageButton cb = new ImageButton(this);
			cb.setX(i*20);
			a.addView(cb);
		}
		this.setContentView(v);
	}
}