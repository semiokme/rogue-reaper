package com.dapperkobold.roguereaper;

//generally what .StartUpActivity needs to do
//button to start the next stage
//readout of xp, gold, souls
//readout of class and level
//save and load Player object


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;


public class StartUp extends Activity {

	// define variables
	public static final String prefFileName = "MyReaper";
	SharedPreferences playerData;


	public void onCreate(Bundle savedInstanceState) {
		//loads the layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// load shared prefs
		playerData = getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
	
		// if there isn't a gold pref, make all prefs with defaults
		// Note: I chose to have all prefs as lowercase here. 
		if(!playerData.contains("gold")) {
		            SharedPreferences.Editor editor = playerData.edit();
		            editor.putInt("gold", 0);
		            editor.putInt("xp", 0);
		            editor.putInt("souls", 0);
		            editor.putString("class","Reaper");
		            editor.putInt("level",1);
		            editor.commit();
		}
		
		// Textviews
		TextView t1, t2;
		t1=(TextView)findViewById(R.id.text);
		t2=(TextView)findViewById(R.id.text2);
		
		// this works as-is, but I can refactor to be a lot less ugly and more usable.  
		t1.setText("Gold: " + playerData.getInt("gold", -1));
		t2.setText("Class: " + playerData.getString("class", "Error"));
		
		// Button
		Button b1;
		b1=(Button)findViewById(R.id.button);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(StartUp.this, BreakoutGame.class);
	                StartUp.this.startActivity(intent);
	                //snagged this from S.O.  there is a bit about passing variables in- probably better than re-accessing preferences
	                //https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
	                finish();
			}
		});
	}
	
}

