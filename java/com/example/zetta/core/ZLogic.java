package com.example.zetta.core;

import com.example.zetta.ZConstants;
import com.example.zetta.ZPrimitive;
import com.example.zetta.entity.ZCell;
import com.example.zetta.entity.ZBuff;
import com.example.zetta.entity.ZEffect;
import com.example.zetta.entity.ZItem;
import com.example.zetta.entity.ZMachinery;
import com.example.zetta.entity.ZMob;
import com.example.zetta.entity.ZObject;

public final class ZLogic 
{	
	static public void SetBuff(ZBuff buff)
	{
		//TODO:
	}
	
	static public void ReleaseBuff(ZBuff buff)
	{ 
		//TODO:
	}

	static public void OnCollision(ZItem item)
	{
		int id = item.id;
		ZCell[][] cells = ZGame.level.map.cells;
		ZMob mob;
		int i, j1, j, t, n, m, k;
		ZPrimitive.Point2f position = new ZPrimitive.Point2f();
		
		if (item.buff != null)
		{
			ZLogic.SetBuff(item.buff);
		}
		item.destroy();
		
		switch(id)
		{
		case ZConstants.ITEM_ID_COIN:
			--ZGame.level.map.coinsCount;
			if (ZGame.level.map.coinsCount == 0)
			{
				ZGame.level.state = ZConstants.GAME_STATE_COMPLETED;
				ZGame.level.map.destroy();
			}
			break;
			
		case ZConstants.ITEM_ID_BOMB1:
			/*ZGraphics.explosion.modelSizeHalf.set(ZSettings.MAP_CELL_SIZE_RELATIVE.width * 3f / 2f, 
					ZSettings.MAP_CELL_SIZE_RELATIVE.height * 3f / 2f);
			ZGraphics.explosion.recalculateModelMatrix(item.physicsData.position, ZGame.level.map.graphicsData.tile.modelSizeHalf);
			ZGraphics.isExplosion = true;*/
			//TODO
			i = (int)item.physicsData.position.y - 1; j1 = (int)item.physicsData.position.x - 1;
			n = i + 3; m = j1 + 3;
			i = Math.max(i, 0); j1 = Math.max(j1, 0);
			n = Math.min(n, ZGame.level.map.size.height); m = Math.min(m, ZGame.level.map.size.width);
			for (; i < n; ++i)
			{
				for (j = j1; j < m; ++j)
				{
					if (cells[i][j].object != null)
					{
						cells[i][j].object.destroy();					
					}
					position.set((float)j, (float)i);
					for (t = 0, k = ZGame.level.map.mobsCount; t < k; ++t)
					{
						mob = ZGame.level.map.mobs.get(t);
						if (mob.isDestroyed == false &&
								position.distance(mob.physicsData.position) < 1f) //TODO 1f cellsOccupied?
						{
							mob.physicsData.health -= ZConstants.ITEM_BOMB_DAMAGE;
							if (mob.physicsData.health <= 0)
							{
								mob.destroy();
							}
						}
					}
				}
			}
			break;
		}
	}
	
	static public void OnCollision(ZMob mob)
	{
		int id = mob.id;
		switch(id)
		{
		
		}
	}
	
	static public void OnCollision(ZMachinery machinery)
	{
		int id = machinery.id;
		switch(id)
		{
		
		}
	}
	
	static public void OnCollision(ZObject object)
	{
		int id = object.id;
		switch(id)
		{
		
		}
	}
	
	static public void OnCollision(ZEffect effect)
	{
		int id = effect.id;
		switch(id)
		{
		
		}
	}
}
