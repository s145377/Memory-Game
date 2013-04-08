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
        String programmer1 = "";
        String programmer2 = "";
        int p = (int) (Math.random()*100);
        //Randomizes who is `credited first as programmers.
        if(p>50) {
        	programmer1 = "Jacob Noel";
        	programmer2 = "Alden Hallak";
        }
        else if(p<50) {
        	programmer1 = "Alden Hallak";
        	programmer2 = "Jacob Noel";
        }
        else {
        	programmer1 = "a monkey";
        	programmer2 = "another monkey";
        }
        developedby.setText("App envisioned and developed by "+programmer1+" and "+programmer2);
		Typeface bit=Typeface.createFromAsset(getAssets(),"fonts/bus.ttf");
		developedby.setTypeface(bit);
		TextView about = (TextView) findViewById(R.id.textbody);
		about.setTypeface(bit);
		TextView text = (TextView) findViewById(R.id.text);
		text.setTypeface(bit);
    }
	
}
