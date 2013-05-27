package com.example.memorygame;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
//import android.media.AudioManager;
//import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FullscreenActivity extends SwarmActivity {
	//link to this app on github: http://bit.ly/W0MeNP

	//These are the constants
	private final double LEVEL_TIME_DECREASE = .95;
	private final int RED = Color.parseColor("#FF0000");
	private final int WHITE = Color.parseColor("#FFFFFF");
	private final int BLUE = Color.parseColor("#000FFF");
	private final int GREEN = Color.parseColor("#00FF00");
	private final int START_LEVEL_TIME = 7000;
	private final int CORRECT_TIME = 1000;
	private final int START_LIVES = 3;
	
	//Sound Variables
	//private SoundPool wrong = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
	//private int sWrong = sounds.load(context, R.assets.boop);
	
	Button[][] b = new Button[4][4]; //, [row] [column]
	Button liveButton;
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
        
        liveButton = (Button) findViewById(R.id.liveButton);
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
		public void onClick(View v) {
			if(infoMode==1) {
				if(((ColorDrawable)((Button) v).getBackground()).getColor()==WHITE) //check if color is white
					((Button) v).setBackgroundColor(RED); //change color to red
				else if(((ColorDrawable)((Button) v).getBackground()).getColor()==RED)
					((Button) v).setBackgroundColor(BLUE); //change color to blue
				else if(((ColorDrawable)((Button) v).getBackground()).getColor()==BLUE)
					((Button) v).setBackgroundColor(GREEN); //change color to green
				else
					((Button) v).setBackgroundColor(WHITE); //change color to white
			}
			//else if(infoMode==0)
				//beginGame();
		}
	};

	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public boolean checkTile(int row, int column) {
		//checks the tile, sees if pattern is correct. 
		if(((((ColorDrawable)((b[row][column]).getBackground())).getColor() == RED && goal[row][column]==1) || (((ColorDrawable)((b[row][column]).getBackground())).getColor() == WHITE && goal[row][column]==0) || (((ColorDrawable)((b[row][column]).getBackground())).getColor() == BLUE && goal[row][column]==2) || (((ColorDrawable)((b[row][column]).getBackground())).getColor() == GREEN && goal[row][column]==3)))
			return true;
		else
			return false;
	}
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void generateLevel() {
		Typeface bit=Typeface.createFromAsset(getAssets(),"fonts/bus.ttf");
		reset = false;
		infoMode = 0;
		level++;	
		liveButton.setTypeface(bit);
        liveButton.setText(Integer.toString(lives));
		info.setTypeface(bit);
		info.setText("Level: "+level+" - Memorize the pattern!");
		levelTime *= LEVEL_TIME_DECREASE;
		if (level%5 == 0 && level <=15){
			levelTime = START_LEVEL_TIME;
		}

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				double rand = Math.random(); //used to generate next level
				//Determines how hard each level will be
				if(level>=15){
					if (rand>.66){ 
						goal[i][j] = 2;
						b[i][j].setBackgroundColor(BLUE);
					}
					else if(rand>.33) {
						goal[i][j] = 1;
						b[i][j].setBackgroundColor(RED);
					}
					else if(rand>.20) {
						goal[i][j]= 0; //white
						b[i][j].setBackgroundColor(WHITE);
					}
					else {
						goal[i][j] = 0;
						b[i][j].setBackgroundColor(GREEN);
					}
						
				}
				else if(level>=10) {
					if(rand>.66) {
						goal[i][j] = 1; //red
						b[i][j].setBackgroundColor(RED);
					}
					else if(rand>.33) {
						goal[i][j]= 0; //white
						b[i][j].setBackgroundColor(WHITE);
					}
					else {
						goal[i][j] = 2; //blue
						b[i][j].setBackgroundColor(BLUE);
					}
				}
				else if(level>=5) {
					if(rand>.45) {
						goal[i][j] = 1; //red
						b[i][j].setBackgroundColor(RED);
					}
					else if(rand>.05) {
						goal[i][j]= 0; //white
						
						b[i][j].setBackgroundColor(WHITE);
					}
					else {
						goal[i][j] = 2; //blue
						b[i][j].setBackgroundColor(BLUE);
					}
				}
				else {
					if(rand>.5) {
						goal[i][j] = 1; //red
						b[i][j].setBackgroundColor(RED);
					}
					else {
						goal[i][j]= 0; //white
						b[i][j].setBackgroundColor(WHITE);
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
					b[i][j].setBackgroundColor(WHITE); //set all buttons to white
					
				}
			}
	        info.setText("Level: "+level+" - Press for Next Level");
	        reset = true;
		}
	}
	public void show() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if (goal[i][j]==1){
					b[i][j].setBackgroundColor(RED);
				}
				else if (goal[i][j]==2){
					b[i][j].setBackgroundColor(BLUE);
				}
				else if (goal[i][j] == 3){
					b[i][j].setBackgroundColor(GREEN);
					}
				else{
					b[i][j].setBackgroundColor(WHITE);
					}
				}
			}
		info.setText("That was incorrect! This is the correct answer.");
		Handler pause = new Handler();
		Runnable run = new Runnable() {
			public void run() {
				generateLevel();
			}
		};
		pause.postDelayed(run, CORRECT_TIME);
		}
	public void lives() {
		if(lives>0) {
			lives--;
			level--;
			show();
		}
		else
			gameOver();
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
} 