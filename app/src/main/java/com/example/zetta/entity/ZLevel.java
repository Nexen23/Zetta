package com.example.zetta.entity;

import com.example.zetta.ZConstants;

public final class ZLevel
{
	public ZPlayer player;
	public ZMap map;
	public long time;
	public int state;
	
	public ZLevel(int id, ZPlayer player)
	{
		this.player = player;
		time = 0;
		state = ZConstants.GAME_STATE_PLAYING;
		map = new ZMap(id);
	}
}
