package com.example.zetta.core;

import com.example.zetta.ZConstants;
import com.example.zetta.ZGenerator;
import com.example.zetta.ZLog;
import com.example.zetta.ZPrimitive;
import com.example.zetta.ZPrimitive.Point2f;
import com.example.zetta.core.ZGame.IUpdatable;
import com.example.zetta.entity.ZMap;
import com.example.zetta.entity.ZMob;
import com.example.zetta.entity.ZPlayer;

public class ZAI 
{
	static public abstract class AIData implements IUpdatable
	{
		public Point2f positionNext;
		public long timeWaiting;
		
		public AIData()
		{			
			positionNext = new Point2f();
			timeWaiting = GetTimeWaiting(); 
		}
		
		public AIData(AIData d)
		{			
			positionNext = new Point2f(d.positionNext);
			timeWaiting = d.timeWaiting; 
		}
	}
	
	static public void ComputeNextPosition(ZMob mob)
	{
		ZPrimitive.Point2f wayPosition = new ZPrimitive.Point2f(); //TODO mm, objects
		float distanceMin = -1f, distance;
		boolean distanceMinWasFound = false;
		ZMap map = ZGame.level.map;
		ZPlayer player = ZGame.level.player;
		int[] directions = new int[4]; //TODO mm, objects
		int directionsCount = 0, directionIndex = 0, wayDistanceMax, wayDistance;
		
		if (mob.aiData.isPursuing == true)
		{			
			mob.aiData.timeWaiting = 0;
			for (int i = 0, n = ZConstants.DIRECTION_UNIT_VECTORS.length; i < n; ++i)
			{
				wayPosition.set(mob.physicsData.position);
				wayPosition.add(ZConstants.DIRECTION_UNIT_VECTORS[i]);
				if (wayPosition.x >= 0 && wayPosition.y >= 0 && wayPosition.x < map.size.width && 
						wayPosition.y < map.size.height &&
						map.cells[(int)(wayPosition.y)][(int)(wayPosition.x)].object == null)
				{
					distance = wayPosition.distance(player.physicsData.position);
					if (distanceMinWasFound == false)
					{
						distanceMinWasFound = true;
						distanceMin = distance;
						directionIndex = i;
					}
					else
						if (distance < distanceMin)
						{
							distanceMin = distance;
							directionIndex = i;
						}
				}
			}
			
			if (distanceMinWasFound == false)
			{
				ZLog.d(ZLog.TAG_AI, "no way to pursue the player - mob(" + 
						mob.physicsData.position.x + "; " + mob.physicsData.position.y + ")");
				return; //TODO how can I get here?
			}
			else
			{
				wayPosition.set(mob.physicsData.position);
				wayPosition.add(ZConstants.DIRECTION_UNIT_VECTORS[directionIndex]);
				mob.aiData.positionNext.set(wayPosition);
			}
			
			mob.physicsData.direction = directionIndex;
			mob.physicsData.isMoving = true;
		}
		else
		{
			for (int i = 0, n = ZConstants.DIRECTION_UNIT_VECTORS.length; i < n; ++i)
			{
				wayPosition.set(mob.physicsData.position);
				wayPosition.add(ZConstants.DIRECTION_UNIT_VECTORS[i]);
				if (wayPosition.x >= 0 && wayPosition.y >= 0 && wayPosition.x < map.size.width && 
						wayPosition.y < map.size.height &&
						map.cells[(int)(wayPosition.y)][(int)(wayPosition.x)].object == null)
				{
					directions[directionsCount] = (int)i;
					++directionsCount;
				}
			}
			directionIndex = ZGenerator.randomizer.nextInt(directionsCount);
			wayPosition.set(mob.physicsData.position);
			wayPosition.add(ZConstants.DIRECTION_UNIT_VECTORS[directions[directionIndex]]);
			wayDistanceMax = 1;
			while (wayPosition.x >= 0 && wayPosition.y >= 0 &&
					wayPosition.x < map.size.width && wayPosition.y < map.size.height &&
					wayDistanceMax <= mob.physicsData.detectionDistance && 
					map.cells[(int)(wayPosition.y)][(int)(wayPosition.x)].object == null)
			{
				++wayDistanceMax;
				wayPosition.add(ZConstants.DIRECTION_UNIT_VECTORS[directions[directionIndex]]);
			}
			
			wayDistance = ZGenerator.randomizer.nextInt(wayDistanceMax);
			if (wayDistance == 0 && ZGenerator.randomizer.nextBoolean() == true)
			{
				wayDistance = 1;
			}
			if (wayDistance != 0)
			{
				wayPosition.set(ZConstants.DIRECTION_UNIT_VECTORS[directions[directionIndex]]);
				wayPosition.multiply((float)wayDistance);
				wayPosition.add(mob.physicsData.position);
				mob.aiData.positionNext.set(wayPosition);
				mob.physicsData.direction = directions[directionIndex];
				mob.physicsData.isMoving = true;
			}
			else
			{
				mob.aiData.timeWaiting = GetTimeWaiting();
			}
		}
	}
	
	static public void ComputeNearestPosition(ZMob mob)
	{
		switch (mob.physicsData.direction)
		{
		case ZConstants.DIRECTION_UP:
			mob.aiData.positionNext.set(
					(int)mob.physicsData.position.x,
					(float)Math.ceil((double)mob.physicsData.position.y));
			break;
			
		case ZConstants.DIRECTION_RIGHT:
			mob.aiData.positionNext.set(
					(float)Math.ceil((double)mob.physicsData.position.x),
					(int)mob.physicsData.position.y);
			break;
			
		case ZConstants.DIRECTION_DOWN:
			mob.aiData.positionNext.set(
					(int)mob.physicsData.position.x,
					(int)mob.physicsData.position.y);
			break;
			
		case ZConstants.DIRECTION_LEFT:
			mob.aiData.positionNext.set(
					(int)mob.physicsData.position.x,
					(int)mob.physicsData.position.y);
			break;
		}
	}
	
	static public long GetTimeWaiting()
	{
		return (long)(ZConstants.MOB_TIME_WAITING_AVERAGE  - ZConstants.MOB_TIME_WAITING_DELTA + 
				ZGenerator.randomizer.nextInt((int)ZConstants.MOB_TIME_WAITING_DELTA << 1));
	}
}
