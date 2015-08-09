package com.example.zetta.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.example.zetta.ZConstants;
import com.example.zetta.ZLog;
import com.example.zetta.ZPrimitive;
import com.example.zetta.ZPrimitive.Point2f;
import com.example.zetta.ZProperty;
import com.example.zetta.ZSettings;
import com.example.zetta.core.ZGraphics.SingleAnimation;
import com.example.zetta.core.ZGraphics.SpriteResource;
import com.example.zetta.entity.ZCell;
import com.example.zetta.entity.ZItem;
import com.example.zetta.entity.ZMachinery;
import com.example.zetta.entity.ZMap;
import com.example.zetta.entity.ZMob;
import com.example.zetta.entity.ZObject;
import com.example.zetta.entity.ZPlayer;

public final class ZFileSystem 
{
	//TODO WTF CLASS?!?!
	static public class File
	{
		private ArrayList<String> text;
		private int lineOffset;
		
		public File(BufferedReader br)
		{
			text = new ArrayList<String>();
			try
			{
				String line;
				while ((line = br.readLine()) != null)
				{
					text.add(line);
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			lineOffset = 0;
		}
		
		public void reset()
		{
			lineOffset = 0;
		}
		
		public void readLine()
		{
			++lineOffset;
		}
		
		public int getInt()
		{
			return Integer.parseInt(text.get(lineOffset++));
		}
		
		public long getLong()
		{
			return Long.parseLong(text.get(lineOffset++));
		}
		
		public float getFloat()
		{
			return Float.parseFloat(text.get(lineOffset++));
		}
		
		public boolean getBool()
		{
			return (Integer.parseInt(text.get(lineOffset++)) == 1) ? true : false;
		}
	}
	//TODO rewrite
	//static public File entities, graphics, properties;
	static public BufferedReader bmobs, bmachineries, bitems, bobjects, bmaps, beffects, bplayers;
	static public SpriteResource[] spriteResources;
	static public int[] audioResources;
	
	//TODO really delete this!
	static public File mobs, machineries, items, objects, maps, effects, players;
	//TODO failed
	
	//TODO: delete it
	static private final float[] meshPolygons = new float[]
	                  		{
				-1.0f, 	-1.0f, 
	            1.0f, 	-1.0f, 
	            -1.0f,  1.0f, 
	            1.0f,  	1.0f 
		};
	static private final float[] meshTexture = new float[]
	                       {                 
            0.0f, 1.0f, 
		      1.0f, 1.0f, 
		      0.0f, 0.0f, 
		      1.0f, 0.0f     
        };
	//TODO: delete it
	
	static void Adjust()
	{
		spriteResources = new SpriteResource[]
	  		{
				//0
				new SpriteResource(com.example.zetta.R.drawable.map),
				new SpriteResource(com.example.zetta.R.drawable.grid),
	  			new SpriteResource(com.example.zetta.R.drawable.highlight),
	  			new SpriteResource(com.example.zetta.R.drawable.player),
	  			new SpriteResource(com.example.zetta.R.drawable.mobs),
	  			
	  			//5
	  			new SpriteResource(com.example.zetta.R.drawable.mobsflying),
	  			new SpriteResource(com.example.zetta.R.drawable.coin),
	  			new SpriteResource(com.example.zetta.R.drawable.bomb),
	  			new SpriteResource(com.example.zetta.R.drawable.machineries),
	  			new SpriteResource(com.example.zetta.R.drawable.object),
	  			
	  			//10
				new SpriteResource(com.example.zetta.R.drawable.explosion)
	  		};
		
		audioResources = new int[]
		    {
				//0
				com.example.zetta.R.raw.music,
				com.example.zetta.R.raw.coin,
				com.example.zetta.R.raw.explosion
		    };
		
		try
		{
			bplayers = new BufferedReader(new InputStreamReader(
					ZGame.resources.openRawResource(com.example.zetta.R.raw.players)));
			bplayers.mark(ZConstants.FILESYSTEM_MARK_MAX);
			players = new File(bplayers);
			bplayers.close();
			
			bmobs = new BufferedReader(new InputStreamReader(
					ZGame.resources.openRawResource(com.example.zetta.R.raw.mobs)));
			bmobs.mark(ZConstants.FILESYSTEM_MARK_MAX);
			mobs = new File(bmobs);
			bmobs.close();
			
			bmachineries = new BufferedReader(new InputStreamReader(
					ZGame.resources.openRawResource(com.example.zetta.R.raw.machineries)));
			bmachineries.mark(ZConstants.FILESYSTEM_MARK_MAX);
			machineries = new File(bmachineries);
			bmachineries.close();
			
			bitems = new BufferedReader(new InputStreamReader(
					ZGame.resources.openRawResource(com.example.zetta.R.raw.items)));
			bitems.mark(ZConstants.FILESYSTEM_MARK_MAX);
			items = new File(bitems);
			bitems.close();
			
			bobjects = new BufferedReader(new InputStreamReader(
					ZGame.resources.openRawResource(com.example.zetta.R.raw.objects)));
			bobjects.mark(ZConstants.FILESYSTEM_MARK_MAX);
			objects = new File(bobjects);
			bobjects.close();
			
			bmaps = new BufferedReader(new InputStreamReader(
					ZGame.resources.openRawResource(com.example.zetta.R.raw.maps)));
			bmaps.mark(ZConstants.FILESYSTEM_MARK_MAX);
			maps = new File(bmaps);
			bmaps.close();
			
			beffects = new BufferedReader(new InputStreamReader(
					ZGame.resources.openRawResource(com.example.zetta.R.raw.effects)));
			beffects.mark(ZConstants.FILESYSTEM_MARK_MAX);
			effects = new File(beffects);
			beffects.close();
		}
		catch(IOException e)
		{
			e.printStackTrace(); //TODO
		}
	}
	
	static SpriteResource GetSpriteResource(int index)
	{
		if (index >= spriteResources.length)
		{
			ZLog.d(ZLog.SECURITY_LEVEL_ERROR, ZLog.TAG_ACTIVITY);
		}
		spriteResources[index].isUsed = true;
		return spriteResources[index];
	}
	
	static int GetAudioResource(int index)
	{
		return audioResources[index];
	}
	
	static public void LoadStatistics()
	{
		//TODO
		
	}
	
	static public void LoadSettings()
	{
		//TODO
		
	}
	
	static public void LoadProperty()
	{
		//TODO
		try 
		{
			Load(ZProperty.player);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//TODO
	//static public void SaveProperty(ZProperty property); //rewrite and close
	//static public void UpdateProperty(ZProperty property); //rewrite
	
	static public void Load(ZMap map) throws IOException
	{
		//TODO: delete??
		/*map.size.height = 8;
		map.size.width = 7;
		map.buff = ZConstants.MAP_NO_BUFFS;
		map.coinsCount = 2;
		map.score = 30;
		map.itemsCount = 5;
		map.objectsCount = 2;
		map.machineriesCount = 3;
		map.mobsCount = 7;
		
		int i, j;
		float[] meshPolygons, meshTexture;
		meshPolygons = new float[]
 		{
 				-1.0f, 	-1.0f, 
 	            1.0f, 	-1.0f, 
 	            -1.0f,  1.0f, 
 	            1.0f,  	1.0f 
 		};
 		meshTexture = new float[]
 		                       {                 
 	            0.0f, 1.0f, 
 			      1.0f, 1.0f, 
 			      0.0f, 0.0f, 
 			      1.0f, 0.0f     
 	        };
 		map.items = new ArrayList<ZItem>(map.itemsCount);
 		map.items.add(new ZItem()); 
 		item.buff = null; 
 		map.items.get(0).id = 1; 
 		map.items.get(0).score = 3; 
 		map.items.get(0).physicsData.position.set(4, 4);
	
		i = 0;
		map.items.get(0).graphicsData.image.textureSize.width = 1;
		map.items.get(0).graphicsData.image.textureSize.height = 1;
		map.items.get(0).graphicsData.image.texturePosition.set(0, 0);
		map.items.get(0).graphicsData.image.setMeshes(meshPolygons, meshTexture);
		map.items.get(0).graphicsData.postAnimation = null;

		map.items.add(new ZItem(map.items.get(0))); map.items.get(1).id = 0; map.items.get(1).physicsData.position.set(3, 0);
		map.items.add(new ZItem(map.items.get(0))); map.items.get(2).physicsData.position.set(2, 2);
		map.items.add(new ZItem(map.items.get(0))); map.items.get(3).physicsData.position.set(2, 4);
		map.items.add(new ZItem(map.items.get(0))); map.items.get(4).physicsData.position.set(5, 2);
		for (i = 0; i < map.itemsCount; ++i)
		{
			if (i % 2 == 0 && i != 4)
			{
				map.items.get(i).id = ZConstants.ITEM_ID_COIN;
				map.items.get(i).graphicsData.image.resource = GetSpriteResource(6);
				map.items.get(i).sequencerData.sound.set(com.example.zetta.R.raw.coin, ZConstants.sv);
			}
			else
			{
				map.items.get(i).id = ZConstants.ITEM_ID_BOMB1;
				map.items.get(i).graphicsData.image.resource = GetSpriteResource(7);
				map.items.get(i).graphicsData.image.textureSize.width = 1f;
				
				map.items.get(i).graphicsData.image.textureSize.height = 1;
				map.items.get(i).graphicsData.image.texturePosition.set(0, 0);
				map.items.get(i).sequencerData.sound.set(com.example.zetta.R.raw.explosion, ZConstants.sv);
				
				map.items.get(i).graphicsData.postAnimation = new SingleAnimation();
				map.items.get(i).graphicsData.postAnimation.setMeshes(meshPolygons, meshTexture);
				map.items.get(i).graphicsData.postAnimation.isPlaying = false;
				map.items.get(i).graphicsData.postAnimation.step = 0;
				map.items.get(i).graphicsData.postAnimation.textureSize.height = 1f;
				map.items.get(i).graphicsData.postAnimation.textureSize.width = 1f / 27f;
				map.items.get(i).graphicsData.postAnimation.texturePosition = new Point2f(0f, 0f);
				map.items.get(i).graphicsData.postAnimation.stepsCount = 27;
				map.items.get(i).graphicsData.postAnimation.timeStepSwitching = (long)(525f / 27f);
				map.items.get(i).graphicsData.postAnimation.resource = GetSpriteResource(10);
			}
		}
		
		map.objects = new ArrayList<ZObject>(map.objectsCount);	
		map.objects.add(new ZObject()); map.objects.get(0).id = 1; map.objects.get(0).physicsData.damage = 0;
		map.objects.get(0).physicsData.direction = 0; map.objects.get(0).physicsData.position.set(3, 3);
		
		i = 0;
		map.objects.get(i).graphicsData.image.textureSize.width = 1;
		map.objects.get(i).graphicsData.image.textureSize.height = 1;
		map.objects.get(i).graphicsData.image.resource = GetSpriteResource(9);
		map.objects.get(i).graphicsData.image.texturePosition.set(0, 0);
		map.objects.get(i).graphicsData.image.setMeshes(meshPolygons, meshTexture);
		
		map.objects.add(new ZObject(map.objects.get(0))); map.objects.get(1).physicsData.position.set(2, 3);
		
		map.machineries = new ArrayList<ZMachinery>(map.machineriesCount);
		for (i = 0; i < map.machineriesCount; ++i)
		{
			map.machineries.add(new ZMachinery());
			map.machineries.get(i).id = (short)(i + 1);
			map.machineries.get(i).score = (short)(i + 1);
			map.machineries.get(i).physicsData.step = 0;
			map.machineries.get(i).physicsData.stepsCount = 4;
			map.machineries.get(i).physicsData.time = 0;
			map.machineries.get(i).physicsData.damage = 1;
			map.machineries.get(i).graphicsData.animation.resource = GetSpriteResource(8);
			map.machineries.get(i).graphicsData.animation.textureSize.height = 1f / 4f;
			map.machineries.get(i).graphicsData.animation.textureSize.width = 1f / 4f;
			map.machineries.get(i).graphicsData.animation.setMeshes(meshPolygons, meshTexture);
			map.machineries.get(i).graphicsData.animation.modelSizeHalf.set(ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2, 
					ZSettings.MAP_CELL_SIZE_RELATIVE.height);
			
			map.machineries.get(i).graphicsData.animation.isPlaying = true;
			map.machineries.get(i).graphicsData.animation.step = 0;
			map.machineries.get(i).graphicsData.animation.stepsCount = 4;
			map.machineries.get(i).graphicsData.animation.time = 0;
			map.machineries.get(i).graphicsData.animation.texturePosition.set(0, (float)(int)i / 4f);
		}
		map.machineries.get(0).physicsData.position.set(5, 5);
		map.machineries.get(0).physicsData.timesStepSwitching = new long[]{350, 350, 350, 350};
		map.machineries.get(0).graphicsData.animation.timesStepSwitching = new long[]{350, 350, 350, 350};
		map.machineries.get(1).physicsData.position.set(0, 5);
		map.machineries.get(1).physicsData.timesStepSwitching = new long[]{480, 480, 480, 480};
		map.machineries.get(1).graphicsData.animation.timesStepSwitching = new long[]{480, 480, 480, 480};
		map.machineries.get(2).physicsData.position.set(3, 6);
		map.machineries.get(2).physicsData.timesStepSwitching = new long[]{2450, 2450, 2450, 2450};
		map.machineries.get(2).graphicsData.animation.timesStepSwitching = new long[]{2450, 2450, 2450, 2450};
		
		map.machineries.get(1).graphicsData.animation.texturePosition.set(0, (float)(int)3f / 4f);
		
		map.mobs = new ArrayList<ZMob>(map.mobsCount);
		map.mobs.add(new ZMob());
		i = 0;
		map.mobs.get(i).id = 1;
		map.mobs.get(i).score = 4;
		map.mobs.get(i).physicsData.damage = 1;
		map.mobs.get(i).physicsData.detectionDistance = 3;
		map.mobs.get(i).physicsData.direction = ZConstants.DIRECTION_DOWN;
		map.mobs.get(i).physicsData.health = 1;
		map.mobs.get(i).physicsData.isFlying = false;
		map.mobs.get(i).physicsData.isImmortal = false;
		map.mobs.get(i).physicsData.isHostile = false; /////
		map.mobs.get(i).physicsData.isMoving = false;
		map.mobs.get(i).aiData.isPursuing = false;
		map.mobs.get(i).physicsData.isSpectral = false;
		map.mobs.get(i).physicsData.speed = 0.0013f;
		map.mobs.get(i).physicsData.setPosition(5, 1);
		map.mobs.get(i).graphicsData.animationOnMoving.resource = GetSpriteResource(4);
		map.mobs.get(i).graphicsData.animationOnMoving.isPlaying = false;
		map.mobs.get(i).graphicsData.animationOnMoving.state = 0;
		map.mobs.get(i).graphicsData.animationOnMoving.statesCount = 4;
		map.mobs.get(i).graphicsData.animationOnMoving.step = 0;
		map.mobs.get(i).graphicsData.animationOnMoving.stepsCount = new int[] {3, 3, 3, 3};
		map.mobs.get(i).graphicsData.animationOnMoving.stepSwitchTime = 85;
		map.mobs.get(i).graphicsData.animationOnMoving.time = 0;
		map.mobs.get(i).graphicsData.animationOnMoving.textureSize.width = 1f / 6f;
		map.mobs.get(i).graphicsData.animationOnMoving.textureSize.height = 1f / 8f;
		
		map.mobs.get(i).graphicsData.animationOnMoving.texturePosition.set(0, 0);
		map.mobs.get(i).graphicsData.animationOnMoving.texturePositionsInitial = new ZPrimitive.Point2f[4];
		map.mobs.get(i).graphicsData.animationOnMoving.texturePositionsInitial[0] = new ZPrimitive.Point2f(0, 1f / 8f);
		map.mobs.get(i).graphicsData.animationOnMoving.texturePositionsInitial[1] = new ZPrimitive.Point2f(0, 3f / 8f);
		map.mobs.get(i).graphicsData.animationOnMoving.texturePositionsInitial[2] = new ZPrimitive.Point2f(0, 2f / 8f);
		map.mobs.get(i).graphicsData.animationOnMoving.texturePositionsInitial[3] = new ZPrimitive.Point2f(0, 0);
		map.mobs.get(i).graphicsData.animationOnMoving.setMeshes(meshPolygons, meshTexture);
		map.mobs.add(new ZMob(map.mobs.get(0))); map.mobs.get(1).physicsData.setPosition(3, 2);
		map.mobs.get(1).graphicsData.animationOnMoving.texturePosition.set(3f / 6f, 0);
		map.mobs.get(1).graphicsData.animationOnMoving.texturePositionsInitial = new ZPrimitive.Point2f[4];
		map.mobs.get(1).graphicsData.animationOnMoving.texturePositionsInitial[0] = new ZPrimitive.Point2f(3f / 6f, 1f / 8f);
		map.mobs.get(1).graphicsData.animationOnMoving.texturePositionsInitial[1] = new ZPrimitive.Point2f(3f / 6f, 3f / 8f);
		map.mobs.get(1).graphicsData.animationOnMoving.texturePositionsInitial[2] = new ZPrimitive.Point2f(3f / 6f, 2f / 8f);
		map.mobs.get(1).graphicsData.animationOnMoving.texturePositionsInitial[3] = new ZPrimitive.Point2f(3f / 6f, 0);
		map.mobs.add(new ZMob(map.mobs.get(0))); map.mobs.get(2).physicsData.setPosition(0, 3);
		map.mobs.get(2).graphicsData.animationOnMoving.texturePosition.set(0, 4f / 8f);
		map.mobs.get(2).graphicsData.animationOnMoving.texturePositionsInitial = new ZPrimitive.Point2f[4];
		map.mobs.get(2).graphicsData.animationOnMoving.texturePositionsInitial[0] = new ZPrimitive.Point2f(0, 5f / 8f);
		map.mobs.get(2).graphicsData.animationOnMoving.texturePositionsInitial[1] = new ZPrimitive.Point2f(0, 7f / 8f);
		map.mobs.get(2).graphicsData.animationOnMoving.texturePositionsInitial[2] = new ZPrimitive.Point2f(0, 6f / 8f);
		map.mobs.get(2).graphicsData.animationOnMoving.texturePositionsInitial[3] = new ZPrimitive.Point2f(0, 4f / 8f);
		map.mobs.add(new ZMob(map.mobs.get(0))); map.mobs.get(3).physicsData.setPosition(1, 6);
		map.mobs.get(3).graphicsData.animationOnMoving.texturePosition.set(3f / 6f, 4f / 8f);
		map.mobs.get(3).graphicsData.animationOnMoving.texturePositionsInitial = new ZPrimitive.Point2f[4];
		map.mobs.get(3).graphicsData.animationOnMoving.texturePositionsInitial[0] = new ZPrimitive.Point2f(3f / 6f, 5f / 8f);
		map.mobs.get(3).graphicsData.animationOnMoving.texturePositionsInitial[1] = new ZPrimitive.Point2f(3f / 6f, 7f / 8f);
		map.mobs.get(3).graphicsData.animationOnMoving.texturePositionsInitial[2] = new ZPrimitive.Point2f(3f / 6f, 6f / 8f);
		map.mobs.get(3).graphicsData.animationOnMoving.texturePositionsInitial[3] = new ZPrimitive.Point2f(3f / 6f, 4f / 8f);
		
		map.mobs.add(new ZMob(map.mobs.get(0))); map.mobs.get(4).physicsData.setPosition(0, 0);
		map.mobs.get(4).graphicsData.animationOnMoving.textureSize.width = 1f / 9f;
		map.mobs.get(4).graphicsData.animationOnMoving.textureSize.height = 1f / 4f;
		map.mobs.get(4).graphicsData.animationOnMoving.texturePosition.set(0, 0);
		map.mobs.get(4).graphicsData.animationOnMoving.texturePositionsInitial = new ZPrimitive.Point2f[4];
		map.mobs.get(4).graphicsData.animationOnMoving.texturePositionsInitial[0] = new ZPrimitive.Point2f(0, 1f / 4f);
		map.mobs.get(4).graphicsData.animationOnMoving.texturePositionsInitial[1] = new ZPrimitive.Point2f(0, 3f / 4f);
		map.mobs.get(4).graphicsData.animationOnMoving.texturePositionsInitial[2] = new ZPrimitive.Point2f(0, 2f / 4f);
		map.mobs.get(4).graphicsData.animationOnMoving.texturePositionsInitial[3] = new ZPrimitive.Point2f(0, 0f / 4f);
		map.mobs.get(4).graphicsData.animationOnMoving.resource = GetSpriteResource(5);
		map.mobs.add(new ZMob(map.mobs.get(4))); map.mobs.get(5).physicsData.setPosition(4, 7);
		map.mobs.get(5).graphicsData.animationOnMoving.texturePosition.set(3f / 9f, 0);
		map.mobs.get(5).graphicsData.animationOnMoving.texturePositionsInitial = new ZPrimitive.Point2f[4];
		map.mobs.get(5).graphicsData.animationOnMoving.texturePositionsInitial[0] = new ZPrimitive.Point2f(3f / 9f, 1f / 4f);
		map.mobs.get(5).graphicsData.animationOnMoving.texturePositionsInitial[1] = new ZPrimitive.Point2f(3f / 9f, 3f / 4f);
		map.mobs.get(5).graphicsData.animationOnMoving.texturePositionsInitial[2] = new ZPrimitive.Point2f(3f / 9f, 2f / 4f);
		map.mobs.get(5).graphicsData.animationOnMoving.texturePositionsInitial[3] = new ZPrimitive.Point2f(3f / 9f, 0f / 4f);
		map.mobs.add(new ZMob(map.mobs.get(4))); map.mobs.get(6).physicsData.setPosition(6, 4);
		map.mobs.get(6).graphicsData.animationOnMoving.texturePosition.set(6f / 9f, 0);
		map.mobs.get(6).graphicsData.animationOnMoving.texturePositionsInitial = new ZPrimitive.Point2f[4];
		map.mobs.get(6).graphicsData.animationOnMoving.texturePositionsInitial[0] = new ZPrimitive.Point2f(6f / 9f, 1f / 4f);
		map.mobs.get(6).graphicsData.animationOnMoving.texturePositionsInitial[1] = new ZPrimitive.Point2f(6f / 9f, 3f / 4f);
		map.mobs.get(6).graphicsData.animationOnMoving.texturePositionsInitial[2] = new ZPrimitive.Point2f(6f / 9f, 2f / 4f);
		map.mobs.get(6).graphicsData.animationOnMoving.texturePositionsInitial[3] = new ZPrimitive.Point2f(6f / 9f, 0f / 4f);
		
		
		map.cells = new ZCell[map.size.height][map.size.width];
		for (i = 0; i < map.size.height; ++i)
		{
			for (j = 0; j < map.size.width; ++ j)
			{
				map.cells[i][j] = new ZCell();
			}
		}

		for (i = 0; i < map.objectsCount; ++i)
		{
			map.cells[(int)map.objects.get(i).physicsData.position.y]
			          [(int)map.objects.get(i).physicsData.position.x].object = map.objects.get(i);
		}
		for (i = 0; i < map.effectsCount; ++i)
		{
			map.cells[(int)map.effects.get(i).physicsData.position.y]
			          [(int)map.effects.get(i).physicsData.position.x].effect = map.effects.get(i);
		}
		for (i = 0; i < map.machineriesCount; ++i)
		{
			map.cells[(int)map.machineries.get(i).physicsData.position.y]
			          [(int)map.machineries.get(i).physicsData.position.x].machinery = map.machineries.get(i);
		}
		for (i = 0; i < map.itemsCount; ++i)
		{
			map.cells[(int)map.items.get(i).physicsData.position.y]
			          [(int)map.items.get(i).physicsData.position.x].item = map.items.get(i);
		}
		
		////
		////
		
		map.graphicsData.tile.textureSize.width = (float)map.size.width;
		map.graphicsData.tile.textureSize.height = (float)map.size.height;
		map.graphicsData.tile.resource = GetSpriteResource(0);
		map.graphicsData.tile.texturePosition.set(0, 0);
		
		map.graphicsData.tile.setMeshes(meshPolygons, meshTexture);

		ZGame.level.map.graphicsData.tile.modelSizeHalf.set(
				(float)(int)ZGame.level.map.size.width * ZSettings.MAP_CELL_SIZE_RELATIVE.width / (float)2,
				(float)(int)ZGame.level.map.size.height * ZSettings.MAP_CELL_SIZE_RELATIVE.height / (float)2);
		map.graphicsData.tile.modelSizeHalf.set((float)map.size.width * ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2,
											(float)map.size.height * ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
		map.sequencerData.music.set(com.example.zetta.R.raw.music, ZConstants.mv);
		
		//TODO: delete it
		map.graphicsData.grid.resource = GetSpriteResource(1);
		map.graphicsData.grid.setMeshes(ZGame.level.map.graphicsData.tile.modelMesh,
				ZGame.level.map.graphicsData.tile.textureMesh);
		map.graphicsData.grid.textureSize.width = (float)(int)ZGame.level.map.size.width;
		map.graphicsData.grid.textureSize.height = (float)(int)ZGame.level.map.size.height;
		map.graphicsData.grid.texturePosition.set(0, 0);
		map.graphicsData.grid.modelPosition.set(0f, 0f);
		
		//TODO: delete it
		map.graphicsData.grid.modelSizeHalf.set(ZGame.level.map.graphicsData.tile.modelSizeHalf.width,
				ZGame.level.map.graphicsData.tile.modelSizeHalf.height);
		
		//TODO: delete it
		map.graphicsData.playerHighlight.resource = GetSpriteResource(2);
		map.graphicsData.playerHighlight.setMeshes(meshPolygons, meshTexture);

		map.graphicsData.playerHighlight.textureSize.width = 1f;
		map.graphicsData.playerHighlight.textureSize.height = 1f;
		map.graphicsData.playerHighlight.modelSizeHalf.set(
				ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2,
				ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
		
		map.graphicsData.playerHighlight.texturePosition.set(0, 0);
		*/
		
		
		int i, j;
		for (int k = 0; k < map.id; ++k)
		{
			maps.readLine(); // info
			
			map.buff = GetInt(maps);
			map.score = GetInt(maps);
			map.size.width = GetInt(maps);
			map.size.height = GetInt(maps);
			
	 		////////////////////
			maps.readLine(); // info
			map.itemsCount = GetInt(maps);
			map.coinsCount = GetInt(maps);
	 		map.items = new ArrayList<ZItem>(map.itemsCount);
	 		for (i = 0; i < map.itemsCount; ++i)
	 		{
	 			map.items.add(new ZItem(GetInt(maps))); 
	 			map.items.get(i).physicsData.position.set(GetFloat(maps), GetFloat(maps));
	 			Load(map.items.get(i));
	 		}
			//
			
			///////////////
			maps.readLine(); // info
			map.objectsCount = GetInt(maps);
			map.objects = new ArrayList<ZObject>(map.objectsCount);
			for (i = 0; i < map.objectsCount; ++i)
	 		{
	 			map.objects.add(new ZObject(GetInt(maps))); 
	 			map.objects.get(i).physicsData.position.set(GetFloat(maps), GetFloat(maps));
	 			Load(map.objects.get(i));
	 		}
			//
			
			//////////////////////
			maps.readLine(); // info
			map.machineriesCount = GetInt(maps);
			map.machineries = new ArrayList<ZMachinery>(map.machineriesCount);
			for (i = 0; i < map.machineriesCount; ++i)
	 		{
	 			map.machineries.add(new ZMachinery(GetInt(maps))); 
	 			map.machineries.get(i).physicsData.position.set(GetFloat(maps), GetFloat(maps));
	 			Load(map.machineries.get(i));
	 		}
			//
			
			////////////////////////////
			maps.readLine(); // info
			map.mobsCount = GetInt(maps);
			map.mobs = new ArrayList<ZMob>(map.mobsCount);
			for (i = 0; i < map.mobsCount; ++i)
	 		{
	 			map.mobs.add(new ZMob(GetInt(maps))); 
	 			map.mobs.get(i).physicsData.position.set(GetFloat(maps), GetFloat(maps));
	 			Load(map.mobs.get(i));
	 		}
			//
			maps.readLine(); // info
			
			map.cells = new ZCell[map.size.height][map.size.width];
			for (i = 0; i < map.size.height; ++i)
			{
				for (j = 0; j < map.size.width; ++ j)
				{
					map.cells[i][j] = new ZCell();
				}
			}

			for (i = 0; i < map.objectsCount; ++i)
			{
				map.cells[(int)map.objects.get(i).physicsData.position.y]
				          [(int)map.objects.get(i).physicsData.position.x].object = map.objects.get(i);
			}
			for (i = 0; i < map.effectsCount; ++i)
			{
				map.cells[(int)map.effects.get(i).physicsData.position.y]
				          [(int)map.effects.get(i).physicsData.position.x].effect = map.effects.get(i);
			}
			for (i = 0; i < map.machineriesCount; ++i)
			{
				map.cells[(int)map.machineries.get(i).physicsData.position.y]
				          [(int)map.machineries.get(i).physicsData.position.x].machinery = map.machineries.get(i);
			}
			for (i = 0; i < map.itemsCount; ++i)
			{
				map.cells[(int)map.items.get(i).physicsData.position.y]
				          [(int)map.items.get(i).physicsData.position.x].item = map.items.get(i);
			}
			
			map.sequencerData.music.set(GetAudioResource(GetInt(maps)), ZConstants.mv);
			map.graphicsData.tile.resource = GetSpriteResource(GetInt(maps));
			map.graphicsData.grid.resource = GetSpriteResource(GetInt(maps));
			map.graphicsData.playerHighlight.resource = GetSpriteResource(GetInt(maps));
			
			map.graphicsData.tile.textureSize.width = (float)map.size.width;
			map.graphicsData.tile.textureSize.height = (float)map.size.height;
			map.graphicsData.tile.texturePosition.set(0, 0);
			map.graphicsData.tile.modelSizeHalf.set(
					(float)(int)ZGame.level.map.size.width * ZSettings.MAP_CELL_SIZE_RELATIVE.width / (float)2,
					(float)(int)ZGame.level.map.size.height * ZSettings.MAP_CELL_SIZE_RELATIVE.height / (float)2);
			map.graphicsData.tile.modelSizeHalf.set((float)map.size.width * ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2,
												(float)map.size.height * ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
			map.graphicsData.tile.setMeshes(meshPolygons, meshTexture);
			
			map.graphicsData.grid.textureSize.width = (float)(int)ZGame.level.map.size.width;
			map.graphicsData.grid.textureSize.height = (float)(int)ZGame.level.map.size.height;
			map.graphicsData.grid.texturePosition.set(0, 0);
			map.graphicsData.grid.modelPosition.set(0f, 0f);
			map.graphicsData.grid.modelSizeHalf.set(map.graphicsData.tile.modelSizeHalf.width,
					ZGame.level.map.graphicsData.tile.modelSizeHalf.height);
			map.graphicsData.grid.setMeshes(meshPolygons, meshTexture);

			map.graphicsData.playerHighlight.textureSize.width = 1f;
			map.graphicsData.playerHighlight.textureSize.height = 1f;
			map.graphicsData.playerHighlight.modelSizeHalf.set(ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2,
					ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
			map.graphicsData.playerHighlight.texturePosition.set(0, 0);
			map.graphicsData.playerHighlight.setMeshes(meshPolygons, meshTexture);
		}
		
		maps.reset();
		
		
		
		/*map.buff = ZConstants.MAP_NO_BUFFS;
		map.score = 30;
		
 		////////////////////
		map.itemsCount = 5;
		map.coinsCount = 2;
 		map.items = new ArrayList<ZItem>(map.itemsCount);

 		map.items.add(new ZItem(1)); 
 		map.items.add(new ZItem(2)); 
 		map.items.add(new ZItem(1)); 
 		map.items.add(new ZItem(2)); 
 		map.items.add(new ZItem(2)); 
 		
 		for (i = 0; i < map.itemsCount; ++i)
 		{
 			Load(map.items.get(i));
 		}
 		
 		map.items.get(0).physicsData.position.set(4, 4);
 		map.items.get(1).physicsData.position.set(3, 0);
		map.items.get(2).physicsData.position.set(2, 2);
		map.items.get(3).physicsData.position.set(2, 4);
		map.items.get(4).physicsData.position.set(5, 2);
		//
		
		///////////////
		map.objectsCount = 2;
		map.objects = new ArrayList<ZObject>(map.objectsCount);	
		map.objects.add(new ZObject(1));
		map.objects.add(new ZObject(1));
		
		for (i = 0; i < map.objectsCount; ++i)
 		{
 			Load(map.objects.get(i));
 		}
		
		map.objects.get(0).physicsData.position.set(3, 3);
		map.objects.get(1).physicsData.position.set(2, 3);
		//
		
		//////////////////////
		map.machineriesCount = 3;
		map.machineries = new ArrayList<ZMachinery>(map.machineriesCount);
		map.machineries.add(new ZMachinery(1));
		map.machineries.add(new ZMachinery(2));
		map.machineries.add(new ZMachinery(3));
		
		for (i = 0; i < map.machineriesCount; ++i)
 		{
 			Load(map.machineries.get(i));
 		}
		
		map.machineries.get(0).physicsData.position.set(5, 5);
		map.machineries.get(1).physicsData.position.set(0, 5);
		map.machineries.get(2).physicsData.position.set(3, 6);
		//
		
		////////////////////////////
		map.mobsCount = 7;
		map.mobs = new ArrayList<ZMob>(map.mobsCount);
		map.mobs.add(new ZMob(1));
		map.mobs.add(new ZMob(2));
		map.mobs.add(new ZMob(3));
		map.mobs.add(new ZMob(4));
		map.mobs.add(new ZMob(5));
		map.mobs.add(new ZMob(6));
		map.mobs.add(new ZMob(7));

		for (i = 0; i < map.mobsCount; ++i)
 		{
 			Load(map.mobs.get(i));
 		}
		map.mobs.get(0).physicsData.setPosition(5, 1);
		map.mobs.get(1).physicsData.setPosition(3, 2);
		map.mobs.get(2).physicsData.setPosition(0, 3);
		map.mobs.get(3).physicsData.setPosition(1, 6);
		map.mobs.get(4).physicsData.setPosition(0, 0);
		map.mobs.get(5).physicsData.setPosition(4, 7);
		map.mobs.get(6).physicsData.setPosition(6, 4);
		//
		
		map.size.height = 8;
		map.size.width = 7;
		map.cells = new ZCell[map.size.height][map.size.width];
		for (i = 0; i < map.size.height; ++i)
		{
			for (j = 0; j < map.size.width; ++ j)
			{
				map.cells[i][j] = new ZCell();
			}
		}

		for (i = 0; i < map.objectsCount; ++i)
		{
			map.cells[(int)map.objects.get(i).physicsData.position.y]
			          [(int)map.objects.get(i).physicsData.position.x].object = map.objects.get(i);
		}
		for (i = 0; i < map.effectsCount; ++i)
		{
			map.cells[(int)map.effects.get(i).physicsData.position.y]
			          [(int)map.effects.get(i).physicsData.position.x].effect = map.effects.get(i);
		}
		for (i = 0; i < map.machineriesCount; ++i)
		{
			map.cells[(int)map.machineries.get(i).physicsData.position.y]
			          [(int)map.machineries.get(i).physicsData.position.x].machinery = map.machineries.get(i);
		}
		for (i = 0; i < map.itemsCount; ++i)
		{
			map.cells[(int)map.items.get(i).physicsData.position.y]
			          [(int)map.items.get(i).physicsData.position.x].item = map.items.get(i);
		}
		
		////
		////
		
		map.graphicsData.tile.textureSize.width = (float)map.size.width;
		map.graphicsData.tile.textureSize.height = (float)map.size.height;
		map.graphicsData.tile.resource = GetSpriteResource(0);
		map.graphicsData.tile.texturePosition.set(0, 0);
		
		map.graphicsData.tile.setMeshes(meshPolygons, meshTexture);

		ZGame.level.map.graphicsData.tile.modelSizeHalf.set(
				(float)(int)ZGame.level.map.size.width * ZSettings.MAP_CELL_SIZE_RELATIVE.width / (float)2,
				(float)(int)ZGame.level.map.size.height * ZSettings.MAP_CELL_SIZE_RELATIVE.height / (float)2);
		map.graphicsData.tile.modelSizeHalf.set((float)map.size.width * ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2,
											(float)map.size.height * ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
		map.sequencerData.music.set(com.example.zetta.R.raw.music, ZConstants.mv);
		
		//TODO: delete it
		map.graphicsData.grid.resource = GetSpriteResource(1);
		map.graphicsData.grid.setMeshes(ZGame.level.map.graphicsData.tile.modelMesh,
				ZGame.level.map.graphicsData.tile.textureMesh);
		map.graphicsData.grid.textureSize.width = (float)(int)ZGame.level.map.size.width;
		map.graphicsData.grid.textureSize.height = (float)(int)ZGame.level.map.size.height;
		map.graphicsData.grid.texturePosition.set(0, 0);
		map.graphicsData.grid.modelPosition.set(0f, 0f);
		
		//TODO: delete it
		map.graphicsData.grid.modelSizeHalf.set(ZGame.level.map.graphicsData.tile.modelSizeHalf.width,
				ZGame.level.map.graphicsData.tile.modelSizeHalf.height);
		
		//TODO: delete it
		map.graphicsData.playerHighlight.resource = GetSpriteResource(2);
		map.graphicsData.playerHighlight.setMeshes(meshPolygons, meshTexture);

		map.graphicsData.playerHighlight.textureSize.width = 1f;
		map.graphicsData.playerHighlight.textureSize.height = 1f;
		map.graphicsData.playerHighlight.modelSizeHalf.set(
				ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2,
				ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
		
		map.graphicsData.playerHighlight.texturePosition.set(0, 0);*/
	}
	
	static public void Load(ZPlayer player) throws IOException
	{
	/////TODO
		/*player.id = 1;
		player.score.setMultiplier(1);
		player.score.amount = 0;
		player.physicsData.health = 1;
		player.physicsData.hasInertial = false;
		player.physicsData.isMoving = false;
		player.physicsData.speed = 0.003f;
		player.physicsData.setPosition(1, 1);
		player.physicsData.direction = ZConstants.DIRECTION_DOWN;
		float[] meshPolygons = new float[] 
		{
				-1.0f, 	-1.0f, 
	            1.0f, 	-1.0f, 
	            -1.0f,  1.0f, 
	            1.0f,  	1.0f 
		};
		float[] meshTexture = new float[] 
		                                     {                 
 	            0.0f, 1.0f, 
 			      1.0f, 1.0f, 
 			      0.0f, 0.0f, 
 			      1.0f, 0.0f     
 	        };
		player.graphicsData.animationOnMoving.setMeshes(meshPolygons, meshTexture);
		
		player.graphicsData.animationOnMoving.textureSize.width = 1f / 3f;
		player.graphicsData.animationOnMoving.textureSize.height = 1f / 4f;
		player.graphicsData.animationOnMoving.resource = GetSpriteResource(3);
		player.graphicsData.animationOnMoving.isPlaying = false;
		player.graphicsData.animationOnMoving.state = 0;
		player.graphicsData.animationOnMoving.statesCount = 4;
		player.graphicsData.animationOnMoving.step = 0;
		player.graphicsData.animationOnMoving.stepsCount = new int[] {3, 3, 3, 3};
		player.graphicsData.animationOnMoving.stepSwitchTime = 65;
		player.graphicsData.animationOnMoving.time = 0;
		player.graphicsData.animationOnMoving.texturePosition.set(0, 0);
		player.graphicsData.animationOnMoving.texturePositionsInitial = new ZPrimitive.Point2f[4];
		player.graphicsData.animationOnMoving.texturePositionsInitial[0] = new ZPrimitive.Point2f(0, 0.25f);
		player.graphicsData.animationOnMoving.texturePositionsInitial[1] = new ZPrimitive.Point2f(0, 0.75f);
		player.graphicsData.animationOnMoving.texturePositionsInitial[2] = new ZPrimitive.Point2f(0, 0.50f);
		player.graphicsData.animationOnMoving.texturePositionsInitial[3] = new ZPrimitive.Point2f(0, 0);
		*/
		/////
		
		players.readLine(); // info
		
		player.id = 1;
		player.score.setMultiplier(1);
		player.score.amount = 0;
		
		player.physicsData.health = GetInt(players);
		
		player.physicsData.hasInertial = GetBool(players);
		
		player.physicsData.isMoving = GetBool(players);
		
		player.physicsData.speed = GetFloat(players);
		player.physicsData.sizeHalf.set(GetFloat(players) / 2, GetFloat(players) / 2);
		
		player.physicsData.position.x = GetFloat(players);
		player.physicsData.position.y = GetFloat(players);
		player.physicsData.setCellsOccupied();
		
		player.physicsData.direction = GetInt(players);
		ZTouchScreen.SetDirectionAsync(player.physicsData.direction);

		player.graphicsData.animationOnMoving.setMeshes(meshPolygons, meshTexture);
		
		player.graphicsData.animationOnMoving.textureSize.width = 1f / GetFloat(players);
		player.graphicsData.animationOnMoving.textureSize.height = 1f / GetFloat(players);
		
		player.graphicsData.animationOnMoving.resource = GetSpriteResource(GetInt(players));
		player.graphicsData.animationOnMoving.isPlaying = GetBool(players);
		player.graphicsData.animationOnMoving.state = GetInt(players);
		player.graphicsData.animationOnMoving.statesCount = GetInt(players);
		player.graphicsData.animationOnMoving.step = GetInt(players);
		player.graphicsData.animationOnMoving.stepsCount = new int[player.graphicsData.animationOnMoving.statesCount];
		for (int i = 0, n = player.graphicsData.animationOnMoving.statesCount; i < n; ++i)
		{
			player.graphicsData.animationOnMoving.stepsCount[i] = GetInt(players);
		}
		player.graphicsData.animationOnMoving.stepSwitchTime = GetInt(players);
		player.graphicsData.animationOnMoving.time = 0;
		player.graphicsData.animationOnMoving.texturePosition.set(GetFloat(players), GetFloat(players));
		player.graphicsData.animationOnMoving.texturePositionsInitial = new ZPrimitive.Point2f[
		        player.graphicsData.animationOnMoving.statesCount];
		for (int i = 0, n = player.graphicsData.animationOnMoving.statesCount; i < n; ++i)
		{
			player.graphicsData.animationOnMoving.texturePositionsInitial[i] = new ZPrimitive.Point2f(
					GetFloat(players),
					GetFloat(players));
		}
		
		players.reset();
	}
	
	/*static public void Load(ZBuff buff) throws IOException
	{
		for (int i = 0; i < buff.id; ++i)
		{
			buffs.readLine(); // info
			
			
		}
		
		buffs.reset();
	}*/
	//TODO no?
	
	static public void Load(ZItem item) throws IOException
	{	
		for (int i = 0; i < item.id; ++i)
		{
			items.readLine(); // info
			
			item.buff = null; 
			item.score = GetInt(items); 
			item.sequencerData.postSound.set(GetAudioResource(GetInt(items)), ZConstants.sv);
		
			item.graphicsData.image.textureSize.width = 1f / GetFloat(items);
			item.graphicsData.image.textureSize.height = 1f / GetFloat(items);
			item.graphicsData.image.texturePosition.set(GetFloat(items), GetFloat(items));
			item.graphicsData.image.resource = GetSpriteResource(GetInt(items));
			item.graphicsData.image.setMeshes(meshPolygons, meshTexture);
			
			item.graphicsData.postAnimation = (GetBool(items) == true) ? new SingleAnimation() : null;
			if (item.graphicsData.postAnimation != null)
			{			
				item.graphicsData.postAnimation.isPlaying = GetBool(items);;
				item.graphicsData.postAnimation.step = GetInt(items);
				item.graphicsData.postAnimation.textureSize.height = 1f / GetFloat(items);
				item.graphicsData.postAnimation.textureSize.width = 1f / GetFloat(items);
				item.graphicsData.postAnimation.texturePosition = new Point2f(GetFloat(items), GetFloat(items));
				item.graphicsData.postAnimation.stepsCount = GetInt(items);
				item.graphicsData.postAnimation.timeStepSwitching = (long)(GetLong(items) / 
						(float)item.graphicsData.postAnimation.stepsCount);
				item.graphicsData.postAnimation.resource = GetSpriteResource(GetInt(items));
				item.graphicsData.postAnimation.setMeshes(meshPolygons, meshTexture);
			}
		}
		
		items.reset();
	}
	
	static public void Load(ZMachinery machinery) throws IOException
	{
		for (int i = 0; i < machinery.id; ++i)
		{
			machineries.readLine(); // info
			
			machinery.score = GetInt(machineries);
			machinery.physicsData.step = GetInt(machineries);
			machinery.physicsData.stepsCount = GetInt(machineries);
			machinery.physicsData.time = 0;
			machinery.physicsData.damage = GetInt(machineries);
			machinery.physicsData.timesStepSwitching = new long[machinery.physicsData.stepsCount];
			for (int j = 0; j < machinery.physicsData.stepsCount; ++j)
			{
				machinery.physicsData.timesStepSwitching[j] = GetLong(machineries);
			}
			
			machinery.graphicsData.animation.resource = GetSpriteResource(GetInt(machineries));
			machinery.graphicsData.animation.textureSize.height = 1f / GetFloat(machineries);
			machinery.graphicsData.animation.textureSize.width = 1f / GetFloat(machineries);		
			machinery.graphicsData.animation.isPlaying = GetBool(machineries);
			machinery.graphicsData.animation.step = GetInt(machineries);
			machinery.graphicsData.animation.stepsCount = GetInt(machineries);
			machinery.graphicsData.animation.time = GetInt(machineries);
			machinery.graphicsData.animation.texturePosition.set(GetFloat(machineries), GetFloat(machineries));
			machinery.graphicsData.animation.timesStepSwitching = new long[machinery.graphicsData.animation.stepsCount];
			for (int j = 0; j < machinery.graphicsData.animation.stepsCount; ++j)
			{
				machinery.graphicsData.animation.timesStepSwitching[j] = GetLong(machineries);
			}
			machinery.graphicsData.animation.setMeshes(meshPolygons, meshTexture);
		}
		
		machineries.reset();
	}
	
	static public void Load(ZMob mob) throws IOException
	{
		for (int i = 0; i < mob.id; ++i)
		{
			mobs.readLine(); // info
			
			mob.score = GetInt(mobs);
			mob.physicsData.damage = GetInt(mobs);
			mob.physicsData.detectionDistance = GetInt(mobs);
			mob.physicsData.direction = GetInt(mobs);
			mob.physicsData.health = GetInt(mobs);
			mob.physicsData.isFlying = GetBool(mobs);
			mob.physicsData.isImmortal = GetBool(mobs);
			mob.physicsData.isMoving = GetBool(mobs);
			mob.physicsData.isSpectral = GetBool(mobs);
			mob.physicsData.isHostile = GetBool(mobs);
			mob.physicsData.speed = GetFloat(mobs);
			mob.physicsData.sizeHalf.set(GetFloat(mobs) / 2, GetFloat(mobs) / 2);
			mob.graphicsData.animationOnMoving.resource = GetSpriteResource(GetInt(mobs));
			mob.graphicsData.animationOnMoving.isPlaying = GetBool(mobs);
			mob.graphicsData.animationOnMoving.state = GetInt(mobs);
			mob.graphicsData.animationOnMoving.statesCount = GetInt(mobs);
			mob.graphicsData.animationOnMoving.step = GetInt(mobs);
			mob.graphicsData.animationOnMoving.stepsCount = 
				new int[mob.graphicsData.animationOnMoving.statesCount];
			for (int j = 0; j < mob.graphicsData.animationOnMoving.statesCount; ++j)
			{
				mob.graphicsData.animationOnMoving.stepsCount[j] = GetInt(mobs);
			}
			mob.graphicsData.animationOnMoving.stepSwitchTime = GetInt(mobs);
			mob.graphicsData.animationOnMoving.time = 0;
			mob.graphicsData.animationOnMoving.textureSize.width = 1f / GetFloat(mobs);
			mob.graphicsData.animationOnMoving.textureSize.height = 1f / GetFloat(mobs);
			
			mob.graphicsData.animationOnMoving.texturePosition.set(GetFloat(mobs), GetFloat(mobs));
			mob.graphicsData.animationOnMoving.texturePositionsInitial = 
				new ZPrimitive.Point2f[mob.graphicsData.animationOnMoving.statesCount];
			for (int j = 0; j < mob.graphicsData.animationOnMoving.statesCount; ++j)
			{
				mob.graphicsData.animationOnMoving.texturePositionsInitial[j] = new ZPrimitive.Point2f(GetFloat(mobs),
					GetFloat(mobs));
			}
			mob.graphicsData.animationOnMoving.setMeshes(meshPolygons, meshTexture);
		}
		
		mobs.reset();
	}
	
	static public void Load(ZObject object) throws IOException
	{
		for (int i = 0; i < object.id; ++i)
		{
			objects.readLine(); // info
			
			object.physicsData.damage = GetInt(objects);
			object.physicsData.direction = GetInt(objects); 

			object.graphicsData.image.textureSize.width = 1f / GetFloat(objects);
			object.graphicsData.image.textureSize.height = 1f / GetFloat(objects);
			object.graphicsData.image.resource = GetSpriteResource(GetInt(objects));
			object.graphicsData.image.texturePosition.set(GetFloat(objects), GetFloat(objects));
			object.graphicsData.image.setMeshes(meshPolygons, meshTexture);
		}
		
		objects.reset();
	}
	
	
	//TODO delete them
	
	//static private String line;
	//static private char[] cline = new char[100];
	//static private StringBuilder sb = new StringBuilder();
	
	/*static public int GetInt(BufferedReader br)
	{
		int x = -1;
		try {
			x = Integer.parseInt(line = br.readLine());
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}
	
	static public long GetLong(BufferedReader br)
	{
		long x = -1;
		try {
			x = Long.parseLong(line = br.readLine());
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}
	
	static public float GetFloat(BufferedReader br)
	{
		float x = -1;
		try {
			GetLine(br);
			x = Float.parseFloat(line);// = br.readLine());
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}
	
	static public boolean GetBool(BufferedReader br)
	{
		int x = -1;
		try {
			x = Integer.parseInt(line = br.readLine());
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (x == 0) ? false : true;
	}*/
	
	/*static private void GetLine(BufferedReader br) throws IOException
	{
		int i = 1;

		cline[i - 1] = (char)br.read();
		while(cline[i - 1] != '\n')
		{
			cline[i] = (char)br.read();
			++i;
		}
		//line.valueOf(cline, 0, i);
	}
	
	static public int GetInt(BufferedReader br)
	{
		int x = -1;
		try {
			GetLine(br);
			x = ParseInt(cline);
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}
	
	static public long GetLong(BufferedReader br)
	{
		long x = -1;
		try {
			GetLine(br);
			x = ParseLong(cline);
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}
	
	static public float GetFloat(BufferedReader br)
	{
		float x = -1;
		try {
			GetLine(br);
			x = ParseFloat(cline);
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}
	
	static public boolean GetBool(BufferedReader br)
	{
		int x = -1;
		try {
			GetLine(br);
			x = ParseInt(cline);
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (x == 0) ? false : true;
	}*/
	
	static public int GetInt(File file)
	{
		return file.getInt();
	}
	
	static public float GetFloat(File file)
	{
		return file.getFloat();
	}
	
	static public long GetLong(File file)
	{
		return file.getLong();
	}
	
	static public boolean GetBool(File file)
	{
		return file.getBool();
	}
}
