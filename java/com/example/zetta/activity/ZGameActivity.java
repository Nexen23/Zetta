package com.example.zetta.activity;

import com.example.zetta.R;
import com.example.zetta.ZLog;
import com.example.zetta.ZSettings;
import com.example.zetta.core.ZGame;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.WindowManager;

public class ZGameActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		ZLog.d(ZLog.TAG_ACTIVITY);
		if (ZSettings.SCREEN_FULLSCREEN == false)
		{
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}		
		game = new ZGame(this);
		game.onCreate();
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		ZLog.d(ZLog.TAG_ACTIVITY);
		game.onDestroy();
		game = null;
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		ZLog.d(ZLog.TAG_ACTIVITY);
		game.onPause();
	}

	@Override
	protected void onRestart() 
	{
		super.onRestart();
		ZLog.d(ZLog.TAG_ACTIVITY);
		game.onRestart();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		ZLog.d(ZLog.TAG_ACTIVITY);
		game.onResume();
	}

	@Override
	protected void onStart() 
	{
		super.onStart();
		ZLog.d(ZLog.TAG_ACTIVITY);
		game.onStart();
	}

	@Override
	protected void onStop() 
	{
		super.onStop();
		ZLog.d(ZLog.TAG_ACTIVITY);
		game.onStop();
	}
	
	ZGame game;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		ZLog.d(ZLog.TAG_ACTIVITY);
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		ZLog.d(ZLog.TAG_ACTIVITY);
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) 
	{
		super.onConfigurationChanged(newConfig);
		ZLog.d(ZLog.TAG_ACTIVITY);
    }

	//TODO: delete all of these (bottom)
	@Override
	public void finish() 
	{
		ZLog.d(ZLog.TAG_ACTIVITY);
		super.finish();
	}

	@Override
	public void finishActivity(int requestCode) 
	{
		ZLog.d(ZLog.TAG_ACTIVITY);
		super.finishActivity(requestCode);
	}

	@Override
	public void finishActivityFromChild(Activity child, int requestCode) 
	{
		ZLog.d(ZLog.TAG_ACTIVITY);
		super.finishActivityFromChild(child, requestCode);
	}

	@Override
	public void finishFromChild(Activity child) 
	{
		ZLog.d(ZLog.TAG_ACTIVITY);
		super.finishFromChild(child);
	}
}