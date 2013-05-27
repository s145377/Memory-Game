package com.example.memorygame;
 
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;
public class MainActivity extends SwarmActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Button start,about;
		//Button startnumbcolor,startnumb;
        super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        Swarm.init(this, 4761, "ffb27e79e8be8fff7593f3f0da8704a6");
        start = new Button(this);
        start = (Button) findViewById(R.id.start_game);
        about = new Button(this);
        about = (Button) findViewById(R.id.about);
		//startnumbcolor = new Button(this);
		//start = (Button) findViewById(R.id.start_game_numbers_colors);
		//startnumb = new Button(this);
		//startnumb = (Button) findViewById(R.id.start_game_numbers);		
		Typeface bit=Typeface.createFromAsset(getAssets(),"fonts/bus.ttf");
		about.setTypeface(bit);
		start.setTypeface(bit);
		//startnumbcolor.setTypeface(bit);
		//startnumb.setTypeface(bit);
    } 
    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }
 
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	int id = item.getItemId();
    	if(id == R.id.start_game) {
    		 Toast.makeText(MainActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(MainActivity.this, FullscreenActivity.class);
             startActivity(intent);
             return true;
    	}
    	else if(id == R.id.swarm) {
        	Swarm.showDashboard();
        	return true;
    	}
    	else {
            return super.onOptionsItemSelected(item);
    	}
    }   
    //The buttons on the home screen
    //Game Start
    public void start (View view){
        Toast.makeText(MainActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, FullscreenActivity.class);
        startActivity(intent);
    }
    //About Screen
    public void about (View view){
    	Intent intent = new Intent(MainActivity.this, about.class);
    	startActivity(intent);
    	
    }
    public void startnumbcolor (View view){
    	Intent intent = new Intent(MainActivity.this, FullScreenActivityWithNumbers.class);
    	startActivity(intent);
    }
    public void startnumb (View view){
    	Intent intent = new Intent(MainActivity.this, onlynumbers.class);
    	startActivity(intent);
    }
 
}