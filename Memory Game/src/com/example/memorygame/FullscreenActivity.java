package com.example.memorygame;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.EventLog.Event;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;

public class FullscreenActivity extends Activity {
   
	Button[][] b = new Button[4][4]; //, [row] [column]
	boolean goal[][] = new boolean[4][4];
	boolean paused = false;
	int level = 0;
	int levelTime = 15000; //in milliseconds (15 seconds)
	int view = R.layout.pattern;

	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(view);

        
        b[0][0] = (Button) findViewById(R.id.pattern1x1);
        b[1][0] = (Button) findViewById(R.id.pattern2x1);
        b[2][0] = (Button) findViewById(R.id.pattern3x1);
        b[3][0] = (Button) findViewById(R.id.pattern4x1);

        b[0][1] = (Button) findViewById(R.id.pattern1x2);
        b[1][1] = (Button) findViewById(R.id.pattern2x2);
        b[2][1] = (Button) findViewById(R.id.pattern3x2);
        b[3][1] = (Button) findViewById(R.id.pattern4x2);

        b[0][2] = (Button) findViewById(R.id.pattern1x3);
        b[1][2] = (Button) findViewById(R.id.pattern2x3);
        b[2][2] = (Button) findViewById(R.id.pattern3x3);
        b[3][2] = (Button) findViewById(R.id.pattern4x3);

        b[0][3] = (Button) findViewById(R.id.pattern1x4);
        b[1][3] = (Button) findViewById(R.id.pattern2x4);
        b[2][3] = (Button) findViewById(R.id.pattern3x4);
        b[3][3] = (Button) findViewById(R.id.pattern4x4);
        
        for(int i = 0; i < 4; i++) {
        	for(int j = 0; j < 4; j++) {
        		b[i][j].setOnClickListener(press);
        	}
        }
	
		generateLevel();

	    	
	    
	}
	public void onPause() {
		super.onPause();
		level = 0;
		levelTime = 15000;
	}
	public void onResume() {
		super.onResume();
		generateLevel();
	}
	
	public OnClickListener press = new OnClickListener() {
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public void onClick(View v) {
			if(!paused) {
				if(((ColorDrawable)((Button) v).getBackground()).getColor()==Color.parseColor("#FFFFFF")) //check if color is white
					((Button) v).setBackgroundColor(Color.parseColor("#FF0000")); //change color to red
				else
					((Button) v).setBackgroundColor(Color.parseColor("#FFFFFF")); //change color to white
			
				check();
			}
		}
	};
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void check() {
		int correct = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if((((ColorDrawable)((b[i][j]).getBackground())).getColor() == Color.parseColor("#FF0000") && goal[i][j]) || (((ColorDrawable)((b[i][j]).getBackground())).getColor() == Color.parseColor("#FFFFFF") && !goal[i][j]))
					correct++;
			}
		}
		if(correct==16)
			generateLevel();
	}
	public void generateLevel() {
		paused = true;
		level++;
		levelTime *= .92; //decreases time by 8 percent per level
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				double rand = Math.random(); //used to generate next level
				if(rand>.5) {
					goal[i][j] = true; //red
					b[i][j].setBackgroundColor(Color.parseColor("#FF0000"));
				}
				else {
					goal[i][j]= false; //white
					b[i][j].setBackgroundColor(Color.parseColor("#FF00FF"));
				}
			}
		}
		
		Handler pause = new Handler();
		Runnable run = new Runnable() {
			public void run() {
				paused = false;
				for(int i = 0; i < 4; i++) {
					for(int j = 0; j < 4; j++) {
						b[i][j].setBackgroundColor(Color.parseColor("#FFFFFF")); //set all buttons to white
					}
				}
				
			}
		};
		pause.postDelayed(run, levelTime);
	}
}