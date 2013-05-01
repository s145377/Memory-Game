package com.example.memorygame;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
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
public class onlynumbers extends SwarmActivity {
	//link to this app on github: http://bit.ly/W0MeNP

	//These are the constants
	public final double LEVEL_TIME_DECREASE = .95;
	public final int RED = Color.RED;
	public final int WHITE = Color.WHITE;
	public final int BLUE = Color.BLUE;
	public final int PURPLE = Color.MAGENTA;
	public final int GREEN = Color.GREEN;
	public final int YELLOW = Color.YELLOW;
	public final int ORANGE = Color.parseColor("#FD8700");
	public final int LIGHT_BLUE = Color.parseColor("#00C3FD");
	public final int PINK = Color.parseColor("#F9ABE5");
	public final int START_LEVEL_TIME = 7000;
	public final int START_LIVES = 3;
	
	Button[][] b = new Button[4][4]; //, [row] [column]
	int goal[][] = new int[4][4];
	Button info;
	int infoMode = 0; //0 = showing pattern, 1 = user tries to replicate pattern, 2 = game over
	int level = 0;
	int levelTime = START_LEVEL_TIME;
	int view = R.layout.pattern;
	int lives = START_LIVES;
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
		levelTime = START_LEVEL_TIME;
		infoMode = 2;
		lives = START_LIVES;
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
		}
	};
	//Start Game Button
	public OnClickListener press = new OnClickListener() {
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public void onClick(View v) {
			if(infoMode==1) {
				if((((Button) v).getText().toString()) == "0") //check if color is white
					setTile(1, ((Button) v)); //change color to red
				else if((((Button) v).getText().toString()) == "1") //check if color is red
					setTile(2, ((Button) v)); //change color to blue
				else
					setTile(0, ((Button) v)); //change color to white
			}
			//else if(infoMode==0)
				//beginGame();
		}
	};

	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public boolean checkTile(int row, int column) {
		//checks the tile, sees if pattern is correct. 
		if(((((b[row][column]).getText().toString())) == "1" && goal[row][column]==1) || ((((b[row][column]).getText().toString())) == "0" && goal[row][column]==0) || ((((b[row][column]).getText().toString())) == "2" && goal[row][column]==2))
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
		Typeface bit=Typeface.createFromAsset(getAssets(),"fonts/bus.ttf");
		reset = false;
		infoMode = 0;
		level++;
		info.setTypeface(bit);
		info.setText("Level: "+level+" - Memorize the pattern!");
		levelTime *= LEVEL_TIME_DECREASE;

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				double rand = Math.random(); //used to generate next level
				//Determines how hard each level will be
				if(level>=20) {
					
				}
				else if(level>=15) {
					
				}
				else if(level>=10) {
					if(rand>.66) {
						goal[i][j] = 1; //red
						setTile(1, b[i][j]);
					}
					else if(rand>.33) {
						goal[i][j]= 0; //white
						setTile(0, b[i][j]);
					}
					else {
						goal[i][j] = 2; //blue
						setTile(2, b[i][j]);
					}
				}
				else if(level>=5) {
					if(rand>.45) {
						goal[i][j] = 1; //red
						setTile(1, b[i][j]);
					}
					else if(rand>.05) {
						goal[i][j]= 0; //white
						setTile(0, b[i][j]);
					}
					else {
						goal[i][j] = 2; //blue
						setTile(2, b[i][j]);
					}
				}
				else {
					if(rand>.5) {
						goal[i][j] = 1; //red
						setTile(1, b[i][j]);
					}
					else {
						goal[i][j]= 0; //white
						setTile(0, b[i][j]);
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
					setTile(0, b[i][j]); //set all buttons to white
				}
			}
	        info.setText("Level: "+level+" - Press for Next Level");
	        reset = true;
		}
	}
	public void lives() {
		if(lives>0) {
	        Toast.makeText(onlynumbers.this, "Ouch! You have "+lives+" lives left!", Toast.LENGTH_SHORT).show();
			lives--;
			level--;
			generateLevel();
		}
		else
			gameOver();
	}
	public void setTile(int c, Button b) {

		b.setTextColor(Color.BLACK);
		if(c==1)
			b.setText("1");
		else if(c==0)
			b.setText("0");
		else if(c==2)
			b.setText("2");
	}
} 