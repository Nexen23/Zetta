package com.example.zetta.core;

import java.util.ArrayList;

import android.media.MediaPlayer;
import com.example.zetta.ZConstants;
import com.example.zetta.ZLog;

public final class ZSequencer 
{
	static public abstract interface IPlayable
	{
		public void start();
		public void stop();
		
		public void resume();
		public void pause();
	}
	
	static public abstract class SequencerData
	{
		public SequencerData()
		{
			
		}
		
		public SequencerData(SequencerData d)
		{
			
		}
	}
	
	static abstract class Audio implements IPlayable
	{
		public MediaPlayer mediaPlayer;
		public float volume = 0f;
		public int resourceId;
		
		public Audio()
		{
			
		}
		
		public Audio(int id)
		{
			resourceId = id;
			mediaPlayer = MediaPlayer.create(ZGame.context, id);
		}
		
		public Audio(Audio d)
		{
			resourceId = d.resourceId;
			mediaPlayer = MediaPlayer.create(ZGame.context, d.resourceId);
			volume = d.volume;
		}
		
		public void set(int id, int _volume)
		{
			resourceId = id;
			mediaPlayer = MediaPlayer.create(ZGame.context, id);
			volume = GetVolumeFloat(_volume);
		}
		
		public void setVolume(int _volume)
		{
			volume = GetVolumeFloat(_volume);
		}
		
		public boolean isPlaying()
		{
			return mediaPlayer.isPlaying();
		}
		
		@Override
		public void stop()
		{
			mediaPlayer.stop();
		}

		@Override
		public void resume() 
		{
			mediaPlayer.start();	
		}

		@Override
		public void pause() 
		{
			mediaPlayer.pause();
		}
	}
	
	static public final class Sound extends Audio
	{
		public Sound()
		{
			super();
		}
		
		public Sound(int id)
		{
			super(id);
		}
		
		public Sound(Sound s)
		{
			super(s);
		}
		
		@Override
		public void start() 
		{
			mediaPlayer.setVolume(volume, volume);
			mediaPlayer.start();
		}
	}

	static public final class Music extends Audio
	{
		public Music()
		{
			super();
		}
		
		public Music(int id)
		{
			super(id);
		}
		
		public Music(Music m)
		{
			super(m);
		}
		
		@Override
		public void start() 
		{
			mediaPlayer.setLooping(true);
			mediaPlayer.setVolume(volume, volume);
			mediaPlayer.start();
		}
	}
	
	static private ArrayList<Sound> postSounds;
	static private int postSoundsCount;
	
	static 
	{
		postSounds = new ArrayList<Sound>();
	}
	
	static public void AddPostSound(Sound a)
	{
		ZLog.d(ZLog.TAG_ACTIVITY);
		if (postSounds.size() > postSoundsCount)
		{
			postSounds.set(postSoundsCount, a);
			++postSoundsCount;
		}
		else
		{
			postSounds.add(a);
			postSoundsCount = postSounds.size();
		}
	}
	
	static public void DeletePostSingleAnimation(int index)
	{
		--postSoundsCount;
		postSounds.set(index, postSounds.get(postSoundsCount));
		postSounds.set(postSoundsCount, null);
	}
	
	static public void Update(long timeElapsed)
	{
		int i = 0;
		while (i < postSoundsCount)
		{
			if (postSounds.get(i).isPlaying() == true)
			{
				++i;
			}
			else
			{
				DeletePostSingleAnimation(i);
			}
		}
	}
	
	static private float GetVolumeFloat(int volume)
	{
		return (float) (1 - (Math.log(ZConstants.MIXER_VOLUME_MAX - volume) /
				Math.log(ZConstants.MIXER_VOLUME_MAX)));
	}
}
