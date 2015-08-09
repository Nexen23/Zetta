package com.example.zetta.entity;

import java.util.ArrayList;

import com.example.zetta.ZConstants;
import com.example.zetta.core.ZGame;
import com.example.zetta.core.ZLogic;
import com.example.zetta.core.ZTouchScreen;

public final class ZPlayer extends ZCreature 
{
	public final class GraphicsData extends ZCreature.GraphicsData
	{
		public GraphicsData()
		{
			super();
		}
		
		public GraphicsData(GraphicsData d)
		{
			super(d);
		}

		@Override
		public void draw() 
		{
			animationOnMoving.draw();
		}

		@Override
		public void update(long timeElapsed) 
		{
			if (physicsData.isMoving == false)
			{
				animationOnMoving.zeroStep();
			}
			animationOnMoving.setPlaying(physicsData.isMoving);
			animationOnMoving.setState(physicsData.direction);
			animationOnMoving.setModelPosition(physicsData.position);
			animationOnMoving.setModelSizeHalfMapCell();
			animationOnMoving.update(timeElapsed);
		}
	}

	public final class PhysicsData extends ZCreature.PhysicsData
	{
		public boolean hasInertial;
		
		public PhysicsData()
		{
			super();
		}
		
		public PhysicsData(boolean _isInertial)
		{
			super();
			hasInertial = _isInertial;
		}
		
		public PhysicsData(PhysicsData d)
		{
			super(d);
			hasInertial = d.hasInertial;
		}

