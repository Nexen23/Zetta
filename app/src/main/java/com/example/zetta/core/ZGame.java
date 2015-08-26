package com.example.zetta.core;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.example.zetta.ZConstants;
import com.example.zetta.ZLog;
import com.example.zetta.ZProperty;
import com.example.zetta.entity.ZLevel;

public final class ZGame 
{	
	static public abstract interface IUpdatable
	{
		public abstract void update(long timeElapsed);
	}
	
	static public abstract class Identity
	{
		public int id = ZConstants.IDENTITY_ID_INVALID;
		public boolean isDestroyed = false;
		
		public Identity()
		{
			
		}
		
		public Identity(int _id)
		{
			id = _id;
		}
		
		public Identity(Identity e)
		{
			id = e.id;
			isDestroyed = e.isDestroyed;
		}
		
		public abstract void onDestroy();
		public final void destroy()
		{
			isDestroyed = true;
			onDestroy();
		}
	}
	
	private class UpdateRunnable implements Runnable
	{
		public void run()
    	{
    		String info = "";
    		ZLog.d(ZLog.TAG_THREAD_GAME, "game thread - started");
    		
    		int x = 0; //TODO: delete
    		level.player.physicsData.setCellsOccupied(); // TODO delete it?
    		level.map.sequencerData.music.start();
    		long timePrevious = System.currentTimeMillis(), timeCurrent, timeElapsed = 0; //timeSleeping; TODO
    		try
    		{
	    		while(Thread.currentThread().isInterrupted() == false)
	    		{
	    			
	    			++x;//TODO:: delete it
	    			if (x == 33)
	    			{
	    				x = 0;
	    				info = "\n";
	    				info += "Total memory:\t" + (float)Runtime.getRuntime().totalMemory() / (float)(1024 * 1024) + "mb\n";
	    				info += "Free memory:\t" + (float)Runtime.getRuntime().freeMemory() / (float)(1024 * 1024) + "mb\n";
	    				info += "Max memory:\t" + (float)Runtime.getRuntime().maxMemory() / (float)(1024 * 1024) + "mb\n";
	    				ZLog.d(ZLog.TAG_MEMORY_USAGE, info);
	    			}
	    			
	    			timeCurrent = System.currentTimeMillis();
	    			timeElapsed += timeCurrent - timePrevious;
	    			level.time += timeElapsed;
	    			info = "elapsed time = " + timeElapsed;
	    			ZLog.d(ZLog.TAG_THREAD_GAME, info);
	    			
	    			ZPhysics.Update(timeElapsed);
	    			ZGraphics.Update(timeElapsed);
	    			ZSequencer.Update(timeElapsed);
	    			ZGraphics.RequestRender();
	    			
	    			if (level.state != ZConstants.GAME_STATE_PLAYING)
	    			{
	    				info = "level state - " + 
	    					((level.state == ZConstants.GAME_STATE_COMPLETED) ? "completed" : "failed");
	    				ZLog.d(ZLog.TAG_THREAD_GAME, info);
		    			Thread.currentThread().interrupt();
	    			}

	    			Thread.sleep(ZConstants.GAME_TIME_SLEEPING);
	    			timeElapsed = System.currentTimeMillis() - timeCurrent; //TODO FAIL
	    			//timeElapsed = 40;
	    			
					updateLock.acquire();
					updateLock.release();
					timePrevious = System.currentTimeMillis();
	    		}
    		}
    		catch(InterruptedException e)
    		{
    			ZLog.d(ZLog.SECURITY_LEVEL_WARNING, ZLog.TAG_THREAD_GAME, "game thread - stopped");
    			Thread.currentThread().interrupt();
    		}
    		level.map.sequencerData.music.stop();
    	}
	}
	
	static public ZLevel level;
	static public Activity activity;
	static public Context context;
	static public Resources resources;
	public Thread updateThread;
	public Semaphore updateLock;
	
	public ZGame(Activity _activity)
	{		
		activity = _activity;	
		context = activity.getApplicationContext();
		resources = context.getResources();
		updateLock = new Semaphore(1, true);
		
		try 
		{
			updateLock.acquire();
		} 
		catch (InterruptedException e) 
		{

		}
		updateThread = new Thread(new UpdateRunnable());
	}
	
	public void onCreate() 
	{
		short levelId = 1; // TODO: edit it 
		ZFileSystem.Adjust();
		ZGraphics.Adjust();
		
		ZFileSystem.LoadProperty();
		ZFileSystem.LoadStatistics();
		
		level = new ZLevel(levelId, ZProperty.GetPlayer());
		try
		{
			ZFileSystem.Load(level.map);
		}
		catch (IOException e)
		{
			e.printStackTrace(); //TODO
		}
		
		ZGraphics.SetScene(level);
		ZPhysics.SetScene(level);
	}


	public void onDestroy() 
	{
		updateThread = null;
		updateLock = null;
	}
	
	public void onStart() 
	{
		updateThread.start(); //TODO: 
	}

	public void onStop() 
	{
		updateThread.interrupt();
		updateLock.release();
	}
	
	public void onRestart() 
	{
		updateThread = new Thread(new UpdateRunnable());
	}
	
	public void onResume() 
	{
		//TODO music wtf?
		level.map.sequencerData.music.resume();
		updateLock.release();
		ZGraphics.OnResume();
	}

	public void onPause() 
	{
		try 
		{
			//TODO music wtf?
			level.map.sequencerData.music.pause();
			updateLock.acquire();
			ZGraphics.OnPause();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
}
