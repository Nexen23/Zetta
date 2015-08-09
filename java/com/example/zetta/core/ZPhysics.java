package com.example.zetta.core;

import java.util.ArrayList;

import com.example.zetta.ZPrimitive.Point2f;
import com.example.zetta.core.ZGame.IUpdatable;
import com.example.zetta.entity.ZEffect;
import com.example.zetta.entity.ZItem;
import com.example.zetta.entity.ZLevel;
import com.example.zetta.entity.ZMachinery;
import com.example.zetta.entity.ZMap;
import com.example.zetta.entity.ZMob;
import com.example.zetta.entity.ZObject;
import com.example.zetta.entity.ZPlayer;

public final class ZPhysics 
{
	//TODO
	/*static public class Explosion
	{
		public Explosion()
		{
		explosion.id = 1;
		explosion.textureSize.height = 1f;
		explosion.textureSize.width = 1f / 27f;
		explosion.isPlaying = false;
		explosion.textureCoordinatesInitial = new Point2f[]{new Point2f(0, 0)};
		explosion.setMeshes(ZGame.level.map.graphicsData.image.modelMesh,
				ZGame.level.map.graphicsData.image.textureMesh);
		explosion.state = 0;
		explosion.statesCount = 1;
		explosion.stepsCount = new byte[]{27};
		explosion.step = 0;
		explosion.stepSwitchTime = (int)(525f / 27f);
		explosion.texturePosition.set(0, 0);
		
		
		//TODO delete
		if (isExplosion == true)
		{
			explosion.time += timeElapsed;
			if (explosion.time > explosion.stepSwitchTime)
			{
				explosion.step += explosion.time / explosion.stepSwitchTime;
				explosion.time %= explosion.stepSwitchTime;
				
				if (explosion.step >= explosion.stepsCount[explosion.state])
				{
					isExplosion = false;
					explosion.step = 0;
				}
			}
			explosion.texturePosition.set(explosion.textureCoordinatesInitial[explosion.state]);
			explosion.texturePosition.add(explosion.step * explosion.textureSize.width, 0);
			explosion.recalculateTextureMatrix();
			explosion.prepareMeshes();
		}
		//;
		}
	}*/
	
	static public abstract class PhysicsData implements IUpdatable
	{
		public Point2f position;

		public PhysicsData()
		{
			position = new Point2f();
		}
		
		public PhysicsData(PhysicsData d)
		{
			position = new Point2f(d.position);	
		}
	}
	
	static public boolean wasMobDestroyed, wasItemDestroyed, wasObjectDestroyed,
		wasMachineryDestroyed, wasEffectDestroyed;
	
	static private ZPlayer player;
	static private ZMap map;
	static private ArrayList<ZMob> mobs;
	static private ArrayList<ZEffect> effects;
	static private ArrayList<ZItem> items;
	static private ArrayList<ZMachinery> machineries;
	static private ArrayList<ZObject> objects;
	
	static public void SetScene(ZLevel level)
 	{
 		player = level.player;
 		map = level.map;
 		
 		effects = level.map.effects;
 		mobs = level.map.mobs;
 		items = level.map.items;
 		objects = level.map.objects;
 		machineries = level.map.machineries;
 	}
	
	static public void Update(long timeElapsed)
	{	
		wasObjectDestroyed = wasMobDestroyed = wasMachineryDestroyed = wasItemDestroyed = false;
		int i, n;
		
		for (i = 0, n = map.machineriesCount; i < n; ++i)
		{
			if (machineries.get(i).isDestroyed == false)
			{
				machineries.get(i).physicsData.update(timeElapsed);
			}
		}
				
		player.physicsData.update(timeElapsed);	
		
		for (i = 0, n = map.mobsCount; i < n; ++i)
		{
			if (mobs.get(i).isDestroyed == false)
			{
				mobs.get(i).physicsData.update(timeElapsed);
			}
		}
		
		if (wasItemDestroyed == true)
		{
			i = 0;
			n = map.itemsCount;
			while (i < n)
			{
				if (items.get(i).isDestroyed == false) 
				{
					++i;
				}
				else
				{
					n = --map.itemsCount;
					items.set(i, items.get(n));
					items.set(n, null);
				}
			}
		}
		
		if (wasMachineryDestroyed == true)
		{
			i = 0;
			n = map.machineriesCount;
			while (i < n)
			{
				if (machineries.get(i).isDestroyed == false) 
				{
					++i;
				}
				else
				{
					n = --map.machineriesCount;
					machineries.set(i, machineries.get(n));
					machineries.set(n, null);
				}
			}
		}
		
		if (wasMobDestroyed == true)
		{
			i = 0;
			n = map.mobsCount;
			while (i < n)
			{
				if (mobs.get(i).isDestroyed == false) 
				{
					++i;
				}
				else
				{
					n = --map.mobsCount;
					mobs.set(i, mobs.get(n));
					mobs.set(n, null);
				}
			}
		}
		
		if (wasObjectDestroyed == true)
		{
			i = 0;
			n = map.objectsCount;
			while (i < n)
			{
				if (objects.get(i).isDestroyed == false) 
				{
					++i;
				}
				else
				{
					n = --map.objectsCount;
					objects.set(i, objects.get(n));
					objects.set(n, null);
				}
			}
		}
		
		if (wasEffectDestroyed == true)
		{
			i = 0;
			n = map.effectsCount;
			while (i < n)
			{
				if (effects.get(i).isDestroyed == false) 
				{
					++i;
				}
				else
				{
					n = --map.effectsCount;
					effects.set(i, effects.get(n));
					effects.set(n, null);
				}
			}
		}
	}
}
