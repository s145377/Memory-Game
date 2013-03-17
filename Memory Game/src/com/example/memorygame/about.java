package com.example.memorygame;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.swarmconnect.SwarmActivity;

public class about extends SwarmActivity{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.about);
        TextView developedby = (TextView) findViewById(R.id.developedby);
		Typeface bit=Typeface.createFromAsset(getAssets(),"fonts/bus.ttf");
		developedby.setTypeface(bit);
		TextView about = (TextView) findViewById(R.id.textbody);
		about.setTypeface(bit);
		TextView text = (TextView) findViewById(R.id.text);
		text.setTypeface(bit);
    }
	
}
