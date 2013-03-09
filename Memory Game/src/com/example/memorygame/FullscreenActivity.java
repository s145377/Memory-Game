package com.example.memorygame;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
   
	boolean color = true; //false = white, true = red
	Button[][] b = new Button[4][4]; // [row] [column]
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
	

	    	
	    
	}
	public OnClickListener press = new OnClickListener() {
		public void onClick(View v) {
			if(color)
				((Button) v).setBackgroundColor(Color.parseColor("#FF0000"));
			else
				((Button) v).setBackgroundColor(Color.parseColor("#FFFFFF"));
			color = !color;
		}
	};
}