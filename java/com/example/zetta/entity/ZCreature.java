package com.example.zetta.entity;

import com.example.zetta.ZConstants;
import com.example.zetta.ZPrimitive.Point2f;
import com.example.zetta.ZPrimitive.Size2f;
import com.example.zetta.ZPrimitive.Vector2f;
import com.example.zetta.core.ZGame;
import com.example.zetta.core.ZGraphics;
import com.example.zetta.core.ZGraphics.IteratableAnimation;
import com.example.zetta.core.ZPhysics;
import com.example.zetta.core.ZGame.Identity;

public abstract class ZCreature extends Identity
{
	public abstract class GraphicsData extends ZGraphics.GraphicsData
	{
		public IteratableAnimation animationOnMoving;
		
		public GraphicsData()
		{
			super();
			animationOnMoving = new IteratableAnimation();
		}
		
		public GraphicsData(IteratableAnimation _animationOnMoving)
		{
			super();
			animationOnMoving = new IteratableAnimation(_animationOnMoving);
		}
		
		public GraphicsData(GraphicsData d)
		{
			super(d);
			animationOnMoving = new IteratableAnimation(d.animationOnMoving);
		}
	}

	public abstract class PhysicsData extends ZPhysics.PhysicsData
	{
		public int health, direction = ZConstants.DIRECTION_INVALID;
		public float speed;
		public boolean isMoving = false;
		public Vector2f movementVector;
		public Point2f[] cellsOccupied;
		public Point2f positionPrevious;
		public Size2f sizeHalf;
		public int cellsOccupiedCount;
		
		public PhysicsData()
		{
			super();	
			sizeHalf = new Size2f();
			movementVector = new Vector2f();
			positionPrevious = new Point2f();
			cellsOccupied = new Point2f[ZConstants.CREATURE_CELLS_OCCUPIED_MAX];
			for (int i = 0, n = cellsOccupied.length; i < n; ++i)
			{
				cellsOccupied[i] = new Point2f();
			}
			cellsOccupiedCount = 0;
		}
		
		public PhysicsData(PhysicsData d)
		{
			super(d);
			health = d.health;
			speed = d.speed;
			direction = d.direction;
			sizeHalf = new Size2f(d.sizeHalf);
			movementVector = new Vector2f(d.movementVector);
			positionPrevious = new Point2f(d.positionPrevious);
			isMoving = d.isMoving;
			
			cellsOccupied = new Point2f[ZConstants.CREATURE_CELLS_OCCUPIED_MAX];
			cellsOccupiedCount = d.cellsOccupiedCount;
			for (int i = 0, n = cellsOccupied.length; i < n; ++i)
			{
				cellsOccupied[i] = new Point2f(d.cellsOccupied[i]);
			}
		}
		
		/*public void setCellsOccupied()
		{
			cellsOccupiedCount = 1;
			cellsOccupied[0].set((float)(int)position.y, (float)(int)position.x);
			if (Float.compare(cellsOccupied[0].x, position.y) != 0 ||
					Float.compare(cellsOccupied[0].y, position.x) != 0)
			{
				cellsOccupiedCount = 2;
				cellsOccupied[1].set((float)Math.ceil((double)position.y), 
						(float)Math.ceil((double)position.x));
				if(Float.compare(cellsOccupied[0].x, cellsOccupied[1].x) != 0 &&
						Float.compare(cellsOccupied[0].y, cellsOccupied[1].y) != 0)
				{
					cellsOccupiedCount = 4;
					cellsOccupied[2].set(cellsOccupied[0].x, cellsOccupied[1].y);
					cellsOccupied[3].set(cellsOccupied[1].x, cellsOccupied[0].y);
				}
			}
		}*/ //TODO: I have new method
		
