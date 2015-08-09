package com.example.zetta;

import java.util.ArrayList;
import com.example.zetta.entity.ZPlayer;

public final class ZProperty 
{
	static public ZPlayer player;
	static public ArrayList<Integer> items, mobs, players, machineries, objects, levels;
	
	static
	{
		player = new ZPlayer();
		items = new ArrayList<Integer>();
		mobs = new ArrayList<Integer>();
		players = new ArrayList<Integer>();
		machineries = new ArrayList<Integer>();
		objects = new ArrayList<Integer>();
		levels = new ArrayList<Integer>();
	}

	static public ZPlayer GetPlayer()
	{
		ZPlayer playerCopy = new ZPlayer(player);
		player.buffs.clear();
		player.score.setMultiplier(ZConstants.SCORE_MULTIPLIER_INITIAL);
		return playerCopy;
	}
	
	static public void SetItems(int[] _items)
	{
		for (int i = 0, n = _items.length; i < n; ++i)
		{
			items.add(Integer.valueOf(_items[i]));
		}
	}
	
	static public void SetMobs(int[] _mobs)
	{
		for (int i = 0, n = _mobs.length; i < n; ++i)
		{
			mobs.add(Integer.valueOf(_mobs[i]));
		}
	}
	
	static public void SetPlayers(int[] _players)
	{
		for (int i = 0, n = _players.length; i < n; ++i)
		{
			players.add(Integer.valueOf(_players[i]));
		}
	}
	
	static public void SetMachineries(int[] _machineries)
	{
		for (int i = 0, n = _machineries.length; i < n; ++i)
		{
			machineries.add(Integer.valueOf(_machineries[i]));
		}
	}
	
	static public void SetObjects(int[] _objects)
	{
		for (int i = 0, n = _objects.length; i < n; ++i)
		{
			objects.add(Integer.valueOf(_objects[i]));
		}
	}
	
	static public void SetLevels(int[] _levels)

	{
		for (int i = 0, n = _levels.length; i < n; ++i)
		{
			levels.add(Integer.valueOf(_levels[i]));
		}
	}

	static public int[] GetItems()
	{
		int n = items.size();
		int[] _items = new int[n];
		for (int i = 0; i < n; ++i)
		{
			_items[i] = items.get(i).intValue();
		}
		return _items;
	}
	
	static public int[] GetMobs()
	{
		int n = mobs.size();
		int[] _mobs = new int[n];
		for (int i = 0; i < n; ++i)
		{
			_mobs[i] = mobs.get(i).intValue();
		}
		return _mobs;
	}
	
	static public int[] GetPlayers()
	{
		int n = players.size();
		int[] _players = new int[n];
		for (int i = 0; i < n; ++i)
		{
			_players[i] = players.get(i).intValue();
		}
		return _players;
	}
	
	static public int[] GetMachineries()
	{
		int n = machineries.size();
		int[] _machineries = new int[n];
		for (int i = 0; i < n; ++i)
		{
			_machineries[i] = machineries.get(i).intValue();
		}
		return _machineries;
	}
	
	static public int[] GetObjects()
	{
		int n = objects.size();
		int[] _objects = new int[n];
		for (int i = 0; i < n; ++i)
		{
			_objects[i] = objects.get(i).intValue();
		}
		return _objects;
	}
	
	static public int[] GetLevels()
	{
		int n = levels.size();
		int[] _levels = new int[n];
		for (int i = 0; i < n; ++i)
		{
			_levels[i] = levels.get(i).intValue();
		}
		return _levels;
	}
}