		@Override
		public void update(long timeElapsed) 
		{
			int i, n;
			ZMob mob;
			ZCell cell;
			
			for (i = 0, n = buffs.size(); i < n; ++i)
			{
				buffs.get(i).physicsData.update(timeElapsed);
			}
			
			isMoving = ZTouchScreen.GetIsPressing();
			direction = ZTouchScreen.GetDirection();			
			
			if (isMoving == true)
			{
				movementVector.set(ZConstants.DIRECTION_UNIT_VECTORS[direction]);
				movementVector.multiply(speed * timeElapsed);
				setPosition(movementVector.x + position.x,
						movementVector.y + position.y);
				
				if (position.x - (sizeHalf.width - 0.5f) < 0)
				{
					position.x = (sizeHalf.width - 0.5f) + ZConstants.MAP_CELL_PRECISION;
				}
				else //////TODO :: 0.5f!!!!!!!
					if (position.x - (0.5f - sizeHalf.width) > (float)ZGame.level.map.size.width - 1)
					{
						position.x = (float)ZGame.level.map.size.width - 1 + (0.5f - sizeHalf.width) - ZConstants.MAP_CELL_PRECISION;
					}
				if (position.y - (sizeHalf.height - 0.5f) < 0)
				{
					position.y = (sizeHalf.height - 0.5f) + ZConstants.MAP_CELL_PRECISION;
				}
				else
					if (position.y - (0.5f - sizeHalf.height) > (float)ZGame.level.map.size.height - 1)
					{
						position.y = (float)ZGame.level.map.size.height - 1 + (0.5f - sizeHalf.height) - ZConstants.MAP_CELL_PRECISION;
					}
				setCellsOccupied();
				
				//TODO: rewrite object collision
				/*for (i = 0, n = cellsOccupiedCount; i < n && 
					ZGame.level.map.isDestroyed == false; ++i)
				{
					cell = ZGame.level.map.cells[(int)cellsOccupied[i].x][(int)cellsOccupied[i].y];		
					if (cell.object != null && cell.object.isDestroyed == false)
					{
						ZLogic.OnCollision(cell.object);
						switch(direction)
						{
						case ZConstants.DIRECTION_UP:
							position.set(position.x, cellsOccupied[i].x - (0.5f + size.height));
							break;
							
						case ZConstants.DIRECTION_DOWN:
							position.set(position.x, cellsOccupied[i].x + (0.5f + size.height));
							break; //////TODO :: 0.5f!!!!!!!
							
						case ZConstants.DIRECTION_RIGHT:
							position.set(cellsOccupied[i].y - (0.5f + size.width), position.y);
							break;
							
						case ZConstants.DIRECTION_LEFT:
							position.set(cellsOccupied[i].y + (0.5f + size.width), position.y);
							break;
						}
						setCellsOccupied();
						break;
					}
				}*/
				
				/////TODO wtf collision?
				boolean wasEnclosed = false;
				for (i = 0; i < cellsOccupiedCount && 
					ZGame.level.map.isDestroyed == false; ++i)
				{
					cell = ZGame.level.map.cells[(int)cellsOccupied[i].x][(int)cellsOccupied[i].y];		
					if (cell.object != null && cell.object.isDestroyed == false)
					{
						/*if (0.5f + size.width <= Math.abs(position.x - cell.object.physicsData.position.x) &&
								0.5f + size.height <= Math.abs(position.y - cell.object.physicsData.position.y))
						{
							--cellsOccupiedCount;
							cellsOccupied[i].set(cellsOccupied[cellsOccupiedCount]);
						}
						else
						{*/
						ZLogic.OnCollision(cell.object);
						switch(direction)
						{
						case ZConstants.DIRECTION_UP:
							position.set(position.x, cellsOccupied[i].x - (0.5f + sizeHalf.height) - ZConstants.MAP_CELL_PRECISION);
							break;
							
						case ZConstants.DIRECTION_DOWN:
							position.set(position.x, cellsOccupied[i].x + (0.5f + sizeHalf.height) + ZConstants.MAP_CELL_PRECISION);
							break; //////TODO :: 0.5f!!!!!!!
							
						case ZConstants.DIRECTION_RIGHT:
							position.set(cellsOccupied[i].y - (0.5f + sizeHalf.width) - ZConstants.MAP_CELL_PRECISION, position.y);
							break;
							
						case ZConstants.DIRECTION_LEFT:
							position.set(cellsOccupied[i].y + (0.5f + sizeHalf.width) + ZConstants.MAP_CELL_PRECISION, position.y);
							break;
						}
						setCellsOccupied();
						wasEnclosed = true;
						break;
					}
				}
				
				//help to pass objects
				if (wasEnclosed == true && cellsOccupiedCount == 2)
				{
					long timeElapsedAfterMove = timeElapsed - 
						(long)((Math.abs(position.x - positionPrevious.x) +
						Math.abs(position.y - positionPrevious.y)) / speed);
					for (i = 0; i < cellsOccupiedCount && 
						ZGame.level.map.isDestroyed == false; ++i)
					{
						switch(direction)
						{
						case ZConstants.DIRECTION_UP:
							cell = ZGame.level.map.cells[(int)cellsOccupied[i].x + 1][(int)cellsOccupied[i].y];	
							break;
							
						case ZConstants.DIRECTION_DOWN:
							cell = ZGame.level.map.cells[(int)cellsOccupied[i].x - 1][(int)cellsOccupied[i].y];	
							break; //////TODO :: 0.5f!!!!!!!
							
						case ZConstants.DIRECTION_RIGHT:
							cell = ZGame.level.map.cells[(int)cellsOccupied[i].x][(int)cellsOccupied[i].y + 1];	
							break;
							
						case ZConstants.DIRECTION_LEFT:
							cell = ZGame.level.map.cells[(int)cellsOccupied[i].x][(int)cellsOccupied[i].y - 1];	
							break;
							
						default: 
							cell = null;
							break;
						}
						
						if ((cell.object == null || cell.object.isDestroyed == true) &&
								(cell.machinery == null || cell.machinery.isDestroyed == true))
						{
							if (direction == ZConstants.DIRECTION_UP ||
									direction == ZConstants.DIRECTION_DOWN)
								if (cellsOccupied[i].y < position.x)
								{
									movementVector.set(ZConstants.DIRECTION_UNIT_VECTORS[ZConstants.DIRECTION_LEFT]);
									movementVector.multiply(speed * timeElapsedAfterMove);
									position.set(movementVector.x + position.x,
											movementVector.y + position.y);
									if (position.x < cellsOccupied[i].y + 0.5f - sizeHalf.width)
									{
										position.x = cellsOccupied[i].y + 0.5f - sizeHalf.width
											- ZConstants.MAP_CELL_PRECISION;
									}
									else
									{
										direction = ZConstants.DIRECTION_LEFT;
									}
								}
								else
								{
									movementVector.set(ZConstants.DIRECTION_UNIT_VECTORS[ZConstants.DIRECTION_RIGHT]);
									movementVector.multiply(speed * timeElapsedAfterMove);
									position.set(movementVector.x + position.x,
											movementVector.y + position.y);
									if (position.x > cellsOccupied[i].y - 0.5f + sizeHalf.width)
									{
										position.x = cellsOccupied[i].y - 0.5f + sizeHalf.width
											+ ZConstants.MAP_CELL_PRECISION;
									}
									else
									{
										direction = ZConstants.DIRECTION_RIGHT;
									}
								}
							else //(direction == ZConstants.DIRECTION_LEFT ||
								//direction == ZConstants.DIRECTION_RIGHT)
							{
								if (cellsOccupied[i].x < position.y)
								{
									movementVector.set(ZConstants.DIRECTION_UNIT_VECTORS[ZConstants.DIRECTION_DOWN]);
									movementVector.multiply(speed * timeElapsedAfterMove);
									position.set(movementVector.x + position.x,
											movementVector.y + position.y);
									if (position.y < cellsOccupied[i].x + 0.5f - sizeHalf.height)
									{
										position.y = cellsOccupied[i].x + 0.5f - sizeHalf.height
											- ZConstants.MAP_CELL_PRECISION;
									}
									else
									{
										direction = ZConstants.DIRECTION_DOWN;
									}
								}
								else
								{
									movementVector.set(ZConstants.DIRECTION_UNIT_VECTORS[ZConstants.DIRECTION_UP]);
									movementVector.multiply(speed * timeElapsedAfterMove);
									position.set(movementVector.x + position.x,
											movementVector.y + position.y);
									if (position.y > cellsOccupied[i].x - 0.5f + sizeHalf.width)
									{
										position.y = cellsOccupied[i].x - 0.5f + sizeHalf.width
											+ ZConstants.MAP_CELL_PRECISION;
									}
									else
									{
										direction = ZConstants.DIRECTION_UP;
									}
								}
							}
							setCellsOccupied();
							break;
						}
					}
				}
				////

				for (i = 0, n = ZGame.level.map.mobsCount; i < n &&
					ZGame.level.map.isDestroyed == false; ++i)
				{
					mob = ZGame.level.map.mobs.get(i);
					if (mob.isDestroyed == false)
					{
						if (position.distance(mob.physicsData.position) < 1f) //TODO 1f
						{
							ZLogic.OnCollision(mob);
							if (mob.physicsData.isHostile == true)
							{
								health -= mob.physicsData.damage;
								if (health <= 0)
								{
									ZGame.level.state = ZConstants.GAME_STATE_FAILED;
								}
							}
						}
					}
				}
				
				for (i = 0, n = cellsOccupiedCount; i < n && 
					ZGame.level.map.isDestroyed == false; ++i)
				{
					cell = ZGame.level.map.cells[(int)cellsOccupied[i].x][(int)cellsOccupied[i].y];
					
					if (cell.item != null && cell.item.isDestroyed == false)
					{
						ZLogic.OnCollision(cell.item);
					}
					
					if (cell.machinery != null && cell.machinery.isDestroyed == false)
					{
						ZLogic.OnCollision(cell.machinery);
						if (cell.machinery.physicsData.step != 0)
						{
							health -= cell.machinery.physicsData.damage;
							if (health <= 0)
							{
								ZGame.level.state = ZConstants.GAME_STATE_FAILED;
								ZGame.level.map.destroy();
							}
						}
					}
				}
			}
			else // isMoving == false
			{
				for (i = 0, n = cellsOccupiedCount; i < n && 
					ZGame.level.map.isDestroyed == false; ++i)
				{
					cell = ZGame.level.map.cells[(int)cellsOccupied[i].x][(int)cellsOccupied[i].y];
					if (cell.machinery != null && cell.machinery.isDestroyed == false)
					{
						ZLogic.OnCollision(cell.machinery);
						if (cell.machinery.physicsData.step != 0)
						{
							health -= cell.machinery.physicsData.damage;
							if (health <= 0)
							{
								ZGame.level.state = ZConstants.GAME_STATE_FAILED;
								ZGame.level.map.destroy();
							}
						}
					}
				}
				
				for (i = 0, n = ZGame.level.map.mobsCount; i < n &&
					ZGame.level.map.isDestroyed == false; ++i)
				{
					mob = ZGame.level.map.mobs.get(i);
					if (mob.isDestroyed == false)
					{
						if (position.distance(mob.physicsData.position) < 1f) //TODO 1f
						{
							ZLogic.OnCollision(mob);
							if (mob.physicsData.isHostile == true)
							{
								health -= mob.physicsData.damage;
								if (health <= 0)
								{
									ZGame.level.state = ZConstants.GAME_STATE_FAILED;
									ZGame.level.map.destroy();
								}
							}
						}
					}
				}
			}
		}
	}
	
	public ArrayList<ZBuff> buffs;
	public ZScore score;
	public GraphicsData graphicsData;
	public PhysicsData physicsData;
	
	public ZPlayer()
	{
		super();
		buffs = new ArrayList<ZBuff>();
		score = new ZScore();
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
	}
	
	public ZPlayer(int id)
	{
		super(id);
		buffs = new ArrayList<ZBuff>();
		score = new ZScore();
		graphicsData = new GraphicsData();
		physicsData = new PhysicsData();
	}
	
	public ZPlayer(ZPlayer p)
	{
		super(p);
		buffs = new ArrayList<ZBuff>(p.buffs);
		score = new ZScore(p.score);
		graphicsData = new GraphicsData(p.graphicsData);
		physicsData = new PhysicsData(p.physicsData);
	}

	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
		
	}
}
