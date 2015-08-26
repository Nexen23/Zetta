package com.example.zetta.entity;

import java.util.ArrayList;

import com.example.zetta.ZConstants;
import com.example.zetta.ZPrimitive;
import com.example.zetta.ZSettings;
import com.example.zetta.ZPrimitive.Point2f;
import com.example.zetta.ZPrimitive.Size2i;
import com.example.zetta.core.ZGame;
import com.example.zetta.core.ZGraphics;
import com.example.zetta.core.ZSequencer;
import com.example.zetta.core.ZGame.Identity;
import com.example.zetta.core.ZGraphics.Image;
import com.example.zetta.core.ZSequencer.Music;

public final class ZMap extends Identity
{
	public final class GraphicsData extends ZGraphics.GraphicsData
	{
		public Image tile, grid, playerHighlight;
		
		public GraphicsData()
		{
			super();
			tile = new Image();
			grid = new Image();
			playerHighlight = new Image();
		}
		
		public GraphicsData(GraphicsData d)
		{
			super(d);
			tile = new Image(d.tile);
			grid = new Image(d.grid);
			playerHighlight = new Image(d.playerHighlight);
		}

		@Override
		public void draw() 
		{
			tile.draw();
			grid.draw();
			playerHighlight.draw();
		}

		@Override
		public void update(long timeElapsed) 
		{
			Point2f[] playerCellsOccupied = ZGame.level.player.physicsData.cellsOccupied;
			int playerCellsOccupiedCount = ZGame.level.player.physicsData.cellsOccupiedCount;
			
			tile.setModelPositionMap();
			tile.setModelSizeHalf(size);
			tile.update(timeElapsed);
			
			//TODO rewrite it?
			playerHighlight.setModelPosition(playerCellsOccupied[0].y, playerCellsOccupied[0].x);
			if (playerCellsOccupiedCount == ZConstants.CREATURE_CELLS_OCCUPIED_MAX)
			{
				playerHighlight.modelPosition.add(ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2, 
						ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
				playerHighlight.textureSize.set(2, 2);
				playerHighlight.setModelSizeHalf(2, 2);
			}
			else
				if (playerCellsOccupiedCount == 1)
				{
					playerHighlight.textureSize.set(1, 1);
					playerHighlight.setModelSizeHalf(1, 1);
				}
				else
				{
					if (ZPrimitive.Compare(playerCellsOccupied[0].y, playerCellsOccupied[1].y) == true)
					{
						playerHighlight.modelPosition.add(0f, 
								ZSettings.MAP_CELL_SIZE_RELATIVE.height / 2);
						playerHighlight.textureSize.set(1, 2);
						playerHighlight.setModelSizeHalf(1, 2);
					}
					else
					{
						playerHighlight.modelPosition.add(ZSettings.MAP_CELL_SIZE_RELATIVE.width / 2, 
								0f);
						playerHighlight.textureSize.set(2, 1);
						playerHighlight.setModelSizeHalf(2, 1);
					}
				}
			playerHighlight.update(timeElapsed);
			
			grid.setModelPositionMap();
			grid.setModelSizeHalf(size);
			grid.update(timeElapsed);
		}
	}
	
	public final class SequencerData extends ZSequencer.SequencerData
	{
		public Music music;
		
		public SequencerData()
		{
			super();
			music = new Music();
		}
		
		public SequencerData(Music _music)
		{
			super();
			music = new Music(_music);
		}
		
		public SequencerData(SequencerData d)
		{
			super(d);
			this.music = new Music(d.music);
		}
	}
	
	public int coinsCount, score, buff = ZConstants.IDENTITY_ID_INVALID, 
		itemsCount, objectsCount, machineriesCount, mobsCount, effectsCount;
	public Size2i size;
	public ZCell[][] cells;
	public ArrayList<ZItem> items;
	public ArrayList<ZObject> objects;
	public ArrayList<ZMachinery> machineries;
	public ArrayList<ZMob> mobs;
	public ArrayList<ZEffect> effects;
	public GraphicsData graphicsData;
	public SequencerData sequencerData;
	
	public ZMap()
	{	
		super();
		size = new Size2i();
		items = new ArrayList<ZItem>();
		objects = new ArrayList<ZObject>();
		machineries = new ArrayList<ZMachinery>();
		mobs = new ArrayList<ZMob>();
		effects = new ArrayList<ZEffect>();
		graphicsData = new GraphicsData();
		sequencerData = new SequencerData();
	}
	
	public ZMap(int id)
	{	
		super(id);
		size = new Size2i();
		items = new ArrayList<ZItem>();
		objects = new ArrayList<ZObject>();
		machineries = new ArrayList<ZMachinery>();
		mobs = new ArrayList<ZMob>();
		effects = new ArrayList<ZEffect>();
		graphicsData = new GraphicsData();
		sequencerData = new SequencerData();
	}
	
	public ZMap(ZMap map)
	{
		super(map);
		int i, j;
		size = new Size2i(map.size);
		buff = map.buff;
		score = map.score;
		coinsCount = map.coinsCount;
		itemsCount = map.itemsCount;
		objectsCount = map.objectsCount;
		machineriesCount = map.machineriesCount;
		mobsCount = map.mobsCount;
		effectsCount = map.effectsCount;
		
		cells = new ZCell[size.height][size.width];
		for (i = 0; i < size.height; ++i)
		{
			for (j = 0; j < size.width; ++j)
			{
				cells[i][j] = new ZCell(map.cells[i][j]);
			}
		}
		
		items = new ArrayList<ZItem>(itemsCount);
		for (i = 0; i < itemsCount; ++i)
		{
			items.set(i, map.items.get(i));
		}
		
		objects = new ArrayList<ZObject>(objectsCount);
		for (i = 0; i < objectsCount; ++i)
		{
			objects.set(i, map.objects.get(i));
		}
		
		machineries = new ArrayList<ZMachinery>(machineriesCount);
		for (i = 0; i < machineriesCount; ++i)
		{
			machineries.set(i, map.machineries.get(i));
		}
		
		mobs = new ArrayList<ZMob>(mobsCount);
		for (i = 0; i < mobsCount; ++i)
		{
			mobs.set(i, map.mobs.get(i));
		}
		
		effects = new ArrayList<ZEffect>(effectsCount);
		for (i = 0; i < effectsCount; ++i)
		{
			effects.set(i, map.effects.get(i));
		}
		
		graphicsData = new GraphicsData(map.graphicsData);
		sequencerData = new SequencerData(map.sequencerData);
	}

	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
		
	}
}
