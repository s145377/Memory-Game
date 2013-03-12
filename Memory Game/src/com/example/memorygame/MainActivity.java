package com.example.memorygame;
 
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;
public class MainActivity extends SwarmActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Swarm.init(this, 4761, "ffb27e79e8be8fff7593f3f0da8704a6");

    }
 
    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }
 
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {
        //Starts game
        case R.id.start_game:
            Toast.makeText(MainActivity.this, "Game Started", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, FullscreenActivity.class);
            startActivity(intent);
            return true;
        case R.id.end_game:
        	Toast.makeText(MainActivity.this, "`This has no purpose", Toast.LENGTH_LONG).show();
        case R.id.swarm:
        	Swarm.showDashboard();
        	return true;
 
 
        default:
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
 
}