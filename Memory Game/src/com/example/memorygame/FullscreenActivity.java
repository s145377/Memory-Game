package com.example.memorygame;

import java.util.zip.Inflater;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;
public class FullscreenActivity extends SwarmActivity {


	//link to this app on github: http://bit.ly/W0MeNP

	Button[][] b = new Button[4][4]; //, [row] [column]
	int goal[][] = new int[4][4];
	Button info;
	int infoMode = 0; //0 = showing pattern, 1 = user tries to replicate pattern, 2 = game over
	int level = 0;
	int levelTime = 7000; //in milliseconds (7 seconds)
	int view = R.layout.pattern;
	int lives = 3;
	boolean reset = false;
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Ensures that there will be no title, fullscreen, and landscape.
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(view);
        info = new Button(this);
        info = (Button) findViewById(R.id.info);
        info.setOnClickListener(infoPress);
        
        
        b[0][0] = (Button) findViewById(R.id.pattern1x4);
        b[1][0] = (Button) findViewById(R.id.pattern2x4);
        b[2][0] = (Button) findViewById(R.id.pattern3x1);
        b[3][0] = (Button) findViewById(R.id.pattern4x4);

        b[0][1] = (Button) findViewById(R.id.pattern1x2);
        b[1][1] = (Button) findViewById(R.id.pattern2x3);
        b[2][1] = (Button) findViewById(R.id.pattern3x2);
        b[3][1] = (Button) findViewById(R.id.pattern4x3);

        b[0][2] = (Button) findViewById(R.id.pattern1x3);
        b[1][2] = (Button) findViewById(R.id.pattern2x2);
        b[2][2] = (Button) findViewById(R.id.pattern3x3);
        b[3][2] = (Button) findViewById(R.id.pattern4x2);

        b[0][3] = (Button) findViewById(R.id.pattern1x1);
        b[1][3] = (Button) findViewById(R.id.pattern2x1);
        b[2][3] = (Button) findViewById(R.id.pattern3x4);
        b[3][3] = (Button) findViewById(R.id.pattern4x1);
        
        for(int i = 0; i < 4; i++) {
        	for(int j = 0; j < 4; j++) {
        		b[i][j].setOnClickListener(press);
        	}
        }
	
		generateLevel();
	    	
	    
	}
	
	public void onPause() {
		super.onPause();
		gameOver();
	}
	
	public void onResume() {
		super.onResume();
	}
	
	public void gameOver() {
		SwarmLeaderboard.submitScoreAndShowLeaderboard(6977, level);
		//Displays Game Over text.
		info.setText("Level: "+level+" - Game Over! Press to play again");
		level = 0;
		levelTime = 7000;
		infoMode = 2;
		lives = 3;
	}
	
	public OnClickListener infoPress = new OnClickListener() {
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public void onClick(View v) {
			if(infoMode==1) {
				if(check())
					generateLevel();
				else
					lives();
			}
			else if(infoMode==2)
				generateLevel();
			else if(infoMode==0)
				beginGame();
		}
	};
	//Start Game Button
	public OnClickListener press = new OnClickListener() {
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public void onClick(View v) {
			if(infoMode==1) {
				if(((ColorDrawable)((Button) v).getBackground()).getColor()==Color.parseColor("#FFFFFF")) //check if color is white
					((Button) v).setBackgroundColor(Color.parseColor("#FF0000")); //change color to red
				else if(((ColorDrawable)((Button) v).getBackground()).getColor()==Color.parseColor("#FF0000"))
					((Button) v).setBackgroundColor(Color.parseColor("#000FFF")); //change color to blue
				else
					((Button) v).setBackgroundColor(Color.parseColor("#FFFFFF")); //change color to white
			}
			else if(infoMode==0)
				beginGame();
		}
	};

	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public boolean checkTile(int row, int column) {

		if(((((ColorDrawable)((b[row][column]).getBackground())).getColor() == Color.parseColor("#FF0000") && goal[row][column]==1) || (((ColorDrawable)((b[row][column]).getBackground())).getColor() == Color.parseColor("#FFFFFF") && goal[row][column]==0) || (((ColorDrawable)((b[row][column]).getBackground())).getColor() == Color.parseColor("#000FFF") && goal[row][column]==2)))
			return true;
		else
			return false;
	}
	
	public boolean check() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(!checkTile(i, j)) {
					return false;
				}
					
			}
		}
		return true;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void generateLevel() {
		reset = false;
		infoMode = 0;
		level++;
		
		info.setTextColor(Color.GREEN);//sets the color of the text to Green.
		info.setText("Level: "+level+" - Memorize the pattern!");
		levelTime *= .95; //decreases time to memorize by 5 percent per level

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				double rand = Math.random(); //used to generate next level
				//Determines how hard each level will be
				if(level>=10) {
					if(rand>.66) {
						goal[i][j] = 1; //red
						b[i][j].setBackgroundColor(Color.parseColor("#FF0000"));
					}
					else if(rand>.33) {
						goal[i][j]= 0; //white
						b[i][j].setBackgroundColor(Color.parseColor("#FFFFFF"));
					}
					else {
						goal[i][j] = 2; //blue
						b[i][j].setBackgroundColor(Color.parseColor("#000FFF"));
					}
				}
				else if(level>=5) {
					if(rand>.45) {
						goal[i][j] = 1; //red
						b[i][j].setBackgroundColor(Color.parseColor("#FF0000"));
					}
					else if(rand>.05) {
						goal[i][j]= 0; //white
						b[i][j].setBackgroundColor(Color.parseColor("#FFFFFF"));
					}
					else {
						goal[i][j] = 2; //blue
						b[i][j].setBackgroundColor(Color.parseColor("#000FFF"));
					}
				}
				else {
					if(rand>.5) {
						goal[i][j] = 1; //red
						b[i][j].setBackgroundColor(Color.parseColor("#FF0000"));
					}
					else {
						goal[i][j]= 0; //white
						b[i][j].setBackgroundColor(Color.parseColor("#FFFFFF"));
					}
				}
			}
		}
		
		Handler pause = new Handler();
		Runnable run = new Runnable() {
			public void run() {
				beginGame();
			}
		};
		pause.postDelayed(run, levelTime);
	}
	public void beginGame() {
		if(!reset) {
			infoMode = 1;
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 4; j++) {
					b[i][j].setBackgroundColor(Color.parseColor("#FFFFFF")); //set all buttons to white
				}
			}
	        info.setText("Level: "+level+" - Repeat the Pattern \n Press to go to next level");
	        reset = true;
		}
	}
	public void lives() {
		if(lives>0) {
	        Toast.makeText(FullscreenActivity.this, "Ouch! You have "+lives+" lives left!", Toast.LENGTH_SHORT).show();
			lives--;
			level--;
			generateLevel();
		}
		else
			gameOver();
	}
} 