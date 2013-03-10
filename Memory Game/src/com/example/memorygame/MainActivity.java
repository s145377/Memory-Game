package com.example.memorygame;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
 
public class MainActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
 
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
 
}