		public void setCellsOccupied()
		{		
			//TODO wtf..?
			cellsOccupied[0].set((float)(int)position.y, (float)(int)position.x);
			cellsOccupied[1].set(cellsOccupied[0]); cellsOccupied[1].add(1, 1);
			cellsOccupied[2].set(cellsOccupied[0]); cellsOccupied[2].add(1, 0);
			cellsOccupied[3].set(cellsOccupied[0]); cellsOccupied[3].add(0, 1);
			//TODO ������� ���-������ � 0.5f - s������ ������ ������������
			
			if (ZGame.level != null)
			{
				if ((int)cellsOccupied[1].x != ZGame.level.map.size.height &&
						(int)cellsOccupied[1].y != ZGame.level.map.size.width)
				{
					if (0.5f + sizeHalf.width > Math.abs(position.x - cellsOccupied[0].y))
					{
						if (0.5f + sizeHalf.height <= Math.abs(position.y - cellsOccupied[0].x))
						{
							cellsOccupied[0].set(cellsOccupied[2]);
							cellsOccupiedCount = 1;
							if (0.5f + sizeHalf.width > Math.abs(position.x - cellsOccupied[1].y))
							{
								//cellsOccupied[1].set(cellsOccupied[1]);
								cellsOccupiedCount = 2;
							}
						}
						else
						{
							//cellsOccupied[0].set(cellsOccupied[0]);
							cellsOccupiedCount = 1;
							if (0.5f + sizeHalf.width > Math.abs(position.x - cellsOccupied[1].y))
							{
								if (0.5f + sizeHalf.height > Math.abs(position.y - cellsOccupied[1].x))
								{
									//cellsOccupied[1].set(cellsOccupied[1]);
									//cellsOccupied[2].set(cellsOccupied[2]);
									//cellsOccupied[3].set(cellsOccupied[3]);
									cellsOccupiedCount = 4;
								}
								else
								{
									cellsOccupied[1].set(cellsOccupied[3]);
									cellsOccupiedCount = 2;
								}
							}
							else
							{
								if (0.5f + sizeHalf.height > Math.abs(position.y - cellsOccupied[2].x))
								{
									cellsOccupied[1].set(cellsOccupied[2]);
									cellsOccupiedCount = 2;
								}
							}
						}
					}
					else
					{			
						if (0.5f + sizeHalf.height > Math.abs(position.y - cellsOccupied[3].x))
						{
							cellsOccupied[0].set(cellsOccupied[3]);
							cellsOccupiedCount = 1;
							if (0.5f + sizeHalf.height > Math.abs(position.y - cellsOccupied[1].x))
							{
								cellsOccupied[1].set(cellsOccupied[1]);
								cellsOccupiedCount = 2;
							}
						}
						else
						{
							cellsOccupied[0].set(cellsOccupied[1]);
							cellsOccupiedCount = 1;
						}
					}
				}
				else
				{
					if ((int)cellsOccupied[1].y != ZGame.level.map.size.width)
					{
						if (0.5f + sizeHalf.width > Math.abs(position.x - cellsOccupied[0].y))
						{
							//cellsOccupied[0].set(cellsOccupied[0]);
							cellsOccupiedCount = 1;
							if (0.5f + sizeHalf.width > Math.abs(position.x - cellsOccupied[3].y))
							{
								cellsOccupied[1].set(cellsOccupied[3]);
								cellsOccupiedCount = 2;
							}
						}
						else
						{
							cellsOccupied[0].set(cellsOccupied[3]);
							cellsOccupiedCount = 1;
						}
					}
					else
						if ((int)cellsOccupied[1].x != ZGame.level.map.size.height)
						{
							if (0.5f + sizeHalf.height > Math.abs(position.y - cellsOccupied[0].x))
							{
								//cellsOccupied[0].set(cellsOccupied[0]);
								cellsOccupiedCount = 1;
								if (0.5f + sizeHalf.height > Math.abs(position.y - cellsOccupied[2].x))
								{
									cellsOccupied[1].set(cellsOccupied[2]);
									cellsOccupiedCount = 2;
								}
							}
							else
							{
								cellsOccupied[0].set(cellsOccupied[2]);
								cellsOccupiedCount = 1;
							}
						}
						else
						{
							//cellsOccupied[0].set(cellsOccupied[0]);
							cellsOccupiedCount = 1;
						}
				}
			}
		}
		
		public void setPosition(float x, float y)
		{
			//TODO rewrite them
			positionPrevious.set(position);
			position.set(x, y);
			setCellsOccupied();
		}
		
		public void setPosition(Point2f p)
		{
			//TODO rewrite them
			positionPrevious.set(position);
			position.set(p);
			setCellsOccupied();
		}
		
		//TODO: movePosition/reducePosition!
	}
	
	public ZCreature()
	{
		super();
	}
	
	public ZCreature(int id)
	{
		super(id);
	}
	
	public ZCreature(ZCreature c)
	{
		super(c);
	}
}
